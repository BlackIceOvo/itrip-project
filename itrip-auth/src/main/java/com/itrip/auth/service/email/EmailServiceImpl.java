package com.itrip.auth.service.email;

import com.itrip.utils.common.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ice
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class EmailServiceImpl implements EmailService {

    @Autowired
    private SimpleMailMessage simpleMailMessage;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 发送邮件
     *
     * @param code
     * @param userCode
     */
    @Override
    public void send(String code, String userCode) {
        simpleMailMessage.setTo(userCode);
        simpleMailMessage.setText("欢迎注册爱旅行 ! 您的注册激活码为:" + code + ",请尽快激活!");
        mailSender.send(simpleMailMessage);
        this.redisUtils.setExpire("activeEmail:" + userCode, code, 10000 * 60);
    }
}
