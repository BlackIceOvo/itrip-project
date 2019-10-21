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
@PropertySource("classpath:SMS.properties")
public class SmsProperties {
    @Value("${sms.accountSid}")
    private String accountSid;
    @Value("${sms.authToken}")
    private String  authToken;
    @Value("${sms.appID}")
    private String appId;
    @Value("${sms.serverIP}")
    private String serverIp;
    @Value("${sms.serverPort}")
    private String serverPort;
    @Value("${sms.sendSuccess}")
    private String sendSuccess;
}
