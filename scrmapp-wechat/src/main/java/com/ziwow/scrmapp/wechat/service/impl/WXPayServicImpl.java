package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.persistence.entity.ProductFilter;
import com.ziwow.scrmapp.common.persistence.mapper.ProductFilterMapper;
import com.ziwow.scrmapp.wechat.service.WXPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WXPayServicImpl implements WXPayService {
    @Autowired
    private ProductFilterMapper productFilterMapper;

    @Override
    public int insert(ProductFilter productFilter) {
        return productFilterMapper.insertSelective(productFilter);
    }

    @Override
    public int updateProductFilterByOrderId(String orderId, int payStatus) {
        return productFilterMapper.updateProductFilterByOrderId(orderId, payStatus);
    }

    @Override
    public int syncProductFilterByOrderId(String orderId, int syncStatus) {
        return productFilterMapper.syncProductFilterByOrderId(orderId, syncStatus);
    }

    @Override
    public List<ProductFilter> getProductFilterListByOrderId(String orderId) {
        return productFilterMapper.getProductFilterListByOrderId(orderId);
    }

    @Override
    public String getOrderIdFromProductFilterByModelName(String modelName) {
        return productFilterMapper.getOrderIdFromProductFilterByModelName(modelName);
    }

    @Override
    public ProductFilter selectByPrimaryKey(Long id) {
        return productFilterMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateProductFilterByModelName(String modelName, int payStatus, String refundId) {
        return productFilterMapper.updateProductFilterByModelName(modelName, payStatus, refundId);
    }

    @Override
    public int getTotalFeeByOrderId(String orderId) {
        return productFilterMapper.getTotalFeeByOrderId(orderId);
    }

    @Override
    public ProductFilter getProductFilterByModelName(String modelName) {
        return productFilterMapper.getProductFilterByModelName(modelName);
    }

    @Override
    public String getUnionIdByUserId(String userId) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(ProductFilter productFilter) {
        return productFilterMapper.updateByPrimaryKeySelective(productFilter);
    }

    @Override
    public ProductFilter getProductFilterByProductId(long pid) {
        return productFilterMapper.getProductFilterByProductId(pid);
    }
}
