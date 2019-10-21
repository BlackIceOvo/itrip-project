package com.itrip.biz.service.itripHotelOrder;

import com.itrip.beans.dto.Dto;
import com.itrip.beans.pojo.ItripHotelTempStore;
import com.itrip.beans.pojo.ItripUserLinkUser;
import com.itrip.beans.vo.order.ItripAddHotelOrderVO;
import com.itrip.beans.vo.order.ItripSearchOrderVO;
import com.itrip.beans.vo.order.ValidateRoomStoreVO;
import com.itrip.utils.common.Page;
import com.itrip.beans.pojo.ItripHotelOrder;
import com.itrip.utils.common.ValidationToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangrj
 * @Date: 2019/5/20 下午9:26
 */
public interface ItripHotelOrderService {

    public ItripHotelOrder getItripHotelOrderById(Long id)throws Exception;

    public List<ItripHotelOrder>	getItripHotelOrderListByMap(Map<String, Object> param)throws Exception;

    public Integer getItripHotelOrderCountByMap(Map<String, Object> param)throws Exception;

    public Integer itriptxAddItripHotelOrder(ItripHotelOrder itripHotelOrder)throws Exception;

    public Integer itriptxModifyItripHotelOrder(ItripHotelOrder itripHotelOrder)throws Exception;

    public Integer itriptxDeleteItripHotelOrderById(Long id)throws Exception;

    public Page<ItripHotelOrder> queryItripHotelOrderPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;

    Dto getItripRoomStore1(ValidateRoomStoreVO vo) throws Exception;
    Dto getItripRoom(ValidateRoomStoreVO vo) throws Exception;
    List<ItripHotelTempStore> queryRoomStore(HashMap<String, Object> param);

    Dto itriptxInsertItripHotelOrder(ItripAddHotelOrderVO vo, String token) throws Exception;

    Map<String, Object> insertItripHotelOrder(ItripHotelOrder order, List<ItripUserLinkUser> linkUsers) throws Exception;

    Dto getPersonOrderInfo(Integer id);

    Dto getPersonalOrderList(ItripSearchOrderVO vo,String token) throws Exception;
}
