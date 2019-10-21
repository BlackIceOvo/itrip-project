package com.itrip.auth.service.email;

/**
 * @author ice
 */
public interface EmailService {
    /**
     * 发送邮件
     * @param code
     * @param userCode
     */
    void send(String code,String userCode);
}
