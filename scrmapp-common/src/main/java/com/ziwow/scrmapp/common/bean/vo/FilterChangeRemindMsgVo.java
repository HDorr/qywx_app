package com.ziwow.scrmapp.common.bean.vo;

public class FilterChangeRemindMsgVo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String openId;			// openId
	private String nickName;		// 微信昵称
	private String mobilePhone;		// 用户手机号
	private String productName;		// 产品名称
	private String productSpec;		// 产品型号
	private String filterName;		// 滤芯名称
	private String purchaseDate; 	// 购买时间
	private String remindDate;		// 换芯时间
	private Integer type;			// 1:滤芯到期前30天  2:滤芯到期后30天   3:滤芯到期前三天
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductSpec() {
		return productSpec;
	}
	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getRemindDate() {
		return remindDate;
	}
	public void setRemindDate(String remindDate) {
		this.remindDate = remindDate;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
}