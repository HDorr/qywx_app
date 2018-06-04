package com.ziwow.scrmapp.common.bean.vo;

public class QyhUserMsgVo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String content;
	private String qyhUserMobile;
	private String msgContent;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getQyhUserMobile() {
		return qyhUserMobile;
	}
	public void setQyhUserMobile(String qyhUserMobile) {
		this.qyhUserMobile = qyhUserMobile;
	}
}	
