package com.itrip.search.config.applicationContext;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 全局配置 管理
 * @author ice
 */
@Configuration
@ComponentScan({"com.itrip.search.config","com.itrip.search.service"})
public class RootConfig {


}
