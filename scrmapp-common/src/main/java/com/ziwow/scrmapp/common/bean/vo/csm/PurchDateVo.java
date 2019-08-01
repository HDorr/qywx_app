package com.ziwow.scrmapp.common.bean.vo.csm;

/**
 * 购买时间
 * @author songkaiqi
 * @since 2019/07/31/下午6:40
 */
public class PurchDateVo {

    private Status status;

    private PurchDateItems items;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PurchDateItems getItems() {
        return items;
    }

    public void setItems(PurchDateItems items) {
        this.items = items;
    }
}
