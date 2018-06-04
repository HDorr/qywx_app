package com.ziwow.scrmapp.common.persistence.mapper;


import com.ziwow.scrmapp.common.persistence.entity.RepairPart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RepairPartMapper {

    int batchSave(List<RepairPart> repairPartList);

    List<RepairPart> findByParts(String[] parts);

    List<RepairPart> findByName(@Param("modelName") String modelName, @Param("name") String name);

    List<RepairPart> findByModelName(String modelName);
}