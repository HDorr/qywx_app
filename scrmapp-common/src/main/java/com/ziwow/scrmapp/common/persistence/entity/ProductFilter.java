package com.ziwow.scrmapp.common.persistence.entity;

import java.util.Date;

public class ProductFilter {

    private Long id;
    private Long productId;
    private String orderId;
    private String refundId;
    private String modelName;
    private Integer serviceFee;
    private Integer payStatus;
    private Integer syncStatus;
    private Date createTime;

    private String scOrderNo;
    private String serviceFeeId;

    public String getServiceFeeId() {
        return serviceFeeId;
    }

    public void setServiceFeeId(String serviceFeeId) {
        this.serviceFeeId = serviceFeeId;
    }

    public String getScOrderNo() {
        return scOrderNo;
    }

    public void setScOrderNo(String scOrderNo) {
        this.scOrderNo = scOrderNo;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }


    public String getRefundId() {
        return refundId;
    }


    public void setRefundId(String refundId) {
        this.refundId = refundId == null ? null : refundId.trim();
    }


    public String getModelName() {
        return modelName;
    }


    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }


    public Integer getServiceFee() {
        return serviceFee;
    }


    public void setServiceFee(Integer serviceFee) {
        this.serviceFee = serviceFee;
    }


    public Integer getPayStatus() {
        return payStatus;
    }


    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}