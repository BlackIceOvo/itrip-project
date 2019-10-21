package com.itrip.utils.common;

import io.swagger.models.auth.In;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisUtils {
    private StringRedisTemplate stringRedisTemplate;

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * redis插入数据
     * @param key
     * @param value
     */
     public void setValue(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
     }

    /**
     * 查询数据
     * @param key
     * @return
     */
     public String getValue(String key){
        return stringRedisTemplate.opsForValue().get(key);
     }

    /**
     * 删除数据
     * @param key
     */
     public void del(String key){
         stringRedisTemplate.delete(key);
     }

    /**
     * 设置数据的有效时间
     * @param key
     * @param value
     * @param time
     */
     public void setExpire(String key,String value,long time){
         stringRedisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
     }

    /**
     * 判断数据是否存在
     * @param key
     * @return
     */
     public boolean setExists(String key){
         return stringRedisTemplate.hasKey(key);
     }

    /**
     * 获得Key时间
     * @param key
     * @return
     */
    public Long getExpire(String key){
        return stringRedisTemplate.getExpire(key);
    }

}
