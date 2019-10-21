package com.itrip.biz.service.itripAreaDic;

import com.itrip.utils.common.Page;
import com.itrip.beans.pojo.ItripAreaDic;

import java.util.List;
import java.util.Map;

/**
 * @author ice
 */
public interface ItripAreaDicService {

     ItripAreaDic getItripAreaDicById(Long id)throws Exception;

     List<ItripAreaDic>	getItripAreaDicListByMap(Map<String, Object> param)throws Exception;

     Integer getItripAreaDicCountByMap(Map<String, Object> param)throws Exception;

     Integer itriptxAddItripAreaDic(ItripAreaDic itripAreaDic)throws Exception;

     Integer itriptxModifyItripAreaDic(ItripAreaDic itripAreaDic)throws Exception;

     Integer itriptxDeleteItripAreaDicById(Long id)throws Exception;

     Page<ItripAreaDic> queryItripAreaDicPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
