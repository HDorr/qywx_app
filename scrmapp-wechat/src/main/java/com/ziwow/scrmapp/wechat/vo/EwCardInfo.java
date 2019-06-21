package com.ziwow.scrmapp.wechat.vo;

import com.ziwow.scrmapp.common.utils.EwCardUtil;


/**
 * 根据卡号查询延保卡vo
 * @author songkaiqi
 * @since 2019/06/16/下午2:08
 */
public class EwCardInfo {

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 延保期限
     */
    private int validTime;

    /**
     * 对应机型
     */
    private String itemName;


    /**
     * 卡片属性   例如：年卡 月卡 季卡
     */
    private String cardAttribute;

    public String getCardAttribute() {
        return EwCardUtil.getEwDate(this.validTime);
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getValidTime() {
        return validTime;
    }

    public void setValidTime(int validTime) {
        this.validTime = validTime;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
