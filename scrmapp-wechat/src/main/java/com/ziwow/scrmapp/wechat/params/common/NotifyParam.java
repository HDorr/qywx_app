package com.ziwow.scrmapp.wechat.params.common;

import java.util.List;

/**
 * @program: serviceAccount-scrmapp
 * @description: 通知模板参数
 * @author: Goblin
 * @create: 2020-01-16 15:38
 */
public class NotifyParam {

  /** 时间戳 */
  private String timestamp;
  /** 签名 */
  private String signature;
  /** 模板ID */
  private String templateId;
  /** 参数集合,多个以逗号隔开 */
  private List<String> params;
  /** 跳转路径 */
  private String url;
  /** 是否跳转到小程序,默认false，表示否 */
  private Boolean toMini;
  /** 备注 */
  private String remark;
  /** 标题 */
  private String title;

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  public List<String> getParams() {
    return params;
  }

  public void setParams(List<String> params) {
    this.params = params;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Boolean getToMini() {
    return toMini;
  }

  public void setToMini(Boolean toMini) {
    this.toMini = toMini;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
