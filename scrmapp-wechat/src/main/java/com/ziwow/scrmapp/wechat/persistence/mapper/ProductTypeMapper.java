package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.wechat.persistence.entity.ProductType;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCity;

import java.util.List;

public interface ProductTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductType record);

    int insertSelective(ProductType record);

    ProductType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductType record);

    int updateByPrimaryKey(ProductType record);

    //List<ProductType> findBySeriesId(Long seriesId);
    List<WechatCity> findBySeriesId(Long seriesId);
}