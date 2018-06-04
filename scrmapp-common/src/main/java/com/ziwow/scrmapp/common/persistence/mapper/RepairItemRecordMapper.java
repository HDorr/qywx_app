package com.ziwow.scrmapp.common.persistence.mapper;

import com.ziwow.scrmapp.common.persistence.entity.RepairItemRecord;

import java.util.List;

/**
 * Created by xiaohei on 2017/6/21.
 */
public interface RepairItemRecordMapper {
    int save(RepairItemRecord repairItemRecord);

    int batchSave(List<RepairItemRecord> repairItemRecords);
}
