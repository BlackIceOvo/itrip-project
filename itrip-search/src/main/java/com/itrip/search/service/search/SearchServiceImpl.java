package com.itrip.search.service.search;

import com.alibaba.druid.util.StringUtils;
import com.itrip.beans.vo.hotel.ItripHotelVO;
import com.itrip.beans.vo.hotel.SearchHotCityVO;
import com.itrip.beans.vo.hotel.SearchHotelVO;
import com.itrip.utils.common.Constants;
import com.itrip.utils.common.EmptyUtils;
import com.itrip.utils.common.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 酒店search 接口实现类
 * @author ice
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrTemplate solrTemplate;



    @Override
    public Page searchItripHotelPage(SearchHotelVO vo) throws Exception {
        Query query=new SimpleQuery("*:*");
        Criteria criteria=new Criteria("destination");

        // 目的地
        if(EmptyUtils.isNotEmpty(vo.getDestination())){
            criteria=criteria.contains(vo.getDestination());
        }
        //关键词
        if(EmptyUtils.isNotEmpty(vo.getKeywords())){
            criteria=criteria.and("keyword").contains(vo.getKeywords());
        }
        //位置(商圈)
        if(EmptyUtils.isNotEmpty(vo.getTradeAreaIds())){
            String[] tradeAreaIds = vo.getTradeAreaIds().split(",");
            for (String s:tradeAreaIds){
                criteria = criteria.and("tradingAreaIds").contains("," + s + ",");
            }
        }




        //价格 高
        if(EmptyUtils.isNotEmpty(vo.getMaxPrice())){
            criteria= criteria.and("minPrice").lessThanEqual(vo.getMaxPrice());
        }
        // 价格 低
        if(EmptyUtils.isNotEmpty(vo.getMinPrice())){
            criteria=   criteria.and("minPrice").greaterThanEqual(vo.getMinPrice());
        }
        // 星级
        if(EmptyUtils.isNotEmpty(vo.getHotelLevel())){
            criteria=   criteria.and("hotelLevel").is(vo.getHotelLevel());
        }

        // 特色
        if(EmptyUtils.isNotEmpty(vo.getFeatureIds())){
            String[] featureIds = vo.getFeatureIds().split(",");
            for (String s:featureIds){
                criteria = criteria.and("featureIds").contains(","+s+",");
            }
        }
        //升序
        if(EmptyUtils.isNotEmpty(vo.getAscSort())){
            query.addSort(new Sort(Sort.Direction.ASC,vo.getAscSort()));
        }
        //降序
        if(EmptyUtils.isNotEmpty(vo.getDescSort())){
            query.addSort(new Sort(Sort.Direction.DESC,vo.getDescSort()));
        }
        //当前页
        int pageNo= Constants.DEFAULT_PAGE_NO;
        if(EmptyUtils.isNotEmpty(vo.getPageNo())){
            pageNo=vo.getPageNo();
        }
        int pageSize=Constants.DEFAULT_PAGE_SIZE;
        if(EmptyUtils.isNotEmpty(vo.getPageSize())){
            pageSize=vo.getPageSize();
        }
        //分页之 第几页
        query.setOffset((long)(pageNo-1)*pageSize);
        //分页之 一行显示几条数据
        query.setRows(pageSize);
        query.addCriteria(criteria);
        ScoredPage<ItripHotelVO> vos=solrTemplate.queryForPage("itrip_core",query,ItripHotelVO.class);
        Page page=new Page(pageNo,pageSize,new Long(vos.getTotalElements()).intValue());
        page.setRows(vos.getContent());
        return page;
    }

    @Override
    public List<ItripHotelVO> searchItripHotelListByHotCity(SearchHotCityVO vo) {
        Query query=new SimpleQuery("*:*");
        Criteria criteria=new Criteria("cityId");
        criteria.is(vo.getCityId());
        if(EmptyUtils.isNotEmpty(vo.getCount())){
            query.setRows(vo.getCount());
        }
        query.addCriteria(criteria);
        ScoredPage<ItripHotelVO> vos=solrTemplate.queryForPage("itrip_core",query,ItripHotelVO.class);
        return vos.getContent();
    }
}
