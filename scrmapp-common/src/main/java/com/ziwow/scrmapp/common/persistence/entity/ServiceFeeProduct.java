package com.ziwow.scrmapp.common.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ServiceFeeProduct {


    /**
     * serviceFee : 0.01
     * serviceStatus : 1
     * serviceFeeId : 1764
     * serviceStatusStr : 已购买滤芯并购买服务费
     * serviceFeeName : MK-UF-12服务费
     * scOrderNo : ESO180702182800241089
     */

    private String productId;
    private BigDecimal serviceFee;
    private String serviceStatus;
    private String serviceFeeId;
    private String serviceStatusStr;
    private String serviceFeeName;
    private String scOrderNo;
    private Date scOrderPayAt;

    public Date getScOrderPayAt() {
        return scOrderPayAt;
    }

    public void setScOrderPayAt(Date scOrderPayAt) {
        this.scOrderPayAt = scOrderPayAt;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceFeeId() {
        return serviceFeeId;
    }

    public void setServiceFeeId(String serviceFeeId) {
        this.serviceFeeId = serviceFeeId;
    }

    public String getServiceStatusStr() {
        return serviceStatusStr;
    }

    public void setServiceStatusStr(String serviceStatusStr) {
        this.serviceStatusStr = serviceStatusStr;
    }

    public String getServiceFeeName() {
        return serviceFeeName;
    }

    public void setServiceFeeName(String serviceFeeName) {
        this.serviceFeeName = serviceFeeName;
    }

    public String getScOrderNo() {
        return scOrderNo;
    }

    public void setScOrderNo(String scOrderNo) {
        this.scOrderNo = scOrderNo;
    }
}