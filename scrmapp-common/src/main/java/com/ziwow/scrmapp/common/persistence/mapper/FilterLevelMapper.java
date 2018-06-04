package com.ziwow.scrmapp.common.persistence.mapper;


import com.ziwow.scrmapp.common.persistence.entity.FilterLevel;

import java.util.List;

public interface FilterLevelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FilterLevel record);

    int insertSelective(FilterLevel record);

    FilterLevel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FilterLevel record);

    int updateByPrimaryKey(FilterLevel record);

    int batchSave(List<FilterLevel> filterLevelList);
}