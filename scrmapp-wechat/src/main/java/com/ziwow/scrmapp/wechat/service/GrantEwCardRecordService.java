package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.enums.EwCardSendTypeEnum;
import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;
import com.ziwow.scrmapp.wechat.persistence.entity.GrantEwCardRecord;

import java.util.LinkedList;
import java.util.List;

/**
 * 发放延保卡记录
 * @author songkaiqi
 * @since 2019/08/12/上午10:00
 */
public interface GrantEwCardRecordService {

    /**
     * 查询出没有发放的记录
     * @return
     */
    List<GrantEwCardRecord> selectRecord(EwCardSendTypeEnum sendType);


    /**
     * 根据手机号修改发送状态
     * @param phone
     * @param send
     * @param sendType
     */
    void updateSendByPhone(String phone,boolean send,EwCardSendTypeEnum sendType);


    /**
     * 增加记录卡
     * @param mobile
     * @param mask
     * @param type
     * @param send
     */
    void addEwCardRecord(String mobile, String mask, EwCardTypeEnum type,Boolean send);

    /**
     * 根据手机号增加掩码
     * @param mask
     * @param mobile
     * @param sendType
     */
    void addMaskByMobile(String mask, String mobile,EwCardSendTypeEnum sendType);


    /**
     * 根据掩码查询对应的发放记录
     * @param mask
     * @return
     */
    GrantEwCardRecord selectRecordByMask(String mask);


    /**
     * 根据手机号修改领取记录
     * @param phone
     * @param receive
     */
    void updateReceiveByPhone(String phone, boolean receive);


    /**
     * 根据领取状态查询延保卡发放记录
     * @param recevice
     * @return
     */
    List<GrantEwCardRecord> selectReceiveRecord(Boolean recevice);


    /**
     * 重置赠送延保记录
     * @param phone
     */
    void resetGrantEwCardRecord(String phone);

    /**
     * 根据手机号查询该延保卡是否存在
     * @param mobile
     * @return
     */
    boolean selectReceiveRecordByPhone(String mobile);

    /**
     * 查询指定时间段发送延保卡的用户电话
     * @param format
     * @return
     */
    LinkedList<GrantEwCardRecord> selectRecordByDate(String format);

    /**
     * 修改标记该用户已经二次发送短信
     * @param id
     */
    void updateMessageSend(String id);

    /**
     * 根据手机号修改领取记录
     * @param phone
     * @param receive
     */
    void updateReceiveByMask(String phone, boolean receive);

    /**
     * 根据手机号修改发送状态
     * @param mobile
     * @param send
     * @param sendType
     */
    void updateSendNoTimeByPhone(String mobile, boolean send, EwCardSendTypeEnum sendType);

    /**
     * 判断手机号是否是完工送过的
     * @param mobile
     * @return
     */
    boolean isGrantCard(String mobile);
}
