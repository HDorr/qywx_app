package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCardActivity;

import java.util.List;

/**
 *  活动延保卡
 * @author songkaiqi
 * @since 2019/08/08/下午5:04
 */
public interface EwCardActivityService {


    /**
     * 根据掩码查询对应的延保卡号
     * @param cardNo
     * @return
     */
    String selectCardNoByMask(String cardNo);

    /**
     * 根据卡号修改活动延保卡的状态
     * @param cardNo
     * @param receive
     */
    void updateReceiveByCardNo(String cardNo,boolean receive);

    /**
     * 查询出未使用的延保卡
     * @return
     */
    String selectCardNo(EwCardTypeEnum type);

    /**
     *  添加掩码
     * @param cardNo
     * @param mask
     */
    void addMaskByCardNo(String cardNo, String mask);

    /**
     * 增加发送时间
     * @param cardNo
     */
    void addSendTimeByCardNo(String cardNo);

    /**
     * 获取已经发送，但是没有领取，未到期的延保卡号
     * @return
     */
    List<EwCardActivity> selectCardByNoReceive();

    /**
     * 重置延保卡数据
     * @param cardNo
     */
    void resetActivityCard(String cardNo);
}
