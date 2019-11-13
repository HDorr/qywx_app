package com.ziwow.scrmapp.common.bean.vo.csm;


import java.util.List;

/**
 * csm 根据条码查询延保卡信息
 * @author songkaiqi
 * @since 2019/06/09/下午2:16
 */
public class QueryBarCodeEwCardVo extends BaseCardVo {

    private Status status;

    /**
     * 延保卡详细
     */
    private List<EwCardItem> items;


    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    public List<EwCardItem> getItems() {
        return items;
    }

    public void setItems(List<EwCardItem> items) {
        this.items = items;
    }
}
