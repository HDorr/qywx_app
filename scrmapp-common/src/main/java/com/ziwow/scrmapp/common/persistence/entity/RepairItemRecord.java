package com.ziwow.scrmapp.common.persistence.entity;

/**
 * Created by xiaohei on 2017/6/20.
 */
public class RepairItemRecord {
    private Long id;
    private Long orderId;
    private Long productId;
    private String record;

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

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
    
}
