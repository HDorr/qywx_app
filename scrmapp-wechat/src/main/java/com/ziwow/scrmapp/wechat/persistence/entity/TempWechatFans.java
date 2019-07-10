package com.ziwow.scrmapp.wechat.persistence.entity;

/**
 * @author: yyc
 * @date: 19-5-22 上午10:14
 */
public class TempWechatFans extends WechatFans {

  /** 手机号 */
  private String mobile;

  private String product;

  private String count;

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getCount() {
    return count;
  }

  public void setCount(String count) {
    this.count = count;
  }

  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }
}
