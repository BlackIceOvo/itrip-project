package com.itrip.dao.mapper.itripHotelOrder;
import com.itrip.beans.pojo.ItripHotelOrder;
import com.itrip.beans.pojo.ItripHotelTempStore;
import com.itrip.beans.vo.order.ItripPersonalOrderRoomVO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ItripHotelOrderMapper {

	public ItripHotelOrder getItripHotelOrderById(@Param(value = "id") Long id)throws Exception;

	public List<ItripHotelOrder>	getItripHotelOrderListByMap(Map<String, Object> param)throws Exception;
	public List<ItripHotelOrder>	getItripHotelOrderListByMap2(Map<String, Object> param)throws Exception;

	public Integer getItripHotelOrderCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripHotelOrder(ItripHotelOrder itripHotelOrder)throws Exception;

	public Integer updateItripHotelOrder(ItripHotelOrder itripHotelOrder)throws Exception;

	public Integer deleteItripHotelOrderById(@Param(value = "id") Long id)throws Exception;

	/**
	 * 调用存储过程获取表
	 * @param param
	 */
    void flushRoomStore(HashMap<String, Object> param);

	List<ItripHotelTempStore> queryRoomStore(HashMap<String, Object> param);

    ItripPersonalOrderRoomVO getPersonOrderInfo(Integer id);
}
