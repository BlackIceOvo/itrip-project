package com.itrip.biz.service.itripHotelOrder;

import com.itrip.beans.dto.Dto;
import com.itrip.beans.pojo.*;
import com.itrip.beans.vo.order.*;
import com.itrip.dao.mapper.itripHotel.ItripHotelMapper;
import com.itrip.dao.mapper.itripHotelRoom.ItripHotelRoomMapper;
import com.itrip.dao.mapper.itripOrderLinkUser.ItripOrderLinkUserMapper;
import com.itrip.utils.common.*;
import com.itrip.dao.mapper.itripHotelOrder.ItripHotelOrderMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = RuntimeException.class)

public class ItripHotelOrderServiceImpl implements ItripHotelOrderService {

    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;

    @Resource
    private ItripHotelMapper itripHotelMapper;

    @Resource
    private ItripHotelRoomMapper itripHotelRoomMapper;

    @Autowired
    private ValidationToken validationToken;

    @Resource
    private ItripOrderLinkUserMapper itripOrderLinkUserMapper;

    @Override
    public ItripHotelOrder getItripHotelOrderById(Long id) throws Exception {
        return itripHotelOrderMapper.getItripHotelOrderById(id);
    }

    @Override

    public List<ItripHotelOrder> getItripHotelOrderListByMap(Map<String, Object> param) throws Exception {
        return itripHotelOrderMapper.getItripHotelOrderListByMap(param);
    }

    @Override

    public Integer getItripHotelOrderCountByMap(Map<String, Object> param) throws Exception {
        return itripHotelOrderMapper.getItripHotelOrderCountByMap(param);
    }

    @Override

    public Integer itriptxAddItripHotelOrder(ItripHotelOrder itripHotelOrder) throws Exception {
        itripHotelOrder.setCreationDate(new Date());
        return itripHotelOrderMapper.insertItripHotelOrder(itripHotelOrder);
    }

    @Override

    public Integer itriptxModifyItripHotelOrder(ItripHotelOrder itripHotelOrder) throws Exception {
        itripHotelOrder.setModifyDate(new Date());
        return itripHotelOrderMapper.updateItripHotelOrder(itripHotelOrder);
    }

    @Override

    public Integer itriptxDeleteItripHotelOrderById(Long id) throws Exception {
        return itripHotelOrderMapper.deleteItripHotelOrderById(id);
    }

    @Override

