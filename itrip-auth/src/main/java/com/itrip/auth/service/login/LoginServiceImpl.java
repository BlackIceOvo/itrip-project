package com.itrip.auth.service.login;

import com.itrip.auth.service.token.TokenService;
import com.itrip.beans.dto.Dto;
import com.itrip.beans.pojo.ItripUser;
import com.itrip.beans.vo.ItripTokenVO;
import com.itrip.dao.mapper.itripUser.ItripUserMapper;
import com.itrip.utils.common.DtoUtils;
import com.itrip.utils.common.ErrorCode;
import com.itrip.utils.common.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ice
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class LoginServiceImpl implements LoginService {

    @Autowired
    ItripUserMapper itripUserMapper;

    @Autowired
    TokenService tokenService;

    @Override
    public Dto doLogin(String name, String password, String agent) throws Exception {
        Map map = new HashMap();
        map.put("userCode", name);
        List<ItripUser> users = itripUserMapper.getItripUserListByMap(map);
        if (users.size() > 0) {
            ItripUser itripUser = users.get(0);
            if (itripUser.getActivated() == 1) {
                if(itripUser.getUserPassword().equals(MD5.getMd5(password,6))){
                    String token=tokenService.genToken(itripUser,agent);
                    tokenService.saveToken(itripUser,token);
                    Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(token.split("-")[3]);
                    ItripTokenVO tokenVO=new ItripTokenVO(token,date.getTime()+2*60*60*1000,date.getTime());
                    return DtoUtils.returnDataSuccess(tokenVO);
                }
                return DtoUtils.returnFail("密码错误!", ErrorCode.AUTH_UNKNOWN);
            }
        }
        return DtoUtils.returnFail("用户名不存在!", ErrorCode.AUTH_UNKNOWN);
    }

    @Override
    public Dto logout(String token, String agent) throws Exception {
        return tokenService.delToken(token,agent)?DtoUtils.returnSuccess("注销成功"):DtoUtils.returnFail("Token 错误 或 不存在!",ErrorCode.AUTH_TOKEN_INVALID);
    }
}
