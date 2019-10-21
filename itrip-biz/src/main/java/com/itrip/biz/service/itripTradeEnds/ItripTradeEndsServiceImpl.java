package com.itrip.biz.service.itripTradeEnds;

import com.itrip.utils.common.Constants;
import com.itrip.utils.common.EmptyUtils;
import com.itrip.utils.common.Page;
import com.itrip.dao.mapper.itripTradeEnds.ItripTradeEndsMapper;
import com.itrip.beans.pojo.ItripTradeEnds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ItripTradeEndsServiceImpl implements ItripTradeEndsService {

    @Resource
    private ItripTradeEndsMapper itripTradeEndsMapper;
    @Override

    public ItripTradeEnds getItripTradeEndsById(Long id)throws Exception{
        return itripTradeEndsMapper.getItripTradeEndsById(id);
    }
    @Override

    public List<ItripTradeEnds>	getItripTradeEndsListByMap(Map<String,Object> param)throws Exception{
        return itripTradeEndsMapper.getItripTradeEndsListByMap(param);
    }
    @Override

    public Integer getItripTradeEndsCountByMap(Map<String,Object> param)throws Exception{
        return itripTradeEndsMapper.getItripTradeEndsCountByMap(param);
    }
    @Override

    public Integer itriptxAddItripTradeEnds(ItripTradeEnds itripTradeEnds)throws Exception{
            //itripTradeEnds.setCreationDate(new Date());
            return itripTradeEndsMapper.insertItripTradeEnds(itripTradeEnds);
    }
    @Override

    public Integer itriptxModifyItripTradeEnds(ItripTradeEnds itripTradeEnds)throws Exception{
       // itripTradeEnds.setModifyDate(new Date());
        return itripTradeEndsMapper.updateItripTradeEnds(itripTradeEnds);
    }
    @Override

    public Integer itriptxDeleteItripTradeEndsById(Long id)throws Exception{
        return itripTradeEndsMapper.deleteItripTradeEndsById(id);
    }
    @Override

    public Page<ItripTradeEnds> queryItripTradeEndsPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripTradeEndsMapper.getItripTradeEndsCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripTradeEnds> itripTradeEndsList = itripTradeEndsMapper.getItripTradeEndsListByMap(param);
        page.setRows(itripTradeEndsList);
        return page;
    }

}
