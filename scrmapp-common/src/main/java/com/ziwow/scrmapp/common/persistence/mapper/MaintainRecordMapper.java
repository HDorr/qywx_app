package com.ziwow.scrmapp.common.persistence.mapper;


import com.ziwow.scrmapp.common.persistence.entity.MaintainRecord;

import java.util.List;

public interface MaintainRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaintainRecord record);

    int insertSelective(MaintainRecord record);

    MaintainRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaintainRecord record);

    int updateByPrimaryKey(MaintainRecord record);

    int batchSave(List<MaintainRecord> maintainRecords);
}