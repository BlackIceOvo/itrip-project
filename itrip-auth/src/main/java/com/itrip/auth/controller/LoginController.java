package com.itrip.auth.controller;

import com.itrip.auth.service.login.LoginService;
import com.itrip.beans.dto.Dto;
import com.itrip.utils.common.DtoUtils;
import com.itrip.utils.common.EmptyUtils;
import com.itrip.utils.common.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 * @author ice
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * 用户登录
     * @param name
     * @param password
     * @param request
     * @return
     */
    @PostMapping(value = "/dologin",produces = "application/json")
    public Dto doLogin(String name, String password, HttpServletRequest request){
        if(EmptyUtils.isEmpty(name) || EmptyUtils.isEmpty(password)){
            return DtoUtils.returnFail("账号和密码不能为空!", ErrorCode.AUTH_PARAMETER_ERROR);
        }
        try {
            String agent = request.getHeader("user-agent");
            return  loginService.doLogin(name,password,agent);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("异常",ErrorCode.AUTH_UNKNOWN);
        }
    }

    /**
     * 用户注销
     * @return
     */
    @GetMapping(value = "/logout",produces = "application/json")
    public Dto logout(@RequestHeader HttpHeaders headers){
        try {
            return loginService.logout(headers.getFirst("token"), headers.getFirst("user-agent"));
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("服务器异常",ErrorCode.AUTH_TOKEN_INVALID);
        }

    }
}
