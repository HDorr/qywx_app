package com.ziwow.scrmapp.common.persistence.entity;

import java.math.BigDecimal;

/**
 * Created by xiaohei on 2017/6/20.
 */
public class InstallPartRecord {
    private Long id;
    private Long orderId;
    private String record;
    private BigDecimal cost;

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

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
