package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.tools.weixin.InMessage;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatArea;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCity;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatProvince;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatCityMapper;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatMessageLogMapper;
import com.ziwow.scrmapp.wechat.service.WechatCityService;
import com.ziwow.scrmapp.wechat.service.WechatMessageLogService;
import com.ziwow.scrmapp.wechat.vo.WechatUserVo;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class WechatMessageLogServiceImpl implements WechatMessageLogService {

    Logger logger = LoggerFactory.getLogger(WechatMessageLogServiceImpl.class);
    
    private WechatMessageLogMapper wechatMessageLogMapper;
    @Autowired
    public void setWechatMessageLogMapper(WechatMessageLogMapper wechatMessageLogMapper) {
        this.wechatMessageLogMapper = wechatMessageLogMapper;
    }

    @Async
    @Override
    public void saveLog(InMessage message) {
      wechatMessageLogMapper.saveLog(message);
    }
}