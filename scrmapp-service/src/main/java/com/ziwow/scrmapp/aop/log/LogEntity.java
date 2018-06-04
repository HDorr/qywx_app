/**   
* @Title: LogEntity.java
* @Package com.ziwow.scrmapp.aop.log
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-1-17 下午2:52:02
* @version V1.0   
*/
package com.ziwow.scrmapp.aop.log;

import java.io.Serializable;

/**
 * @ClassName: LogEntity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-1-17 下午2:52:02
 * 
 */
public class LogEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;//登录账号
	private String module;//执行模块
	private String method;//执行方法
	private String responseDate;//响应时间
	private String ip;//IP地址
	private String date;//执行时间
	private String commite;//执行描述
	private String param;//执行参数
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}
	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return the responseDate
	 */
	public String getResponseDate() {
		return responseDate;
	}
	/**
	 * @param responseDate the responseDate to set
	 */
	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the commite
	 */
	public String getCommite() {
		return commite;
	}
	/**
	 * @param commite the commite to set
	 */
	public void setCommite(String commite) {
		this.commite = commite;
	}
	/**
	 * @return the param
	 */
	public String getParam() {
		return param;
	}
	/**
	 * @param param the param to set
	 */
	public void setParam(String param) {
		this.param = param;
	}
	
	
	
	
	
}
