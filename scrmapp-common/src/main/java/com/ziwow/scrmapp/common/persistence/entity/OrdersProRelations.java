package com.ziwow.scrmapp.common.persistence.entity;

import java.util.Date;

public class OrdersProRelations {
    private Long id;
    private Long orderId;
    private Long productId;
    private String status;
    private String qyhUserId;
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQyhUserId() {
        return qyhUserId;
    }

    public void setQyhUserId(String qyhUserId) {
        this.qyhUserId = qyhUserId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
