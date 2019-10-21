package com.itrip.utils.common;

import com.itrip.beans.pojo.ItripUser;
import com.alibaba.fastjson.JSONObject;

/**
 * Token验证
 * Created by hanlu on 2017/5/7.
 */
public class ValidationToken {


    //private RedisAPI redisAPI;

    private RedisUtils redisUtil;

    public RedisUtils getRedisUtil() {
        return redisUtil;
    }

    public void setRedisUtil(RedisUtils redisUtil) {
        this.redisUtil = redisUtil;
    }

//    public RedisAPI getRedisAPI() {
//        return redisAPI;
//    }
//    public void setRedisAPI(RedisAPI redisAPI) {
//        this.redisAPI = redisAPI;
//    }
    public ItripUser getCurrentUser(String tokenString){
        //根据token从redis中获取用户信息
			/*
			 test token:
			 key : token:1qaz2wsx
			 value : {"id":"100078","userCode":"myusercode","userPassword":"78ujsdlkfjoiiewe98r3ejrf","userType":"1","flatID":"10008989"}

			*/
        ItripUser itripUser = null;
        if(null == tokenString || "".equals(tokenString)){
            return null;
        }
        try{
            //String userInfoJson = redisAPI.get(tokenString);
            String userInfoJson = redisUtil.getValue(tokenString);
            itripUser = JSONObject.parseObject(userInfoJson,ItripUser.class);
        }catch(Exception e){
            itripUser = null;
        }
        return itripUser;
    }

    /**
     *  验证token
     * @param agent 请求头中header中的User-Agent
     * @param token 前端传过来的token
     * @return
     * @throws Exception
     */
    public boolean valiate(String agent, String token) throws Exception{
        //1 验证token是否存在
        if(!redisUtil.setExists(token)){
            return false;
        }else{
            //2 如果存在,是否是redis中的token
            String code=  token.split("-")[4];
            return code.equals(MD5.getMd5(agent,6));
        }
    }
}
