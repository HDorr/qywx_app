package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.wechat.weixin.TemplateData;
import java.util.List;

/**
 * 
 * ClassName: WechatTemplateService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2017-04-06 上午11:26:31 <br/>
 * 
 * @author john.chen
 * @version
 * @since JDK 1.6
 */
public interface WechatTemplateService {
	public String getTemplateID(String shorId);
	public void sendTemplateMsgByToken(String wx_token, TemplateData data);
	
	/**
	 * 会员注册成功通知
	* @Title: registerTemplate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param openId  		openId
	* @param @param url     		跳转链接
	* @param @param title			标题
	* @param @param nikeName		昵称
	* @param @param registerTime	注册时间
	* @param @param remark    		备注
	* @return void    返回类型
	* @version 1.0
	* @author Hogen.hu
	* 
	* 恭喜您注册成为会员！
	     会员昵称：super
	    注册时间：2014年7月21日 18:36
	    恭喜您注册成为会员，您将享受到会员所有权利！
	 */
	public void registerTemplate(String openId,String url,String title,String nikeName,String registerTime,String remark);
	
	
	/**
	 * 信息更改通知
	* @Title: msgChangeTemplate
	* @param @param openId
	* @param @param url
	* @param @param title
	* @param @param msgType		 信息类型
	* @param @param changeTime   更改时间
	* @param @param remark    设定文件
	* @return void    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public void msgChangeTemplate(String openId,String url,String title,String msgType,String changeTime,String remark);
	
	/**
	 * 滤芯到期提醒
	* @Title: expirationReminderTemplate
	* @param @param openId
	* @param @param url
	* @param @param title
	* @param @param productModel    产品型号
	* @param @param purchaseDate	购买日期
	* @param @param remark    设定文件
	* @return void    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public void expirationReminderTemplate(String openId,String url,String title,String productModel,String purchaseDate,String remark);
	
	
	/**
	 * 滤芯更换提醒
	* @Title: changeReminderTemplate
	* @param @param openId				
	* @param @param url
	* @param @param title
	* @param @param productName			产品名称
	* @param @param installTime			安装时间
	* @param @param expectReplaceTime	预计换芯时间
	* @param @param changeModal			更换滤芯型号
	* @param @param remark    设定文件
	* @return void    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public void changeReminderTemplate(String openId,String url,String title,String productName,String installTime,String expectReplaceTime,String changeModal,String remark);
	
	
	/**
	 * 预约结果通知
	* @Title: subscribeResultNoticeTemplate
	* @param @param openId
	* @param @param url
	* @param @param title
	* @param @param name				我的姓名
	* @param @param phone				我的电话
	* @param @param address				我的地址
	* @param @param projectName			服务项目
	* @param @param subscribeResult		预约结果
	* @param @param remark
	* @param @return    设定文件
	* @return TemplateData    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public void subscribeResultNoticeTemplate(String openId,String url,
			String title,String name,String phone,String address,String projectName,String subscribeResult,String remark);
	
	
	/**
	 * 派单成功通知
	* @Title: servicesToNoticeTemplate
	* @param @param openId
	* @param @param templateId
	* @param @param url
	* @param @param title
	* @param @param orderType			订单类型
	* @param @param orderNum			订单编号
	* @param @param appointmentTime		预约时间
	* @param @param engineer			工程师
	* @param @param phone				联系电话
	* @param @param remark
	* @param @return    设定文件
	* @return TemplateData    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public void servicesToNoticeTemplate(String openId,String url,
			String title,String orderType,String orderNum,String appointmentTime,String engineer,String phone,String remark);
	
	
	/**
	 * 预约服务提醒
	* @Title: reservationServiceRemind
	* @param @param openId
	* @param @param templateId
	* @param @param url
	* @param @param title
	* @param @param name			会员姓名
	* @param @param serverType		服务类型
	* @param @param serverTime		服务时间
	* @param @param remark
	* @param @return    设定文件
	* @return TemplateData    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public void reservationServiceRemind(String openId,String url,String title,
			String name,String serverType,String serverTime,String remark);
	
	
	/**
	 * 服务取消通知
	* @Title: serviceOrCancellationTemplateSetting
	* @param @param openId
	* @param @param templateId
	* @param @param url
	* @param @param title
	* @param @param srviceName		服务名称
	* @param @param applyTime		申请时间
	* @param @param cancelTime		取消时间
	* @param @param remark
	* @param @return    设定文件
	* @return TemplateData    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public void serviceOrCancellationTemplate(String openId,String url,String title,
			String srviceName,String applyTime,String cancelTime,String remark);
	
	/**
	 * 服务评价提醒
	* @Title: serviceEvaluationToRemindTemplateSetting
	* @param @param openId
	* @param @param templateId
	* @param @param url
	* @param @param title
	* @param @param orderNum		订单编号
	* @param @param serviceTime		服务时间
	* @param @param remark
	* @param @return    设定文件
	* @return TemplateData    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public void serviceEvaluationToRemindTemplate(String openId,String url,String title,
			String orderNum,String serviceTime,String remark);
	
	
	/**
	 * 服务评价提醒
	* @Title: evaluationOfCompleteRemindTemplate
	* @param @param openId
	* @param @param templateId
	* @param @param url
	* @param @param title
	* @param @param orderNum		订单编号
	* @param @param serviceTime		服务时间
	* @param @param remark
	* @param @return    设定文件
	* @return TemplateData    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public void evaluationOfCompleteRemindTemplate(String openId,String url,String title,
			String orderNum,String serviceTime,String remark);
	
	
	/**
	 * 绑定成功通知
	* @Title: bindingToInformTemplateTemplate
	* @param @param openId
	* @param @param templateId
	* @param @param url
	* @param @param title
	* @param @param productName		产品名称
	* @param @param productModel	产品型号
	* @param @param remark
	* @param @return    设定文件
	* @return TemplateData    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public void bindingToInformTemplateTemplate(String openId,String url,String title,
			String productName,String productModel,String remark);
	
	/**
	 * 服务时间更改通知
	* @Title: bindingToInformTemplateTemplate
	* @param @param openId
	* @param @param url
	* @param @param title
	* @param @param orderType      	订单类型 
	* @param @param ordersCode		订单编号
	* @param @param orderTime		预约时间
	* @param @param qyhUserName		工程师
	* @param @param qyhUserPhone	联系电话
	* @param @param remark
	* @param @return
	* @version 1.0
	* 
	* @author John.chen
	 */
	public void changeAppointmentTemplate(String openId, String url, String title, String orderType, String ordersCode,
			String orderTime, String qyhUserName, String qyhUserPhone, String remark);

	/****
	 * 下单通知模板
   * @param openId 用户openid
   * @param url 链接
   * @param params
   * @param type
   * @param toMiniProgram
   */
	void sendTemplate(String openId, String url, List<String> params, String type,
      boolean toMiniProgram);

	void sendTemplateType2(String openId, String url, List<String> params, String type,
			boolean toMiniProgram);
}