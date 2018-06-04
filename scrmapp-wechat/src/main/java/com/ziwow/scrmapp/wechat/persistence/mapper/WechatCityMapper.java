/**
 * Project Name:api-service
 * File Name:CartServiceMapper.java
 * Package Name:com.ziwow.service.scrm.persistence
 * Date:2014-7-22下午7:10:08
 * Copyright (c) 2014, 上海智握网络科技有限公司版权所有.
 */

package com.ziwow.scrmapp.wechat.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziwow.scrmapp.wechat.persistence.entity.WechatArea;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCity;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatProvince;
import com.ziwow.scrmapp.wechat.vo.WechatUserVo;

/**
 * @包名 com.ziwow.scrmapp.wechat.persistence.mapper
 * @文件名 WechatCityMapper.java
 * @作者 john.chen
 * @创建日期 2017-2-21
 * @版本 V 1.0
 */
public interface WechatCityMapper {
    public List<WechatProvince> getProvince();

    public List<WechatCity> getCity(@Param("provinceId") String provinceId);

    public List<WechatArea> getArea(@Param("cityId") String cityId);

    WechatUserVo getAllName(@Param("areaId") String areaId);
}