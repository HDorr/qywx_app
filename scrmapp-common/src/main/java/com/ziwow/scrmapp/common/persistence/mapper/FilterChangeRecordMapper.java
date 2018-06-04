package com.ziwow.scrmapp.common.persistence.mapper;


import com.ziwow.scrmapp.common.persistence.entity.FilterChangeRecord;

import java.util.List;

public interface FilterChangeRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FilterChangeRecord record);

    int insertSelective(FilterChangeRecord record);

    FilterChangeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FilterChangeRecord record);

    int updateByPrimaryKey(FilterChangeRecord record);

    int batchSave(List<FilterChangeRecord> filterChangeRecords);
}