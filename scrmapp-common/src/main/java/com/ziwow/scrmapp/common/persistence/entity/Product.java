package com.ziwow.scrmapp.common.persistence.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Product {
    private Long id;

    private String itemKind;

    private String typeName;

    private String smallcName;

    private String modelName;

    private Long levelId;

    private String levelName;

    private String productName;

    private String productCode;

    private String productImage;

    private String productBarCode;

    private String barCodeImage;

    private String shoppingOrder;

    private Integer saleType;

    private Integer o2o;

    private Integer buyChannel;

    private Integer filterRemind;

    private String userId;

    private Date buyTime;

    private Integer status;

    private Date createTime;

    private String productBarCodeTwenty;

    private String serviceFee;

    private String serviceStatus;

    private String serviceFeeId;

    private String serviceStatusStr;

    private String serviceFeeName;

    private String scOrderNo;

    private Long scOrderPayAt;

    public Long getScOrderPayAt() {
        return scOrderPayAt;
    }

    public void setScOrderPayAt(Long scOrderPayAt) {
        this.scOrderPayAt = scOrderPayAt;
    }

    public String getScOrderNo() {
        return scOrderNo;
    }

    public void setScOrderNo(String scOrderNo) {
        this.scOrderNo = scOrderNo;
    }

    public String getServiceFeeName() {
        return serviceFeeName;
    }

    public void setServiceFeeName(String serviceFeeName) {
        this.serviceFeeName = serviceFeeName;
    }

    public String getServiceStatusStr() {
        return serviceStatusStr;
    }

    public void setServiceStatusStr(String serviceStatusStr) {
        this.serviceStatusStr = serviceStatusStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemKind() {
        return itemKind;
    }

    public void setItemKind(String itemKind) {
        this.itemKind = itemKind;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSmallcName() {
        return smallcName;
    }

    public void setSmallcName(String smallcName) {
        this.smallcName = smallcName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductBarCode() {
        return productBarCode;
    }

    public void setProductBarCode(String productBarCode) {
        this.productBarCode = productBarCode;
    }

    public String getBarCodeImage() {
        return barCodeImage;
    }

    public void setBarCodeImage(String barCodeImage) {
        this.barCodeImage = barCodeImage;
    }

    public String getShoppingOrder() {
        return shoppingOrder;
    }

    public void setShoppingOrder(String shoppingOrder) {
        this.shoppingOrder = shoppingOrder;
    }

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }

    public Integer getO2o() {
        return o2o;
    }

    public void setO2o(Integer o2o) {
        this.o2o = o2o;
    }

    public Integer getBuyChannel() {
        return buyChannel;
    }

    public void setBuyChannel(Integer buyChannel) {
        this.buyChannel = buyChannel;
    }

    public Integer getFilterRemind() {
        return filterRemind;
    }

    public void setFilterRemind(Integer filterRemind) {
        this.filterRemind = filterRemind;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProductBarCodeTwenty() {
        return productBarCodeTwenty;
    }

    public void setProductBarCodeTwenty(String productBarCodeTwenty) {
        this.productBarCodeTwenty = productBarCodeTwenty;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceFeeId() { return serviceFeeId; }

    public void setServiceFeeId(String serviceFeeId) { this.serviceFeeId = serviceFeeId; }
}