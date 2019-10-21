package com.itrip.dao.mapper.itripHotel;
import com.itrip.beans.pojo.ItripHotel;
import com.itrip.beans.pojo.ItripLabelDic;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripHotelMapper {

	public ItripHotel getItripHotelById(@Param(value = "id") Long id)throws Exception;

	public List<ItripHotel>	getItripHotelListByMap(Map<String, Object> param)throws Exception;

	public Integer getItripHotelCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripHotel(ItripHotel itripHotel)throws Exception;

	public Integer updateItripHotel(ItripHotel itripHotel)throws Exception;

	public Integer deleteItripHotelById(@Param(value = "id") Long id)throws Exception;

	public List<String> getItripTradingAreaNameList(@Param("cityId") Integer cityId) throws Exception;

	public List<ItripLabelDic>  getQueryhoteldetails(@Param("cityId") Integer cityId) throws Exception;



}
