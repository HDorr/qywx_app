package com.ziwow.scrmapp.common.bean.pojo;

import com.ziwow.scrmapp.common.bean.vo.ProductVo;

import com.ziwow.scrmapp.common.enums.DeliveryType;
import com.ziwow.scrmapp.common.persistence.entity.ServiceFeeProduct;
import java.util.List;

/**
 * Created by xiaohei on 2017/4/14.
 */
public class WechatOrdersParam {

//  private Long productId;

//  private String itemKind;

//  private String typeName;

//  private String modelName;

//  private String productBarCode;

//  private String shoppingOrder;

//  private int o2o;

//  private int buyChannelId;

//  private String buyChannel;

//  private String buyTime;

    private Long ordersId;

    private String contacts;

    private String contactsMobile;

    private String tel;

    private String province;

    private String city;

    private String area;

    private String address;

    private Integer orderType;

    private int maintType; // 工单为保养单时，区分滤芯和清洗（1：清洗；2：滤芯）

    private int buyFilter;

    private String orderTime;

    private String description;

    private String faultImage;

    private int status;

    private String contactsTelephone;

    private List<ProductVo> products;

    /**
     * 微信订单号
     */
    private String orderNo;

    /**
     *  发货方式
     */
    private DeliveryType deliveryType;


    /**
     * 服务商名称
     */
    private String departmentName;


    /**
     * 服务子类型1
     */
    private String kindName;

    /**
     * 服务子类型2
     */
    private String kindName2;

    /**
     * 预约内部识别码，1默认常规换芯，2包年换芯
     */
    private Integer insideCode;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getKindName2() {
        return kindName2;
    }

    public void setKindName2(String kindName2) {
        this.kindName2 = kindName2;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    private List<ServiceFeeProduct> serviceFeeProducts;

    public List<ServiceFeeProduct> getServiceFeeProducts() {
        return serviceFeeProducts;
    }

    public void setServiceFeeProducts(
        List<ServiceFeeProduct> serviceFeeProducts) {
        this.serviceFeeProducts = serviceFeeProducts;
    }


    //    public Long getProductId() {
//        return productId;
//    }

//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }

//    public String getItemKind() {
//        return itemKind;
//    }

//    public void setItemKind(String itemKind) {
//        this.itemKind = itemKind;
//    }

//    public String getTypeName() {
//        return typeName;
//    }

//    public void setTypeName(String typeName) {
//        this.typeName = typeName;
//    }

//    public String getModelName() {
//        return modelName;
//    }

//    public void setModelName(String modelName) {
//        this.modelName = modelName;
//    }

//    public String getProductBarCode() {
//        return productBarCode;
//    }

//    public void setProductBarCode(String productBarCode) {
//        this.productBarCode = productBarCode;
//    }

//    public String getShoppingOrder() {
//        return shoppingOrder;
//    }

//    public void setShoppingOrder(String shoppingOrder) {
//        this.shoppingOrder = shoppingOrder;
//    }

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

//    public int getO2o() {
//        return o2o;
//    }

//    public void setO2o(int o2o) {
//        this.o2o = o2o;
//    }

//    public int getBuyChannelId() {
//        return buyChannelId;
//    }

//    public void setBuyChannelId(int buyChannelId) {
//        this.buyChannelId = buyChannelId;
//    }

//    public String getBuyChannel() {
//        return buyChannel;
//    }

//    public void setBuyChannel(String buyChannel) {
//        this.buyChannel = buyChannel;
//    }

//    public String getBuyTime() {
//        return buyTime;
//    }

//    public void setBuyTime(String buyTime) {
//        this.buyTime = buyTime;
//    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public int getMaintType() {
        return maintType;
    }

    public void setMaintType(int maintType) {
        this.maintType = maintType;
    }

    public int getBuyFilter() {
        return buyFilter;
    }

    public void setBuyFilter(int buyFilter) {
        this.buyFilter = buyFilter;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaultImage() {
        return faultImage;
    }

    public void setFaultImage(String faultImage) {
        this.faultImage = faultImage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ProductVo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductVo> products) {
        this.products = products;
    }

    public String getContactsTelephone() {
        return contactsTelephone;
    }

    public void setContactsTelephone(String contactsTelephone) {
        this.contactsTelephone = contactsTelephone;
    }

    public Integer getInsideCode() {
      return insideCode;
    }

    public void setInsideCode(Integer insideCode) {
      this.insideCode = insideCode;
    }
}
