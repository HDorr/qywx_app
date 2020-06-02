package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.common.iot.IotFilterInfo;
import com.ziwow.scrmapp.common.iot.IotFilterLifeInfo;
import com.ziwow.scrmapp.wechat.schedule.iot.dto.IotFilterReminder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * iot 滤芯信息
 */
public interface IotFilterInfoMapper {


    /**
     * 同步滤芯基本信息
     * @param filterInfos
     */
    void saveFilterInfos(List<IotFilterInfo> filterInfos);

    /**
     *同步iot滤芯寿命信息
     * @param iotFilterInfos
     */
    void saveFilterLifeInfos(List<IotFilterLifeInfo> iotFilterInfos);

    /**
     * 根据滤芯剩余使用天数查询滤芯
     * @param i
     */
    List<IotFilterReminder> queryByFilterLife(@Param("filerLife") int i);

    /**
     * 根据过期时间查询iot滤芯过期时间
     * @param date
     */
    List<IotFilterReminder> queryByOverdueDate(@Param("date") String date);


}
