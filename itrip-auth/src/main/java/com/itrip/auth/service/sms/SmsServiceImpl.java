package com.itrip.auth.service.sms;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.itrip.beans.dto.Dto;
import com.itrip.utils.common.RedisUtils;
import com.itrip.utils.common.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Set;

/**
 * @author ice
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SmsServiceImpl implements SmsService {


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SystemConfig systemConfig;

    @Override
    public void send(String to, String template, String[] datas) {
        HashMap<String, Object> result = null;
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init(systemConfig.getSmsServerIP(), systemConfig.getSmsServerPort());
        // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883.
        restAPI.setAccount(systemConfig.getSmsAccountSid(), systemConfig.getSmsAuthToken());
        // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
        restAPI.setAppId(systemConfig.getSmsAppID());
        // 请使用管理控制台中已创建应用的APPID。
        result = restAPI.sendTemplateSMS(to, template, datas);
        System.out.println("SDKTestGetSubAccounts result=" + result);
        if (systemConfig.getSendSuccess().equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
            this.redisUtils.setExpire("activePhone:"+to,datas[0],10000 * 60);
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }
    }
}
