package com.ziwow.scrmapp.common.bean.vo.iot;

import com.ziwow.scrmapp.common.iot.IotFilterInfo;
import com.ziwow.scrmapp.common.iot.IotFilterLifeInfo;

import java.util.List;

/**
 * @author songkaiqi
 * @since 2020/06/03/上午11:41
 */
public class FilterLifeInfoRet extends IotBaseResult{

    private List<IotFilterLifeInfo> data;

    public List<IotFilterLifeInfo> getData() {
        return data;
    }

    public void setData(List<IotFilterLifeInfo> data) {
        this.data = data;
    }

}
