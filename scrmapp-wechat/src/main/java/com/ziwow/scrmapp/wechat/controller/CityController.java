package com.ziwow.scrmapp.wechat.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatArea;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCity;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatProvince;
import com.ziwow.scrmapp.wechat.service.WechatAESService;
import com.ziwow.scrmapp.wechat.service.WechatCityService;
import com.ziwow.scrmapp.wechat.service.WechatFansService;

/**
 * 获取省市区三级互联接口
 *
 * @包名 com.ziwow.scrmapp.wechat.controller
 * @文件名 CityController.java
 * @作者 john.chen
 * @创建日期 2017-2-21
 * @版本 V 1.0
 */
@Controller
@RequestMapping(value = "/scrmapp/consumer/")
public class CityController {
    private static final Logger logger = LoggerFactory.getLogger(CityController.class);
    @Resource
    WechatCityService wechatCityService;
    @Resource
    WechatFansService wechatFansService;
    @Resource
    WechatAESService wechatAESService;

    @RequestMapping(value = "/province/get", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result getProvince(@RequestParam(value = "openId", required = false) String openId) {
        Result result = new BaseResult();
        try {
            if (!StringUtils.isEmpty(openId)) {
                String openIdDes = wechatAESService.Decrypt(openId);
                WechatFans wechatFans = wechatFansService.getWechatFans(openIdDes);
                if (wechatFans == null) {
                    result.setReturnMsg("请先关注当前服务号!");
                    result.setReturnCode(Constant.FAIL);
                    return result;
                }
            }
            List<WechatProvince> pgd = wechatCityService.getProvince();
            if (pgd != null) {
                result.setReturnMsg(Constant.OK);
                result.setReturnCode(Constant.SUCCESS);
                result.setData(pgd);
            }

        } catch (Exception e) {
            logger.error("用户获取省份列表失败，原因：", e);
            result.setReturnMsg("获取省份列表失败!");
            result.setReturnCode(Constant.FAIL);
        }
        return result;
    }

    @RequestMapping(value = "/city/get", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result getCity(@RequestParam(value = "openId", required = false) String openId,
                          @RequestParam("provinceId") String provinceId) {
        Result result = new BaseResult();
        try {
            if (!StringUtils.isEmpty(openId)) {
                String openIdDes = wechatAESService.Decrypt(openId);
                WechatFans wechatFans = wechatFansService.getWechatFans(openIdDes);
                if (wechatFans == null) {
                    result.setReturnMsg("请先关注当前服务号!");
                    result.setReturnCode(Constant.FAIL);
                    return result;
                }
            }
            List<WechatCity> cityLst = wechatCityService.getCity(provinceId);
            if (cityLst != null && !cityLst.isEmpty()) {
                result.setReturnMsg(Constant.OK);
                result.setReturnCode(Constant.SUCCESS);
                result.setData(cityLst);
            }
        } catch (Exception e) {
            logger.error("用户获取市列表失败，原因：", e);
            result.setReturnMsg("获取市列表失败!");
            result.setReturnCode(Constant.FAIL);
        }
        return result;
    }

    @RequestMapping(value = "/area/get", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result getArea(@RequestParam(value = "openId", required = false) String openId,
                          @RequestParam("cityId") String cityId) {
        Result result = new BaseResult();
        try {
            if (!StringUtils.isEmpty(openId)) {
                String openIdDes = wechatAESService.Decrypt(openId);
                WechatFans wechatFans = wechatFansService.getWechatFans(openIdDes);
                if (wechatFans == null) {
                    result.setReturnCode(Constant.FAIL);
                    result.setReturnMsg("请先关注当前服务号!");
                    return result;
                }
            }

            List<WechatArea> areaLst = wechatCityService.getArea(cityId);
            if (areaLst != null && !areaLst.isEmpty()) {
                result.setReturnMsg(Constant.OK);
                result.setReturnCode(Constant.SUCCESS);
                result.setData(areaLst);
            }
        } catch (Exception e) {
            logger.error("用户获取区列表失败，原因：", e);
            result.setReturnMsg("获取区列表失败!");
            result.setReturnCode(Constant.FAIL);
        }

        return result;
    }

}