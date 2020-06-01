package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.common.iot.IotFilterInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IotFilterInfoMapper {

    /**
     *同步iot滤芯寿命信息
     * @param iotFilterInfos
     */
    void saveFilterInfos(List<IotFilterInfo> iotFilterInfos);

    /**
     * 根据滤芯剩余使用天数查询滤芯
     * @param i
     */
    void queryByFilterLife(@Param("filerLife") int i);
}
