package com.ziwow.scrmapp.common.bean.vo.csm;


/**
 * csm 延保卡
 * @author songkaiqi
 * @since 2019/06/09/下午2:16
 */
public class EwCardVo extends BaseCardVo {


    /**
     * 延保卡详细
     */
    private EwCardItem item;

    public EwCardItem getItem() {
        return item;
    }

    public void setItem(EwCardItem item) {
        this.item = item;
    }
}
