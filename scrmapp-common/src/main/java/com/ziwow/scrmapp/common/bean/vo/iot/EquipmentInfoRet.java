package com.ziwow.scrmapp.common.bean.vo.iot;

import com.ziwow.scrmapp.common.iot.IotEquipmentInfo;

import java.util.List;

/**
 * @author songkaiqi
 * @since 2020/06/03/上午11:41
 */
public class EquipmentInfoRet extends IotBaseResult{

    private List<IotEquipmentInfo> data;

    public List<IotEquipmentInfo> getData() {
        return data;
    }

    public void setData(List<IotEquipmentInfo> data) {
        this.data = data;
    }

}
