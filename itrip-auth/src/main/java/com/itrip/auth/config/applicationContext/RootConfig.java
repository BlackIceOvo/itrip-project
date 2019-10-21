package com.itrip.auth.config.applicationContext;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 全局配置 管理
 * @author ice
 */
@Configuration
@ComponentScan(value = {"com.itrip.auth.config","com.itrip.auth.service"})

public class RootConfig {


}
