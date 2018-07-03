package com.ziwow.scrmapp.common.persistence.entity;

public class ServiceFeeProduct {


    /**
     * serviceFee : 0.01
     * serviceStatus : 1
     * serviceFeeId : 1764
     * serviceStatusStr : 已购买滤芯并购买服务费
     * serviceFeeName : MK-UF-12服务费
     * scOrderNo : ESO180702182800241089
     */

    private String serviceFee;
    private String serviceStatus;
    private String serviceFeeId;
    private String serviceStatusStr;
    private String serviceFeeName;
    private String scOrderNo;

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