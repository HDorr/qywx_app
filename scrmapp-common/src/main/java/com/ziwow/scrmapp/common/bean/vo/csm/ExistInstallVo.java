package com.ziwow.scrmapp.common.bean.vo.csm;

import java.io.Serializable;

/**
 * @author songkaiqi
 * @since 2019/07/24/上午11:02
 */
public class ExistInstallVo implements Serializable {

    private String items;

    private Status status;

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
