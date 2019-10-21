package com.itrip.auth.service.token;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itrip.beans.dto.Dto;
import com.itrip.beans.pojo.ItripUser;
import com.itrip.beans.vo.ItripTokenVO;
import com.itrip.utils.common.*;
import cz.mallat.uasparser.UserAgentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class TokenServiceImpl implements TokenService {


    @Autowired
    RedisUtils redisUtils;

    @Autowired
    ValidationToken validationToken;
    /**
     *
     * 生成Token
     *
     * @param user
     * @param agent
     * @return 格式token:PC-ed6e201becad0e79ae04178e519fd13b-29-20181210145848-c91c25
     * 前缀：token:
     * 设备类型：PC/MOBILE
     * 用户名（md5 加密 32）
     * 用户id:23
     * tokens生成的时间：20170531133947
     * 后6位数（通过agent生成）：4f6496
     */
    @Override
    public String genToken(ItripUser user, String agent) throws IOException {
        UserAgentInfo agentInfo = UserAgentUtil.getUasParser().parse(agent);
        StringBuilder sb = new StringBuilder("token:");
        String deviced = agentInfo.getDeviceType();
        switch (deviced) {
            case "Personal computer":
            case UserAgentInfo.UNKNOWN:
                sb.append("PC-");
                break;
            default:
                if (UserAgentUtil.CheckAgent(agent)) {
                    sb.append("MOBILE-");
                } else {
                    sb.append("PC-");
                }
                break;
        }
        sb.append(MD5.getMd5(user.getUserCode(), 32) + "-");
        sb.append(user.getId() + "-");
        sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-");
        sb.append(MD5.getMd5(agent, 6));
        return sb.toString();
    }

    /**
     * 保存token
     * @param itripUser
     * @param token
     */
    @Override
    public void saveToken(ItripUser itripUser, String token) {
        if (token.indexOf("token:PC") > -1) {
            redisUtils.setExpire(token, JSON.toJSONString(itripUser), 2 * 60 * 60);
        } else {
            redisUtils.setExpire(token, JSON.toJSONString(itripUser), 2 * 60 * 60 * 24);
        }
    }

    /**
     * token交换
     * @param token
     * @param agent
     * @return
     * @throws Exception
     */
    @Override
    public Dto reToken(String token, String agent) throws Exception {
        if (redisUtils.setExists(token)) {
            //通过旧token获得对象
            String tokenJson = redisUtils.getValue(token);
            // 判断token时间
            if (redisUtils.getExpire(token) < 60 * 5) {
                JSONObject jsonObject = JSONObject.parseObject(tokenJson);
                //类型转换 通过旧Token 获取对象 并且创建对象赋值给新对象
                ItripUser itripUser = JSON.toJavaObject(jsonObject, ItripUser.class);
//                // old token 续命
                redisUtils.setExpire(token, tokenJson, 60*5);
                //新token诞生
                String newsToken = genToken(itripUser, agent);
                // 保存新Token
                this.saveToken(itripUser, newsToken);
                Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(newsToken.split("-")[3]);
                return DtoUtils.returnDataSuccess(new ItripTokenVO(newsToken, date.getTime() + 2 * 60 * 60 * 1000, date.getTime()));
            }
            return DtoUtils.returnFail("Token 目前还处于保护状态!!!", ErrorCode.AUTH_TOKEN_INVALID);
        }
        return DtoUtils.returnFail("Token不存在!!!", ErrorCode.AUTH_TOKEN_INVALID);
    }

    @Override
    public boolean delToken(String token, String agent) throws Exception {
        boolean valiate = validationToken.valiate(agent, token);
        if (valiate){
            redisUtils.del(token);
            return true;
        }
        return false;
    }
}
