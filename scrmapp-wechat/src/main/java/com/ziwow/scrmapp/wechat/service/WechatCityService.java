package com.ziwow.scrmapp.wechat.service;

import java.util.List;

import com.ziwow.scrmapp.wechat.persistence.entity.WechatArea;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCity;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatProvince;
import com.ziwow.scrmapp.wechat.vo.WechatUserVo;

public interface WechatCityService {
    public List<WechatProvince> getProvince();

    public List<WechatCity> getCity(String provinceId);

    public List<WechatArea> getArea(String cityId);

    WechatUserVo getCityName(String areaId);
}