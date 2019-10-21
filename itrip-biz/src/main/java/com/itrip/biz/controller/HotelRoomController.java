package com.itrip.biz.controller;

import com.itrip.beans.dto.Dto;
import com.itrip.beans.pojo.ItripImage;
import com.itrip.beans.pojo.ItripLabelDic;
import com.itrip.beans.vo.ItripImageVO;
import com.itrip.beans.vo.ItripLabelDicVO;
import com.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import com.itrip.beans.vo.hotelroom.SearchHotelRoomVO;
import com.itrip.beans.vo.order.ItripAddHotelOrderVO;
import com.itrip.biz.service.itripHotelRoom.ItripHotelRoomService;
import com.itrip.biz.service.itripImage.ItripImageService;
import com.itrip.biz.service.itripLabelDic.ItripLabelDicService;
import com.itrip.utils.common.DtoUtils;
import com.itrip.utils.common.EmptyUtils;
import com.itrip.utils.common.ValidationToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ice
 */
@RestController
@RequestMapping("/api/hotelroom")
public class HotelRoomController {

    @Autowired
    ItripHotelRoomService itripHotelRoomService;


    @Autowired
    private ItripImageService itripImageService;

    @Autowired
    private ItripLabelDicService labelDicService;

    @Autowired
    private ValidationToken validationToken;

    /**
     * 查询酒店房间列表
     * @param vo
     * @return
     */
    @PostMapping(value = "/queryhotelroombyhotel", produces = "application/json")
    public Dto queryHotelRoomByHotel(@RequestBody SearchHotelRoomVO vo) {
        try {
            if (EmptyUtils.isNotEmpty(vo.getStartDate()) && EmptyUtils.isNotEmpty(vo.getEndDate()) && EmptyUtils.isNotEmpty(vo.getHotelId()) && vo.getStartDate().getTime() < vo.getEndDate().getTime()) {
                List<List<ItripHotelRoomVO>> lists = null;
                lists = new ArrayList<List<ItripHotelRoomVO>>();
                SearchHotelRoomVO vo1 = null;
                for (ItripHotelRoomVO itripHotelRoomVOSs : itripHotelRoomService.findItripHotelRoomBy(vo)) {
                    vo1 = new SearchHotelRoomVO();
                    vo1.setStartDate(vo.getStartDate());
                    vo1.setEndDate(vo.getEndDate());
                    vo1.setHotelId(itripHotelRoomVOSs.getHotelId());
                    vo1.setRoomBedTypeId(itripHotelRoomVOSs.getRoomBedTypeId());
                    HashMap<String,Object> param = new HashMap();
                    param.put("startTime",vo1.getStartDate());
                    param.put("endTime",vo1.getEndDate());
                    param.put("roomId",itripHotelRoomVOSs.getId());
                    param.put("hotelId",vo1.getHotelId());
                    if (itripHotelRoomService.isSufficient(param)) {
                        List<ItripHotelRoomVO> list = new ArrayList<ItripHotelRoomVO>();
                        list.add(itripHotelRoomVOSs);
                        lists.add(list);
                    }
                }
                return DtoUtils.returnDataSuccess(lists);
            } else {
                return DtoUtils.returnFail(" 酒店id不能为空,酒店入住及退房时间不能为空,入住时间不能大于退房时间", "100303");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("查询酒店房型列表失败", "100401");
        }
    }

    /**
     * 查询图片
     *
     * @param targetId
     * @return
     */
    @GetMapping(value = "/getimg/{targetId}", produces = "application/json")
    public Dto getImg(@PathVariable Integer targetId) {
        if (EmptyUtils.isEmpty(targetId)) {
            return DtoUtils.returnFail("酒店id不能为空", "100213 ");
        }
        try {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("targetId", targetId);
            List<ItripImage> itripImageListByMap = itripImageService.getItripImageListByMap(stringObjectHashMap);
            List<ItripImageVO> list = new ArrayList<>();
            ItripImageVO imageVO = null;
            for (ItripImage i : itripImageListByMap) {
                imageVO = new ItripImageVO();
                BeanUtils.copyProperties(i, imageVO);
                list.add(imageVO);
            }
            return DtoUtils.returnDataSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("获取酒店图片失败", "100212");
        }
    }

    /**
     * 查询床型
     * @return
     */
    @GetMapping(value = "/queryhotelroombed", produces = "application/json")
    public Dto queryHotelRoomBed() {
        try {
            HashMap<String, Object> param = new HashMap();
            param.put("name", "床型");
            List<ItripLabelDic> listByMap = labelDicService.getItripLabelDicListByMap(param);
            if (listByMap.size() > 0 && EmptyUtils.isNotEmpty(listByMap.get(0))) {
                param.clear();
                param.put("parentId", listByMap.get(0).getId());
                List<ItripLabelDic> labelDicListByMap = labelDicService.getItripLabelDicListByMap(param);
                List<ItripLabelDicVO> list = new ArrayList<>();
                ItripLabelDicVO vo = null;
                for (ItripLabelDic item : labelDicListByMap) {
                    vo = new ItripLabelDicVO();
                    BeanUtils.copyProperties(item, vo);
                    list.add(vo);
                }
                return DtoUtils.returnSuccess("获取床型成功", list);
            }
            return DtoUtils.returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtils.returnFail("获取酒店房间床型失败", "100305");
        }
    }


}
