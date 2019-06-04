package com.ziwow.scrmapp.wechat.weixin;


public class TemplateSetting {
	public static final String red_color = "#FF0000";
	public static final String yellow_color = "#ffa800";
	public static final String blue_color = "#1e95d7";
	

	
	/**
	 * 会员注册成功通知
	* @Title: registerTemplateSetting
	* @param @param openId
	* @param @param templateId
	* @param @param url
	* @param @param title
	* @param @param nikeName
	* @param @param registerTime
	* @param @param remark
	* @param @return    设定文件
	* @return TemplateData    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public static TemplateData registerTemplateSetting(String openId,String templateId,String url,String title,String nikeName,String registerTime,String remark){
		TemplateData data = new TemplateData(openId, templateId, url);
		
		data.getTemplateDataItemInstance().addItem("first", title);
		data.getTemplateDataItemInstance().addItem("keyword1", nikeName, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword2", registerTime, blue_color);
		data.getTemplateDataItemInstance().addItem("remark", remark);
		return data;
	}
	
	/**
	 * 信息更改通知
	* @Title: msgChangeTemplateSetting
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param openId     openId
	* @param @param templateId
	* @param @param url
	* @param @param title
	* @param @param msgType
	* @param @param changeTime
	* @param @param remark
	* @param @return    设定文件
	* @return TemplateData    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public static TemplateData msgChangeTemplateSetting(String openId,String templateId,String url,String title,String msgType,String changeTime,String remark){
		TemplateData data = new TemplateData(openId, templateId, url);
		data.getTemplateDataItemInstance().addItem("first", title);
		data.getTemplateDataItemInstance().addItem("keyword1", msgType, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword2", changeTime, blue_color);
		data.getTemplateDataItemInstance().addItem("remark", remark);
		return data;
	}
	
	/**
	 * 滤芯到期提醒
	* @Title: expirationReminderTemplateSetting
	* @param @param openId
	* @param @param templateId
	* @param @param url
	* @param @param title
	* @param @param productModel
	* @param @param purchaseDate
	* @param @param remark
	* @param @return    设定文件
	* @return TemplateData    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public static TemplateData expirationReminderTemplateSetting(String openId,String templateId,String url,String title,String productModel,String purchaseDate,String remark){
		TemplateData data = new TemplateData(openId, templateId, url);
		data.getTemplateDataItemInstance().addItem("first", title);
		data.getTemplateDataItemInstance().addItem("keyword1", productModel, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword2", purchaseDate, blue_color);
		data.getTemplateDataItemInstance().addItem("remark", remark);
		return data;
	}
	
	/**
	 * 滤芯更换提醒
	* @Title: changeReminderTemplateSetting
	* @param @param openId
	* @param @param templateId
	* @param @param url
	* @param @param title
	* @param @param productName
	* @param @param installTime
	* @param @param expectReplaceTime
	* @param @param changeModal
	* @param @param remark
	* @param @return    设定文件
	* @return TemplateData    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public static TemplateData changeReminderTemplateSetting(String openId,String templateId,String url,String title,String productName,String installTime,String expectReplaceTime,String changeModal,String remark){
		TemplateData data = new TemplateData(openId, templateId, url);
		data.getTemplateDataItemInstance().addItem("first", title);
		data.getTemplateDataItemInstance().addItem("keyword1", productName, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword2", installTime, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword3", expectReplaceTime, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword4", changeModal, blue_color);
		data.getTemplateDataItemInstance().addItem("remark", remark);
		return data;
	}
	
	/**
	 * 
	* @Title: subscribeResultNoticeTemplateSetting
	* @param @param openId
	* @param @param templateId
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
	public static TemplateData subscribeResultNoticeTemplateSetting(String openId,String templateId,String url,
			String title,String name,String phone,String address,String projectName,String subscribeResult,String remark){
		TemplateData data = new TemplateData(openId, templateId, url);
		data.getTemplateDataItemInstance().addItem("first", title);
		data.getTemplateDataItemInstance().addItem("keyword1", name, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword2", phone, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword3", address, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword4", projectName, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword5", subscribeResult, blue_color);
		data.getTemplateDataItemInstance().addItem("remark", remark);
		return data;
	}
	
	
	/**
	 * 服务派单通知
	* @Title: servicesToNoticeTemplateSetting
	* @param @param openId
	* @param @param templateId
	* @param @param url
	* @param @param title
	* @param @param orderType			订单类型
	* @param @param orderNum			订单编号
	* @param @param appointmentTime		预约时间
	* @param @param sendStore			派单门店
	* @param @param phone				联系电话
	* @param @param remark
	* @param @return    设定文件
	* @return TemplateData    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public static TemplateData servicesToNoticeTemplateSetting(String openId,String templateId,String url,
			String title,String orderType,String orderNum,String appointmentTime,String sendStore,String phone,String remark){
		TemplateData data = new TemplateData(openId, templateId, url);
		data.getTemplateDataItemInstance().addItem("first", title);
		data.getTemplateDataItemInstance().addItem("keyword1", orderType, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword2", orderNum, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword3", appointmentTime, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword4", sendStore, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword5", phone, blue_color);
		data.getTemplateDataItemInstance().addItem("remark", remark);
		return data;
	}
	
	/**
	 * 预约服务提醒
	* @Title: reservationServiceRemindTemplateSetting
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
	public static TemplateData reservationServiceRemindTemplateSetting(String openId,String templateId,String url,String title,
			String name,String serverType,String serverTime,String remark){
		TemplateData data = new TemplateData(openId, templateId, url);
		data.getTemplateDataItemInstance().addItem("first", title);
		data.getTemplateDataItemInstance().addItem("keyword1", name, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword2", serverType, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword3", serverTime, blue_color);
		data.getTemplateDataItemInstance().addItem("remark", remark);
		return data;
	}
	
	
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
	public static TemplateData serviceOrCancellationTemplateSetting(String openId,String templateId,String url,String title,
			String srviceName,String applyTime,String cancelTime,String remark){
		TemplateData data = new TemplateData(openId, templateId, url);
		data.getTemplateDataItemInstance().addItem("first", title);
		data.getTemplateDataItemInstance().addItem("keyword1", srviceName, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword2", applyTime, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword3", cancelTime, blue_color);
		data.getTemplateDataItemInstance().addItem("remark", remark);
		return data;
	}
	
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
	public static TemplateData serviceEvaluationToRemindTemplateSetting(String openId,String templateId,String url,String title,
			String orderNum,String serviceTime,String remark){
		TemplateData data = new TemplateData(openId, templateId, url);
		data.getTemplateDataItemInstance().addItem("first", title);
		data.getTemplateDataItemInstance().addItem("keyword1", orderNum, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword2", serviceTime, blue_color);
		data.getTemplateDataItemInstance().addItem("remark", remark);
		return data;
	}
	
	
	/**
	 * 服务评价提醒
	* @Title: evaluationOfCompleteRemindSetting
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
	public static TemplateData evaluationOfCompleteRemindTemplateSetting(String openId,String templateId,String url,String title,
			String orderNum,String serviceTime,String remark){
		TemplateData data = new TemplateData(openId, templateId, url);
		data.getTemplateDataItemInstance().addItem("first", title);
		data.getTemplateDataItemInstance().addItem("keyword1", orderNum, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword2", serviceTime, blue_color);
		data.getTemplateDataItemInstance().addItem("remark", remark);
		return data;
	}
	
	/**
	 * 绑定成功通知
	* @Title: bindingToInformTemplateSetting
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
	public static TemplateData bindingToInformTemplateSetting(String openId,String templateId,String url,String title,
			String productName,String productModel,String remark){
		TemplateData data = new TemplateData(openId, templateId, url);
		data.getTemplateDataItemInstance().addItem("first", title);
		data.getTemplateDataItemInstance().addItem("keyword1", productName, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword2", productModel, blue_color);
		data.getTemplateDataItemInstance().addItem("remark", remark);
		return data;
	}
	
	/**
	 * 服务时间更改通知
	* @Title: servicesToNoticeTemplateSetting
	* @param @param openId
	* @param @param templateId
	* @param @param url
	* @param @param title
	* @param @param orderType			订单类型
	* @param @param ordersCode			订单编号
	* @param @param orderTime			预约时间
	* @param @param qyhUserName			工程师名称
	* @param @param qyhUserPhone		工程师电话
	* @param @param remark
	* @param @return    设定文件
	* @return TemplateData    返回类型
	* @version 1.0
	* @author John.chen
	 */
	public static TemplateData changeAppointmentTemplateSetting(String openId,String templateId,String url,
			String title,String orderType,String ordersCode,String orderTime,String qyhUserName,String qyhUserPhone,String remark){
		TemplateData data = new TemplateData(openId, templateId, url);
		data.getTemplateDataItemInstance().addItem("first", title);
		data.getTemplateDataItemInstance().addItem("keyword1", orderType, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword2", ordersCode, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword3", orderTime, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword4", qyhUserName, blue_color);
		data.getTemplateDataItemInstance().addItem("keyword5", qyhUserPhone, blue_color);
		data.getTemplateDataItemInstance().addItem("remark", remark);
		return data;
	}

	/***
	 * 生成模板消息的数据类型
	 * @param openId 用户openid
	 * @param templateId 模板的id
	 * @param url 模板对应的链接
	 * @param params 模板参数
	 * @return
	 */
	public static TemplateData  generateTemplateData(String openId,String templateId,String url, String[] params){
		TemplateData data = new TemplateData(openId, templateId, url);
		if(params.length>2){
			for (int i = 0; i < params.length; i++) {
			  if(i==0){
					data.getTemplateDataItemInstance().addItem("first",params[i]);
				}else if(i==(params.length-1)){
					data.getTemplateDataItemInstance().addItem("remark", params[i]);
				}
				else{
					data.getTemplateDataItemInstance().addItem("keyword"+i,params[i], blue_color);
				}
			}
			return data;
		}else{
			return data;
		}
	}


	public static TemplateData  generateTemplateDataType2(String openId,String templateId,String url, String[] params){
		TemplateData data = new TemplateData(openId, templateId, url);
		if(params.length>2){
			for (int i = 0; i < params.length; i++) {
				if(i==0){
					data.getTemplateDataItemInstance().addItem("first",params[i]);
				}else if(i==(params.length-1)){
					data.getTemplateDataItemInstance().addItem("remark", params[i]);
				}
				else{
					data.getTemplateDataItemInstance().addItem("keynote"+i,params[i], blue_color);
				}
			}
			return data;
		}else{
			return data;
		}
	}
}