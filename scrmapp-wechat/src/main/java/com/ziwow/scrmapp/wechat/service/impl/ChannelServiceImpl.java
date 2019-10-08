package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.persistence.entity.Channel;
import com.ziwow.scrmapp.common.persistence.mapper.ChannelMapper;
import com.ziwow.scrmapp.wechat.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ChannelMapper channelMapper;


    @Override
    public Channel query(Long channelId) {
        return channelMapper.query(channelId);
    }

    @Override
    public String selectWelcomeTextByChannelId(String channelId) {
        return channelMapper.selectWelcomeTextByChannelId(channelId);
    }

}
