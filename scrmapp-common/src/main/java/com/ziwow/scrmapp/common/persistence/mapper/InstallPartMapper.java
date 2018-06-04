package com.ziwow.scrmapp.common.persistence.mapper;


import com.ziwow.scrmapp.common.persistence.entity.InstallPart;
import com.ziwow.scrmapp.common.persistence.entity.RepairPart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InstallPartMapper {

    int batchSave(List<InstallPart> installPartList);

    List<InstallPart> findByParts(String[] parts);

    List<InstallPart> findByName(@Param("modelName") String modelName, @Param("name") String name);

    List<InstallPart> findByModelName(String modelName);

}