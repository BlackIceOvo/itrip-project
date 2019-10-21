package com.itrip.biz.controller;

import com.itrip.beans.dto.Dto;
import com.itrip.beans.pojo.ItripAreaDic;
import com.itrip.beans.pojo.ItripHotel;
import com.itrip.beans.pojo.ItripLabelDic;
import com.itrip.beans.vo.ItripAreaDicVO;
import com.itrip.beans.vo.ItripLabelDicVO;
import com.itrip.beans.vo.TempVO;
import com.itrip.beans.vo.hotel.HotelVideoDescVO;
import com.itrip.beans.vo.hotel.ItripSearchFacilitiesHotelVO;
import com.itrip.biz.service.itripAreaDic.ItripAreaDicService;
import com.itrip.biz.service.itripHotel.ItripHotelService;
import com.itrip.biz.service.itripImage.ItripImageService;
import com.itrip.biz.service.itripLabelDic.ItripLabelDicService;
import com.itrip.biz.service.itripLabelDic.ItripLabelDicServiceImpl;
import com.itrip.utils.common.DtoUtils;
import com.itrip.utils.common.EmptyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ice
 */
@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private ItripAreaDicService itripAreaDicService;

    @Autowired
    private ItripLabelDicService itripLabelDicService;


    @Autowired
    private ItripHotelService itripHotelService;

    /**
     * 热门城市
     *
     * @param type
     * @return
     */
    @GetMapping(value = "/queryhotcity/{type}", produces = "application/json")
    public Dto queryhotcity(@PathVariable("type") Integer type) {
        if (EmptyUtils.isEmpty(type)) {
            return DtoUtils.returnFail("ID Not Null!", "10201");
        }
        try {
            Map hashMap = new HashMap();
            hashMap.put("isHot", 1);
            hashMap.put("isChina", type);
            List<ItripAreaDic> itripAreaDicListByMap = itripAreaDicService.getItripAreaDicListByMap(hashMap);
            List<ItripAreaDicVO> list = new ArrayList<>();
            ItripAreaDicVO vo = null;
            if (itripAreaDicListByMap.size() > 0) {
                for (ItripAreaDic x : itripAreaDicListByMap) {
                    vo = new ItripAreaDicVO();
                    BeanUtils.copyProperties(x, vo);
                    list.add(vo);
                }
            }
            return DtoUtils.returnSuccess("获取热门城市成功",list);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("Error", "10202");
        }
    }

    /**
     * 商圈
     *
     * @param cityId
     * @return
     */
    @GetMapping(value = "/querytradearea/{cityId}", produces = "application/json")
    public Dto querytradearea(@PathVariable Integer cityId) {
        if (EmptyUtils.isEmpty(cityId)) {
            return DtoUtils.returnFail("cityId not Null ", "10203");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("parent", cityId);
        map.put("isHot", 0);
        List<ItripAreaDic> itripAreaDicListByMap = null;
        try {
            itripAreaDicListByMap = itripAreaDicService.getItripAreaDicListByMap(map);
            ItripAreaDicVO itripAreaDicVO = null;
            List<ItripAreaDicVO> itripAreaDicVOArrayList = new ArrayList<>();
            if (itripAreaDicListByMap.size() > 0) {
                for (ItripAreaDic x : itripAreaDicListByMap) {
                    itripAreaDicVO = new ItripAreaDicVO();
                    BeanUtils.copyProperties(x, itripAreaDicVO);
                    itripAreaDicVOArrayList.add(itripAreaDicVO);
                }
            }
            return DtoUtils.returnSuccess("获取商圈成功",itripAreaDicVOArrayList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("System error", "10204 ");
        }

    }


    /**
     * 特色
     *
     * @return
     */
    @GetMapping(value = "/queryhotelfeature", produces = "application/json")
    public Dto queryhotelfeature() {
        try {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("name", "酒店特色");
            List<ItripLabelDic> itripLabelDicListByMap = itripLabelDicService.getItripLabelDicListByMap(stringObjectHashMap);
            if (itripLabelDicListByMap.size() > 0 && EmptyUtils.isNotEmpty(itripLabelDicListByMap.get(0))) {
                stringObjectHashMap.clear();
                stringObjectHashMap.put("parentId", itripLabelDicListByMap.get(0).getId());
                List<ItripLabelDic> listByMap = itripLabelDicService.getItripLabelDicListByMap(stringObjectHashMap);
                return DtoUtils.returnSuccess("获取特色成功",listByMap);
            }
            return DtoUtils.returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("系统异常,获取失败", "10205");
        }
    }


    /**
     * 获取视频文字信息
     *
     * @param hotelId
     * @return
     */
    @GetMapping(value = "/getvideodesc/{hotelId}", produces = "application/json")
    public Dto getvideodesc(@PathVariable Integer hotelId) {
        if (EmptyUtils.isEmpty(hotelId)) {
            return DtoUtils.returnFail("酒店id不能为空", "100215");
        }
        try {
            HotelVideoDescVO hotelVideoDescVO = new HotelVideoDescVO();
            hotelVideoDescVO.setHotelName(itripHotelService.getItripHotelById((long) hotelId).getHotelName());
            hotelVideoDescVO.setTradingAreaNameList(itripHotelService.getItripTradingAreaNameList(hotelId));
            hotelVideoDescVO.setHotelFeatureList(itripLabelDicService.getItripHotelFeatureList(hotelId));
            return DtoUtils.returnSuccess("获取视屏文字信息成功",hotelVideoDescVO);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("获取酒店视频文字描述失败 ", "100214");
        }
    }


    /**
     * 酒店公共设施
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryhotelfacilities/{id}", produces = "application/json")
    public Dto queryhotelfacilities(@PathVariable Integer id) {
        if (EmptyUtils.isEmpty(id)) {
            return DtoUtils.returnFail("酒店id不能为空", "10206");
        }
        try {
            ItripHotel itripHotelById = itripHotelService.getItripHotelById((long) id);
            if (EmptyUtils.isNotEmpty(itripHotelById)) {
                return DtoUtils.returnSuccess("获取公共设施信息成功",itripHotelById.getFacilities());
            }
            return DtoUtils.returnFail("获取失败,该酒店不存在!", "10207");
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail(e.getMessage(), "10207");
        }

    }

    /**
     * 酒店介绍
     *
     * @param id
     * @return
     */
    @GetMapping("/queryhoteldetails/{id}")
    public Dto queryhoteldetails(@PathVariable Integer id) {
        if (EmptyUtils.isEmpty(id)) {
            return DtoUtils.returnFail("酒店id不能为空", "10210");
        }
        try {
            List<ItripLabelDic> queryhoteldetails = itripHotelService.getQueryhoteldetails(id);
            if (queryhoteldetails.size() > 0) {
                ItripLabelDicVO vo = null;
                List<ItripLabelDicVO> labelDicVOList = new ArrayList<>();
                ItripHotel itripHotelById = itripHotelService.getItripHotelById((long) id);
                vo = new ItripLabelDicVO();
                vo.setDescription(itripHotelById.getDetails());
                vo.setName("酒店介绍");
                labelDicVOList.add(vo);
                for (ItripLabelDic q : queryhoteldetails) {
                    vo = new ItripLabelDicVO();
                    vo.setName(q.getName());
                    vo.setDescription(q.getDescription());
                    labelDicVOList.add(vo);
                }
                return DtoUtils.returnSuccess("酒店介绍获取成功",labelDicVOList);
            }
            return DtoUtils.returnSuccess("无介绍");
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail(e.getMessage(), "10211");
        }
    }

    /**
     * 获取酒店政策
     * @param id
     * @return
     */
    @GetMapping("/queryhotelpolicy/{id}")
    public Dto queryhotelpolicy(@PathVariable Integer id) {
        try {
            ItripHotel itripHotelById = itripHotelService.getItripHotelById((long) id);
            if (EmptyUtils.isNotEmpty(itripHotelById)) {
                return DtoUtils.returnSuccess("获取政策酒店政策信息成功",itripHotelById.getHotelPolicy());
            }
            return DtoUtils.returnSuccess("无政策");
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail(e.getMessage(), "10209");

        }
    }
}
