package com.ziwow.scrmapp.wechat.vo;

public class MiniappSendSms {


  private String signture;
  private String timestamp;


  private int type;
  private String message;
  private String phone;

  public String getSignture() {
    return signture;
  }

  public void setSignture(String signature) {
    this.signture = signature;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
