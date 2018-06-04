/**   
* @Title: SessionKeyVo.java
* @Package com.ziwow.wxapp.vo
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-1-12 上午10:43:12
* @version V1.0   
*/
package com.ziwow.scrmapp.miniapp.vo;

import java.io.Serializable;

/**
 * @ClassName: SessionKeyVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-1-12 上午10:43:12
 * 
 */
public class SessionKeyVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String openid;//用户唯一标识
	private String session_key;//会话密钥
	private Long expires_in;
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
	 * @return the session_key
	 */
	public String getSession_key() {
		return session_key;
	}
	/**
	 * @param session_key the session_key to set
	 */
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	/**
	 * @return the expires_in
	 */
	public Long getExpires_in() {
		return expires_in;
	}
	/**
	 * @param expires_in the expires_in to set
	 */
	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}
	
	
	
	
	
	
}
