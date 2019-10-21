package com.itrip.biz.service.itripHotelRoom;

import com.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import com.itrip.beans.vo.hotelroom.SearchHotelRoomVO;
import com.itrip.utils.common.Page;
import com.itrip.beans.pojo.ItripHotelRoom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangrj
 * @Date: 2019/5/20 下午9:26
 */
public interface ItripHotelRoomService {

    public ItripHotelRoom getItripHotelRoomById(Long id)throws Exception;

    public List<ItripHotelRoom>	getItripHotelRoomListByMap(Map<String, Object> param)throws Exception;

    public Integer getItripHotelRoomCountByMap(Map<String, Object> param)throws Exception;

    public Integer itriptxAddItripHotelRoom(ItripHotelRoom itripHotelRoom)throws Exception;

    public Integer itriptxModifyItripHotelRoom(ItripHotelRoom itripHotelRoom)throws Exception;

    public Integer itriptxDeleteItripHotelRoomById(Long id)throws Exception;

    public Page<ItripHotelRoom> queryItripHotelRoomPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
    List<ItripHotelRoomVO> findItripHotelRoomBy(SearchHotelRoomVO searchHotelRoomVO)throws Exception;

    boolean isSufficient( HashMap<String,Object> param );
}
