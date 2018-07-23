package com.ziwow.scrmapp.common.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;

public class WechatOrderServiceFee {

    private Long id;
    private Long productId;
    private Long orderId;
    private BigDecimal serviceFee;
    private Date createTime;

    private String scOrderNo;
    private String serviceFeeId;

    public WechatOrderServiceFee() {
    }

    public WechatOrderServiceFee(ServiceFeeProduct pf,Long orderId) {
        this.productId=Long.parseLong(pf.getProductId());
        this.orderId=orderId;
        this.serviceFee=pf.getServiceFee();
        this.scOrderNo=pf.getScOrderNo();
        this.serviceFeeId=pf.getServiceFeeId();
    }

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

    public Long getOrderId() {
        return orderId;
    }


    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}