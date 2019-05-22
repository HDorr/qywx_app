package com.ziwow.scrmapp.wechat.persistence.entity;

/**
 * @author: yyc
 * @date: 19-5-22 上午10:14
 */
public class TempWechatFans extends WechatFans {

  /** 手机号 */
  private String mobile;

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
}
