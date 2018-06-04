/**   
* @Title: AccessToken.java
* @Package com.ziwow.marketing.weixin.util
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-8-4 下午3:05:13
* @version V1.0   
*/
package com.ziwow.scrmapp.weixin.util;

import java.io.Serializable;

/**
 * @ClassName: AccessToken
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-8-4 下午3:05:13
 * 
 */
public class AccessToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String access_token;
	private String refresh_token;
	private String openid;
	private String scope;
	/**
	 * @return the access_token
	 */
	public String getAccess_token() {
		return access_token;
	}
	/**
	 * @param access_token the access_token to set
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	/**
	 * @return the refresh_token
	 */
	public String getRefresh_token() {
		return refresh_token;
	}
	/**
	 * @param refresh_token the refresh_token to set
	 */
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}
	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	
}
