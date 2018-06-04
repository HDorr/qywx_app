/**   
* @Title: WaterMark.java
* @Package com.ziwow.scrmapp.vo
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-1-12 下午4:32:57
* @version V1.0   
*/
package com.ziwow.scrmapp.vo;

import java.io.Serializable;

/**
 * @ClassName: WaterMark
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-1-12 下午4:32:57
 * 
 */
public class WaterMark implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String timestamp;
    String appid;
	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the appid
	 */
	public String getAppid() {
		return appid;
	}
	/**
	 * @param appid the appid to set
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}
    
    
}
