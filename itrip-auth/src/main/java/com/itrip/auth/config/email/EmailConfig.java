package com.itrip.auth.config.email;

import com.itrip.auth.config.EmailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author ice
 */
@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    SimpleMailMessage mailMessage(){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(emailProperties.getUsername());
        return simpleMailMessage;
    }
    @Bean
    JavaMailSender mailSender(){
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setHost(emailProperties.getHost());
        javaMailSender.setUsername(emailProperties.getUsername());
        javaMailSender.setPassword(emailProperties.getPassword());
        javaMailSender.setDefaultEncoding(emailProperties.getEncoding());
        Properties properties= new Properties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.socketFactory","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.port",emailProperties.getPort());
        properties.put("mail.smtp.ssl.enable",true);
        properties.put("mail.smtp.socketFactory.port", emailProperties.getPort());
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
 }
