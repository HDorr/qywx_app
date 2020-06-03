package com.ziwow.scrmapp.common.bean.vo.iot;

import com.ziwow.scrmapp.common.iot.IotEquipmentInfo;
import com.ziwow.scrmapp.common.iot.IotFilterInfo;

import java.util.List;

/**
 * @author songkaiqi
 * @since 2020/06/03/上午11:41
 */
public class FilterInfoRet extends IotBaseResult{

    private List<IotFilterInfo> data;

    public List<IotFilterInfo> getData() {
        return data;
    }

    public void setData(List<IotFilterInfo> data) {
        this.data = data;
    }

}
