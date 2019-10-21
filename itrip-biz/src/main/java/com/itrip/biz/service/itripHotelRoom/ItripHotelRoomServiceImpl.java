package com.itrip.biz.service.itripHotelRoom;

import com.itrip.beans.pojo.ItripHotelTempStore;
import com.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import com.itrip.beans.vo.hotelroom.SearchHotelRoomVO;
import com.itrip.biz.service.itripHotelOrder.ItripHotelOrderService;
import com.itrip.utils.common.Constants;
import com.itrip.utils.common.EmptyUtils;
import com.itrip.utils.common.Page;
import com.itrip.dao.mapper.itripHotelRoom.ItripHotelRoomMapper;
import com.itrip.beans.pojo.ItripHotelRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = RuntimeException.class)

public class ItripHotelRoomServiceImpl implements ItripHotelRoomService {

    @Resource
    private ItripHotelRoomMapper itripHotelRoomMapper;

    @Autowired
    private ItripHotelOrderService itripHotelOrderService;

    @Override
    public ItripHotelRoom getItripHotelRoomById(Long id)throws Exception{
        return itripHotelRoomMapper.getItripHotelRoomById(id);
    }

    @Override
    public List<ItripHotelRoom>	getItripHotelRoomListByMap(Map<String,Object> param)throws Exception{
        return itripHotelRoomMapper.getItripHotelRoomListByMap(param);
    }

    @Override
    public Integer getItripHotelRoomCountByMap(Map<String,Object> param)throws Exception{
        return itripHotelRoomMapper.getItripHotelRoomCountByMap(param);
    }
    @Override
    public Integer itriptxAddItripHotelRoom(ItripHotelRoom itripHotelRoom)throws Exception{
            itripHotelRoom.setCreationDate(new Date());
            return itripHotelRoomMapper.insertItripHotelRoom(itripHotelRoom);
    }
    @Override
    public Integer itriptxModifyItripHotelRoom(ItripHotelRoom itripHotelRoom)throws Exception{
        itripHotelRoom.setModifyDate(new Date());
        return itripHotelRoomMapper.updateItripHotelRoom(itripHotelRoom);
    }
    @Override
    public Integer itriptxDeleteItripHotelRoomById(Long id)throws Exception{
        return itripHotelRoomMapper.deleteItripHotelRoomById(id);
    }

    @Override
    public Page<ItripHotelRoom> queryItripHotelRoomPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripHotelRoomMapper.getItripHotelRoomCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripHotelRoom> itripHotelRoomList = itripHotelRoomMapper.getItripHotelRoomListByMap(param);
        page.setRows(itripHotelRoomList);
        return page;
    }




    @Override
    public List<ItripHotelRoomVO> findItripHotelRoomBy(SearchHotelRoomVO searchHotelRoomVO)  {
        return itripHotelRoomMapper.getItripQueryHotelRoomByHotel(searchHotelRoomVO);
    }

    // 判断库存是否充足
    @Override
    public boolean isSufficient( HashMap<String,Object> param ) {
        List<ItripHotelTempStore> itripHotelTempStores = this.itripHotelOrderService.queryRoomStore(param);
        if(itripHotelTempStores.size()>0 && EmptyUtils.isNotEmpty(itripHotelTempStores.get(0))){
            if(itripHotelTempStores.get(0).getStore()>0){
                return true;
            }
        }
        return false;
    }

}
