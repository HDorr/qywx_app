/**   
* @Title: PermanentCodeVo.java
* @Package com.ziwow.qyhapp.vo
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-11-30 下午3:20:59
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.vo;

import java.io.Serializable;

/**
 * @ClassName: PermanentCodeVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-11-30 下午3:20:59
 * 
 */
public class PermanentCodeVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String access_token;//授权方（企业）access_token
	private String expires_in;
	private String permanent_code;//企业号永久授权码。长度为64至512个字节
	private AuthCorpInfoVo auth_corp_info;//授权方企业信息
	private AuthInfoVo auth_info;//授权信息
	private AuthUserInfoVo auth_user_info;//授权管理员的信息
	private String authCorpInfo;//授权方企业信息
	private String authInfo;//授权信息
	private String authUserInfo;//授权管理员的信息
	
	
	
	/**
	 * @return the authCorpInfo
	 */
	public String getAuthCorpInfo() {
		return authCorpInfo;
	}
	/**
	 * @param authCorpInfo the authCorpInfo to set
	 */
	public void setAuthCorpInfo(String authCorpInfo) {
		this.authCorpInfo = authCorpInfo;
	}
	/**
	 * @return the authInfo
	 */
	public String getAuthInfo() {
		return authInfo;
	}
	/**
	 * @param authInfo the authInfo to set
	 */
	public void setAuthInfo(String authInfo) {
		this.authInfo = authInfo;
	}
	/**
	 * @return the authUserInfo
	 */
	public String getAuthUserInfo() {
		return authUserInfo;
	}
	/**
	 * @param authUserInfo the authUserInfo to set
	 */
	public void setAuthUserInfo(String authUserInfo) {
		this.authUserInfo = authUserInfo;
	}
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
	 * @return the expires_in
	 */
	public String getExpires_in() {
		return expires_in;
	}
	/**
	 * @param expires_in the expires_in to set
	 */
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	/**
	 * @return the permanent_code
	 */
	public String getPermanent_code() {
		return permanent_code;
	}
	/**
	 * @param permanent_code the permanent_code to set
	 */
	public void setPermanent_code(String permanent_code) {
		this.permanent_code = permanent_code;
	}
	/**
	 * @return the auth_corp_info
	 */
	public AuthCorpInfoVo getAuth_corp_info() {
		return auth_corp_info;
	}
	/**
	 * @param auth_corp_info the auth_corp_info to set
	 */
	public void setAuth_corp_info(AuthCorpInfoVo auth_corp_info) {
		this.auth_corp_info = auth_corp_info;
	}
	/**
	 * @return the auth_info
	 */
	public AuthInfoVo getAuth_info() {
		return auth_info;
	}
	/**
	 * @param auth_info the auth_info to set
	 */
	public void setAuth_info(AuthInfoVo auth_info) {
		this.auth_info = auth_info;
	}
	/**
	 * @return the auth_user_info
	 */
	public AuthUserInfoVo getAuth_user_info() {
		return auth_user_info;
	}
	/**
	 * @param auth_user_info the auth_user_info to set
	 */
	public void setAuth_user_info(AuthUserInfoVo auth_user_info) {
		this.auth_user_info = auth_user_info;
	}
	
	
}
