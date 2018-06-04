package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.wechat.persistence.entity.ProductModel;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatArea;

import java.util.List;

public interface ProductModelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductModel record);

    int insertSelective(ProductModel record);

    ProductModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductModel record);

    int updateByPrimaryKey(ProductModel record);

    //List<ProductModel> findByTypeId(Long typeId);
    List<WechatArea> findByTypeId(Long typeId);
}