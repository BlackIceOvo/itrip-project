package com.itrip.biz.service.itripTradeEnds;

import com.itrip.utils.common.Page;
import com.itrip.beans.pojo.ItripTradeEnds;

import java.util.List;
import java.util.Map;

/**
 * @Author: wangrj
 * @Date: 2019/5/20 下午9:26
 */
public interface ItripTradeEndsService {

    public ItripTradeEnds getItripTradeEndsById(Long id)throws Exception;

    public List<ItripTradeEnds>	getItripTradeEndsListByMap(Map<String, Object> param)throws Exception;

    public Integer getItripTradeEndsCountByMap(Map<String, Object> param)throws Exception;

    public Integer itriptxAddItripTradeEnds(ItripTradeEnds itripTradeEnds)throws Exception;

    public Integer itriptxModifyItripTradeEnds(ItripTradeEnds itripTradeEnds)throws Exception;

    public Integer itriptxDeleteItripTradeEndsById(Long id)throws Exception;

    public Page<ItripTradeEnds> queryItripTradeEndsPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
