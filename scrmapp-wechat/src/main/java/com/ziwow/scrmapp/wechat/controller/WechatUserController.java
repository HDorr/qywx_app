package com.ziwow.scrmapp.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.redis.RedisService;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.tools.thirdParty.SignUtil;
import com.ziwow.scrmapp.tools.utils.*;
import com.ziwow.scrmapp.wechat.constants.RedisKeyConstants;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.enums.*;
import com.ziwow.scrmapp.wechat.persistence.entity.MallPcUser;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.*;
import com.ziwow.scrmapp.wechat.utils.MobileStack;
import com.ziwow.scrmapp.wechat.vo.EnumVo;
import com.ziwow.scrmapp.wechat.vo.UserInfo;
import com.ziwow.scrmapp.wechat.vo.WechatFansVo;
import com.ziwow.scrmapp.wechat.vo.WechatUserVo;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/scrmapp/consumer/user")
public class WechatUserController {
    private static final Logger logger = LoggerFactory.getLogger(WechatUserController.class);
    @Autowired
    WechatUserService wechatUserService;
    @Autowired
    WechatFansService wechatFansService;
    @Autowired
    WechatAESService wechatAESService;
    @Autowired
    WechatCityService wechatCityService;
    @Autowired
    MobileService mobileService;
    @Autowired
    RedisService redisService;
    @Autowired
    ProductService productService;
    @Autowired
    WechatOrdersService wechatOrdersService;
    @Autowired
    ThirdPartyService thirdPartyService;
    @Autowired
    SmsSendRecordService smsSendRecordService;
    @Resource
    HttpSession session;

