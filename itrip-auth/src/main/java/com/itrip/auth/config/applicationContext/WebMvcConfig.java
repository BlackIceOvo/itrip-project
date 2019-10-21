package com.itrip.auth.config.applicationContext;

import com.itrip.auth.config.ApplicationProperties;
import com.itrip.auth.config.swagger.SwaggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;


/**
 * Spring Mvc 管理
 * @author ice
 */
@EnableWebMvc
@Configuration
@ComponentScan(value = {"com.itrip.auth.controller"},basePackageClasses = SwaggerConfig.class)
public class WebMvcConfig implements WebMvcConfigurer {


    @Autowired
    ApplicationProperties applicationProperties;

    /**
     *放行静态资源
     * @param configurer
     */
	 @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 设置响应编码
     * @return
     */
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName(applicationProperties.getEncoding()));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
    }
}
