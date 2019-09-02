package com.ziwow.scrmapp.wechat.params.address;

import com.ziwow.scrmapp.wechat.params.common.CenterServiceParam;

/**
 * @author: yyc
 * @date: 19-9-1 下午7:03
 */
public class AddressDeleteParam extends CenterServiceParam {

  private Long addressId;

  public Long getAddressId() {
    return addressId;
  }

  public void setAddressId(Long addressId) {
    this.addressId = addressId;
  }
}
