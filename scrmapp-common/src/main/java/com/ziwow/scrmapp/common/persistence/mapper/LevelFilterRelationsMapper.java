package com.ziwow.scrmapp.common.persistence.mapper;


import com.ziwow.scrmapp.common.persistence.entity.LevelFilterRelations;

import java.util.List;

public interface LevelFilterRelationsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LevelFilterRelations record);

    int insertSelective(LevelFilterRelations record);

    LevelFilterRelations selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LevelFilterRelations record);

    int updateByPrimaryKey(LevelFilterRelations record);

    int batchSave(List<LevelFilterRelations> filterRelationsList);
}