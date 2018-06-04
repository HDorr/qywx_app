package com.ziwow.scrmapp.common.persistence.mapper;


import com.ziwow.scrmapp.common.persistence.entity.Filter;

import java.util.List;

public interface FilterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Filter record);

    int insertSelective(Filter record);

    Filter selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Filter record);

    int updateByPrimaryKey(Filter record);

    List<Filter> findByLevelId(Long levelId);

    int batchSave(List<Filter> filters);

    List<Filter> findFilterByIds(String[] filters);
}