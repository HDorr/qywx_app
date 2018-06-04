package com.ziwow.scrmapp.wechat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ziwow.scrmapp.wechat.persistence.entity.WechatArea;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCity;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatProvince;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatCityMapper;
import com.ziwow.scrmapp.wechat.service.WechatCityService;
import com.ziwow.scrmapp.wechat.vo.WechatUserVo;

@Service
public class WechatCityServiceImpl implements WechatCityService {

    Logger logger = LoggerFactory.getLogger(WechatCityServiceImpl.class);
    @Resource
    WechatCityMapper wechatCityMapper;

    @Override
    @Cacheable(value = "provinces", key = "'province'")
    public List<WechatProvince> getProvince() {
        return wechatCityMapper.getProvince();
    }


    @Override
    @Cacheable(value = "citys", key = "'city' + #provinceId")
    public List<WechatCity> getCity(String provinceId) {
        return wechatCityMapper.getCity(provinceId);
    }

    @Override
    @Cacheable(value = "areas", key = "'area' + #cityId")
    public List<WechatArea> getArea(String cityId) {
        return wechatCityMapper.getArea(cityId);
    }

    @Override
    public WechatUserVo getCityName(String areaId) {
        return wechatCityMapper.getAllName(areaId);
    }
}