package com.ziwow.scrmapp.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ziwow.scrmapp.common.persistence.entity.WechatOrdersRecord;
import com.ziwow.scrmapp.common.persistence.mapper.WechatOrdersRecordMapper;
import com.ziwow.scrmapp.wechat.service.WechatOrdersRecordService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WechatOrdersRecordServiceImpl implements WechatOrdersRecordService {
	@Autowired
	private WechatOrdersRecordMapper wechatOrdersRecordMapper;
	
	@Override
	@Transactional
	public void saveWechatOrdersRecord(WechatOrdersRecord wechatOrdersRecord) {
		wechatOrdersRecordMapper.insert(wechatOrdersRecord);
	}
}
