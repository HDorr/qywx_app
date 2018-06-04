package com.ziwow.scrmapp.common.persistence.mapper;

import com.ziwow.scrmapp.common.persistence.entity.Channel;

public interface ChannelMapper {
    Channel query(Long channelId);
}
