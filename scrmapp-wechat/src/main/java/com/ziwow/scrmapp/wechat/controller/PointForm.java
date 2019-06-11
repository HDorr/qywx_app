package com.ziwow.scrmapp.wechat.controller;

/**
 * User: wangdong
 * Date: 19-6-11.
 * Time: 下午5:37
 * Description: ${DESCRIPTION}
 */

public class PointForm {
  private String userId;
  private String orderCode;
  private Integer orderType;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Integer getOrderType() {
    return orderType;
  }

  public void setOrderType(Integer orderType) {
    this.orderType = orderType;
  }
}
