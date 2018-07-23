package com.ziwow.scrmapp.common.persistence.mapper;


import com.ziwow.scrmapp.common.persistence.entity.ProductFilter;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrderServiceFee;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface WechatOrderServiceFeeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WechatOrderServiceFee wechatOrderServiceFee);

    WechatOrderServiceFee selectByPrimaryKey(Long id);

}