package com.ziwow.scrmapp.wechat.params.order;

import com.ziwow.scrmapp.wechat.params.common.CenterServiceParam;

/**
 * @author: yyc
 * @date: 19-8-30 下午8:08
 */
public class OrderModifyParam extends CenterServiceParam {

  /** 受理单号 */
  private String ordersCode;

  /** 联系人 */
  private String contacts;

  /** 更改的预约时间 */
  private String updateTime;

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

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }
}
