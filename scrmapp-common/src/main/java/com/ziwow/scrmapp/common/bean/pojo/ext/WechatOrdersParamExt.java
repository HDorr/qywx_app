package com.ziwow.scrmapp.common.bean.pojo.ext;

import com.ziwow.scrmapp.common.bean.pojo.WechatOrdersParam;

/**
 * Created by gp6 on 2017/12/12.
 */
public class WechatOrdersParamExt extends WechatOrdersParam{
	private String productIds;

	private String serviceFeeProductsIds;

	private String scOrderItemId;

	private String serviceFeeIds;

	public String getServiceFeeProductsIds() {
		return serviceFeeProductsIds;
	}

	public void setServiceFeeProductsIds(String serviceFeeProductsIds) {
		this.serviceFeeProductsIds = serviceFeeProductsIds;
	}

	public String getServiceFeeIds() {
		return serviceFeeIds;
	}

	public void setServiceFeeIds(String serviceFeeIds) {
		this.serviceFeeIds = serviceFeeIds;
	}

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
