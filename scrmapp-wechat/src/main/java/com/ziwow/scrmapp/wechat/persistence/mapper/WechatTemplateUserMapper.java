package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.wechat.persistence.entity.Config;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatTemplateUserGroup;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatTemplateUserSrc;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 通知模板自动化推送
 * @author jitre
 * @since 2020年02月07日
 */
public interface WechatTemplateUserMapper {

  /**
   * 查询未发送的微信模板消息
   * @param offset
   * @param size
   * @return
   */
  @Results({
      @Result(column = "identity_type", property = "identityType"),
      @Result(column = "group_id", property = "groupId"),
      @Result(column = "param_count", property = "paramCount"),
      @Result(column = "param_1", property = "param1"),
      @Result(column = "param_2", property = "param2"),
      @Result(column = "param_3", property = "param3"),
  })
  @Select("select id,group_id,identity,identity_type,param_count,param_1,param_2,param_3,flag  from t_wechat_template_user_src where flag=0 and group_id=#{groupId}  order by id limit #{size}")
  List<WechatTemplateUserSrc> selectUnsendTemplateUserSrc(@Param("groupId") long groupId,@Param("size")int size);


  /**
   * 查询未发送的人群总数
   * @return
   */
  @Select("select count(*)  from t_wechat_template_user_src where flag=0 and group_id=#{groupId}")
  Long countUnsendTotal(long groupId);


  /**
   * 设置发送过的人群的状态
   * @param id
   */
  @Update("update t_wechat_template_user_src set flag=1 where id=#{id}")
  void updateSendStatusById(@Param("id")long id);


  /***
   * 根据人群包的名称查找人群包
   * @param name
   * @return
   */
  @Results({
      @Result(column = "to_mini", property = "toMini"),
      @Result(column = "template_id", property = "templateId"),
      @Result(column = "center_param", property = "centerParam"),
      @Result(column = "param_count", property = "paramCount"),
      @Result(column = "param_1", property = "param1"),
      @Result(column = "param_2", property = "param2"),
      @Result(column = "param_3", property = "param3"),
  })
  @Select("select id,name,title,remark,url,template_id,to_mini,center_param,param_count,param_1,param_2,param_3 from t_wechat_template_user_group where name=#{name} limit 1")
  WechatTemplateUserGroup selectGroupByName(@Param("name") String name);

}
