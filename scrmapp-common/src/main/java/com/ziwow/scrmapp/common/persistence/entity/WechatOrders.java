package com.ziwow.scrmapp.common.persistence.entity;

import java.util.Date;

public class WechatOrders {
    private Long id;

    private String ordersCode;

    private String ordersNo;

    private Long productId;

    private String contacts;

    private String contactsMobile;

    private String province;

    private String city;

    private String area;

    private String address;

    private Integer orderType;
    
    //保养类型    orderType为3时,1:清洗  2:滤芯;
    private int maintType;
    //1:已购买滤芯  2:未购买滤芯'
    private int buyFilter;

    private Date orderTime;

    private Date createTime;

    private Date updateTime;

    private String description;

    private String faultImage;

    private Integer status;

    private Integer source;

    private String userId;

    private String qyhUserId;

    private Date endTime;

    private String refuseReason;

    private String changeTimeReason;
    
    private String contactsTelephone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrdersCode() {
        return ordersCode;
    }

    public void setOrdersCode(String ordersCode) {
        this.ordersCode = ordersCode;
    }

    public String getOrdersNo() {
        return ordersNo;
    }

    public void setOrdersNo(String ordersNo) {
        this.ordersNo = ordersNo;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsMobile() {
        return contactsMobile;
    }

    public void setContactsMobile(String contactsMobile) {
        this.contactsMobile = contactsMobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public int getMaintType() {
		return maintType;
	}

	public void setMaintType(int maintType) {
		this.maintType = maintType;
	}

	public int getBuyFilter() {
		return buyFilter;
	}

	public void setBuyFilter(int buyFilter) {
		this.buyFilter = buyFilter;
	}

	public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaultImage() {
        return faultImage;
    }

    public void setFaultImage(String faultImage) {
        this.faultImage = faultImage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQyhUserId() {
        return qyhUserId;
    }

    public void setQyhUserId(String qyhUserId) {
        this.qyhUserId = qyhUserId;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getChangeTimeReason() {
        return changeTimeReason;
    }

    public void setChangeTimeReason(String changeTimeReason) {
        this.changeTimeReason = changeTimeReason;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	public String getContactsTelephone() {
		return contactsTelephone;
	}

	public void setContactsTelephone(String contactsTelephone) {
		this.contactsTelephone = contactsTelephone;
	}
}