package com.ziwow.scrmapp.wechat.controller;

import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.tools.thirdParty.SignUtil;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatTemplateService;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/template")
public class WechatTemplateController {
    private static final Logger logger = LoggerFactory.getLogger(WechatTemplateController.class);

    private WechatTemplateService wechatTemplateService;
    @Autowired
    public void setWechatTemplateService(WechatTemplateService wechatTemplateService) {
        this.wechatTemplateService = wechatTemplateService;
    }
    private WechatFansService wechatFansService;
    @Autowired
    public void setWechatFansService(WechatFansService wechatFansService) {
        this.wechatFansService = wechatFansService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result sendTemplateNotify(@RequestParam("timestamp") String timestamp,
                                         @RequestParam("signture") String signture,
                                         @RequestParam("unionId") String unionId,@RequestParam String type,
        @RequestParam String param,String url,@RequestParam(value="toMini", defaultValue="false")Boolean toMini) {
        Result result = new BaseResult();
        try {
            boolean isLegal = SignUtil.checkSignature(signture, timestamp, Constant.AUTH_KEY);
            if (!isLegal) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg(Constant.ILLEGAL_REQUEST);
                return result;
            }
            String[] params = param.split(",");
            List<String>  paramList= Arrays.asList(params);
            if (StringUtils.isEmpty(unionId) ||StringUtils.isBlank(type)||CollectionUtils.isEmpty(paramList)) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("请求参数有误，请检查后再试！");
                return result;
            }
            //通过union查询用户的openId
            WechatFans fans = wechatFansService.getFans(unionId);
            //没有查到用户
            if (null == fans) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                logger.error("发送通知模板出错，用户不存在");
                return result;
            }
            wechatTemplateService.sendTemplate(fans.getOpenId(),StringUtils.isNotBlank(url)?url:"", paramList,type, toMini
                !=null? toMini :false,"", "");
        } catch (Exception e) {
            logger.error("通知发送失败，原因", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("发送通知失败!");
        }
        return result;
    }



}