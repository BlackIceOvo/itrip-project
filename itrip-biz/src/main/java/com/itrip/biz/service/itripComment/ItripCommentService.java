package com.itrip.biz.service.itripComment;

import com.itrip.utils.common.Page;
import com.itrip.beans.pojo.ItripComment;

import java.util.List;
import java.util.Map;

/**
 * @Author: wangrj
 * @Date: 2019/5/20 下午9:26
 */
public interface ItripCommentService {

      public ItripComment getItripCommentById(Long id)throws Exception;

    public List<ItripComment>	getItripCommentListByMap(Map<String, Object> param)throws Exception;

    public Integer getItripCommentCountByMap(Map<String, Object> param)throws Exception;

    public Integer itriptxAddItripComment(ItripComment itripComment)throws Exception;

    public Integer itriptxModifyItripComment(ItripComment itripComment)throws Exception;

    public Integer itriptxDeleteItripCommentById(Long id)throws Exception;

    public Page<ItripComment> queryItripCommentPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
