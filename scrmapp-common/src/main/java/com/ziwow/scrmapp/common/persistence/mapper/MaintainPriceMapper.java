package com.ziwow.scrmapp.common.persistence.mapper;

import com.ziwow.scrmapp.common.persistence.entity.MaintainPrice;

import java.util.List;

public interface MaintainPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaintainPrice record);

    int insertSelective(MaintainPrice record);

    MaintainPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaintainPrice record);

    int updateByPrimaryKey(MaintainPrice record);

    int batchSave(List<MaintainPrice> maintainPriceList);

    List<MaintainPrice> findByProductCode(String productCode);

    List<MaintainPrice> findMaintainByIds(String[] items);

}