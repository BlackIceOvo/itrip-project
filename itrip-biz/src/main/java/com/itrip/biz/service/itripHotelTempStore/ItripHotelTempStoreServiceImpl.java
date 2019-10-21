package com.itrip.biz.service.itripHotelTempStore;

import com.itrip.utils.common.Constants;
import com.itrip.utils.common.EmptyUtils;
import com.itrip.utils.common.Page;
import com.itrip.dao.mapper.itripHotelTempStore.ItripHotelTempStoreMapper;
import com.itrip.beans.pojo.ItripHotelTempStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = RuntimeException.class)

public class ItripHotelTempStoreServiceImpl implements ItripHotelTempStoreService {

    @Resource
    private ItripHotelTempStoreMapper itripHotelTempStoreMapper;
    @Override

    public ItripHotelTempStore getItripHotelTempStoreById(Long id)throws Exception{
        return itripHotelTempStoreMapper.getItripHotelTempStoreById(id);
    }    @Override


    public List<ItripHotelTempStore>	getItripHotelTempStoreListByMap(Map<String,Object> param)throws Exception{
        return itripHotelTempStoreMapper.getItripHotelTempStoreListByMap(param);
    }
    @Override

    public Integer getItripHotelTempStoreCountByMap(Map<String,Object> param)throws Exception{
        return itripHotelTempStoreMapper.getItripHotelTempStoreCountByMap(param);
    }
    @Override

    public Integer itriptxAddItripHotelTempStore(ItripHotelTempStore itripHotelTempStore)throws Exception{
            itripHotelTempStore.setCreationDate(new Date());
            return itripHotelTempStoreMapper.insertItripHotelTempStore(itripHotelTempStore);
    }
    @Override

    public Integer itriptxModifyItripHotelTempStore(ItripHotelTempStore itripHotelTempStore)throws Exception{
        itripHotelTempStore.setModifyDate(new Date());
        return itripHotelTempStoreMapper.updateItripHotelTempStore(itripHotelTempStore);
    }
    @Override

    public Integer itriptxDeleteItripHotelTempStoreById(Long id)throws Exception{
        return itripHotelTempStoreMapper.deleteItripHotelTempStoreById(id);
    }
    @Override

    public Page<ItripHotelTempStore> queryItripHotelTempStorePageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripHotelTempStoreMapper.getItripHotelTempStoreCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripHotelTempStore> itripHotelTempStoreList = itripHotelTempStoreMapper.getItripHotelTempStoreListByMap(param);
        page.setRows(itripHotelTempStoreList);
        return page;
    }

}
