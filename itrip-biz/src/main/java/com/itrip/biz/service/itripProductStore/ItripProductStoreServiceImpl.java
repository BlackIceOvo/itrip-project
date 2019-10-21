package com.itrip.biz.service.itripProductStore;

import com.itrip.utils.common.Constants;
import com.itrip.utils.common.EmptyUtils;
import com.itrip.utils.common.Page;
import com.itrip.dao.mapper.itripProductStore.ItripProductStoreMapper;
import com.itrip.beans.pojo.ItripProductStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = RuntimeException.class)

public class ItripProductStoreServiceImpl implements ItripProductStoreService {

    @Resource
    private ItripProductStoreMapper itripProductStoreMapper;

    @Override

    public ItripProductStore getItripProductStoreById(Long id)throws Exception{
        return itripProductStoreMapper.getItripProductStoreById(id);
    }
    @Override

    public List<ItripProductStore>	getItripProductStoreListByMap(Map<String,Object> param)throws Exception{
        return itripProductStoreMapper.getItripProductStoreListByMap(param);
    }
    @Override

    public Integer getItripProductStoreCountByMap(Map<String,Object> param)throws Exception{
        return itripProductStoreMapper.getItripProductStoreCountByMap(param);
    }
    @Override

    public Integer itriptxAddItripProductStore(ItripProductStore itripProductStore)throws Exception{
            itripProductStore.setCreationDate(new Date());
            return itripProductStoreMapper.insertItripProductStore(itripProductStore);
    }
    @Override

    public Integer itriptxModifyItripProductStore(ItripProductStore itripProductStore)throws Exception{
        itripProductStore.setModifyDate(new Date());
        return itripProductStoreMapper.updateItripProductStore(itripProductStore);
    }
    @Override

    public Integer itriptxDeleteItripProductStoreById(Long id)throws Exception{
        return itripProductStoreMapper.deleteItripProductStoreById(id);
    }
    @Override

    public Page<ItripProductStore> queryItripProductStorePageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripProductStoreMapper.getItripProductStoreCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripProductStore> itripProductStoreList = itripProductStoreMapper.getItripProductStoreListByMap(param);
        page.setRows(itripProductStoreList);
        return page;
    }

}
