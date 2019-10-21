package com.itrip.auth.service.user;

import com.itrip.beans.dto.Dto;
import com.itrip.beans.vo.userinfo.ItripUserVO;

public interface UserService {
    /**
     * 用户注册
     * @param itripUserVO
     * @return
     */
    Dto itripAddUser(ItripUserVO itripUserVO) throws Exception;

    /**
     * 激活账号
     * @param user
     * @param code
     * @return
     */
    Dto validate(String user,String code) throws Exception;

    Dto ckusr(String name) throws Exception;

}
