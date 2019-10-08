package com.ziwow.scrmapp.common.persistence.mapper;

import com.ziwow.scrmapp.common.persistence.entity.Channel;
import org.apache.ibatis.annotations.Param;

public interface ChannelMapper {
    Channel query(Long channelId);

    String selectWelcomeTextByChannelId(@Param("channelId") String channelId);
}
