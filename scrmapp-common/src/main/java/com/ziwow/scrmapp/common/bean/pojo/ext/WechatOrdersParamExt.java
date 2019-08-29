package com.ziwow.scrmapp.common.bean.pojo.ext;

import com.ziwow.scrmapp.common.bean.pojo.WechatOrdersParam;
import com.ziwow.scrmapp.common.persistence.entity.ProductFilter;
import com.ziwow.scrmapp.common.persistence.entity.ServiceFeeProduct;
import java.util.List;

/**
 * Created by gp6 on 2017/12/12.
 */
public class WechatOrdersParamExt extends WechatOrdersParam{
	private String productIds;




	private String scOrderItemId;

	private String serviceFeeIds;

    /**
     * 商城调用做身份验证
     */
    private String unionId;


	private String userId;

	/**
	 * 产品是否是滤芯 （商城）
	 */
	private Boolean filter;

	/**
	 * 滤芯所匹配的产品code
	 */
	List<String> productModelNames;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getProductModelNames() {
		return productModelNames;
	}

	public void setProductModelNames(List<String> productModelNames) {
		this.productModelNames = productModelNames;
	}

	public Boolean getFilter() {
		return filter;
	}

	public void setFilter(Boolean filter) {
		this.filter = filter;
	}

	public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
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
