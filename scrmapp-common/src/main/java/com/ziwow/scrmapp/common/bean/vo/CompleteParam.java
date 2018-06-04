package com.ziwow.scrmapp.common.bean.vo;

import java.util.List;

/**
 * Created by xiaohei on 2017/4/25.
 */
public class CompleteParam {
    private Long ordersId;
    private String ordersCode;
    private Integer orderType;
    //    private Long productId;
//    private String productName;
//    private String productCode;
//    private String modelName;
    private String userName;
    private String userMobile; // 联系人的手机号
    private String regMobile;  // 注册用户的手机号
    private String userAddress;
    private String orderTime;
    private int status;
    private int qyhUserId;
    private String qyhUserPhone; // 企业号用户手机号
    private String getQyhUserName;
    private String itemKind;
    //    private int channelId;
    private String description;
    private String userId;

    private List<ProductVo> products;

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public String getOrdersCode() {
        return ordersCode;
    }

    public void setOrdersCode(String ordersCode) {
        this.ordersCode = ordersCode;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

//    public Long getProductId() {
//        return productId;
//    }

//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }

//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }

//    public String getProductCode() {
//        return productCode;
//    }
//
//    public void setProductCode(String productCode) {
//        this.productCode = productCode;
//    }
//
//    public String getModelName() {
//        return modelName;
//    }
//
//    public void setModelName(String modelName) {
//        this.modelName = modelName;
//    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQyhUserId() {
        return qyhUserId;
    }

    public void setQyhUserId(int qyhUserId) {
        this.qyhUserId = qyhUserId;
    }

    public String getGetQyhUserName() {
        return getQyhUserName;
    }

    public void setGetQyhUserName(String getQyhUserName) {
        this.getQyhUserName = getQyhUserName;
    }

    public String getItemKind() {
        return itemKind;
    }

    public void setItemKind(String itemKind) {
        this.itemKind = itemKind;
    }

//    public int getChannelId() {
//        return channelId;
//    }

//    public void setChannelId(int channelId) {
//        this.channelId = channelId;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegMobile() {
        return regMobile;
    }

    public void setRegMobile(String regMobile) {
        this.regMobile = regMobile;
    }

    public String getQyhUserPhone() {
        return qyhUserPhone;
    }

    public void setQyhUserPhone(String qyhUserPhone) {
        this.qyhUserPhone = qyhUserPhone;
    }

    public List<ProductVo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductVo> products) {
        this.products = products;
    }
}
