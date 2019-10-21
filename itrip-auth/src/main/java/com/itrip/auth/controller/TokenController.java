package com.itrip.auth.controller;

import com.itrip.auth.service.token.TokenService;
import com.itrip.beans.dto.Dto;
import com.itrip.utils.common.DtoUtils;
import com.itrip.utils.common.ErrorCode;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ice
 */
@RestController
@RequestMapping("/api")
public class TokenController {

    @Autowired
    TokenService tokenService;

    /**
     * Token 置换
     * @return
     */
    @PostMapping(value = "/retoken",produces = "application/json")
    public Dto reToken(@RequestHeader HttpHeaders headers){
        try {
           return tokenService.reToken(headers.getFirst("token"),headers.getFirst("user-agent"));
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("异常!", ErrorCode.AUTH_TOKEN_INVALID);
        }
    }
}
