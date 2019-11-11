package com.ziwow.scrmapp.common.bean.vo.csm;


import java.util.List;

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
    private List<EwCardItem> items;

    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


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
