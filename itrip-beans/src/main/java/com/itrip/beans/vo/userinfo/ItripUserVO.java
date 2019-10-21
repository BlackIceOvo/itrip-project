package com.itrip.beans.vo.userinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 接收客户端表单中的用户注册信息VO
 *
 * @author hduser
 *
 */
@Data
@ApiModel(value = "ItripUserVO", description = "用户注册信息")
public class ItripUserVO {

	@ApiModelProperty("[必填] 注册用户名称")
	private String userCode;
	@ApiModelProperty("[必填] 密码")
	private String userPassword;
	/*
	@ApiModelProperty("[非必填] 用户类型：0自注册、1微信、2QQ、3微博")
	private Integer userType;
	@ApiModelProperty("[非必填] 平台ID")
	private Long flatID;
	*/
	@ApiModelProperty("[非必填] 昵称")
	private String userName="";
	/*
	@ApiModelProperty("[非必填] 微信号")
	private String weChat;
	@ApiModelProperty("[非必填] QQ号")
	private String QQ;
	@ApiModelProperty("[非必填] 微博号")
	private String weibo;
	@ApiModelProperty("[非必填] 百度号")
	private String baidu;
	*/


}
