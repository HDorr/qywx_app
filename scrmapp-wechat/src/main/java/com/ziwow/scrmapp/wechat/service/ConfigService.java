package com.ziwow.scrmapp.wechat.service;

import java.util.Map;

/**
 * 配置信息
 * @author songkaiqi
 * @since 2019/11/18/下午4:09
 */
public interface ConfigService {

    Map<String,Object> getConfig(String key);

}
