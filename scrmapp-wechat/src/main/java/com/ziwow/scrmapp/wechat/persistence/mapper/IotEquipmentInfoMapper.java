package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.common.iot.IotEquipmentInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * iot产品信息
 */
public interface IotEquipmentInfoMapper {

    /**
     * 同步Iot设备信息
     * @param equipmentInfos
     */
    void saveEquipmentInfos(List<IotEquipmentInfo> equipmentInfos);

    /**
     * 根据设备条码查询相关信息
     * @param sncode
     * @return
     */
    IotEquipmentInfo queryBySnCode(@Param("sncode") String sncode);
}
