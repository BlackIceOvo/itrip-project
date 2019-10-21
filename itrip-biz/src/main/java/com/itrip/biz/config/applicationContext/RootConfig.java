package com.itrip.biz.config.applicationContext;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ice
 */
@Configuration
@ComponentScan(value = {"com.itrip.biz.config","com.itrip.biz.service"})
public class RootConfig {
}
