package com.ziwow.scrmapp.wechat.params.order;

import com.ziwow.scrmapp.wechat.params.common.CenterServiceParam;

/**
 * @author: yyc
 * @date: 19-8-30 下午8:13
 */
public class OrderCancelParam extends CenterServiceParam {

  /** 受理单号 */
  private String ordersCode;

  /** 联系人 */
  private String contacts;

  public String getOrdersCode() {
    return ordersCode;
  }

  public void setOrdersCode(String ordersCode) {
    this.ordersCode = ordersCode;
  }

  public String getContacts() {
    return contacts;
  }

  public void setContacts(String contacts) {
    this.contacts = contacts;
  }
}
