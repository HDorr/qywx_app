package com.ziwow.scrmapp.wechat.params.address;

import com.ziwow.scrmapp.wechat.params.common.CenterServiceParam;

/**
 * @author: yyc
 * @date: 19-9-1 下午7:03
 */
public class AddressDeleteParam extends CenterServiceParam {

  /** 地址主键 */
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
