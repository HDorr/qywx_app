package com.ziwow.scrmapp.wechat.persistence.entity;

import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;

import java.util.Date;

/**
 *  延保卡 活动卡
 * @author songkaiqi
 * @since 2019/08/08/下午4:40
 */
public class EwCardActivity {

    private Integer id;

    /**
     * 是否领取（1：已领取 0：未领取）
     */
    private Boolean receive;


    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 掩码
     */
    private String mask;

    /**
     * 延保卡号
     */
    private String cardNo;

    /**
     * 机器类型
     */
    private EwCardTypeEnum type;


    public EwCardTypeEnum getType() {
        return type;
    }

    public void setType(EwCardTypeEnum type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getReceive() {
        return receive;
    }

    public void setReceive(Boolean receive) {
        this.receive = receive;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
