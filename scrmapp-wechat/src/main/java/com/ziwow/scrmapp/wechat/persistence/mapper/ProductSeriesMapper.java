package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.wechat.persistence.entity.ProductSeries;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatProvince;

import java.util.List;

public interface ProductSeriesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSeries record);

    int insertSelective(ProductSeries record);

    ProductSeries selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSeries record);

    int updateByPrimaryKey(ProductSeries record);

    //List<ProductSeries> findAll();
    List<WechatProvince> findAll();
}