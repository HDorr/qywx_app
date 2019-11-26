package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.wechat.enums.ServiceSubscribeCrowd;
import com.ziwow.scrmapp.wechat.persistence.entity.NoticeRoster;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 *  发放通知名单
 * @author songkaiqi
 * @since 2019/11/21/上午10:01
 */
public interface NoticeRosterMapper {

    /**
     * 根据人群查询未发放的
     * @param serviceSubscribeCrowd
     * @return
     */
    @Results(value={
            @Result(column="id", property="id"),
            @Result(column="phone", property="phone"),
            @Result(column="buy_time", property="buyTime"),
            @Result(column="product_code", property="productCode"),
            @Result(column="proper_type", property="properType")
    })
    @Select("select id,phone,buy_time,proper_type,product_code from t_notice_list where send = 0 and proper_type = #{crowd}")
    List<NoticeRoster> queryNoSendByType(@Param("crowd") ServiceSubscribeCrowd serviceSubscribeCrowd);

    /**
     * 根据id 修改发放标识
     * @param id
     */
    @Update("update t_notice_list set send_time = now() , send = 1 ,updated_at = now() where id = #{id}")
    void updateSendById(@Param("id") Long id);

    /**
     * 根据id 修改发放标识(无时间)
     * @param id
     */
    @Update("update t_notice_list set send = 1 ,updated_at = now() where id = #{id}")
    void updateSendNoTimeById(@Param("id") Long id);

    /**
     * 根据id 修改操作标识
     * @param id
     * @param handleType
     */
    @Update("update t_notice_list set handle = 1,handle_time = now() ,handle_type = #{handleType} ,updated_at = now()  where id = #{id}")
    void updateHandleById(@Param("id") Long id, @Param("handleType") String handleType);

    /**
     *  依据手机号查 已发放通知。但是未进行预约的记录
     * @param mobilePhone
     * @return
     */
    @Select("select id from t_notice_list where phone = #{phone} and send = 1 and handle = 0 and send_time is not null limit 1")
    Long queryNoHandleByPhone(@Param("phone") String mobilePhone);

    /**
     * 查询符合预约条件的 id和人群类型
     * @param mobilePhone
     * @return
     */
    @Select("select id,proper_type from t_notice_list where phone = #{phone} and send = 1 and handle = 0 and send_time is not null limit 1")
    Map<String, Object> queryIdAndTypeByPhone(@Param("phone") String mobilePhone);
}
