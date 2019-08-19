package com.ziwow.scrmapp.wechat.persistence.entity;

import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;

import java.util.Date;

/**
 *  延保卡 活动卡
 * @author songkaiqi
 * @since 2019/08/08/下午4:40
 */
public class EwCardActivity {


    private Long id;

    /**
     * 是否领取（1：已领取 0：未领取）
     */
    private Boolean receive;

    /**
     * 延保卡号
     */
    private String cardNo;

    /**
     * 机器类型
     */
    private EwCardTypeEnum type;

    /**
     * 发送的手机号码
     */
    private String phone;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EwCardTypeEnum getType() {
        return type;
    }

    public void setType(EwCardTypeEnum type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getReceive() {
        return receive;
    }

    public void setReceive(Boolean receive) {
        this.receive = receive;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
