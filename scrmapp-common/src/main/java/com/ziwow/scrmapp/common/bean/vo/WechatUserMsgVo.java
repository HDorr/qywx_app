package com.ziwow.scrmapp.common.bean.vo;

public class WechatUserMsgVo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ordersCode;		// 订单编号
	private String userId;			// 用户ID
	private String orderTime;		// 预约时间
	private String qyhUserName;		// 工程师名称 
	private String qyhUserPhone; 	// 工程师电话
	private String orderType;		// 订单类型
	private Integer type;			// 1:工程师发起更改预约通知用户   2:工程师提交完工后通知用户
	public String getOrdersCode() {
		return ordersCode;
	}
	public void setOrdersCode(String ordersCode) {
		this.ordersCode = ordersCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getQyhUserName() {
		return qyhUserName;
	}
	public void setQyhUserName(String qyhUserName) {
		this.qyhUserName = qyhUserName;
	}
	public String getQyhUserPhone() {
		return qyhUserPhone;
	}
	public void setQyhUserPhone(String qyhUserPhone) {
		this.qyhUserPhone = qyhUserPhone;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}