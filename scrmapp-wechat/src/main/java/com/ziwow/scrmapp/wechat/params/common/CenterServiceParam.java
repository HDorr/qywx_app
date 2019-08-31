package com.ziwow.scrmapp.wechat.params.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author: yyc
 * @date: 19-8-31 下午2:52
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CenterServiceParam {

  private String unionId;

  private String signature;

  private String timeStamp;

  public String getUnionId() {
    return unionId;
  }

  public void setUnionId(String unionId) {
    this.unionId = unionId;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }
}
