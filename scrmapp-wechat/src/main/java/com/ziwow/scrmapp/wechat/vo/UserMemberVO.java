package com.ziwow.scrmapp.wechat.vo;

import com.sun.istack.NotNull;

public class UserMemberVO {
  private String openID;

  private String unionID;

  private String phone;

  public String getOpenID() {
    return openID;
  }

  public void setOpenID(String openID) {
    this.openID = openID;
  }

  public String getUnionID() {
    return unionID;
  }

  public void setUnionID(String unionID) {
    this.unionID = unionID;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
