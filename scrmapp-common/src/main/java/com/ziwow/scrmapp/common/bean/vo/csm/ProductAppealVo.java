package com.ziwow.scrmapp.common.bean.vo.csm;

import java.util.List;

public class ProductAppealVo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appealNo;		// 受理单号
	private String appealNumber;	// 安装单号/保养单号/维修单号
	private String createTime;		// 受理单生成时间
	private String serviceTime;		// 预约时间
	private Integer orderStatus;	// 订单状态
	private String saleMarket;		// 卖场名称
	private Integer appealKindId;   // 受理类型
	private String appealContent;  	// 受理内容
	private String enduserName;		// 用户姓名
	private String mobile;			// 电话
	private String enduserAddress;	// 地址
	private Integer provinceId;		// 省Id
	private String provinceName;	// 省名称
	private Integer cityId;			// 市Id
	private String cityName;		// 市名称
	private Integer countyId;		// 区Id
	private String countyName;		// 区名称
	private String qyhUserId;		//服务工程师id
	private List<AppealProduct> products;	// 产品信息
	public String getAppealNo() {
		return appealNo;
	}
	public void setAppealNo(String appealNo) {
		this.appealNo = appealNo;
	}
	public String getAppealNumber() {
		return appealNumber;
	}
	public void setAppealNumber(String appealNumber) {
		this.appealNumber = appealNumber;
	}
	public String getSaleMarket() {
		return saleMarket;
	}
	public void setSaleMarket(String saleMarket) {
		this.saleMarket = saleMarket;
	}
	public Integer getAppealKindId() {
		return appealKindId;
	}
	public void setAppealKindId(Integer appealKindId) {
		this.appealKindId = appealKindId;
	}
	public String getAppealContent() {
		return appealContent;
	}
	public void setAppealContent(String appealContent) {
		this.appealContent = appealContent;
	}
	public String getEnduserName() {
		return enduserName;
	}
	public void setEnduserName(String enduserName) {
		this.enduserName = enduserName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEnduserAddress() {
		return enduserAddress;
	}
	public void setEnduserAddress(String enduserAddress) {
		this.enduserAddress = enduserAddress;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getQyhUserId() {
		return qyhUserId;
	}
	public void setQyhUserId(String qyhUserId) {
		this.qyhUserId = qyhUserId;
	}
	public List<AppealProduct> getProducts() {
		return products;
	}

	public void setProducts(List<AppealProduct> products) {
		this.products = products;
	}
}