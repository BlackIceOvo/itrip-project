package com.itrip.biz.service.itripComment;

import com.itrip.utils.common.Constants;
import com.itrip.utils.common.EmptyUtils;
import com.itrip.utils.common.Page;
import com.itrip.dao.mapper.itripComment.ItripCommentMapper;
import com.itrip.beans.pojo.ItripComment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ItripCommentServiceImpl implements ItripCommentService {

    @Resource
    private ItripCommentMapper itripCommentMapper;

    @Override
    public ItripComment getItripCommentById(Long id) throws Exception {
        return itripCommentMapper.getItripCommentById(id);
    }

    @Override
    public List<ItripComment> getItripCommentListByMap(Map<String, Object> param) throws Exception {
        return itripCommentMapper.getItripCommentListByMap(param);
    }

    @Override
    public Integer getItripCommentCountByMap(Map<String, Object> param) throws Exception {
        return itripCommentMapper.getItripCommentCountByMap(param);
    }

    @Override
    public Integer itriptxAddItripComment(ItripComment itripComment) throws Exception {
        itripComment.setCreationDate(new Date());
        return itripCommentMapper.insertItripComment(itripComment);
    }

    @Override
    public Integer itriptxModifyItripComment(ItripComment itripComment) throws Exception {
        itripComment.setModifyDate(new Date());
        return itripCommentMapper.updateItripComment(itripComment);
    }

    @Override
    public Integer itriptxDeleteItripCommentById(Long id) throws Exception {
        return itripCommentMapper.deleteItripCommentById(id);
    }

    @Override
    public Page<ItripComment> queryItripCommentPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        Integer total = itripCommentMapper.getItripCommentCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripComment> itripCommentList = itripCommentMapper.getItripCommentListByMap(param);
        page.setRows(itripCommentList);
        return page;
    }

}
