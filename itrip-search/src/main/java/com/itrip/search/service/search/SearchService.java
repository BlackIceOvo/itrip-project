package com.itrip.search.service.search;

import com.itrip.beans.vo.hotel.ItripHotelVO;
import com.itrip.beans.vo.hotel.SearchHotCityVO;
import com.itrip.beans.vo.hotel.SearchHotelVO;
import com.itrip.utils.common.Page;

import java.util.List;

public interface SearchService {
    Page searchItripHotelPage (SearchHotelVO vo) throws Exception;
    List<ItripHotelVO> searchItripHotelListByHotCity(SearchHotCityVO vo);
}
