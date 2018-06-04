/**   
* @Title: WechatTemplateMapper.java
* @Package com.ziwow.scrmapp.wechat.persistence.mapper
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-4-13 下午3:16:51
* @version V1.0   
*/
package com.ziwow.scrmapp.wechat.persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName: WechatTemplateMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-4-13 下午3:16:51
 * 
 */
public interface WechatTemplateMapper {

	@Select("SELECT tempalte_id FROM `t_wechat_tempalte_message` WHERE template_id_short=#{shorId}")
	public String getTemplateID(@Param("shorId")String shorId);
}
