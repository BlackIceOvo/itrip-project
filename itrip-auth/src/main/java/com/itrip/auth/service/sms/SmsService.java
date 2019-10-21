package com.itrip.auth.service.sms;

import com.itrip.beans.dto.Dto;

/**
 * @author ice
 */
public interface SmsService {
    void send(String to,String template,String [] datas);
}
