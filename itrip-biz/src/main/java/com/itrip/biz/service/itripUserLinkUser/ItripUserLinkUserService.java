package com.itrip.biz.service.itripUserLinkUser;

import com.itrip.utils.common.Page;
import com.itrip.beans.pojo.ItripUserLinkUser;

import java.util.List;
import java.util.Map;

/**
 * @Author: wangrj
 * @Date: 2019/5/20 下午9:26
 */
public interface ItripUserLinkUserService {

    public ItripUserLinkUser getItripUserLinkUserById(Long id)throws Exception;

    public List<ItripUserLinkUser>	getItripUserLinkUserListByMap(Map<String, Object> param)throws Exception;

    public Integer getItripUserLinkUserCountByMap(Map<String, Object> param)throws Exception;

    public Integer itriptxAddItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;

    public Integer itriptxModifyItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;

    public Integer itriptxDeleteItripUserLinkUserById(Long id)throws Exception;

    public Page<ItripUserLinkUser> queryItripUserLinkUserPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;

    public boolean validationOrderStatus(Integer id)throws Exception;
}
