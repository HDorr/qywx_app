package com.ziwow.scrmapp.common.bean.pojo;

import java.io.Serializable;

public class AppraiseParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderCode;//受理单号
    private String is_order;//是否准时预约并上门
    private String timeStamp;
    private String signature;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getIs_order() {
        return is_order;
    }

    public void setIs_order(String is_order) {
        this.is_order = is_order;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
