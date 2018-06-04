package com.ziwow.scrmapp.common.persistence.entity;

import java.math.BigDecimal;

public class FilterChangeRecord {
    private Long id;

    private Long productId;

    private Long orderId;

    private String changeItem;

    private BigDecimal changeCost;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getChangeItem() {
        return changeItem;
    }

    public void setChangeItem(String changeItem) {
        this.changeItem = changeItem;
    }

    public BigDecimal getChangeCost() {
        return changeCost;
    }

    public void setChangeCost(BigDecimal changeCost) {
        this.changeCost = changeCost;
    }
}