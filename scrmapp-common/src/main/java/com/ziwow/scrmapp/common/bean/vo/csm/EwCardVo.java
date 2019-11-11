package com.ziwow.scrmapp.common.bean.vo.csm;


/**
 * csm 延保卡
 * @author songkaiqi
 * @since 2019/06/09/下午2:16
 */
public class EwCardVo extends BaseCardVo {

    private Status status;

    /**
     * 延保卡详细
     */
    private EwCardItem items;

    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public EwCardItem getItems() {
        return items;
    }

    public void setItem(EwCardItem items) {
        this.items = items;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    public void setItems(EwCardItem items) {
        this.items = items;
    }
}
