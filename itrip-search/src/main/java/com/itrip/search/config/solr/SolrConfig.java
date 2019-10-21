package com.itrip.search.config.solr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;


/**
 * @author ice
 */
@Configuration
public class SolrConfig {

    @Bean
    SolrTemplate solrTemplate() {
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:solr.xml");
        return ioc.getBean(SolrTemplate.class);
    }

}
