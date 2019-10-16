package com.ziwow.scrmapp.wechat.persistence.entity;

import com.ziwow.scrmapp.common.enums.EwCardSendTypeEnum;
import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;

import java.util.Date;

/**
 * 发放延保卡记录
 * @author songkaiqi
 * @since 2019/08/12/上午10:02
 */
public class GrantEwCardRecord {

    private String id;

    /**
     * 发放的手机号
     */
    private String phone;

    /**
     * 发放的延保卡类型
     */
    private EwCardTypeEnum type;

    /**
     * 是否发放 true:已发放
     */
    private Boolean send;

    /**
     * 掩码
     */
    private String mask;

    /**
     * 发送时间
     */
    private Date sendTime;


    /**
     * 延保卡发放类型
     */
    private EwCardSendTypeEnum srcType;


    /**
     * 是否领取 true:已领取
     */
    private Boolean recevice;


    public Boolean getRecevice() {
        return recevice;
    }

    public void setRecevice(Boolean recevice) {
        this.recevice = recevice;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Boolean getSend() {
        return send;
    }

    public void setSend(Boolean send) {
        this.send = send;
    }

    public EwCardSendTypeEnum getSrcType() {
        return srcType;
    }

    public void setSrcType(EwCardSendTypeEnum srcType) {
        this.srcType = srcType;
    }
}
