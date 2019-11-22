package com.ziwow.scrmapp.common.bean.vo.csm;


/**
 * csm 根据卡号查询延保卡
 * @author songkaiqi
 * @since 2019/06/09/下午2:16
 */
public class QueryNoEwCardVo extends BaseCardVo {

    private Status status;

    /**
     * 延保卡详细
     */
    private EwCardItem items;


    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    public EwCardItem getItems() {
        return items;
    }

    public void setItems(EwCardItem items) {
        this.items = items;
    }
}
