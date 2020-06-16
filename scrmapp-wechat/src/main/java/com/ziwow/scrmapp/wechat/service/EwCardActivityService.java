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
     * 根据手机号查询领取卡号
     * @param phone
     * @return
     */
    String selectCardNoByPhone(String phone);

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
     * @param phone
     */
    void addSendTimeAndPhoneByCardNo(String cardNo,String phone);

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


    /**
     * 判断改手机号是否发放
     * @return
     * @param phone
     */
    boolean existCardByPhone(String phone);

    /**
     * 根据卡号增加发放手机号码记录
     * @param cardNo
     * @param mobile
     */
    void addPhoneByCardNo(String cardNo, String mobile);

    /**
     * 根据手机号和类型查询真正的卡号
     * @param phone
     * @param type
     * @return
     */
    String selectCardNoByPhoneAndType(String phone, EwCardTypeEnum type);

}
