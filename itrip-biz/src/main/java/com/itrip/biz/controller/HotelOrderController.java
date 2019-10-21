package com.itrip.biz.controller;

import com.itrip.beans.dto.Dto;
import com.itrip.beans.vo.order.ItripAddHotelOrderVO;
import com.itrip.beans.vo.order.ItripSearchOrderVO;
import com.itrip.beans.vo.order.ValidateRoomStoreVO;
import com.itrip.biz.service.itripHotelOrder.ItripHotelOrderService;
import com.itrip.utils.common.DtoUtils;
import com.itrip.utils.common.EmptyUtils;
import com.itrip.utils.common.ValidationToken;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * @author ice
 */
@RestController
@RequestMapping("/api/hotelorder")
public class HotelOrderController {
    @Autowired
    private ValidationToken validationToken;
    @Autowired
    private ItripHotelOrderService itripHotelOrderService;

    /**
     * 修改订房日期验证是否有房 3.2.1
     * @param vo
     * @param httpHeaders
     * @return
     */
    @PostMapping(value = "/validateroomstore",produces = "application/json")
    public Dto validateRoomStore(@RequestBody ValidateRoomStoreVO vo, @RequestHeader HttpHeaders httpHeaders) {

        try {
            if (EmptyUtils.isEmpty(httpHeaders.getFirst("user-agent")) || EmptyUtils.isEmpty(httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            } else if (!validationToken.valiate(httpHeaders.getFirst("user-agent"), httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            }
            if (EmptyUtils.isEmpty(vo.getRoomId())) {
                return DtoUtils.returnFail("roomId不能为空", "100511");
            }
            if (EmptyUtils.isEmpty(vo.getHotelId())) {
                return DtoUtils.returnFail("hotelId不能为空", "100510");
            }
            if (EmptyUtils.isEmpty(vo.getCount())) {
                return DtoUtils.returnFail("用户库存不能为空", "100510");
            }
            if (vo.getCheckInDate().getTime() > vo.getCheckOutDate().getTime()) {
                return DtoUtils.returnFail("入住时间不能大于退房时间", "100514");
            }
            //2、封装前端需要的实体类
            Dto dto = itripHotelOrderService.getItripRoomStore1(vo);
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail(" 系统异常", "100513");
        }
    }

    @GetMapping(value = "getpersonalorderinfo/{orderId}",produces = "application/json")
    public Dto getPersonalOrderInfo(@PathVariable Integer orderId,@RequestHeader HttpHeaders httpHeaders){
        try{
            if (EmptyUtils.isEmpty(httpHeaders.getFirst("user-agent")) || EmptyUtils.isEmpty(httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            } else if (!validationToken.valiate(httpHeaders.getFirst("user-agent"), httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return DtoUtils.returnFail("获取个人订单信息错误","100527");
        }
    }


    /**
     * 预定信息 3.2.6
     * @param vo
     * @param httpHeaders
     * @return
     */
    @PostMapping(value = "/getpreorderinfo", produces = "application/json")
    public Dto getPreOrderInfo(@RequestBody ValidateRoomStoreVO vo, @RequestHeader HttpHeaders httpHeaders) {
        try {
            if (EmptyUtils.isEmpty(httpHeaders.getFirst("user-agent")) || EmptyUtils.isEmpty(httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            } else if (!validationToken.valiate(httpHeaders.getFirst("user-agent"), httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            }
            if (EmptyUtils.isEmpty(vo.getHotelId())) {
                return DtoUtils.returnFail("hotelId不能为空", "100510");
            }
            if (EmptyUtils.isEmpty(vo.getRoomId())) {
                return DtoUtils.returnFail("roomId不能为空", "100511");
            }
            if (EmptyUtils.isEmpty(vo.getCount())) {
                return DtoUtils.returnFail("用户库存不能为空", "100510");
            }
            if (vo.getCheckInDate().getTime() > vo.getCheckOutDate().getTime()) {
                return DtoUtils.returnFail("入住时间不能大于退房时间", "100514");
            }
            return itripHotelOrderService.getItripRoom(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("error", "100513 ");
        }
    }


    /**
     * 生成一笔订单 3.2.8
     *
     * @return
     */
    @PostMapping(value = "/addhotelorder", produces = "application/json")
    public Dto addHotelOrder(@RequestBody ItripAddHotelOrderVO vo, @RequestHeader HttpHeaders httpHeaders) {
        try {
            if (EmptyUtils.isEmpty(httpHeaders.getFirst("user-agent")) || EmptyUtils.isEmpty(httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            } else if (!validationToken.valiate(httpHeaders.getFirst("user-agent"), httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            }
            //表单信息的验证
            if (EmptyUtils.isEmpty(vo.getCount()) || EmptyUtils.isEmpty(vo.getHotelId())) {
                return DtoUtils.returnFail("不能提交空，请填写订单信息", "100506");
            }
            return itripHotelOrderService.itriptxInsertItripHotelOrder(vo, httpHeaders.getFirst("token"));
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("生成订单失败", "100505");
        }
    }

    /**
     * 根据订单ID查看个人订单详情 3.2.3
     * @param orderId
     * @param httpHeaders
     * @return
     */
    @GetMapping(value = "/getpersonalorderroominfo/{orderId}", produces = "application/json")
    public Dto getPersonalOrderRoomInfo(@PathVariable Integer orderId, @RequestHeader HttpHeaders httpHeaders) {
        try {
            if (EmptyUtils.isEmpty(httpHeaders.getFirst("user-agent")) || EmptyUtils.isEmpty(httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            } else if (!validationToken.valiate(httpHeaders.getFirst("user-agent"), httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            }
            if (EmptyUtils.isEmpty(orderId)) {
                return DtoUtils.returnFail("请传递参数：orderId", "100525");
            }
            return itripHotelOrderService.getPersonOrderInfo(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("获取个人订单信息错误", "100527");
        }
    }

    /**
     * 根据个人订单列表,并且分页显示 3.2.4
     */
    @PostMapping(value = "/getpersonalorderlist",produces = "application/json")
    public Dto getPersonalOrderList(@RequestHeader HttpHeaders httpHeaders, @RequestBody ItripSearchOrderVO vo) {
        try {
            if (EmptyUtils.isEmpty(httpHeaders.getFirst("user-agent")) || EmptyUtils.isEmpty(httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            } else if (!validationToken.valiate(httpHeaders.getFirst("user-agent"), httpHeaders.getFirst("token"))) {
                return DtoUtils.returnFail("token失效，请重登录", "100000");
            }
            if(EmptyUtils.isEmpty(vo.getOrderType())){
                return DtoUtils.returnFail("请传递参数：orderType","100501");
            }
            if(EmptyUtils.isEmpty(vo.getOrderStatus())){
                return DtoUtils.returnFail("请传递参数：orderStatus","100502");
            }
            return  itripHotelOrderService.getPersonalOrderList(vo,httpHeaders.getFirst("token"));
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail(" 获取个人订单列表错误", "100503");
        }
    }
}
