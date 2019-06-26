package com.ziwow.scrmapp.wechat.vo;

import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;

/**
 * User: wangdong
 * Date: 19-6-11.
 * Time: 下午2:46
 * Description: ${DESCRIPTION}
 */

public class QtyUserVO extends WechatUser {
  private  String unionid;

  public String getUnionid() {
    return unionid;
  }

  public void setUnionid(String unionid) {
    this.unionid = unionid;
  }
}
