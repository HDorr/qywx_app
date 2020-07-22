package com.ziwow.scrmapp.wechat.enums;

import com.ziwow.scrmapp.common.exception.BizException;

/**
 * 预约服务类型，后续在这里统一管理，防止csm发生技术爆炸式扩增
 *
 * @author xiaohei
 * @since 20-7-22
 */
public enum OrderServiceType {
  DEFAULT(1, "76", "更换滤芯", "133", "更换滤芯"),
  QYPY(2, "80", "免单服务", "324", "包年服务"),
  ;

  /**
   * 内部识别码，1常规预约滤芯，2包年预约滤芯
   */
  private int insideCode;

  /**
   * csm 保养类型id1
   */
  private String kindId;
  private String kindName;

  /**
   * csm 保养类型id2
   */
  private String kinkId2;
  private String kindName2;

  OrderServiceType(int insideCode, String kindId, String kindName, String kinkId2, String kindName2) {
    this.insideCode = insideCode;
    this.kindId = kindId;
    this.kindName = kindName;
    this.kinkId2 = kinkId2;
    this.kindName2 = kindName2;
  }

  public static OrderServiceType of(int insideCode) {
    OrderServiceType[] values = values();
    for (OrderServiceType serviceType : values) {
      if (serviceType.insideCode == insideCode) {
        return serviceType;
      }
    }
    throw new BizException("非法的内部识别码，insideCode = " + insideCode);
  }

  public int getInsideCode() {
    return insideCode;
  }

  public void setInsideCode(int insideCode) {
    this.insideCode = insideCode;
  }

  public String getKindId() {
    return kindId;
  }

  public void setKindId(String kindId) {
    this.kindId = kindId;
  }

  public String getKindName() {
    return kindName;
  }

  public void setKindName(String kindName) {
    this.kindName = kindName;
  }

  public String getKinkId2() {
    return kinkId2;
  }

  public void setKinkId2(String kinkId2) {
    this.kinkId2 = kinkId2;
  }

  public String getKindName2() {
    return kindName2;
  }

  public void setKindName2(String kindName2) {
    this.kindName2 = kindName2;
  }
}
