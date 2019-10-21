package com.itrip.biz.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itrip.beans.dto.Dto;
import com.itrip.beans.pojo.ItripUser;
import com.itrip.beans.pojo.ItripUserLinkUser;
import com.itrip.beans.vo.userinfo.ItripAddUserLinkUserVO;
import com.itrip.beans.vo.userinfo.ItripModifyUserLinkUserVO;
import com.itrip.beans.vo.userinfo.ItripSearchUserLinkUserVO;
import com.itrip.biz.service.itripOrderLinkUser.ItripOrderLinkUserService;
import com.itrip.biz.service.itripUser.ItripUserService;
import com.itrip.biz.service.itripUserLinkUser.ItripUserLinkUserService;
import com.itrip.utils.common.DtoUtils;
import com.itrip.utils.common.EmptyUtils;
import com.itrip.utils.common.RedisUtils;
import com.itrip.utils.common.ValidationToken;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/userinfo")
public class UserInfoController {

    @Autowired
    private ItripUserLinkUserService itripUserLinkUserService;

    @Autowired
    private ValidationToken validationToken;


    @Autowired
    private RedisUtils redisUtils;

    /**
     * 添加联系人
     *
     * @param itripAddUserLinkUserVO
     * @param httpHeaders
     * @return
     */
    @PostMapping(value = "/adduserlinkuser", produces = "application/json")
    public Dto addUserLinkUser(@RequestBody ItripAddUserLinkUserVO itripAddUserLinkUserVO, @RequestHeader HttpHeaders httpHeaders) {
        try {
            if (EmptyUtils.isEmpty(httpHeaders.getFirst("user-agent")) || EmptyUtils.isEmpty(httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            } else if (!validationToken.valiate(httpHeaders.getFirst("user-agent"), httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            }
            if (EmptyUtils.isEmpty(itripAddUserLinkUserVO.getLinkIdCardType()) || EmptyUtils.isEmpty(itripAddUserLinkUserVO.getLinkPhone()) || EmptyUtils.isEmpty(itripAddUserLinkUserVO.getLinkUserName()) || EmptyUtils.isEmpty(itripAddUserLinkUserVO.getLinkIdCard())) {
                return DtoUtils.returnFail("不能提交空，请填写常用联系人信息", "100412 ");
            }
            ItripUserLinkUser linkUser = new ItripUserLinkUser();
            BeanUtils.copyProperties(itripAddUserLinkUserVO, linkUser);
            String json = redisUtils.getValue(httpHeaders.getFirst("token"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            ItripUser itripUser = JSON.toJavaObject(jsonObject, ItripUser.class);
            linkUser.setUserId(new Long(itripUser.getId()).intValue());
            linkUser.setCreatedBy(itripUser.getId());
            itripUserLinkUserService.itriptxAddItripUserLinkUser(linkUser);
            return DtoUtils.returnSuccess("新增常用联系人成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("新增常用联系人失败", "100411");
        }
    }

    /**
     * 查询联系人
     *
     * @param itripSearchUserLinkUserVO
     * @param httpHeaders
     * @return
     */
    @PostMapping(value = "/queryuserlinkuser", produces = "application/json")
    public Dto queryUserLinkUser(@RequestBody ItripSearchUserLinkUserVO itripSearchUserLinkUserVO, @RequestHeader HttpHeaders httpHeaders) {
        HashMap hashMap = new HashMap();
        try {
            if (EmptyUtils.isNotEmpty(itripSearchUserLinkUserVO.getLinkUserName())) {
                hashMap.put("linkUserName", itripSearchUserLinkUserVO.getLinkUserName());
            }
            if (EmptyUtils.isEmpty(httpHeaders.getFirst("user-agent")) || EmptyUtils.isEmpty(httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            } else if (!validationToken.valiate(httpHeaders.getFirst("user-agent"), httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            }
            String json = redisUtils.getValue(httpHeaders.getFirst("token"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            ItripUser itripUser = JSON.toJavaObject(jsonObject, ItripUser.class);
            hashMap.put("userId", itripUser.getId());
            List<ItripUserLinkUser> itripUserLinkUserListByMap = itripUserLinkUserService.getItripUserLinkUserListByMap(hashMap);
            return DtoUtils.returnSuccess("获取联系人信息成功", itripUserLinkUserListByMap);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("获取常用联系人信息失败", "100401");
        }
    }


    /**
     * 修改联系人
     *
     * @param itripModifyUserLinkUserVO
     * @param httpHeaders
     * @return
     */
    @PostMapping(value = "/modifyuserlinkuser", produces = "application/json")
    public Dto modifyUserLinkUser(@RequestBody ItripModifyUserLinkUserVO itripModifyUserLinkUserVO, @RequestHeader HttpHeaders httpHeaders) {
        try {
            if (EmptyUtils.isEmpty(httpHeaders.getFirst("user-agent")) || EmptyUtils.isEmpty(httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            } else if (!validationToken.valiate(httpHeaders.getFirst("user-agent"), httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            }
            if (EmptyUtils.isEmpty(itripModifyUserLinkUserVO.getId())) {
                return DtoUtils.returnFail("不能提交空，请填写常用联系人信息", "100422");
            }
            ItripUserLinkUser itripUserLinkUser = new ItripUserLinkUser();
            BeanUtils.copyProperties(itripModifyUserLinkUserVO, itripUserLinkUser);
            itripUserLinkUserService.itriptxModifyItripUserLinkUser(itripUserLinkUser);
            return DtoUtils.returnSuccess("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("修改常用联系人失败", "100421");
        }
    }

    /**
     * 删除联系人
     * @param ids
     * @param httpHeaders
     * @return
     */
    @GetMapping(value = "/deluserlinkuser", produces = "application/json")
    public Dto deluserlinkuser(@RequestParam("ids") Integer[] ids, @RequestHeader HttpHeaders httpHeaders) {
        try {
            if (EmptyUtils.isEmpty(httpHeaders.getFirst("user-agent")) || EmptyUtils.isEmpty(httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            } else if (!validationToken.valiate(httpHeaders.getFirst("user-agent"), httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            }
            if (EmptyUtils.isEmpty(ids)) {
                return DtoUtils.returnFail("请选择要删除的常用联系人", "100433");
            }
            for(Integer i:ids){
                if (!itripUserLinkUserService.validationOrderStatus(i)) {
                    return DtoUtils.returnFail("所选的常用联系人中有与某条待支付的订单关联的项，无法删除", "100431");
                }
                Integer integer = itripUserLinkUserService.itriptxDeleteItripUserLinkUserById((long) i);
                if (integer == 0) {
                    return DtoUtils.returnSuccess("删除失败 用户名可能不存在");
                }
            }
            return DtoUtils.returnSuccess("删除成功");

        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("删除常用联系人失败", "100432");
        }
    }
}
