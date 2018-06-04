package com.ziwow.scrmapp.common.persistence.mapper;


import com.ziwow.scrmapp.common.persistence.entity.RepairItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RepairItemMapper {

    int batchSave(List<RepairItem> repairItemList);

    List<RepairItem> findByItems(String[] items);

    List<RepairItem> findByName(@Param("typeName") String typeName, @Param("smallType") String smallType, @Param("name") String name);

}