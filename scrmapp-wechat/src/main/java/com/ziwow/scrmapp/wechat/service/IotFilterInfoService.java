package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.iot.IotFilterInfo;
import com.ziwow.scrmapp.common.iot.IotFilterLifeInfo;
import com.ziwow.scrmapp.wechat.schedule.iot.dto.IotFilterReminder;

import java.util.Date;
import java.util.List;

/**
 * iot滤芯信息
 */
public interface IotFilterInfoService {

    /**
     * 同步iot滤芯寿命信息
     * @param iotFilterInfos
     */
    void saveFilterLifeInfos(List<IotFilterLifeInfo> iotFilterInfos);

    /**
     * 根据滤芯剩余使用天数查询滤芯
     * @param i
     */
    List<IotFilterReminder> queryByFilterLife(int i);

    /**
     * 根据过期时间查询相应滤芯
     * @param date
     * @return
     */
    List<IotFilterReminder> queryByOverdueDate(String date);

    /**
     * 同步iot滤芯基本信息
     * @param filterInfos
     */
    void saveFilterInfos(List<IotFilterInfo> filterInfos);
}
