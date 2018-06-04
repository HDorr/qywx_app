/**
 * Project Name:ziwow-dubbo-model
 * File Name:OpenWeixin.java
 * Package Name:com.ziwow.dubbo.model.weixin.open
 * Date:2016-4-26下午5:18:11
 * Copyright (c) 2016, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.wechat.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: OpenWeixin <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016-4-26 下午5:18:11 <br/>
 *
 * @author daniel.wang
 * @version 
 * @since JDK 1.6
 */
public class OpenWeixin implements Serializable{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	
	private int id ;
	
	private String component_appid;
	
	private String component_token;
	
	private String component_key;
	
	private String component_appsecret;
	
	private String component_verify_ticket;
	
	private String component_access_token;
	
	private Date update_time;
	
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
	 * component_token.
	 *
	 * @return  the component_token
	 */
	public String getComponent_token() {
		return component_token;
	}

	/**
	 * component_token.
	 *
	 * @param   component_token    the component_token to set
	 */
	public void setComponent_token(String component_token) {
		this.component_token = component_token;
	}

	/**
	 * component_key.
	 *
	 * @return  the component_key
	 */
	public String getComponent_key() {
		return component_key;
	}

	/**
	 * component_key.
	 *
	 * @param   component_key    the component_key to set
	 */
	public void setComponent_key(String component_key) {
		this.component_key = component_key;
	}

	/**
	 * component_appsecret.
	 *
	 * @return  the component_appsecret
	 */
	public String getComponent_appsecret() {
		return component_appsecret;
	}

	/**
	 * component_appsecret.
	 *
	 * @param   component_appsecret    the component_appsecret to set
	 */
	public void setComponent_appsecret(String component_appsecret) {
		this.component_appsecret = component_appsecret;
	}

	/**
	 * component_verify_ticket.
	 *
	 * @return  the component_verify_ticket
	 */
	public String getComponent_verify_ticket() {
		return component_verify_ticket;
	}

	/**
	 * component_verify_ticket.
	 *
	 * @param   component_verify_ticket    the component_verify_ticket to set
	 */
	public void setComponent_verify_ticket(String component_verify_ticket) {
		this.component_verify_ticket = component_verify_ticket;
	}

	/**
	 * component_access_token.
	 *
	 * @return  the component_access_token
	 */
	public String getComponent_access_token() {
		return component_access_token;
	}

	/**
	 * component_access_token.
	 *
	 * @param   component_access_token    the component_access_token to set
	 */
	public void setComponent_access_token(String component_access_token) {
		this.component_access_token = component_access_token;
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
}