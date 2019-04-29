package com.ziwow.scrmapp.wechat.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ziwow.scrmapp.common.redis.RedisService;
import com.ziwow.scrmapp.wechat.constants.RedisKeyConstants;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatTemplateMapper;
import com.ziwow.scrmapp.wechat.service.WechatTemplateService;
import com.ziwow.scrmapp.wechat.service.WeiXinService;
import com.ziwow.scrmapp.wechat.weixin.TemplateData;
import com.ziwow.scrmapp.wechat.weixin.TemplateSetting;
import com.ziwow.scrmapp.wechat.weixin.WechatMsgAction;

/**
 * ClassName: WXTemplateServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2015-10-29 上午11:33:12 <br/>
 * 
 * @author daniel.wang
 * @version
 * @since JDK 1.6
 */
@Service
public class WechatTemplateServiceImpl implements WechatTemplateService {
	private Logger LOG = LoggerFactory.getLogger(WechatTemplateService.class);
	
	@Value("${wechat.appid}")
	private String appid;
	@Value("${wechat.appSecret}")
	private String secret;
	
	@Value("${registerTemplate.id}")
	private String registerTemplateId;
	
	@Value("${msgChangeTemplate.id}")
	private String msgChangeTemplateId;
	
	@Value("${expirationReminderTemplate.id}")
	private String expirationReminderTemplateId;
	
	@Value("${changeReminderTemplage.id}")
	private String changeReminderTemplateId;
	
	@Value("${subscribeResultNoticeTemplate.id}")
	private String subscribeResultNoticeTemplateId;
	
	@Value("${servicesToNoticeTemplate.id}")
	private String servicesToNoticeTemplateId;
	
	@Value("${reservationServiceRemindTemplate.id}")
	private String reservationServiceRemindTemplateId;
	
	@Value("${serviceOrCancellationTemplate.id}")
	private String serviceOrCancellationTemplateId;
	
	@Value("${serviceEvaluationToRemindTemplate.id}")
	private String serviceEvaluationToRemindTemplateId;
	
	@Value("${evaluationOfCompleteRemindTemplate.id}")
	private String evaluationOfCompleteRemindTemplateId;
	
	@Value("${bindingToInformTemplate.id}")
	private String bindingToInformTemplateId;
	
	@Value("${changeAppointmentTemplate.id}")
	private String changeAppointmentTemplateId;

	@Value("${orderSubmittedTemplate.id}")
	private String orderSubmittedTemplateId;

	@Value("${orderRefundTemplate.id}")
	private String orderRefundTemplateId;

	@Value("${pointChangeTemplate.id}")
	private String pointChangeTemplateId;

	@Value("${qyscRegisterTemplate.id}")
	private String  qyscRegsiterTemplateId;

	@Autowired
	Environment environment;

	@Resource
	private RedisService redisService;
	
	@Resource
	private WechatTemplateMapper wechatTemplateMapper;
	
