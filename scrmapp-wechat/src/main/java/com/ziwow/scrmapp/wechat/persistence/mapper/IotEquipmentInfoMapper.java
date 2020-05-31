package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.common.iot.IotEquipmentInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * iot产品信息
 */
public interface IotEquipmentInfoMapper {

    void saveEquipmentInfos(List<IotEquipmentInfo> equipmentInfos);

    IotEquipmentInfo queryBySnCode(@Param("sncode") String sncode);
}
