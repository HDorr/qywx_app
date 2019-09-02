package com.ziwow.scrmapp.wechat.params.address;

import com.ziwow.scrmapp.wechat.params.common.CenterServiceParam;

/**
 * @author: yyc
 * @date: 19-9-1 下午3:42
 */
public class AddressModifyParam extends CenterServiceParam {

  /** 主键 */
  private Long id;

  /** 联系人 */
  private String contacts;

  /** 联系人电话 */
  private String contactsMobile;

  /** 省id */
  private Integer provinceId;

  /** 省 */
  private String provinceName;

  /** 市id */
  private Integer cityId;

  /** 市 */
  private String cityName;

  /** 区id */
  private Integer areaId;

  /** 区 */
  private String areaName;

  /** 详细街道 */
  private String streetName;

  /** 固定电话 */
  private String fixedTelephone;

  /** 是否为默认地址1默认 2非默认 */
  private Integer isDefault = 2;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Integer getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(Integer provinceId) {
    this.provinceId = provinceId;
  }

  public String getProvinceName() {
    return provinceName;
  }

  public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
  }

  public Integer getCityId() {
    return cityId;
  }

  public void setCityId(Integer cityId) {
    this.cityId = cityId;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public Integer getAreaId() {
    return areaId;
  }

  public void setAreaId(Integer areaId) {
    this.areaId = areaId;
  }

  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public String getFixedTelephone() {
    return fixedTelephone;
  }

  public void setFixedTelephone(String fixedTelephone) {
    this.fixedTelephone = fixedTelephone;
  }

  public Integer getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(Integer isDefault) {
    this.isDefault = isDefault;
  }
}
