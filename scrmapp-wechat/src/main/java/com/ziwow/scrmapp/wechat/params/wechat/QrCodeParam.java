package com.ziwow.scrmapp.wechat.params.wechat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 获取二维码参数
 *
 * @author yyc
 * @since 20-3-27
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QrCodeParam {

  private long timestamp;

  private String signture;

  /** 二维码过期时间 */
  private Integer expireSeconds;

  /**
   * 二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值
   */
  private String actionName;

  /** 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000） */
  private Integer sceneId;

  /** 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64 */
  private String sceneStr;

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getSignture() {
    return signture;
  }

  public void setSignture(String signture) {
    this.signture = signture;
  }

  public Integer getExpireSeconds() {
    return expireSeconds;
  }

  public void setExpireSeconds(Integer expireSeconds) {
    this.expireSeconds = expireSeconds;
  }

  public String getActionName() {
    return actionName;
  }

  public void setActionName(String actionName) {
    this.actionName = actionName;
  }

  public Integer getSceneId() {
    return sceneId;
  }

  public void setSceneId(Integer sceneId) {
    this.sceneId = sceneId;
  }

  public String getSceneStr() {
    return sceneStr;
  }

  public void setSceneStr(String sceneStr) {
    this.sceneStr = sceneStr;
  }
}
