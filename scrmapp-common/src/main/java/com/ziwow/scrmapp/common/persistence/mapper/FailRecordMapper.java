package com.ziwow.scrmapp.common.persistence.mapper;


import com.ziwow.scrmapp.common.persistence.entity.FailRecord;

public interface FailRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FailRecord record);

    int insertSelective(FailRecord record);

    FailRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FailRecord record);

    int updateByPrimaryKey(FailRecord record);
}