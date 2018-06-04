package com.ziwow.scrmapp.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ziwow.scrmapp.common.persistence.entity.Filter;
import com.ziwow.scrmapp.common.persistence.entity.FilterChangeRemind;
import com.ziwow.scrmapp.common.persistence.mapper.FilterChangeRemindMapper;
import com.ziwow.scrmapp.wechat.service.FilterChangeRemindService;
import com.ziwow.scrmapp.wechat.service.WechatTemplateService;
import com.ziwow.scrmapp.common.bean.vo.FilterChangeRemindMsgVo;

/**
 * Created by xiaohei on 2017/4/10.
 */
@Service
public class FilterChangeRemindServiceImpl implements FilterChangeRemindService {
	@Autowired
	private FilterChangeRemindMapper filterChangeRemindMapper;
	@Autowired
	private WechatTemplateService wechatTemplateService;
	@Value("${template.msg.url}")
	private String msgUrl;
	@Transactional
	@Override
	public int saveFilterRemind(List<Filter> filterList, Long productId) {
		List<FilterChangeRemind> filterChangeReminds = new ArrayList<FilterChangeRemind>();
		for (Filter f : filterList) {
			FilterChangeRemind filterChangeRemind = new FilterChangeRemind();
			filterChangeReminds.add(filterChangeRemind);
		}
		return 0;
	}

	@Override
	public void sendFilterChangeReminderTemplateMsg(FilterChangeRemindMsgVo filterChangeRemindMsgVo) {
		String title = "亲爱的"+ filterChangeRemindMsgVo.getNickName() +"，知道您忙，忘了家里的净水机还有3天就要更换滤芯了吧。";
		String remark = "点击【一键服务】预约更换滤芯，剩下的事交给沁先生来做。";
		String openId = filterChangeRemindMsgVo.getOpenId();
		String productName = filterChangeRemindMsgVo.getProductName();
		String installTime = filterChangeRemindMsgVo.getPurchaseDate();
		String expectReplaceTime = filterChangeRemindMsgVo.getRemindDate();
		String changeModal = filterChangeRemindMsgVo.getFilterName();
		wechatTemplateService.changeReminderTemplate(openId, msgUrl, title, productName, installTime, expectReplaceTime, changeModal, remark);
	}

	@Override
	public void sendFilterBeforeExpireReminderTemplateMsg(FilterChangeRemindMsgVo filterChangeRemindMsgVo) {
		String title = "亲爱的"+ filterChangeRemindMsgVo.getNickName() +"，时间过得很快，您家的净水机还有1个月就要更换滤芯了。";
		String remark = "届时您可点击【一键服务】预约更换滤芯，让沁先生帮你解决吧！";
		String openId = filterChangeRemindMsgVo.getOpenId();
		String productModel = filterChangeRemindMsgVo.getProductSpec();
		String purchaseDate = filterChangeRemindMsgVo.getPurchaseDate();
		wechatTemplateService.expirationReminderTemplate(openId, msgUrl, title, productModel, purchaseDate, remark);
	}

	@Override
	public void sendFilterAfterExpireReminderTemplateMsg(FilterChangeRemindMsgVo filterChangeRemindMsgVo) {
		String title = "亲爱的"+ filterChangeRemindMsgVo.getNickName() +"，您的净水机，滤芯已过期30天了！";
		String remark = "不及时更换滤芯，等于白装啦，点击【一键服务】预约更换滤芯，沁先生将安排工程师火速上门更换！如已更换，请忽略该消息。";
		String openId = filterChangeRemindMsgVo.getOpenId();
		String productModel = filterChangeRemindMsgVo.getProductSpec();
		String purchaseDate = filterChangeRemindMsgVo.getPurchaseDate();
		wechatTemplateService.expirationReminderTemplate(openId, msgUrl, title, productModel, purchaseDate, remark);
	}

	@Override
	public List<FilterChangeRemindMsgVo> getFilterChangeReminds() {
		return filterChangeRemindMapper.getFilterChangeReminds();
	}
}
