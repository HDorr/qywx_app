package com.ziwow.scrmapp.common.bean.pojo.ext;

import com.ziwow.scrmapp.common.bean.pojo.WechatOrdersParam;

/**
 * Created by gp6 on 2017/12/12.
 */
public class WechatOrdersParamExt extends WechatOrdersParam{
	private String productIds;

	private String scOrderItemId;

	public String getScOrderItemId() {
		return scOrderItemId;
	}

	public void setScOrderItemId(String scOrderItemId) {
		this.scOrderItemId = scOrderItemId;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
}
