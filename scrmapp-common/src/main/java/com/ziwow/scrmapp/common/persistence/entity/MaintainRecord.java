package com.ziwow.scrmapp.common.persistence.entity;

import java.math.BigDecimal;

public class MaintainRecord {
    private Long id;

    private Long orderId;

    private Long productId;

    private String maintainItem;

    private BigDecimal maintainCost;

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

    public String getMaintainItem() {
        return maintainItem;
    }

    public void setMaintainItem(String maintainItem) {
        this.maintainItem = maintainItem;
    }

    public BigDecimal getMaintainCost() {
        return maintainCost;
    }

    public void setMaintainCost(BigDecimal maintainCost) {
        this.maintainCost = maintainCost;
    }
}