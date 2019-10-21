package com.itrip.auth.service.login;

import com.itrip.beans.dto.Dto;

public interface LoginService {

    /**
     * 登陆方法
     * @param name
     * @param password
     * @param agent
     * @return
     * @throws Exception
     */
    Dto doLogin(String name ,String password,String agent) throws Exception;

    Dto logout(String token,String agent) throws  Exception;
}
