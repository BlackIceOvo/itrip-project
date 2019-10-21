package com.itrip.auth.service.token;

import com.itrip.beans.dto.Dto;
import com.itrip.beans.pojo.ItripUser;

import java.io.IOException;

public interface TokenService {

    /**
     * 生成Token
     * @param user
     * @param agent
     * @return
     * @throws IOException
     */
    String genToken(ItripUser user,String agent) throws Exception;

    /**
     * 保存Token到Redis中
     * @param itripUser
     * @param token
     * @throws IOException
     */
    void saveToken(ItripUser itripUser,String token) throws Exception;


    /**
     * Token置换
     * @param token
     * @param agent
     * @return
     * @throws IOException
     */
    Dto reToken(String token,String agent) throws Exception;


    boolean delToken(String token ,String agent) throws Exception;
}
