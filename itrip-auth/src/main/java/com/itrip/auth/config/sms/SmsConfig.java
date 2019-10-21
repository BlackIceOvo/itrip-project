package com.itrip.auth.config.sms;

import com.itrip.auth.config.SmsProperties;
import com.itrip.utils.common.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ice
 */
@Configuration
public class SmsConfig {

    @Autowired
    SmsProperties smsProperties;

    @Bean
    public SystemConfig systemConfig(){
        SystemConfig systemConfig=new SystemConfig();
        systemConfig.setSmsAccountSid(smsProperties.getAccountSid());
        systemConfig.setSmsAuthToken(smsProperties.getAuthToken());
        systemConfig.setSmsAppID(smsProperties.getAppId());
        systemConfig.setSmsServerIP(smsProperties.getServerIp());
        systemConfig.setSmsServerPort(smsProperties.getServerPort());
        systemConfig.setSendSuccess(smsProperties.getSendSuccess());
        return  systemConfig;
    }
}
