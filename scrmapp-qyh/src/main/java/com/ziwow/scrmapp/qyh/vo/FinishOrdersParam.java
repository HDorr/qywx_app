package com.ziwow.scrmapp.qyh.vo;

/**
 * Created by xiaohei on 2017/4/23.
 */
public class FinishOrdersParam {
    private String ordersCode;//受理单号
    private String qyhUserId;//师傅id
    private String qyhName;//师傅名称
    private String itemKind;//产品组织
    private String contacts;//联系人
    private String contactsMobile;//联系人手机号
    private String address;//地址
    private String productCode;//产品条码
    private String productName;//产品名称
    private String productModel;//产品型号
    private String buyChannel;//购买渠道
    private String serviceTime;//服务时间
    private String item_address;//产品安装地址
    private String is_change_filter;//是否更换滤芯

    public String getOrdersCode() {
        return ordersCode;
    }

    public void setOrdersCode(String ordersCode) {
        this.ordersCode = ordersCode;
    }

    public String getQyhUserId() {
        return qyhUserId;
    }

    public void setQyhUserId(String qyhUserId) {
        this.qyhUserId = qyhUserId;
    }

    public String getQyhName() {
        return qyhName;
    }

    public void setQyhName(String qyhName) {
        this.qyhName = qyhName;
    }

    public String getItemKind() {
        return itemKind;
    }

    public void setItemKind(String itemKind) {
        this.itemKind = itemKind;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getBuyChannel() {
        return buyChannel;
    }

    public void setBuyChannel(String buyChannel) {
        this.buyChannel = buyChannel;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getItem_address() {
        return item_address;
    }

    public void setItem_address(String item_address) {
        this.item_address = item_address;
    }

    public String getIs_change_filter() {
        return is_change_filter;
    }

    public void setIs_change_filter(String is_change_filter) {
        this.is_change_filter = is_change_filter;
    }
}
