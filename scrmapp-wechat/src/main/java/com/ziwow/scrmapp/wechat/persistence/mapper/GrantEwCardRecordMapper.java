package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.common.enums.EwCardSendTypeEnum;
import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;
import com.ziwow.scrmapp.wechat.persistence.entity.GrantEwCardRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 发放延保卡记录
 *
 * @author songkaiqi
 * @since 2019/08/12/上午10:55
 */
public interface GrantEwCardRecordMapper {


    /**
     * 查询未发放的记录
     *
     * @return
     */
    @Select("select phone,type from t_grant_ew_card_record where send = false and src_type = #{sendType} ")
    @Results({
            @Result(column = "phone", property = "phone"),
            @Result(column = "type", property = "type")
    })
    List<GrantEwCardRecord> selectRecord(@Param("sendType") EwCardSendTypeEnum sendType);

    /**
     * 根据手机号码修改发送状态
     *
     * @param phone
     * @param send
     */
    @Update("update t_grant_ew_card_record set send = #{send},send_time = now() where phone = #{phone}")
    void updateSendByPhone(@Param("phone") String phone, @Param("send") boolean send);


    /**
     * 增加掩码发放记录
     * @param mobile
     * @param mask
     * @param type
     * @param send
     */
    @Insert("insert into t_grant_ew_card_record (phone,mask,type,send) values (#{mobile},#{mask},#{type},#{send})")
    void addEwCardRecord(@Param("mobile") String mobile, @Param("mask")String mask, @Param("type")EwCardTypeEnum type,@Param("send")Boolean send);

    /**
     * 根据手机号增加掩码
     * @param mask
     * @param phone
     */
    @Update("update t_grant_ew_card_record set mask = #{mask} where phone = #{phone}")
    void addMaskByMobile(@Param("mask") String mask, @Param("phone") String phone);

    /**
     * 根据掩码查询出对应的发放记录
     * @param mask
     * @return
     */
    @Select("select phone,type from t_grant_ew_card_record where mask = #{mask} and send = true and receive = false limit 1")
    @Results({
            @Result(column = "phone", property = "phone"),
            @Result(column = "type", property = "type")
    })
    GrantEwCardRecord selectRecordByMask(@Param("mask") String mask);

    /**
     *  根据手机号修改领取标识
     * @param phone
     * @param receive
     */
    @Update("update t_grant_ew_card_record set receive = #{receive} where phone = #{phone}")
    void updateReceiveByPhone(@Param("phone") String phone,@Param("receive") boolean receive);

    /**
     * 查询已经发放还未领取
     * @param receive
     * @return
     */
    @Select("select phone,type from t_grant_ew_card_record where  receive = #{receive} and send = true")
    @Results({
            @Result(column = "send_time", property = "sendTime"),
            @Result(column = "mask", property = "mask")
    })
    List<GrantEwCardRecord> selectReceiveRecord(@Param("receive") Boolean receive);

    /**
     * 根据手机号重置延保记录
     * @param phone
     */
    @Update("update t_grant_ew_card_record set receive = false,send = false,mask = null,send_time = null where phone = #{phone}")
    void resetGrantEwCardRecord(@Param("phone") String phone);

    /**
     * 查询该手机号发送的id
     * @param phone
     * @return
     */
    @Select("select id from t_grant_ew_card_record where  phone = #{phone} and send = true limit 1")
    Long selectReceiveRecordByPhone(@Param("phone") String phone);
}
