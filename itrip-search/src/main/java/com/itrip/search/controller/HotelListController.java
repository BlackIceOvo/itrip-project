package com.itrip.search.controller;

import com.itrip.beans.dto.Dto;
import com.itrip.beans.vo.hotel.ItripHotelVO;
import com.itrip.beans.vo.hotel.SearchHotCityVO;
import com.itrip.beans.vo.hotel.SearchHotelVO;
import com.itrip.search.service.search.SearchService;
import com.itrip.utils.common.DtoUtils;
import com.itrip.utils.common.EmptyUtils;
import com.itrip.utils.common.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ice
 */
@RestController
@RequestMapping("/api/hotellist")
public class HotelListController {

    @Autowired
    SearchService searchService;


    /**
     * 查询酒店分页
     *
     * @return
     */
    @PostMapping(value = "/searchItripHotelPage", produces = "application/json")
    public Dto<Page<ItripHotelVO>> searchItripHotelPage(@RequestBody SearchHotelVO vo) {
        if (EmptyUtils.isEmpty(vo.getDestination())) {
            return DtoUtils.returnFail("目的地为空", "20004");
        }
        try {
            return DtoUtils.returnDataSuccess(searchService.searchItripHotelPage(vo));
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("异常获取", "200001");
        }
    }

    /**
     * 查询热门城市酒店
     *
     * @return
     */
    @PostMapping(value = "/searchItripHotelListByHotCity",produces = "application/json")
    public Dto searchItripHotelListByHotCity(@RequestBody SearchHotCityVO vo) {
        if(EmptyUtils.isNotEmpty(vo.getCityId())){
                return  DtoUtils.returnDataSuccess(searchService.searchItripHotelListByHotCity(vo));
        }else{
            return DtoUtils.returnFail("城市Id不能 为空!","20004");
        }
    }


}
