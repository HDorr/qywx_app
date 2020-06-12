package com.ziwow.scrmapp.wechat.vo;

import com.ziwow.scrmapp.common.enums.EwCardSendTypeEnum;
import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;

import java.util.Date;

/**
 * 延保卡详细信息
 * @author songkaiqi
 * @since 2020/06/12/下午2:27
 */
public class CardInfoVo {


    /**
     * 延保卡号
     */
    private String ewCardNo;

    /**
     * 收到短信的手机号
     */
    private String receivePhone;

    /**
     * 掩码
     */
    private String mask;

    /**
     * 延保卡类型
     */
    private EwCardTypeEnum ewCardType;

    /**
     * 发放形式
     */
    private EwCardSendTypeEnum srcType;

    /**
     * 是否领取
     */
    private Boolean receive;

    /**
     * 发放时间
     */
    private Date sendTime;

    /**
     * 绑定产品条码
     */
    private String barcode;

    /**
     * 延保期限
     */
    private Integer validTime;


    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getEwCardType() {
        return ewCardType.getName();
    }

    public void setEwCardType(EwCardTypeEnum ewCardTypeEnum) {
        this.ewCardType = ewCardType;
    }

    public String getSrcType() {
        return srcType.getStr();
    }

    public void setSrcType(EwCardSendTypeEnum srcType) {
        this.srcType = srcType;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public String getEwCardNo() {
        return ewCardNo;
    }

    public void setEwCardNo(String ewCardNo) {
        this.ewCardNo = ewCardNo;
    }
}
