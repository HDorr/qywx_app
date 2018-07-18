package com.ziwow.scrmapp.qyh.controller.params;

public class QyhConfirmParams {
    private String userId;
    private String ordersCode;
    private String ordersId;
    private String ordersType;
    private String contacts;
    private String confirmStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrdersCode() {
        return ordersCode;
    }

    public void setOrdersCode(String ordersCode) {
        this.ordersCode = ordersCode;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public String getOrdersType() {
        return ordersType;
    }

    public void setOrdersType(String ordersType) {
        this.ordersType = ordersType;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }
}
