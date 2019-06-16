package com.ziwow.scrmapp.wechat.vo;

import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;

import java.util.List;

/**
 *  保修卡详情信息
 * @author songkaiqi
 * @since 2019/06/14/上午8:53
 */
public class EwCardDetails {

    /**
     * 延保卡的基本信息
     */
    private EwCard ewCard;


    /**
     * 服务记录
     */
    private List<ServiceRecord> serviceRecords;


    public EwCard getEwCard() {
        return ewCard;
    }

    public void setEwCard(EwCard ewCard) {
        this.ewCard = ewCard;
    }

    public List<ServiceRecord> getServiceRecords() {
        return serviceRecords;
    }

    public void setServiceRecords(List<ServiceRecord> serviceRecords) {
        this.serviceRecords = serviceRecords;
    }
}