	@Resource
	private WeiXinService weiXinService;
	
	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.weixin.api.msg.WXTemplateService#getTemplateID(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String getTemplateID(String shorId) {
		String KEY = RedisKeyConstants.getTemplateIdKey() + shorId;
		if (searchTicket(KEY) != null) {
			return (String) redisService.get(KEY);
		} else {
			String tempaltId = wechatTemplateMapper.getTemplateID(shorId);
			if (StringUtils.isNotBlank(tempaltId)) {
				redisService.setKeyExpire(KEY, tempaltId, (long) 30, TimeUnit.DAYS);
				return tempaltId;
			}
		}
		return null;
	}


	
	private String searchTicket(String redis_tiket_key) {
		// 保证20秒内更新
		try {
			Long expireTime = redisService.getExpire(redis_tiket_key, TimeUnit.SECONDS);
			// 大于20秒
			if (expireTime > 20) {
				return (String) redisService.get(redis_tiket_key);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	
	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.weixin.api.msg.WXTemplateService#sendTemplateMsgByToken(java.lang.String,
	 *      com.ziwow.scrmapp.wechat.bean.vo.template.dubbo.model.weixin.common.bo.TemplateData)
	 */
	@Override
	public void sendTemplateMsgByToken(String access_token, TemplateData data) {
		WechatMsgAction action = new WechatMsgAction();
		try {
			action.templateSend(access_token, data);
		} catch (Exception e) {
			LOG.error("发送模板消息失败：", e);
		}
	}



	@Override
	public void registerTemplate(String openId, String url, String title,
			String nikeName, String registerTime, String remark) {
		TemplateData templateData = TemplateSetting.registerTemplateSetting(openId, this.getTemplateID(registerTemplateId), url, title, nikeName, registerTime, remark);
		this.sendTemplateMsgByToken(weiXinService.getAccessToken(appid, secret), templateData);
	}



	@Override
	public void msgChangeTemplate(String openId, String url, String title,
			String msgType, String changeTime, String remark) {
		TemplateData templateData = TemplateSetting.msgChangeTemplateSetting(openId, this.getTemplateID(msgChangeTemplateId), url, title, msgType, changeTime, remark);
		this.sendTemplateMsgByToken(weiXinService.getAccessToken(appid, secret), templateData);
	}

	@Override
	public void expirationReminderTemplate(String openId, String url,
			String title, String productModel, String purchaseDate,
			String remark) {
		TemplateData templateData = TemplateSetting.expirationReminderTemplateSetting(openId, this.getTemplateID(expirationReminderTemplateId), url, title, productModel, purchaseDate, remark);
		this.sendTemplateMsgByToken(weiXinService.getAccessToken(appid, secret), templateData);		
	}
	
	@Override
	public void changeReminderTemplate(String openId, String url,
			String title, String productName, String installTime,
			String expectReplaceTime, String changeModal, String remark) {
		TemplateData templateData = TemplateSetting.changeReminderTemplateSetting(openId, this.getTemplateID(changeReminderTemplateId), url, title, productName, installTime, expectReplaceTime, changeModal, remark);
		this.sendTemplateMsgByToken(weiXinService.getAccessToken(appid, secret), templateData);
	}
	
	@Override
	public void subscribeResultNoticeTemplate(String openId, String url,
			String title, String name, String phone, String address,
			String projectName, String subscribeResult, String remark) {
		TemplateData templateData = TemplateSetting.subscribeResultNoticeTemplateSetting(openId, this.getTemplateID(subscribeResultNoticeTemplateId), url, title, name, phone, address, projectName, subscribeResult, remark);
		this.sendTemplateMsgByToken(weiXinService.getAccessToken(appid, secret), templateData);		
	}

	@Override
	public void servicesToNoticeTemplate(String openId, String url,
			String title, String orderType, String orderNum,
			String appointmentTime, String sendStore, String phone,
			String remark) {
		TemplateData templateData = TemplateSetting.servicesToNoticeTemplateSetting(openId, this.getTemplateID(servicesToNoticeTemplateId), url, title, orderType, orderNum, appointmentTime, sendStore, phone, remark);
		this.sendTemplateMsgByToken(weiXinService.getAccessToken(appid, secret), templateData);	
	}

	@Override
	public void reservationServiceRemind(String openId, String url,
			String title, String name, String serverType, String serverTime,
			String remark) {
		TemplateData templateData = TemplateSetting.reservationServiceRemindTemplateSetting(openId, this.getTemplateID(reservationServiceRemindTemplateId), url, title, name, serverType, serverTime, remark);
		this.sendTemplateMsgByToken(weiXinService.getAccessToken(appid, secret), templateData);
	}

	@Override
	public void serviceOrCancellationTemplate(String openId, String url,
			String title, String srviceName, String applyTime,
			String cancelTime, String remark) {
		TemplateData templateData = TemplateSetting.serviceOrCancellationTemplateSetting(openId, this.getTemplateID(serviceOrCancellationTemplateId), url, title, srviceName, applyTime, cancelTime, remark);
		this.sendTemplateMsgByToken(weiXinService.getAccessToken(appid, secret), templateData);
		
	}

	@Override
	public void serviceEvaluationToRemindTemplate(String openId, String url,
			String title, String orderNum, String serviceTime, String remark) {
		TemplateData templateData = TemplateSetting.serviceEvaluationToRemindTemplateSetting(openId, this.getTemplateID(serviceEvaluationToRemindTemplateId), url, title, orderNum, serviceTime, remark);
		this.sendTemplateMsgByToken(weiXinService.getAccessToken(appid, secret), templateData);
		
	}

	@Override
	public void evaluationOfCompleteRemindTemplate(String openId, String url,
			String title, String orderNum, String serviceTime, String remark) {
		TemplateData templateData = TemplateSetting.evaluationOfCompleteRemindTemplateSetting(openId, this.getTemplateID(evaluationOfCompleteRemindTemplateId), url, title, orderNum, serviceTime, remark);
		this.sendTemplateMsgByToken(weiXinService.getAccessToken(appid, secret), templateData);
		
	}

	@Override
	public void bindingToInformTemplateTemplate(String openId, String url,
			String title, String productName, String productModel, String remark) {
		TemplateData templateData = TemplateSetting.bindingToInformTemplateSetting(openId, this.getTemplateID(bindingToInformTemplateId), url, title, productName, productModel, remark);
		this.sendTemplateMsgByToken(weiXinService.getAccessToken(appid, secret), templateData);
		
	}

	@Override
	public void changeAppointmentTemplate(String openId, String url, String title, String orderType, String ordersCode,
			String orderTime, String qyhUserName, String qyhUserPhone, String remark) {
		TemplateData templateData = TemplateSetting.changeAppointmentTemplateSetting(openId,
				this.getTemplateID(changeAppointmentTemplateId), url, title, orderType, ordersCode, orderTime, qyhUserName,
				qyhUserPhone, remark);
		this.sendTemplateMsgByToken(weiXinService.getAccessToken(appid, secret), templateData);
	}

	private static final String KEY=".id";
	@Override
	public void sendTemplate(String openId, String url, List<String> params, String type) {
	  //根据类型获取模板id
    String templateKey=type+KEY;
		String templateShortId = environment.getProperty(templateKey);
		String templateID = this.getTemplateID(templateShortId);
		String remark = wechatTemplateMapper.getTemplateRemark(templateShortId);
		String title = wechatTemplateMapper.getTemplateTitle(templateShortId);
		List<String> paramList=new ArrayList<>();
		paramList.add(title);
		paramList.addAll(params);
		paramList.add(remark);
		//获取模板的内容
		TemplateData templateData = TemplateSetting.generateTemplateData(openId,templateID
				, url,paramList.toArray(new String[0]));
		this.sendTemplateMsgByToken(weiXinService.getAccessToken(appid, secret), templateData);

	}
}