package com.ziwow.scrmapp.common.bean.vo;

import java.util.List;

/**
 * Created by xiaohei on 2017/4/19.
 */
public class QyhUserOrdersVo {
    private int today;
    private int tomorrow;
    private int total;

    private List<WechatOrdersVo> wechatOrdersVoList;

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public int getTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(int tomorrow) {
        this.tomorrow = tomorrow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<WechatOrdersVo> getWechatOrdersVoList() {
        return wechatOrdersVoList;
    }

    public void setWechatOrdersVoList(List<WechatOrdersVo> wechatOrdersVoList) {
        this.wechatOrdersVoList = wechatOrdersVoList;
    }
}
