package com.itrip.auth.config;


import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ice
 */
@Data
@Configuration
@PropertySource("classpath:email.properties")
public class EmailProperties {
    @Value("${email.host}")
    private String host;
    @Value("${email.username}")
    private String username;
    @Value("${email.password}")
    private String  password;
    @Value("${email.encoding}")
    private String  encoding;
    @Value("${email.port}")
    private Integer port;
}