    /**
     * 发送验证码
     *
     * @param mobile
     * @param openId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/verifycode/send", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Result sendVerifycode(@RequestParam("openId") String openId,
                                 @RequestParam("mobile") String mobile) {
        Result result = new BaseResult();
        // openId不能为空
        if (StringUtils.isEmpty(openId.trim())) {
            logger.info("用户openId为[{}]", openId);
            result.setReturnMsg("用户openId不能为空!");
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        // 手机号不能为空
        if (StringUtils.isEmpty(mobile.trim())) {
            logger.info("注册用户[{}]手机号不能为空", mobile);
            result.setReturnMsg("手机号不能为空!");
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        if (!MobileStack.repeatRequest(mobile)) {
            logger.info("用户[{}]发送验证码时验证openid[{}]时错误,原因是频繁获取验证码", mobile, openId);
            result.setReturnMsg("请不要频繁获取验证码!");
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        // 防止重复请求
        MobileStack.checkMobile(mobile);
        MobileStack.addMobile(mobile);
        try {
            // 解密openId字符串
            String openIdDes = wechatAESService.Decrypt(openId);
            // 注册前请先关注当前服务号
            WechatFans wechatFans = wechatFansService.getWechatFans(openIdDes);
            if (wechatFans == null) {
                logger.info("用户[{}]发送验证码,验证openid[{}]时错误,原因是没有关注当前商号", mobile, openIdDes);
                result.setReturnMsg("注册前请先关注当前服务号!");
                result.setReturnCode(Constant.FAIL);
                return result;
            }
            // 判断用户是否注册过了
            WechatUser wechatUser = wechatUserService.getUserByMobilePhone(mobile);
            if (wechatUser != null) {
                logger.info("用户[{}]发送验证码时验证openid[{}]已被注册!", mobile, openIdDes);
                result.setReturnMsg("该用户已经注册了该服务号!");
                result.setReturnCode(Constant.FAIL);
                return result;
            }
            // cache key
            String KEY = RedisKeyConstants.getUserRegisgterKey() + mobile;
            String sendCountKey = RedisKeyConstants.getUserRegisgterKey() + "count" + ":" + mobile;

            int sendCount = 0;
            Object countStr = redisService.get(sendCountKey);
            if (countStr != null) {
                sendCount = Integer.parseInt(countStr.toString());
            }
            logger.info("用户[{}]获取openid[{}]验证码次数[{}]", mobile, openIdDes, sendCount);

            // 判断发送验证码不能超过3次验证
            if (sendCount < 4) {
                boolean sendResult = mobileService.sendVerifyCodeByEmay(KEY, mobile);
                if (sendResult) {
                    sendCount = sendCount + 1;
                    redisService.set(sendCountKey, sendCount, DateUtil.getNightSecond());
                    String code = redisService.hasKey(KEY) ? (String) redisService.get(KEY) : null;
                    logger.info("用户[{}]获取验证码[{}]成功", mobile, code);
                    result.setReturnMsg(Constant.OK);
                    result.setReturnCode(Constant.SUCCESS);
                    result.setData(code);
                } else {
                    result.setReturnMsg("发送验证码失败!");
                    result.setReturnCode(Constant.FAIL);
                }
            } else {
                logger.info("用户[{}]获取openId[{}]验证码次数[{}]超过当天限制次数", mobile, openIdDes, sendCount);
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("验证码今天只能获取3次哦");
                return result;
            }
        } catch (Exception e) {
            logger.error("用户[" + mobile + "]获取openid[" + openId + "]验证码失败，原因：", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("获取验证码失败");
        } finally {
            if (mobile != null) {
                MobileStack.removeMobile(mobile);
            }
        }
        return result;
    }

    /**
     * 点击下一步校验验证码是否正确
     *
     * @param mobile
     * @param verifyCode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/verifycode/confirm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Result verifycodeConfirm(@RequestParam("mobile") String mobile,
                                    @RequestParam("verifycode") String verifyCode) {
        Result result = new BaseResult();
        // cache key
        String KEY = RedisKeyConstants.getUserRegisgterKey() + mobile;
        Long expireTime = redisService.getExpire(KEY, TimeUnit.SECONDS);
        String code = "";
        if (expireTime > 2) {
            code = (String) redisService.get(KEY);
        }
        // 判断验证码是否正确
        if (!verifyCode.equals(code)) {
            logger.info("用户[{}]输入的验证码[{}]不正确", mobile, verifyCode);
            result.setReturnMsg("验证码不正确");
            result.setReturnCode(Constant.FAIL);
        } else {
            // 判断该用户在商城系统是否注册
            MallPcUser mallPcUser = wechatUserService.getMallPcUserByMobile(mobile);
            boolean flag = (null == mallPcUser) ? true : false;
            result.setReturnMsg("验证码正确");
            result.setReturnCode(Constant.SUCCESS);
            result.setData(flag);
            return result;
        }

        return result;
    }

    /**
     * 用户注册接口
     *
     * @param openId
     * @param mobile
     * @param password
     * @param httpReq
     * @param httpRes
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Result register(@RequestParam("openId") String openId,
                           @RequestParam("mobile") String mobile,
                           @RequestParam("isMallMember") boolean isMallMember,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam("provinceId") String provinceId,
                           @RequestParam("cityId") String cityId,
                           @RequestParam("areaId") String areaId,
                           HttpServletRequest httpReq, HttpServletResponse httpRes) {
        Result result = new BaseResult();
        // 防止重复请求
        MobileStack.checkMobile(mobile);
        MobileStack.addMobile(mobile);
        // openid不能为空
        if (StringUtils.isBlank(openId)) {
            logger.info("用户[{}]openId为空", mobile);
            result.setReturnMsg("请先关注指定服务号");
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        // 手机号不能为空
        if (StringUtils.isEmpty(mobile.trim())) {
            logger.info("注册用户[{}]手机号[{}]不能为空", openId, mobile);
            result.setReturnMsg("手机号不能为空");
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        // 如果不是沁园商城的会员需要填写密码
        if (!isMallMember) {
            // 密码不能为空
            if (StringUtils.isEmpty(password)) {
                logger.info("注册用户[{}],手机[{}]密码不能为空", openId, mobile);
                result.setReturnMsg("密码不能为空");
                result.setReturnCode(Constant.FAIL);
                return result;
            }
        }
        WechatUser wechatUser = new WechatUser();
        try {
            // 解密openId字符串
            String openIdDes = wechatAESService.Decrypt(openId);
            // 注册前请先关注当前服务号
            WechatFans wechatFans = wechatFansService.getWechatFans(openIdDes);
            if (wechatFans == null) {
                logger.info("用户[{}]发送验证码,验证openid[{}]时错误,原因是没有关注当前商号", mobile, openIdDes);
                result.setReturnMsg("注册前请先关注当前服务号!");
                result.setReturnCode(Constant.FAIL);
                return result;
            }
            // 判断用户是否注册过了
            WechatUser user = wechatUserService.getUserByMobilePhone(mobile);
            if (user != null) {
                logger.info("用户[{}]openId[{}]已被注册", mobile, openIdDes);
                result.setReturnMsg("该用户已经被注册");
                result.setReturnCode(Constant.FAIL);
                return result;
            }
            // 设置用户信息
            String userId = UniqueIDBuilder.getUniqueIdValue();
            wechatUser.setUserId(userId);
            wechatUser.setMobilePhone(mobile.trim());
            if (StringUtils.isNotEmpty(password)) {
                String salt = SeedUtil.newSeed();
                wechatUser.setPassword(SeedUtil.encrypt(salt, password.trim()));
                wechatUser.setSeed(salt);
            }
            wechatUser.setNickName(wechatFans.getWfNickName());
            if (wechatFans.getGender() != null) {
                wechatUser.setGender(wechatFans.getGender());
            }
            wechatUser.setProvinceId(provinceId);
            wechatUser.setCityId(cityId);
            wechatUser.setAreaId(areaId);
            wechatUser.setWfId(wechatFans.getId());
            // 注册为会员修改粉丝表isMember状态
            WechatFans fans = new WechatFans();
            fans.setIsMember(2);
            fans.setOpenId(openIdDes);
            wechatUserService.saveUser(wechatUser, fans);
            session.setAttribute(WeChatConstants.USER, wechatUser);
            logger.info("用户编号[{}],用户名[{}],手机号为[{}]注册成功", wechatUser.getUserId(), wechatUser.getUserName(), wechatUser.getMobilePhone());
            // 注册成功异步发送短信提示及模板消息
            final String nikeName = wechatFans.getWfNickName();
            final String msgMobile = mobile;
            final String tmplateOpenId = openIdDes;
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    String msgContent = "亲爱的沁园用户，您已成功注册成为沁园会员！更多资讯和优惠，请持续关注“沁园”官方微信服务号。";
                    try {
                        wechatUserService.sendRegisterTemplateMsg(tmplateOpenId, nikeName);
                        mobileService.sendContentByEmay(msgMobile, msgContent, Constant.CUSTOMER);
                    } catch (Exception e) {
                        logger.info("注册成功异步发送短信提示及模板消息失败:", e);
                    }
                }
            });
            // 刷新短信营销记录
            smsSendRecordService.updateSmsRecordRegTime(mobile);

            // 将注册信息同步给第三方沁园商城
            thirdPartyService.registerMember(mobile, password, openIdDes);
            // 异步同步该用户的历史产品信息
            productService.syncHistroyProductItem(mobile, userId);
            // 异步同步该用户的历史受理单信息
            wechatOrdersService.syncHistoryAppInfo(mobile, userId);
            // 异步推送给小程序
            final String unionId = wechatFans.getUnionId();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    wechatUserService.syncUserToMiniApp(unionId, msgMobile);
                }
            });
            result.setReturnMsg(Constant.OK);
            result.setReturnCode(Constant.SUCCESS);
            return result;
        } catch (Exception e) {
            logger.error("用户编号[" + wechatUser.getUserId() + "],手机号为[" + wechatUser.getMobilePhone() + "]注册失败,失败原因:", e);
            result.setReturnMsg("网络出错了哦,请重试");
            result.setReturnCode(Constant.FAIL);
            return result;
        } finally {
            if (mobile != null) {
                MobileStack.removeMobile(mobile);
            }
        }
    }

    /**
     *
     * 通过openId获取unionId
     * @param openId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUnionid", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public void syncUserFromMiniApp(@RequestParam("openId") String openId) {
        try {
            UserInfo userInfo = wechatFansService.getUserInfoByOpenId(openId);
            logger.info("通过openId:{}获取的用户信息,userInfo:{}", openId, JSON.toJSON(userInfo));
            if(null != userInfo) {
                String opendId = userInfo.getOpenid();
                String unionId = userInfo.getUnionid();
                WechatFans wechatFans = wechatFansService.getWechatFans(opendId);
                if(null != wechatFans && StringUtils.isNotBlank(unionId)) {
                    WechatFans fans = new WechatFans();
                    fans.setId(wechatFans.getId());
                    fans.setUnionId(unionId);
                    wechatFansService.updWechatFansById(fans);
                    logger.info("opendId换unionId:" + opendId + "<===>" + unionId);
                }
            }
        } catch (Exception e) {
            logger.error("通过openId获取unionId失败:", e);
        }
    }

    /**
     * 同步小程序注册的用户
     * @param timestamp
     * @param signture
     * @param mobile
     * @param unionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/syncUser", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Result syncUserFromMiniApp(@RequestParam("timestamp") String timestamp,
                                      @RequestParam("signture") String signture,
                                      @RequestParam("mobile") String mobile,
                                      @RequestParam("unionId") String unionId) {
        logger.info("同步小程序注册的用户,mobile:{},unionId:{}", mobile, unionId);
        Result result = new BaseResult();
        try {
            boolean isLegal = SignUtil.checkSignature(signture, timestamp, Constant.AUTH_KEY);
            if (!isLegal) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg(Constant.ILLEGAL_REQUEST);
                return result;
            }
            if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(unionId)) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("注册请求有误,请检查后重试!");
                return result;
            }

            WechatUser wUser = wechatUserService.getUserByMobilePhone(mobile);
            if (null != wUser) {
                Long wxId = wUser.getWfId();
                WechatFans wechatFans = new WechatFans();
                wechatFans.setId(wxId);
                wechatFans.setUnionId(unionId);
                wechatFansService.updWechatFansById(wechatFans);
            } else {
                // 检查该unionid是否已经存在
                WechatFans wechatFans = wechatFansService.getFans(unionId);
                if (null != wechatFans) {
                    wechatFans.setIsMember(2);
                } else {
                    wechatFans = new WechatFans();
                    wechatFans.setIsMember(2);
                    wechatFans.setUnionId(unionId);
                }
                // 设置用户信息
                String userId = UniqueIDBuilder.getUniqueIdValue();
                WechatUser wechatUser = new WechatUser();
                wechatUser.setUserId(userId);
                wechatUser.setMobilePhone(mobile.trim());
                wechatUserService.syncUserFromMiniApp(wechatFans, wechatUser);
            }
            result.setReturnCode(Constant.SUCCESS);
            result.setReturnMsg("同步用户信息成功!");
        } catch (Exception e) {
            logger.error("同步注册用户失败,参数:mobile={},unionId={}", mobile, unionId, e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("同步注册用户失败!");
        }
        return result;
    }

    /**
     * 获取用户基本信息
     *
     * @param @param  code
     * @param @return 设定文件
     * @return Result    返回类型
     * @Title: getOAuthUserInfo
     * @version 1.0
     * @author Hogen.hu
     */
    @RequestMapping(value = "/getOAuthUserInfo", method = RequestMethod.GET)
    public ModelAndView getOAuthUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("code") String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        WechatFansVo wechatFansVo = wechatFansService.getOAuthUserInfo(code, request, response);
        if (wechatFansVo.getCode() == 0) {
            return new ModelAndView("/register/scan_QR_code");
        } else if (wechatFansVo.getCode() == 1) {
            map.put("data", wechatFansVo);
            return new ModelAndView("/register/register", map);
        } else if (wechatFansVo.getCode() == 2) {
            map.put("data", wechatFansVo);
            return new ModelAndView("/user_home/user_home");
        }
        return new ModelAndView("404");
    }


    /**
     * 获得个人资料详细信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/wechatuser/get", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result getWechatUser(HttpServletRequest request, HttpServletResponse response) {
        Result result = new BaseResult();

        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));

            WechatUser wechatUser = wechatUserService.getUserByUserId(userId);
            //数据库中未查到数据
            if (null == wechatUser) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("您还未注册，请先注册!");
                logger.info("userId无效，userId = [{}]的", userId);
                return result;
            }

            WechatUserVo wechatUserVo = wechatCityService.getCityName(wechatUser.getAreaId());
            if (ObjectUtils.equals(wechatUserVo, null)) {
                wechatUserVo = new WechatUserVo();
            }
            //只复制需要修改的字段到vo
            BeanUtils.copyProperties(wechatUser, wechatUserVo);

            //教育程度
            if (wechatUser.getEducationLevel() != null) {
                Integer code = wechatUser.getEducationLevel();
                String name = EducationLevel.getNameByCode(code);
                wechatUserVo.setEducationLevel(new EnumVo(code.toString(), name));
            }
            //职业
            if (wechatUser.getOccupation() != null) {
                Integer code = wechatUser.getOccupation();
                String name = Occupation.getNameByCode(code);
                wechatUserVo.setOccupation(new EnumVo(code.toString(), name));
            }
            //月收入
            if (wechatUser.getMonthlyIncome() != null) {
                Integer code = wechatUser.getMonthlyIncome();
                String name = MonthlyIncome.getNameByCode(code);
                wechatUserVo.setMonthlyIncome(new EnumVo(code.toString(), name));
            }

            //偏好的沟通渠道列表
            wechatUserVo.setCommunicateChannelList(parseEnum(CommunicateChannel.class));
            //感兴趣的类型列表
            wechatUserVo.setInterestedTypeList(parseEnum(InterestedType.class));

            //将沟通，兴趣字符串分割后转化为数组
            if (wechatUser.getCommunicateChannel() != null) {
                String communicateChannel = wechatUser.getCommunicateChannel();
                String[] splitstr = communicateChannel.split(",");
                wechatUserVo.setCommunicateChannels(splitstr);
            } else {
                wechatUserVo.setCommunicateChannels(new String[]{});
            }

            if (wechatUser.getInterestedType() != null) {
                String interestedType = wechatUser.getInterestedType();
                String[] splitstr = interestedType.split(",");
                wechatUserVo.setInterestedTypes(splitstr);
            } else {
                wechatUserVo.setInterestedTypes(new String[]{});
            }

            //转化日期格式
            if (wechatUser.getBirthday() != null) {
                wechatUserVo.setBirthday(DateUtil.DateToString(wechatUser.getBirthday(), DateUtil.YYYY_MM_DD));
            }

            result.setData(wechatUserVo);
            result.setReturnCode(Constant.SUCCESS);
            result.setReturnMsg("数据获取成功!");

            logger.debug("成功获取个人资料信息,userId = [{}] ", userId);
        } catch (Exception e) {
            logger.error("读取cookie异常", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("获取个人资料失败!");
        }

        return result;
    }


    /**
     * 更新个人资料
     *
     * @param request
     * @param response
     * @param userName           会员姓名
     * @param nickName           会员昵称
     * @param maritalStatus
     * @param gender
     * @param email
     * @param birthday
     * @param provinceId         省
     * @param cityId             市
     * @param areaId             区
     * @param communicateChannel 沟通渠道
     * @param interestedType     感兴趣的信息类型
     * @param educationLevel     教育程度
     * @param occupation         职业
     * @param monthlyIncome      月收入
     * @return
     */
    @RequestMapping(value = "/wechatuser/update", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result updateWechatUser(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam("userName") String userName,
                                   @RequestParam("nickName") String nickName,
                                   @RequestParam(value = "maritalStatus", required = false) Integer maritalStatus,
                                   @RequestParam("gender") Integer gender,
                                   @RequestParam("email") String email,
                                   @RequestParam("birthday") String birthday,
                                   @RequestParam("provinceId") String provinceId,
                                   @RequestParam("cityId") String cityId,
                                   @RequestParam("areaId") String areaId,
                                   @RequestParam("communicateChannel") String communicateChannel,
                                   @RequestParam("interestedType") String interestedType,
                                   @RequestParam(value = "educationLevel", required = false) Integer educationLevel,
                                   @RequestParam(value = "occupation", required = false) Integer occupation,
                                   @RequestParam(value = "monthlyIncome", required = false) Integer monthlyIncome) {
        Result result = new BaseResult();
        WechatUser wechatUser = null;
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));

            wechatUser = wechatUserService.getUserByUserId(userId);
            //数据库中未查到数据
            if (null == wechatUser) {
                result.setReturnCode(Constant.FAIL);
                logger.error("userId无效，userId = [{}]的", userId);
                result.setReturnMsg("您还未注册，请先注册!");
                return result;
            }
        } catch (Exception e) {
            logger.error("读取cookie异常", e);
            result.setReturnCode(Constant.FAIL);
        }

        //用户名不能为空
        if (StringUtils.isEmpty(userName)) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("姓名不能为空!");
            return result;
        }
        //昵称不能为空
        if (StringUtils.isEmpty(nickName)) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("昵称不能为空!");
            return result;
        }
        //性别不能为空
        if (gender == null) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("性别不能为空!");
            return result;
        }
        //email不能为空
        if (StringUtils.isEmpty(email)) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("email不能为空!");
            return result;
        }
        //生日不能为空
        if (StringUtils.isEmpty(birthday)) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("生日不能为空!");
            return result;
        }
        //省市区不能为空
        if (StringUtils.isEmpty(provinceId) || StringUtils.isEmpty(cityId) || StringUtils.isEmpty(areaId)) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("省市区不能为空!");
            return result;
        }
        //沟通渠道不能为空
        if (StringUtils.isEmpty(communicateChannel)) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("沟通渠道不能为空!");
            return result;
        }
        //兴趣类型不能为空
        if (StringUtils.isEmpty(interestedType)) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("兴趣类型不能为空!");
            return result;
        }

        wechatUser.setUserName(userName);
        wechatUser.setNickName(nickName);
        wechatUser.setGender(gender);
        wechatUser.setMaritalStatus(maritalStatus);
        wechatUser.setEmail(email);
        wechatUser.setBirthday(DateUtil.StringToDate(birthday, "yyyy-MM-dd"));
        wechatUser.setProvinceId(provinceId);
        wechatUser.setCityId(cityId);
        wechatUser.setAreaId(areaId);
        wechatUser.setCommunicateChannel(communicateChannel);
        wechatUser.setInterestedType(interestedType);
        wechatUser.setEducationLevel(educationLevel);
        wechatUser.setOccupation(occupation);
        wechatUser.setMonthlyIncome(monthlyIncome);
        long wfId = wechatUser.getWfId();
        int count = wechatUserService.updateUser(wechatUser, wfId);
        if (count > 0) {
            // 修改成功后发送模板消息
            WechatFans wechatFans = wechatFansService.getWechatFansById(wfId);
            wechatUserService.sendUserUpdTemplateMsg(wechatFans.getOpenId(), wechatFans.getWfNickName());
            result.setReturnCode(Constant.SUCCESS);
            result.setReturnMsg("修改成功！");
            logger.info("WechatUser数据更新成功,userId = [{}]", wechatUser.getUserId());
        } else {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("更新失败!");
            logger.info("WechatUser数据更新失败！userId = [{}]", wechatUser.getUserId());
        }

        return result;
    }


    /**
     * 获得教育程度list
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "education/get", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result getEducationList(HttpServletRequest request, HttpServletResponse response) {
        Result result = new BaseResult();
        try {
            List<EnumVo> educationLevels = parseEnum(EducationLevel.class);
            result.setReturnCode(Constant.SUCCESS);
            result.setReturnMsg("数据获取成功!");
            result.setData(educationLevels);
            logger.info("成功获取教育程度列表信息");
        } catch (Exception e) {
            logger.error("获取教育程度列表失败,原因", e);
            result.setReturnCode(Constant.FAIL);
        }

        return result;
    }

    /**
     * 获得职业list
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "occupation/get", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result getoccupationList(HttpServletRequest request, HttpServletResponse response) {
        Result result = new BaseResult();
        try {
            List<EnumVo> occupations = parseEnum(Occupation.class);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(occupations);
            result.setReturnMsg("数据获取成功!");
            logger.info("成功获取职业列表信息");
        } catch (Exception e) {
            logger.error("获取职业列表失败,原因", e);
            result.setReturnCode(Constant.FAIL);
        }

        return result;
    }

    /**
     * 获得月收入list
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "monthlyIncome/get", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result getMonthlyIncomeList(HttpServletRequest request, HttpServletResponse response) {
        Result result = new BaseResult();
        try {
            List<EnumVo> monthlyIncomes = parseEnum(MonthlyIncome.class);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(monthlyIncomes);
            result.setReturnMsg("数据获取成功!");
            logger.info("成功获取月收入列表信息");
        } catch (Exception e) {
            logger.error("获取月收入列表失败,原因", e);
            result.setReturnCode(Constant.FAIL);
        }

        return result;
    }

    /**
     * 调用防伪查询接口
     *
     * @param request
     * @param response
     * @param barcode
     * @return
     */
    @RequestMapping(value = "/query/securityCode", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result securityQuery(HttpServletRequest request, HttpServletResponse response, String barcode) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));

            WechatUserVo wechatUser = wechatUserService.getBaseUserInfoByUserId(userId);

            //用户不存在
            if (wechatUser == null) {
                result.setReturnCode(Constant.FAIL);
                return result;
            }

            //截取省市区
            String area = StringUtils.EMPTY;
            if (!StringUtils.isEmpty(wechatUser.getProvinceName())) {
                int position = wechatUser.getProvinceName().indexOf("省");
                if (position != -1) {
                    String province = wechatUser.getProvinceName().substring(0, position);
                    area = province + "-";
                } else {
                    area = wechatUser.getProvinceName() + "-";
                }
                position = wechatUser.getCityName().indexOf("市");
                if (position != -1) {
                    String city = wechatUser.getCityName().substring(0, position);
                    area += city;
                } else {
                    area += wechatUser.getCityName();
                }
            }
            String ciphertext = MD5.toMD5(barcode + "QYFW").toUpperCase();
            result = wechatUserService.securityQuery(barcode, wechatUser.getMobilePhone(), area, ciphertext);
            if (result.getReturnCode() == 0) {
                result.setData(wechatUser.getNickName());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setReturnMsg("查询失败!");
            result.setReturnCode(Constant.FAIL);
        }
        return result;
    }

    /**
     * 枚举元素保存至list中
     *
     * @param ref
     * @param <T>
     * @return
     */
    public static final <T> List<EnumVo> parseEnum(Class<T> ref) {
        List<EnumVo> lists = new ArrayList<EnumVo>();
        if (ref.isEnum()) {
            //获得所有枚举类元素
            T[] ts = ref.getEnumConstants();
            for (T t : ts) {
                Enum<?> tempEnum = (Enum<?>) t;
                try {
                    //获得code属性值
                    Method codeMethod = tempEnum.getClass().getMethod("getCode");
                    //关闭检查
                    codeMethod.setAccessible(true);
                    String code = codeMethod.invoke(t).toString();
                    //获得name属性值
                    Method nameMethod = tempEnum.getClass().getMethod("getName");
                    nameMethod.setAccessible(true);
                    String name = nameMethod.invoke(t).toString();
                    lists.add(new EnumVo(code, name));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return lists;
    }


    /**
     * 通配配置微信菜单路径
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/filter/**")
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
        String urlPath = extractPathFromPattern(request);

        ModelAndView modelAndView = new ModelAndView(urlPath);

        return modelAndView;
    }

    private static String extractPathFromPattern(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }


    /**
     * 跳转到个人会员
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/memberInfo/index")
    public ModelAndView toMemberPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("memberInfo/memberInfo");
        return modelAndView;
    }
}