    public Page<ItripHotelOrder> queryItripHotelOrderPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        Integer total = itripHotelOrderMapper.getItripHotelOrderCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripHotelOrder> itripHotelOrderList = itripHotelOrderMapper.getItripHotelOrderListByMap(param);
        page.setRows(itripHotelOrderList);
        return page;
    }

    @Override
    public Dto getItripRoomStore1(ValidateRoomStoreVO vo) throws Exception {
        HashMap<String, Object> param = new HashMap<>();
        //起始时间
        param.put("startTime", vo.getCheckInDate());
        //退房时间
        param.put("endTime", vo.getCheckOutDate());
        //房间Id
        param.put("roomId", vo.getRoomId());
        //酒店Id
        param.put("hotelId", vo.getHotelId());
        //调用存储过程刷新事实表
        List<ItripHotelTempStore> stores = queryRoomStore(param);
        Map<String, Object> output = new HashMap<>();
        if (EmptyUtils.isEmpty(stores)) {
            output.put("flag", false);
            output.put("store", 0);
            return DtoUtils.returnDataSuccess(output);
        }
        //判断用户要的房间数量和剩余库存
        output.put("flag", stores.get(0).getStore() >= vo.getCount());
        output.put("store", stores.get(0).getStore());
        return DtoUtils.returnDataSuccess(output);
    }

    /**
     * 预定信息
     *
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public Dto getItripRoom(ValidateRoomStoreVO vo) throws Exception {
        //查询酒店信息
        ItripHotel hotel = itripHotelMapper.getItripHotelById(vo.getHotelId());
        //查询酒店放进的信息
        ItripHotelRoom room = itripHotelRoomMapper.getItripHotelRoomById(vo.getRoomId());

        //封装前端需要返回的实体类
        RoomStoreVO roomStoreVO = new RoomStoreVO();
        roomStoreVO.setCheckInDate(vo.getCheckInDate());
        roomStoreVO.setCheckOutDate(vo.getCheckOutDate());
        roomStoreVO.setCount(vo.getCount());
        roomStoreVO.setHotelId(hotel.getId());
        roomStoreVO.setHotelName(hotel.getHotelName());
        roomStoreVO.setRoomId(room.getId());
        roomStoreVO.setPrice(BigDecimal.valueOf(room.getRoomPrice()));
        //计算库存
        // roomStoreVO.setStore();
        //封装查询参数
        HashMap<String, Object> param = new HashMap();
        param.put("startTime", vo.getCheckInDate());
        param.put("endTime", vo.getCheckOutDate());
        param.put("roomId", vo.getRoomId());
        param.put("hotelId", vo.getHotelId());

        List<ItripHotelTempStore> stores = this.queryRoomStore(param);
        if (EmptyUtils.isEmpty(stores)) {
            return DtoUtils.returnFail("暂时无房", "100512");
        } else {
            roomStoreVO.setStore(stores.get(0).getStore());
            return DtoUtils.returnDataSuccess(roomStoreVO);
        }
    }


    @Override
    public List<ItripHotelTempStore> queryRoomStore(HashMap<String, Object> param) {
        //调用存储过程，刷新实时库存表
        itripHotelOrderMapper.flushRoomStore(param);
        //计算库存：实时库存表的库存数量-当日订单中对应该房型未支付的订单所占的库存
        List<ItripHotelTempStore> tempStores = itripHotelOrderMapper.queryRoomStore(param);
        return tempStores;
    }

    @Override
    public Dto itriptxInsertItripHotelOrder(ItripAddHotelOrderVO vo, String token) throws Exception {
        //校验库存
        HashMap<String, Object> param = new HashMap();
        param.put("startTime", vo.getCheckInDate());
        param.put("endTime", vo.getCheckOutDate());
        param.put("roomId", vo.getRoomId());
        param.put("hotelId", vo.getHotelId());
        List<ItripHotelTempStore> stores = queryRoomStore(param);
        if (stores.size() == 0 || stores.get(0).getStore() < vo.getCount()) {
            return DtoUtils.returnFail("库存不足", "100507 ");
        }
        //复制属性
        ItripHotelOrder order = new ItripHotelOrder();
        BeanUtils.copyProperties(vo, order);
        //获得下单人的信息
        ItripUser currentUser = validationToken.getCurrentUser(token);
        order.setUserId(currentUser.getId());
        order.setCreatedBy(currentUser.getId());
        //支付方式
        ItripHotelRoom itripHotelRoomById = itripHotelRoomMapper.getItripHotelRoomById(vo.getRoomId());
        order.setPayType(itripHotelRoomById.getPayType());
        //设备类型
        if (token.startsWith("token:PC")) {
            order.setBookType(0);
        } else if (token.startsWith("token:MOBILE")) {
            order.setBookType(1);
        } else {
            order.setBookType(2);
        }

        //入住人名称
        List<ItripUserLinkUser> linkUser = vo.getLinkUser();
        if (EmptyUtils.isNotEmpty(linkUser)) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; ; ) {
                sb.append(linkUser.get(i).getLinkUserName());
                if (++i < linkUser.size()) {
                    sb.append(",");
                    continue;
                }
                break;
            }
            order.setLinkUserName(sb.toString());
        }
        //订单状态
        order.setOrderStatus(0);

        //预订天数
        long days = DateUtil.getBetweenDates(vo.getCheckInDate(), vo.getCheckOutDate()).size() - 1;
        order.setBookingDays((int) days);

        //支付金额
        double money = getSumRoomPrice(days * vo.getCount(), itripHotelRoomById.getRoomPrice());
        order.setPayAmount(money);
        //订单号 日期+机器码+md5(hotelId+roomId+时间+随机数)
        StringBuffer md5 = new StringBuffer();
        md5.append(vo.getHotelId());
        md5.append(vo.getRoomId());
        md5.append(System.currentTimeMillis());
        md5.append(Math.random() * 100000);
        String sb_md5 = MD5.getMd5(md5.toString(), 6);
        order.setOrderNo("D100001" + sb_md5);

        //添加订单
        Map<String, Object> output = insertItripHotelOrder(order, vo.getLinkUser());
        return DtoUtils.returnDataSuccess(output);
    }

    /**
     * 计算金额
     *
     * @param count
     * @param roomPrice
     * @return
     * @throws Exception
     */
    public double getSumRoomPrice(long count, Double roomPrice) throws Exception {
        BigDecimal money = BigDecimalUtil.OperationASMD(count, roomPrice,
                BigDecimalUtil.BigDecimalOprations.multiply, 2, BigDecimal.ROUND_DOWN);
        return money.doubleValue();
    }

    /**
     * 添加订单和入住人的信息
     *
     * @param order
     * @param linkUsers
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> insertItripHotelOrder(ItripHotelOrder order, List<ItripUserLinkUser> linkUsers) throws Exception {
        //新曾订单
        if (EmptyUtils.isEmpty(order.getId())) {
            order.setCreationDate(new Date());
            itripHotelOrderMapper.insertItripHotelOrder(order);
        } else {//修改订单
            //删除订单之前的所有联系人
            itripOrderLinkUserMapper.deleteItripOrderLinkUserByOrderId(order.getId());
            itripHotelOrderMapper.updateItripHotelOrder(order);
        }
        //添加联系人
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("orderNo", order.getOrderNo());
        List<ItripHotelOrder> itripHotelOrderListByMap = itripHotelOrderMapper.getItripHotelOrderListByMap(hashMap);
        Map<String, Object> output = null;
        if (itripHotelOrderListByMap.size() > 0 && itripHotelOrderListByMap.get(0) != null) {

            ItripHotelOrder itripHotelOrder = itripHotelOrderListByMap.get(0);
            if (EmptyUtils.isNotEmpty(linkUsers)) {
                ItripOrderLinkUser orderLinkUser = null;
                for (ItripUserLinkUser user : linkUsers) {
                    orderLinkUser = new ItripOrderLinkUser();
                    BeanUtils.copyProperties(user, orderLinkUser);
                    orderLinkUser.setOrderId(itripHotelOrder.getId());
                    orderLinkUser.setLinkUserId(user.getId());
                    itripOrderLinkUserMapper.insertItripOrderLinkUser(orderLinkUser);
                }
            }
            output = new HashMap<>();
            output.put("orderNo", itripHotelOrder.getOrderNo());
            output.put("id", itripHotelOrder.getId());

        }
        return output;

    }

    @Override
    public Dto getPersonOrderInfo(Integer id) {
        ItripPersonalOrderRoomVO itripPersonalOrderRoomVO = itripHotelOrderMapper.getPersonOrderInfo(id);
        if (EmptyUtils.isNotEmpty(itripPersonalOrderRoomVO)) {
            return DtoUtils.returnSuccess("获取个人订单房型信息成功", itripPersonalOrderRoomVO);
        }
        return DtoUtils.returnFail("没有相关订单信息", "100526");
    }

    @Override
    public Dto getPersonalOrderList(ItripSearchOrderVO vo, String token) throws Exception {
        ItripUser currentUser = validationToken.getCurrentUser(token);
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", currentUser.getId());
        if (vo.getOrderStatus() > -1) {
            map.put("orderStatus", vo.getOrderStatus());
        }
        if (vo.getOrderType() > -1) {
            map.put("orderType", vo.getOrderType());
        }
        if(EmptyUtils.isNotEmpty(vo.getOrderNo())){
            map.put("orderNo",vo.getOrderNo());
        }
        if(EmptyUtils.isNotEmpty(vo.getStartDate())){
            map.put("checkInDate",vo.getStartDate());

        }
        if(EmptyUtils.isNotEmpty(vo.getEndDate())){
            map.put("checkOutDate",vo.getEndDate());

        }
        if(EmptyUtils.isNotEmpty(vo.getLinkUserName())){
            map.put("linkUserName",vo.getLinkUserName());
        }
        Integer a = itripHotelOrderMapper.getItripHotelOrderCountByMap(map);
        int no=0;
        int size=6;
        if (EmptyUtils.isNotEmpty(vo.getPageNo())) {
            map.put("beginPos", vo.getPageNo() == 0 ? 0 : (vo.getPageNo() - 1) * 5);
            no=vo.getPageNo();
        }
        if (EmptyUtils.isNotEmpty(vo.getPageSize())) {
            map.put("pageSize", vo.getPageSize());
            size=vo.getPageSize();
        }
        List<ItripHotelOrder> itripHotelOrderListByMap = itripHotelOrderMapper.getItripHotelOrderListByMap(map);

        Page<ItripHotelOrder> page = new Page(no, size, a);

        page.setRows(itripHotelOrderListByMap);
        return DtoUtils.returnSuccess("获取个人订单列表成功", page);
    }

}
