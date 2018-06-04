package com.ziwow.scrmapp.common.bean.vo;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2018-04-18 14:09
 */
public class SmsStatisticsVo {
    private String dispatchDate;
    private Integer sendTolNum;
    private Integer installSendNum;
    private Integer repairSendNum;
    private Integer maintainSendNum;
    private Integer registerNum;
    private Integer installRegisterNum;
    private Integer repairRegisterNum;
    private Integer maintainRegisterNum;
    private Integer smsType;

    public String getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(String dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public Integer getSendTolNum() {
        return sendTolNum;
    }

    public void setSendTolNum(Integer sendTolNum) {
        this.sendTolNum = sendTolNum;
    }

    public Integer getInstallSendNum() {
        return installSendNum;
    }

    public void setInstallSendNum(Integer installSendNum) {
        this.installSendNum = installSendNum;
    }

    public Integer getRepairSendNum() {
        return repairSendNum;
    }

    public void setRepairSendNum(Integer repairSendNum) {
        this.repairSendNum = repairSendNum;
    }

    public Integer getMaintainSendNum() {
        return maintainSendNum;
    }

    public void setMaintainSendNum(Integer maintainSendNum) {
        this.maintainSendNum = maintainSendNum;
    }

    public Integer getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(Integer registerNum) {
        this.registerNum = registerNum;
    }

    public Integer getInstallRegisterNum() {
        return installRegisterNum;
    }

    public void setInstallRegisterNum(Integer installRegisterNum) {
        this.installRegisterNum = installRegisterNum;
    }

    public Integer getRepairRegisterNum() {
        return repairRegisterNum;
    }

    public void setRepairRegisterNum(Integer repairRegisterNum) {
        this.repairRegisterNum = repairRegisterNum;
    }

    public Integer getMaintainRegisterNum() {
        return maintainRegisterNum;
    }

    public void setMaintainRegisterNum(Integer maintainRegisterNum) {
        this.maintainRegisterNum = maintainRegisterNum;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }
}
