package com.ziwow.scrmapp.common.persistence.entity;

import java.util.Date;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2018-04-16 14:48
 */
public class SmsStatistics {
    private Long id;
    private String syncTime;            // 派单日期
    private Integer syncOrderNum;       // 派网点工单数
    private Integer sendSmsNum;         // 发送短信人数
    private Integer installSmsNum;      // 短信发送安装
    private Integer maintainSmsNum;     // 短信发送保养
    private Integer repairSmsNum;       // 短信发送维修
    private Integer registerNum;        // 注册人数
    private Integer installRegisterNum; // 安装短信注册
    private Integer maintainRegisterNum;  // 保养短信注册
    private Integer repairRegisterNum;     // 维修短信注册
    private Integer smsType;               // 1:当天 2:3天 3:7天

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

    public Integer getSyncOrderNum() {
        return syncOrderNum;
    }

    public void setSyncOrderNum(Integer syncOrderNum) {
        this.syncOrderNum = syncOrderNum;
    }

    public Integer getSendSmsNum() {
        return sendSmsNum;
    }

    public void setSendSmsNum(Integer sendSmsNum) {
        this.sendSmsNum = sendSmsNum;
    }

    public Integer getInstallSmsNum() {
        return installSmsNum;
    }

    public void setInstallSmsNum(Integer installSmsNum) {
        this.installSmsNum = installSmsNum;
    }

    public Integer getMaintainSmsNum() {
        return maintainSmsNum;
    }

    public void setMaintainSmsNum(Integer maintainSmsNum) {
        this.maintainSmsNum = maintainSmsNum;
    }

    public Integer getRepairSmsNum() {
        return repairSmsNum;
    }

    public void setRepairSmsNum(Integer repairSmsNum) {
        this.repairSmsNum = repairSmsNum;
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

    public Integer getMaintainRegisterNum() {
        return maintainRegisterNum;
    }

    public void setMaintainRegisterNum(Integer maintainRegisterNum) {
        this.maintainRegisterNum = maintainRegisterNum;
    }

    public Integer getRepairRegisterNum() {
        return repairRegisterNum;
    }

    public void setRepairRegisterNum(Integer repairRegisterNum) {
        this.repairRegisterNum = repairRegisterNum;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }
}