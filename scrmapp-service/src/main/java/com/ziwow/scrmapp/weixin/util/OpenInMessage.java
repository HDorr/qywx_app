/**
 * Project Name:ziwow-dubbo-model
 * File Name:VerifyTicket.java
 * Package Name:com.ziwow.dubbo.model.weixin.open
 * Date:2016-4-25上午11:18:53
 * Copyright (c) 2016, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.weixin.util;

import java.io.Serializable;

/**
 * ClassName: VerifyTicket <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016-4-25 上午11:18:53 <br/>
 *
 * @author daniel.wang
 * @version 
 * @since JDK 1.6
 */
public class OpenInMessage implements Serializable {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1744472964201004325L;
	//加密ticket
	private String AppId;
	private String Encrypt;
	
	//解密后的ticket
	private String CreateTime;
	private String InfoType;
	private String ComponentVerifyTicket;
	
	//授权消息
	private String AuthorizerAppid;
	private String AuthorizationCode;
	private String AuthorizationCodeExpiredTime;
	
	
	/**
	 * appId.
	 *
	 * @return  the appId
	 */
	public String getAppId() {
		return AppId;
	}
	/**
	 * appId.
	 *
	 * @param   appId    the appId to set
	 */
	public void setAppId(String appId) {
		AppId = appId;
	}
	/**
	 * encrypt.
	 *
	 * @return  the encrypt
	 */
	public String getEncrypt() {
		return Encrypt;
	}
	/**
	 * encrypt.
	 *
	 * @param   encrypt    the encrypt to set
	 */
	public void setEncrypt(String encrypt) {
		Encrypt = encrypt;
	}
	/**
	 * createTime.
	 *
	 * @return  the createTime
	 */
	public String getCreateTime() {
		return CreateTime;
	}
	/**
	 * createTime.
	 *
	 * @param   createTime    the createTime to set
	 */
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	/**
	 * infoType.
	 *
	 * @return  the infoType
	 */
	public String getInfoType() {
		return InfoType;
	}
	/**
	 * infoType.
	 *
	 * @param   infoType    the infoType to set
	 */
	public void setInfoType(String infoType) {
		InfoType = infoType;
	}
	/**
	 * componentVerifyTicket.
	 *
	 * @return  the componentVerifyTicket
	 */
	public String getComponentVerifyTicket() {
		return ComponentVerifyTicket;
	}
	/**
	 * componentVerifyTicket.
	 *
	 * @param   componentVerifyTicket    the componentVerifyTicket to set
	 */
	public void setComponentVerifyTicket(String componentVerifyTicket) {
		ComponentVerifyTicket = componentVerifyTicket;
	}
	/**
	 * authorizerAppid.
	 *
	 * @return  the authorizerAppid
	 */
	public String getAuthorizerAppid() {
		return AuthorizerAppid;
	}
	/**
	 * authorizerAppid.
	 *
	 * @param   authorizerAppid    the authorizerAppid to set
	 */
	public void setAuthorizerAppid(String authorizerAppid) {
		AuthorizerAppid = authorizerAppid;
	}
	/**
	 * authorizationCode.
	 *
	 * @return  the authorizationCode
	 */
	public String getAuthorizationCode() {
		return AuthorizationCode;
	}
	/**
	 * authorizationCode.
	 *
	 * @param   authorizationCode    the authorizationCode to set
	 */
	public void setAuthorizationCode(String authorizationCode) {
		AuthorizationCode = authorizationCode;
	}
	/**
	 * authorizationCodeExpiredTime.
	 *
	 * @return  the authorizationCodeExpiredTime
	 */
	public String getAuthorizationCodeExpiredTime() {
		return AuthorizationCodeExpiredTime;
	}
	/**
	 * authorizationCodeExpiredTime.
	 *
	 * @param   authorizationCodeExpiredTime    the authorizationCodeExpiredTime to set
	 */
	public void setAuthorizationCodeExpiredTime(String authorizationCodeExpiredTime) {
		AuthorizationCodeExpiredTime = authorizationCodeExpiredTime;
	}
	
	
}

