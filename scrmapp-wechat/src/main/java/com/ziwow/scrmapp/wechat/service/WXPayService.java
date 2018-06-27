package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.entity.ProductFilter;

import java.util.List;

public interface WXPayService {
    int insert (ProductFilter productFilter);

    int updateProductFilterByOrderId(String orderId, int payStatus);

    int syncProductFilterByOrderId(String orderId, int syncStatus);

    List<ProductFilter> getProductFilterListByOrderId(String orderId);

    String getOrderIdFromProductFilterByModelName(String modelName);

    ProductFilter selectByPrimaryKey(Long id);

    int updateProductFilterByModelName(String modelName, int payStatus, String refundId);

    int getTotalFeeByOrderId(String orderId);

    ProductFilter getProductFilterByModelName(String modelName);

    String getUnionIdByUserId(String userId);

    int updateByPrimaryKeySelective(ProductFilter productFilter);

    ProductFilter getProductFilterByProductId(long pid);


}
