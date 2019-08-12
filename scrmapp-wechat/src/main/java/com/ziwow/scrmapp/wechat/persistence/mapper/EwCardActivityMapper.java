package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCardActivity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 活动延保卡
 *
 * @author songkaiqi
 * @since 2019/08/08/下午5:20
 */
public interface EwCardActivityMapper {


    /**
     * 根据掩码查询延保卡
     *
     * @param mask
     * @return
     */
    @Select("select card_no from t_ew_card_activity where mask = #{mask} and receive = false and send_time is not null  limit 1")
    String selectCardNoByMask(@Param("mask") String mask);


    /**
     * 根据卡号修改活动延保卡的状态
     *
     * @param cardNo
     * @param receive
     */
    @Update("update t_ew_card_activity set receive = #{receive} where card_no = #{cardNo}")
    void updateReceiveByCardNo(@Param("cardNo") String cardNo, @Param("receive") boolean receive);

    /**
     * 查询出可以发送的延保卡
     *
     * @param type 延保卡类型
     * @return
     */
    @Select("select card_no from t_ew_card_activity where receive = false and type = #{type} and send_time is null  limit 1")
    String selectCardNo(@Param("type") EwCardTypeEnum type);

    /**
     * 增加掩码
     *
     * @param cardNo
     * @param mask
     */
    @Update("update t_ew_card_activity set mask = #{mask} where card_no = #{cardNo}")
    void addMaskByCardNo(@Param("cardNo") String cardNo, @Param("mask") String mask);

    /**
     * 增加发送时间
     *
     * @param cardNo
     * @param phone
     */
    @Update("update t_ew_card_activity set send_time = now(),phone = #{phone} where card_no = #{cardNo}")
    void addSendTimeAndPhoneByCardNo(@Param("cardNo") String cardNo, @Param("phone") String phone);

    /**
     * 获取已经发送，但是没有领取，未到期的延保卡号
     *
     * @return
     */
    @Results({
            @Result(column = "card_no", property = "cardNo"),
            @Result(column = "send_time", property = "sendTime")
    })
    @Select("select card_no,send_time from t_ew_card_activity where  receive = false and send_time is not null ")
    List<EwCardActivity> selectCardByNoReceive();

    /**
     * 重置活动延保卡数据
     *
     * @param cardNo
     */
    @Update("update t_ew_card_activity set send_time = null,receive = false,mask = null where card_no = #{cardNo}")
    void resetActivityCard(@Param("cardNo") String cardNo);

    /**
     * 根据手机号查找发放记录
     * @param phone
     * @return
     */
    @Select("select id from t_ew_card_activity where phone = #{phone} limit 1")
    String selectCardByPhone(@Param("phone") String phone);

}
