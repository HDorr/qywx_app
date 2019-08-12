package com.ziwow.scrmapp.wechat.persistence.mapper;

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
    @Select("select phone,type from t_grant_ew_card_record where send = false")
    @Results({
            @Result(column = "phone", property = "phone"),
            @Result(column = "type", property = "type")
    })
    List<GrantEwCardRecord> selectRecord();

    /**
     * 根据手机号码修改发送状态
     *
     * @param phone
     * @param send
     */
    @Update("update t_grant_ew_card_record set send = #{send} where phone = #{phone}")
    void updateSendByPhone(@Param("phone") String phone, @Param("send") boolean send);

}
