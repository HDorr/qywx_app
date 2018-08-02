package com.ziwow.scrmapp.qyh.controller.params;

public class QyhConfirmParams {
    private String userId;
    private String ordersCode;
    private String ordersId;
    private String contacts;
    private String contactsMobile;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsMobile() {
        return contactsMobile;
    }

    public void setContactsMobile(String contactsMobile) {
        this.contactsMobile = contactsMobile;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }
}
