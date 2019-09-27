package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.persistence.entity.Channel;

public interface ChannelService {

    Channel query(Long channelId);

    String selectWelcomeTextByChannelId(String channelId);
}
