package com.ziwow.scrmapp.common.bean.vo.iot;

import com.ziwow.scrmapp.common.iot.IotUserInfo;

import java.util.List;

/**
 * @author songkaiqi
 * @since 2020/06/02/下午5:55
 */
public class UserInfoRet extends IotBaseResult {

    private List<IotUserInfo> data;

    public List<IotUserInfo> getData() {
        return data;
    }

    public void setData(List<IotUserInfo> data) {
        this.data = data;
    }
}
