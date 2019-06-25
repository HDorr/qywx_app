package com.ziwow.scrmapp.wechat.vo;

import com.ziwow.scrmapp.common.utils.EwCardUtil;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCardItems;

import java.util.List;


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
     * 类型编码信息
     */
    private List<EwCardItems> ewCardItems;

    /**
     * 卡片属性   例如：年卡 月卡 季卡
     */
    private String cardAttribute;

    public String getCardAttribute() {
        return EwCardUtil.getEwDate(this.validTime);
    }

    public void setCardAttribute(String cardAttribute) {
        this.cardAttribute = cardAttribute;
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

    public List<EwCardItems> getEwCardItems() {
        return ewCardItems;
    }

    public void setEwCardItems(List<EwCardItems> ewCardItems) {
        this.ewCardItems = ewCardItems;
    }
}
