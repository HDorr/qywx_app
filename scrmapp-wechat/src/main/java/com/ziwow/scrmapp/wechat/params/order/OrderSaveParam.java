package com.ziwow.scrmapp.wechat.params.order;

import com.ziwow.scrmapp.common.enums.DeliveryType;
import com.ziwow.scrmapp.wechat.params.common.CenterServiceParam;

/**
 * @author: yyc
 * @date: 19-8-30 下午8:08
 */
public class OrderSaveParam extends CenterServiceParam {

  /** 联系人 */
  private String contacts;

  /** 手机号 */
  private String contactsMobile;

  /** 固定电话 */
  private String contactsTelephone;

  /** 省 */
  private String province;

  /** 市 */
  private String city;

  /** 区 */
  private String area;

  /** 详细地址 */
  private String address;

  /** 订单类型 INSTALL = 1, REPAIR = 2, MAINTAIN = 3 */
  private Integer orderType;

  /** 保养类型 orderType为3时,1:清洗 2:滤芯; */
  private int maintType;

  /** 1:已购买滤芯 2:未购买滤芯 */
  private int buyFilter;

  /** 预约时间 */
  private String orderTime;

  /** 描述 */
  private String description;

  /** 图片拼接img1,img2,.. */
  private String faultImage;

  /** 产品列表pro1,pro2,.. */
  private String productIds;

  /** 发货方式 */
  private DeliveryType deliveryType;

  /** 服务商名称 */
  private String departmentName;

  /** 服务子类型1 */
  private String kindName;

  /** 服务子类型2 */
  private String kindName2;

  /**
   * 预约服务内部识别码
   */
  private Integer insideCode = 1;

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

  public String getContactsTelephone() {
    return contactsTelephone;
  }

  public void setContactsTelephone(String contactsTelephone) {
    this.contactsTelephone = contactsTelephone;
  }

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

  public String getProductIds() {
    return productIds;
  }

  public void setProductIds(String productIds) {
    this.productIds = productIds;
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

  public Integer getInsideCode() {
    return insideCode;
  }

  public void setInsideCode(Integer insideCode) {
    this.insideCode = insideCode;
  }
}
