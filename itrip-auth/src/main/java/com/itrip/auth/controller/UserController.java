package com.itrip.auth.controller;

import com.itrip.auth.service.user.UserService;
import com.itrip.auth.service.valid.ValidService;
import com.itrip.beans.dto.Dto;
import com.itrip.beans.vo.userinfo.ItripUserVO;
import com.itrip.utils.common.DtoUtils;
import com.itrip.utils.common.EmptyUtils;
import com.itrip.utils.common.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * @author ice
 */
@RestController
@RequestMapping("/api")
public class UserController implements ValidService {


    @Autowired
    UserService userService;

    /**
     * 邮箱注册
     *
     * @param itripUserVO
     * @return
     */
    @PostMapping(value = "/doregister", produces = "application/json")
    public Dto doRegister(@RequestBody ItripUserVO itripUserVO) {
        try {
            //判断用户和密码为空
            if (EmptyUtils.isEmpty(itripUserVO.getUserCode()) || EmptyUtils.isEmpty(itripUserVO.getUserPassword())) {
                return DtoUtils.returnFail("邮箱或密码不能为空", ErrorCode.AUTH_PARAMETER_ERROR);
            }
            //邮箱格式的校验
            if (this.validEmail(itripUserVO.getUserCode())) {
                //邮箱格式正确,进行邮箱注册
                return userService.itripAddUser(itripUserVO);
            }
            return DtoUtils.returnFail("邮箱格式不正确,请重新输入", ErrorCode.AUTH_UNKNOWN);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("注册用户出现异常", ErrorCode.AUTH_ACTIVATE_FAILED);
        }
    }

    /**
     * 手机注册
     *
     * @param itripUserVO
     * @return
     */
    @PostMapping(value = "/registerbyphone",produces = "application/json")
    public Dto registerByPhone(@RequestBody ItripUserVO itripUserVO) {
        try {
            //判断用户和密码为空
            if (EmptyUtils.isEmpty(itripUserVO.getUserCode()) || EmptyUtils.isEmpty(itripUserVO.getUserPassword())) {
                return DtoUtils.returnFail("手机号或密码不能为空", ErrorCode.AUTH_PARAMETER_ERROR);
            }
            //邮箱格式的校验
            if (this.validPhone(itripUserVO.getUserCode())) {
                //验证手机号码,进行手机注册
                return userService.itripAddUser(itripUserVO);
            }
            return DtoUtils.returnFail("手机号格式不正确,请重新输入", ErrorCode.AUTH_UNKNOWN);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("注册用户出现异常", ErrorCode.AUTH_UNKNOWN);
        }
    }

    /**
     * 激活手机注册账号
     *
     * @param user
     * @param code
     * @return
     */
    @PutMapping(value = "/validatephone",produces = "application/json")
    public Dto validatePhone(String user, String code) {
        try {
            if (EmptyUtils.isEmpty(user) || EmptyUtils.isEmpty(code)) {
                return DtoUtils.returnFail("手机号或验证码不能为空", ErrorCode.AUTH_PARAMETER_ERROR);
            }
            if (this.validPhone(user)) {
                return userService.validate(user, code);
            }
            return DtoUtils.returnFail("手机号格式错误!", ErrorCode.AUTH_ACTIVATE_FAILED);
        } catch (Exception e) {
            return DtoUtils.returnFail("手机激活出现异常", ErrorCode.AUTH_ACTIVATE_FAILED);
        }
    }

    /**
     * 激活邮箱注册账号
     * @param user
     * @param code
     * @return
     */
    @PutMapping(value = "/activate",produces = "application/json")
    public Dto activate(String user, String code) {
        try {
            if (EmptyUtils.isEmpty(user) || EmptyUtils.isEmpty(code)) {
                return DtoUtils.returnFail("邮箱或验证码不能为空", ErrorCode.AUTH_PARAMETER_ERROR);
            }
            if (this.validEmail(user)) {
                return userService.validate(user, code);
            }
            return DtoUtils.returnFail("邮箱格式错误!", ErrorCode.AUTH_ACTIVATE_FAILED);
        } catch (Exception e) {
            return DtoUtils.returnFail("邮箱激活出现异常", ErrorCode.AUTH_ACTIVATE_FAILED);
        }
    }

    /**
     * 验证用户名是否存在
     * @param name
     * @return
     */
    @GetMapping(value = "/ckusr",produces = "application/json")
    public Dto ckusr(String name) {
        if (EmptyUtils.isEmpty(name)) {
            return DtoUtils.returnFail("用户名不能为空!!!", ErrorCode.AUTH_PARAMETER_ERROR);
        }
        try {
            return userService.ckusr(name);
        } catch (Exception e) {
            return DtoUtils.returnFail("异常!!!", ErrorCode.AUTH_UNKNOWN);
        }
    }
}
