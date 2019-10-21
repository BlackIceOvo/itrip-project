package com.itrip.biz.service.itripHotelTradingArea;

import com.itrip.utils.common.Constants;
import com.itrip.utils.common.EmptyUtils;
import com.itrip.utils.common.Page;
import com.itrip.dao.mapper.itripHotelTradingArea.ItripHotelTradingAreaMapper;
import com.itrip.beans.pojo.ItripHotelTradingArea;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = RuntimeException.class)

public class ItripHotelTradingAreaServiceImpl implements ItripHotelTradingAreaService {

    @Resource
    private ItripHotelTradingAreaMapper itripHotelTradingAreaMapper;
    @Override

    public ItripHotelTradingArea getItripHotelTradingAreaById(Long id)throws Exception{
        return itripHotelTradingAreaMapper.getItripHotelTradingAreaById(id);
    }
    @Override

    public List<ItripHotelTradingArea>	getItripHotelTradingAreaListByMap(Map<String,Object> param)throws Exception{
        return itripHotelTradingAreaMapper.getItripHotelTradingAreaListByMap(param);
    }
    @Override

    public Integer getItripHotelTradingAreaCountByMap(Map<String,Object> param)throws Exception{
        return itripHotelTradingAreaMapper.getItripHotelTradingAreaCountByMap(param);
    }
    @Override

    public Integer itriptxAddItripHotelTradingArea(ItripHotelTradingArea itripHotelTradingArea)throws Exception{
            itripHotelTradingArea.setCreationDate(new Date());
            return itripHotelTradingAreaMapper.insertItripHotelTradingArea(itripHotelTradingArea);
    }
    @Override

    public Integer itriptxModifyItripHotelTradingArea(ItripHotelTradingArea itripHotelTradingArea)throws Exception{
        itripHotelTradingArea.setModifyDate(new Date());
        return itripHotelTradingAreaMapper.updateItripHotelTradingArea(itripHotelTradingArea);
    }
    @Override

    public Integer itriptxDeleteItripHotelTradingAreaById(Long id)throws Exception{
        return itripHotelTradingAreaMapper.deleteItripHotelTradingAreaById(id);
    }
    @Override

    public Page<ItripHotelTradingArea> queryItripHotelTradingAreaPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripHotelTradingAreaMapper.getItripHotelTradingAreaCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripHotelTradingArea> itripHotelTradingAreaList = itripHotelTradingAreaMapper.getItripHotelTradingAreaListByMap(param);
        page.setRows(itripHotelTradingAreaList);
        return page;
    }

}
