package com.ziwow.scrmapp.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.wechat.persistence.entity.Config;
import com.ziwow.scrmapp.wechat.persistence.mapper.ConfigMapper;
import com.ziwow.scrmapp.wechat.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * @author songkaiqi
 * @since 2019/11/18/下午4:10
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public Map<String, Object> getConfig(String key) {
        Config config = configMapper.selectConfig(key);
        if (config.getAginging() && isTime(config)){
            return Collections.EMPTY_MAP;
        }
        return JSON.parseObject(config.getContent());
    }


    /**
     * 判断当前设置是否在有效时间内
     * @param config
     * @return
     */
    private boolean isTime(Config config){
        final long l = System.currentTimeMillis();
        return config.getStartDate().getTime() < l && l < config.getEndDate().getTime();
    }


}
