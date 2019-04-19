package com.ziwow.scrmapp.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.redis.RedisService;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.tools.thirdParty.SignUtil;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.BeanUtils;
import com.ziwow.scrmapp.tools.utils.CookieUtil;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.tools.utils.MD5;
import com.ziwow.scrmapp.tools.utils.UniqueIDBuilder;
import com.ziwow.scrmapp.wechat.constants.RedisKeyConstants;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.enums.CommunicateChannel;
import com.ziwow.scrmapp.wechat.enums.EducationLevel;
import com.ziwow.scrmapp.wechat.enums.InterestedType;
import com.ziwow.scrmapp.wechat.enums.MonthlyIncome;
import com.ziwow.scrmapp.wechat.enums.Occupation;
import com.ziwow.scrmapp.wechat.persistence.entity.MallPcUser;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUserAddress;
import com.ziwow.scrmapp.wechat.service.ProductService;
import com.ziwow.scrmapp.wechat.service.SmsSendRecordService;
import com.ziwow.scrmapp.wechat.service.WechatAESService;
import com.ziwow.scrmapp.wechat.service.WechatCityService;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatOrdersService;
import com.ziwow.scrmapp.wechat.service.WechatTemplateService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import com.ziwow.scrmapp.wechat.utils.MobileStack;
import com.ziwow.scrmapp.wechat.vo.EnumVo;
import com.ziwow.scrmapp.wechat.vo.UserInfo;
import com.ziwow.scrmapp.wechat.vo.WechatFansVo;
import com.ziwow.scrmapp.wechat.vo.WechatUserVo;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

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




    @RequestMapping(value = "/order/submit", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result notifyOrderSubmitted(@RequestParam("timestamp") String timestamp,
                                         @RequestParam("signture") String signture,
                                         @RequestParam("unionId") String unionId,
        @RequestParam("orderTime")String orderTime, @RequestParam("productName")String productName,
        @RequestParam("totalFee")String totalFee, @RequestParam("usedPoint")String usedPoint) {
        logger.info("订单消费公众号通知,unionId:{},orderTime:{},productName:{},totalFee:{},usedPoint:{}",
            unionId,orderTime,productName ,totalFee,usedPoint);
        Result result = new BaseResult();
        try {
            boolean isLegal = SignUtil.checkSignature(signture, timestamp, Constant.AUTH_KEY);
            if (!isLegal) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg(Constant.ILLEGAL_REQUEST);
                return result;
            }
            if (StringUtils.isEmpty(unionId) || StringUtils.isEmpty(productName) ||
                StringUtils.isEmpty(usedPoint) || StringUtils.isEmpty(totalFee)) {
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
            wechatTemplateService.submittedOrderTemplate(fans.getOpenId(),"","测试标题",
                fans.getWfNickName(),orderTime,productName,totalFee,usedPoint,"mark");
        } catch (Exception e) {
            logger.error("通知发送失败，原因", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("发送通知失败!");
        }
        return result;
    }



}