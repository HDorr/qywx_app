package com.ziwow.scrmapp.common.persistence.mapper;

import com.ziwow.scrmapp.common.persistence.entity.RepairPartRecord;

import java.util.List;

/**
 * Created by xiaohei on 2017/6/21.
 */
public interface RepairPartRecordMapper {
    int save(RepairPartRecord repairPartRecord);

    int batchSave(List<RepairPartRecord> repairPartRecords);
}
