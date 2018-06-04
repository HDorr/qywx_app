/**
 * Project Name:ziwow-dubbo-model
 * File Name:OpenAuthorizationWeixin.java
 * Package Name:com.ziwow.dubbo.model.weixin.open
 * Date:2016-4-27下午6:12:14
 * Copyright (c) 2016, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.wechat.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: OpenAuthorizationWeixin <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016-4-27 下午6:12:14 <br/>
 *
 * @author daniel.wang
 * @version 
 * @since JDK 1.6
 */
public class OpenAuthorizationWeixin implements Serializable {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String component_appid;
	private String authorizer_appid;
	private String authorization_code;
	private String authorizer_access_token;
	private String authorizer_refresh_token;
	private String func_info;
	private Date update_time;
	private String nick_name;
	private String head_img;
	private Integer service_type_info;
	private Integer verify_type_info;
	private String user_name;
	private String alias;
	private String business_info;
	private String qrcode_url;
	
	
	/**
	 * id.
	 *
	 * @return  the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * id.
	 *
	 * @param   id    the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * component_appid.
	 *
	 * @return  the component_appid
	 */
	public String getComponent_appid() {
		return component_appid;
	}
	/**
	 * component_appid.
	 *
	 * @param   component_appid    the component_appid to set
	 */
	public void setComponent_appid(String component_appid) {
		this.component_appid = component_appid;
	}
	/**
	 * authorizer_appid.
	 *
	 * @return  the authorizer_appid
	 */
	public String getAuthorizer_appid() {
		return authorizer_appid;
	}
	/**
	 * authorizer_appid.
	 *
	 * @param   authorizer_appid    the authorizer_appid to set
	 */
	public void setAuthorizer_appid(String authorizer_appid) {
		this.authorizer_appid = authorizer_appid;
	}
	/**
	 * authorization_code.
	 *
	 * @return  the authorization_code
	 */
	public String getAuthorization_code() {
		return authorization_code;
	}
	/**
	 * authorization_code.
	 *
	 * @param   authorization_code    the authorization_code to set
	 */
	public void setAuthorization_code(String authorization_code) {
		this.authorization_code = authorization_code;
	}
	/**
	 * authorizer_access_token.
	 *
	 * @return  the authorizer_access_token
	 */
	public String getAuthorizer_access_token() {
		return authorizer_access_token;
	}
	/**
	 * authorizer_access_token.
	 *
	 * @param   authorizer_access_token    the authorizer_access_token to set
	 */
	public void setAuthorizer_access_token(String authorizer_access_token) {
		this.authorizer_access_token = authorizer_access_token;
	}
	/**
	 * authorizer_refresh_token.
	 *
	 * @return  the authorizer_refresh_token
	 */
	public String getAuthorizer_refresh_token() {
		return authorizer_refresh_token;
	}
	/**
	 * authorizer_refresh_token.
	 *
	 * @param   authorizer_refresh_token    the authorizer_refresh_token to set
	 */
	public void setAuthorizer_refresh_token(String authorizer_refresh_token) {
		this.authorizer_refresh_token = authorizer_refresh_token;
	}
	/**
	 * func_info.
	 *
	 * @return  the func_info
	 */
	public String getFunc_info() {
		return func_info;
	}
	/**
	 * func_info.
	 *
	 * @param   func_info    the func_info to set
	 */
	public void setFunc_info(String func_info) {
		this.func_info = func_info;
	}
	/**
	 * update_time.
	 *
	 * @return  the update_time
	 */
	public Date getUpdate_time() {
		return update_time;
	}
	/**
	 * update_time.
	 *
	 * @param   update_time    the update_time to set
	 */
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	/**
	 * nick_name.
	 *
	 * @return  the nick_name
	 */
	public String getNick_name() {
		return nick_name;
	}
	/**
	 * nick_name.
	 *
	 * @param   nick_name    the nick_name to set
	 */
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	/**
	 * head_img.
	 *
	 * @return  the head_img
	 */
	public String getHead_img() {
		return head_img;
	}
	/**
	 * head_img.
	 *
	 * @param   head_img    the head_img to set
	 */
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	/**
	 * service_type_info.
	 *
	 * @return  the service_type_info
	 */
	public Integer getService_type_info() {
		return service_type_info;
	}
	/**
	 * service_type_info.
	 *
	 * @param   service_type_info    the service_type_info to set
	 */
	public void setService_type_info(Integer service_type_info) {
		this.service_type_info = service_type_info;
	}
	/**
	 * verify_type_info.
	 *
	 * @return  the verify_type_info
	 */
	public Integer getVerify_type_info() {
		return verify_type_info;
	}
	/**
	 * verify_type_info.
	 *
	 * @param   verify_type_info    the verify_type_info to set
	 */
	public void setVerify_type_info(Integer verify_type_info) {
		this.verify_type_info = verify_type_info;
	}
	/**
	 * user_name.
	 *
	 * @return  the user_name
	 */
	public String getUser_name() {
		return user_name;
	}
	/**
	 * user_name.
	 *
	 * @param   user_name    the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	/**
	 * alias.
	 *
	 * @return  the alias
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * alias.
	 *
	 * @param   alias    the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	/**
	 * business_info.
	 *
	 * @return  the business_info
	 */
	public String getBusiness_info() {
		return business_info;
	}
	/**
	 * business_info.
	 *
	 * @param   business_info    the business_info to set
	 */
	public void setBusiness_info(String business_info) {
		this.business_info = business_info;
	}
	/**
	 * qrcode_url.
	 *
	 * @return  the qrcode_url
	 */
	public String getQrcode_url() {
		return qrcode_url;
	}
	/**
	 * qrcode_url.
	 *
	 * @param   qrcode_url    the qrcode_url to set
	 */
	public void setQrcode_url(String qrcode_url) {
		this.qrcode_url = qrcode_url;
	}	
}