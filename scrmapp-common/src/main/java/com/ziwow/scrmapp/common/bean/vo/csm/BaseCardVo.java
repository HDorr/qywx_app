package com.ziwow.scrmapp.common.bean.vo.csm;

import java.io.Serializable;

/**
 * @author songkaiqi
 * @since 2019/06/11/上午10:49
 */
public class BaseCardVo implements Serializable {

    /**
     * 状态信息
     */
    private Status status;

    /**
     * 返回数据
     */
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
