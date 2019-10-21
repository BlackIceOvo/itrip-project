package com.itrip.utils.common;

import lombok.Data;

/**
 * SystemConfig
 * @author wangrj
 *
 */
@Data
public class SystemConfig {

	/**
	 * 文件上传路径，通过properties文件进行配置
	 */
	private String fileUploadPathString;
	/**
	 * 上传文件访问URL，通过properties文件进行配置
	 */
	private String visitImgUrlString;
	/**
	 * 生成订单的机器码，通过properties文件进行配置
	 */
	private String machineCode;

	private String orderProcessOK;

	private String orderProcessCancel;

	/**
	 * 云通信短信平台账户Account Sid
	 */
	private String smsAccountSid;
	/**
	 * 云通信短信平台账户Auth Toke
	 */
	private String smsAuthToken;
	/**
	 * 云通信短信平台账户App ID
	 */
	private String smsAppID;
	/**
	 * 云通信短信平台Server IP
	 */
	private String smsServerIP;
	/**
	 * 云通信短信平台Server Port
	 */
	private String smsServerPort;
	
	/**
	 * 在线支付交易完成通知后续处理接口的地址
	 */
	private String tradeEndsUrl;	

	/**
	 * 支付模块发送Get请求是否使用代理
	 */
	private Boolean tradeUseProxy;
	/**
	 * 代理主机
	 */
	private String tradeProxyHost;
	/**
	 * 代理端口
	 */
	private Integer tradeProxyPort;

	/**
	 * 账号发送成功 带回来的信息
	 */
	private   String sendSuccess ;



}
