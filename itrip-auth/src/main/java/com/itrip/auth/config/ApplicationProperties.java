package com.itrip.auth.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ice
 */
@Data
@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

    @Value("${application.encoding}")
    private String encoding;

}
