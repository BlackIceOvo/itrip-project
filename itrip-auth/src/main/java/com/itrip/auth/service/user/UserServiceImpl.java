package com.itrip.auth.service.user;

import com.itrip.auth.service.email.EmailService;
import com.itrip.auth.service.sms.SmsService;
import com.itrip.auth.service.valid.ValidService;
import com.itrip.beans.dto.Dto;
import com.itrip.beans.pojo.ItripUser;
import com.itrip.beans.vo.userinfo.ItripUserVO;
import com.itrip.dao.mapper.itripUser.ItripUserMapper;
import com.itrip.utils.common.DtoUtils;
import com.itrip.utils.common.ErrorCode;
import com.itrip.utils.common.MD5;
import com.itrip.utils.common.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author ice
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements UserService, ValidService {

    @Autowired
    private ItripUserMapper itripUserMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisUtils redisUtils;


    @Override
    public Dto itripAddUser(ItripUserVO itripUserVO) throws Exception {
        // 验证用户名是否存在
        Map<String, Object> param = new HashMap<>();
        if (itripUserVO != null) {
            param.put("userCode", itripUserVO.getUserCode());
            List<ItripUser> users = itripUserMapper.getItripUserListByMap(param);
            if (users.size() == 0) {
                ItripUser user = new ItripUser();
                //将vo 的值给 pojo
                BeanUtils.copyProperties(itripUserVO, user);
                user.setActivated(0);
                user.setUserPassword(MD5.getMd5(itripUserVO.getUserPassword(), 6));
                itripUserMapper.insertItripUser(user);
                if (this.validEmail(itripUserVO.getUserCode())) {
                    emailService.send(MD5.getMd5(new Date().toLocaleString(), 32), itripUserVO.getUserCode());
                }
                if (this.validPhone(itripUserVO.getUserCode())) {
                    smsService.send(itripUserVO.getUserCode(), "1", new String[]{String.valueOf(MD5.getRandomCode()), String.valueOf(1000 * 60)});
                }
                return DtoUtils.returnSuccess();
            } else {
                return DtoUtils.returnFail("此用户已存在,注册失败!", ErrorCode.AUTH_USER_ALREADY_EXISTS);
            }
        }
        return DtoUtils.returnFail("用户或密码不能为空", ErrorCode.AUTH_PARAMETER_ERROR);
    }

    @Override
    public Dto validate(String user, String code) throws Exception {
        boolean flag = false;
        String grop="";
        Map<String, Object> param = new HashMap<>();
        param.put("userCode", user);
        List<ItripUser> users = itripUserMapper.getItripUserListByMap(param);
        if (users.size() > 0) {
            if(users.get(0).getActivated()==0){
                if (this.validPhone(user)) {
                    grop="activePhone:";
                    flag = this.redisUtils.setExists(grop + user);
                }
                if (this.validEmail(user)) {
                    grop="activeEmail:";
                    flag = this.redisUtils.setExists(grop + user);
                }
                if (flag) {
                    if (code.equals(redisUtils.getValue(grop+user))) {
                        ItripUser itripUser = users.get(0);
                        itripUser.setActivated(1);
                        itripUser.setUserType(0);
                        itripUserMapper.updateItripUser(itripUser);
                        redisUtils.del(grop+user);
                        return DtoUtils.returnSuccess();
                    } else {
                        return DtoUtils.returnFail("验证码错误!", ErrorCode.AUTH_ACTIVATE_FAILED);
                    }
                }
                return DtoUtils.returnFail("格式错误!!", ErrorCode.AUTH_ACTIVATE_FAILED);
            }
            return DtoUtils.returnFail("账号已经被激活!!", ErrorCode.AUTH_USER_ALREADY_EXISTS);
        }
        return DtoUtils.returnFail("该用户不存在!", ErrorCode.AUTH_ACTIVATE_FAILED);
    }

    @Override
    public Dto ckusr(String name) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("userCode", name);
        List<ItripUser> users = itripUserMapper.getItripUserListByMap(param);
        if(users.size()==0){
            return  DtoUtils.returnSuccess("用户名可用!");
        }
        return DtoUtils.returnFail("用户已存在，注册失败",ErrorCode.AUTH_USER_ALREADY_EXISTS);
    }

}
