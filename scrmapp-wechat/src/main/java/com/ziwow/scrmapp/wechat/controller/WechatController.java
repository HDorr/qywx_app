/**
 * @Title: WechatController.java
 * @Package com.ziwow.scrmapp.wechat.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen
 * @date 2017-3-7 上午10:44:13
 * @version V1.0
 */
package com.ziwow.scrmapp.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.sun.tools.jxc.apt.Const;
import com.ziwow.scrmapp.common.annotation.MiniAuthentication;
import com.ziwow.scrmapp.common.bean.pojo.AppraiseParam;
import com.ziwow.scrmapp.common.bean.pojo.DispatchDotParam;
import com.ziwow.scrmapp.common.bean.pojo.DispatchMasterParam;
import com.ziwow.scrmapp.common.bean.pojo.DispatchOrderParam;
import com.ziwow.scrmapp.common.bean.vo.csm.ProductItem;
import com.ziwow.scrmapp.common.bean.vo.mall.MallOrderVo;
import com.ziwow.scrmapp.common.bean.vo.mall.OrderItem;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;
import com.ziwow.scrmapp.common.exception.ParamException;
import com.ziwow.scrmapp.common.persistence.entity.FilterLevel;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.common.utils.EwCardUtil;
import com.ziwow.scrmapp.tools.thirdParty.SignUtil;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.tools.utils.MD5;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.tools.utils.UniqueIDBuilder;
import com.ziwow.scrmapp.tools.weixin.Tools;
import com.ziwow.scrmapp.tools.weixin.XmlUtils;
import com.ziwow.scrmapp.wechat.constants.QYConstans;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.enums.BuyChannel;
import com.ziwow.scrmapp.wechat.persistence.entity.MallPcUser;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.*;
import com.ziwow.scrmapp.wechat.utils.BarCodeConvert;
import com.ziwow.scrmapp.wechat.utils.SyncQYUtil;
import com.ziwow.scrmapp.wechat.vo.MiniappSendSms;
import com.ziwow.scrmapp.wechat.vo.WechatFansVo;
import com.ziwow.scrmapp.wechat.vo.WechatJSSdkSignVO;
import javax.servlet.ServletInputStream;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLDataException;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author hogen
 * @ClassName: WechatController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017-3-7 上午10:44:13
 */
@Controller
@RequestMapping("/weixin/")
public class WechatController {
    Logger logger = LoggerFactory.getLogger(WechatController.class);
    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.appSecret}")
    private String secret;
    @Value("${mall.autologin.url}")
    private String mallAutoLoginUrl;
    @Value("${mgcc.myplan.url}")
    private String myPlanUrl;
    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private ThirdPartyService thirdPartyService;
    @Autowired
    private WeiXinService weiXinService;
    @Autowired
    private WechatFansService wechatFansService;
    @Autowired
    private WechatAESService wechatAESService;
    @Autowired
    private WechatOrdersService wechatOrdersService;
    @Autowired
    private ProductService productService;
    @Resource
    HttpSession session;

    @Autowired
    private MobileService mobileService;

    @Autowired
    private EwCardActivityService ewCardActivityService;

    @Autowired
    private GrantEwCardRecordService grantEwCardRecordService;

    @RequestMapping(value = "grant_ew_card",method = RequestMethod.GET)
    @MiniAuthentication
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Result grantEwCard(@RequestParam("signture") String signture,
                       @RequestParam("timestamp") String timeStamp,
                       @RequestParam("mobile") String mobile,
                       @RequestParam("type") EwCardTypeEnum type){
        logger.info("CSM调用微信短信发放延保卡开始，手机号码为:{}，类型为:{}",mobile,type);
        String cardNo = ewCardActivityService.selectCardNo(type);
        Result result = new BaseResult();
        if (cardNo == null){
            logger.error("延保卡资源不足，手机号码为:{}",mobile);
            result.setReturnMsg("延保卡资源不足");
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        if (grantEwCardRecordService.selectReceiveRecordByPhone(mobile)){
            logger.error("该手机号已发放，手机号码为:{}",mobile);
            result.setReturnMsg("该手机号已发放");
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        if (wechatUserService.getUserByMobilePhone(mobile) != null){
            logger.error("该用户是微信会员，手机号码为:{}",mobile);
            result.setReturnMsg("该用户是微信会员,不满足发放条件");
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        final String mask = EwCardUtil.getMask();
        grantEwCardRecordService.addEwCardRecord(mobile,mask,type,false);
        result.setReturnMsg("发送成功");
        result.setReturnCode(Constant.SUCCESS);
        try {
            //发短信
            final String msgContent = MessageFormat.format("您近期预约的服务已完成。恭喜您成为幸运用户，获赠限量免费的一年延保卡（价值{0}元），您的延保卡号为{1}。（点击券码可直接复制）！\n\n使用方式：关注沁园公众号-【我的沁园】-【个人中心】-【延保服务】-【领取卡券】，复制券码并绑定至您的机器，即可延长一年质保，绑定时请扫描机身条形码，即可识别机器！\n\n如对操作有疑问，可点击公众号左下角小键盘符号，回复【延保卡】，查看绑定教程。卡券码有效期7天，请尽快使用。", type.getPrice(), mask);
            mobileService.sendContentByEmay(mobile,msgContent, Constant.MARKETING);
        } catch (Exception e) {
            logger.error("发送短信失败，手机号码为:{},错误信息为:{}",mobile,e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("短信发送失败");
        }
        //增加发送时间,修改发送标识
        grantEwCardRecordService.updateSendByPhone(mobile,true);
        return result;
    }


    @RequestMapping(value = "/getWechatGetAccessToken", method = RequestMethod.GET)
    @ResponseBody
    public Result getWechatGetAccessToken() {
        Result result = new BaseResult();
        String accessToken = weiXinService.getAccessToken(appid, secret);
        if (StringUtil.isNotBlank(accessToken)) {
            result.setData(accessToken);
            result.setReturnCode(Constant.SUCCESS);
        } else {
            result.setReturnCode(Constant.FAIL);
        }
        return result;
    }

    @RequestMapping(value = "/checkSecurityCode", method = RequestMethod.GET)
    @ResponseBody
    public Object checkSecurityCode(@RequestParam("code") String code, HttpServletRequest request) {
        int status = productService.checkSecurityCode(request, code);
        Result result = new BaseResult();
        result.setReturnCode(status);
        return result;
    }

    @RequestMapping(value = "/getJSApiSign", method = RequestMethod.GET)
    @ResponseBody
    public Result getJSApiSign(@RequestParam("url") String url) {
        Result result = new BaseResult();
        WechatJSSdkSignVO jsSdkSignVO = weiXinService.getJSApiSign(url, appid, secret);
        if (null != jsSdkSignVO) {
            result.setData(jsSdkSignVO);
            result.setReturnCode(Constant.SUCCESS);
        } else {
            result.setReturnCode(Constant.FAIL);
        }
        return result;
    }

    /**
     * "官方直购"链接
     *
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/getMallUrl", method = RequestMethod.GET)
    public ModelAndView getMallUrl(HttpServletRequest request, HttpServletResponse response, @RequestParam("code") String code) {
        String url = StringUtils.EMPTY;
        ModelAndView mav = new ModelAndView();
        try {
            WechatFansVo wechatFansVo = wechatFansService.getFansInfo(code, request, response);
            if (null != wechatFansVo) {
                // 会员标识
                int flag = wechatFansVo.getCode();
                if (flag == 0) {
                    //跳转到二维码页面
                    mav.setViewName("/register/scan_QR_code");
                } else {
                    String openId = wechatAESService.Decrypt(wechatFansVo.getToken());
                    url = mallAutoLoginUrl + "?openid=" + openId;
                    logger.info("获取官方商城跳转链接:[{}]", url);
                    mav.setViewName("redirect:" + url);
                }
            }
        } catch (Exception e) {
            logger.error("获取官方商城跳转链接失败:", e);
        }
        return mav;
    }

    /**
     * "定制我的方案"链接
     *
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/getMyPlanUrl", method = RequestMethod.GET)
    public ModelAndView getMyPlanUrl(HttpServletRequest request, HttpServletResponse response, @RequestParam("code") String code) {
        String url = StringUtils.EMPTY;
        ModelAndView mav = new ModelAndView();
        try {
            WechatFansVo wechatFansVo = wechatFansService.getFansInfo(code, request, response);
            if (null != wechatFansVo) {
                // 会员标识
                int flag = wechatFansVo.getCode();
                if (flag == 0) {
                    //跳转到二维码页面
                    mav.setViewName("/register/scan_QR_code");
                } else {
                    int ismember = (flag == 2) ? 1 : 0;
                    String openId = wechatAESService.Decrypt(wechatFansVo.getToken());
                    url = myPlanUrl + "?ismember=" + ismember + "&openid=" + openId;
                    logger.info("获取定制我的方案跳转链接:[{}]", url);
                    mav.setViewName("redirect:" + url);
                }
            }
        } catch (Exception e) {
            logger.error("获取定制我的方案链接失败:", e);
        }
        return mav;
    }

    /**
     * 商城用户登录或注册后将数据同步到微信端接口
     *
     * @param timeStamp:时间戳
     * @param signture:签名
     * @param mobile:注册手机号
     * @param openId:openId
     * @param provinceId    省ID
     * @param cityId        市ID
     * @param areaId        区ID
     * @return
     */
    @RequestMapping(value = "/syncMallMember", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result getMallMember(@RequestParam("timestamp") String timeStamp,
                                @RequestParam("signture") String signture,
                                @RequestParam("mobile") String mobile,
                                @RequestParam("openid") String openId,
                                @RequestParam(value = "provinceId", required = false) String provinceId,
                                @RequestParam(value = "cityId", required = false) String cityId,
                                @RequestParam(value = "areaId", required = false) String areaId) {
        logger.info("第三方商城推送的登录或注册用户数据timestamp:[{}],signture:[{}],mobile:[{}],openid:[{}],provinceId:[{}],cityId:[{}],areaId:[{}]!",
                timeStamp, signture, mobile, openId, provinceId, cityId, areaId);
        Result result = new BaseResult();
        try {
            boolean isLegal = SignUtil.checkSignature(signture, timeStamp, Constant.AUTH_KEY);
            if (!isLegal) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg(Constant.ILLEGAL_REQUEST);
                return result;
            }
            // 手机号不能为空
            if (StringUtils.isEmpty(mobile)) {
                logger.info("用户[{}]手机号不能为空", mobile);
                result.setReturnMsg("手机号不能为空!");
                result.setReturnCode(Constant.FAIL);
                return result;
            }
            // openId不能为空
            if (StringUtils.isEmpty(openId)) {
                logger.info("用户[{}]手机号不能为空", mobile);
                result.setReturnMsg("手机号不能为空!");
                result.setReturnCode(Constant.FAIL);
                return result;
            }
            mobile = mobile.trim();
            openId = openId.trim();
            WechatFans wechatFans = wechatFansService.getWechatFans(openId);
            if (null == wechatFans) {
                logger.info("用户[{}],openId[{}]不是本公众号粉丝!", mobile, openId);
                result.setReturnMsg("对不起，用户不是该公众号的粉丝!");
                result.setReturnCode(Constant.FAIL);
                return result;
            }
            // 判断用户是否注册过
            WechatUser wechatUser = wechatUserService.getUserByOpenId(openId);
            if (wechatUser == null) {
                wechatUser = new WechatUser();
                String userId = UniqueIDBuilder.getUniqueIdValue();
                wechatUser.setUserId(userId);
                wechatUser.setMobilePhone(mobile);
                wechatUser.setWfId(wechatFans.getId());
                wechatUser.setProvinceId(provinceId);
                wechatUser.setCityId(cityId);
                wechatUser.setAreaId(areaId);
                // 注册为会员修改粉丝表isMember状态
                WechatFans fans = new WechatFans();
                fans.setIsMember(2);
                fans.setOpenId(openId);
                wechatUserService.saveUser(wechatUser, fans);
            }
        } catch (Exception e) {
            logger.error("会员注册公众号失败:", e);
            result.setReturnMsg("会员注册公众号失败!");
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        result.setReturnMsg("会员注册公众号成功!");
        result.setReturnCode(Constant.SUCCESS);
        return result;
    }

    /**
     * 商城pc用户注册后将数据同步到微信系统做注册校验用
     *
     * @param timeStamp:时间戳
     * @param signture:签名
     * @param mobile:pc用户注册手机号
     * @return
     */
    @RequestMapping(value = "/syncMallPcMember", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result getMallMember(@RequestParam("timestamp") String timeStamp,
                                @RequestParam("signture") String signture,
                                @RequestParam("mobile") String mobile) {
        logger.info("PC端商城用户注册时推送数据timestamp:[{}],signture:[{}],mobile:[{}]!", timeStamp, signture, mobile);
        Result result = new BaseResult();
        try {
            boolean isLegal = SignUtil.checkSignature(signture, timeStamp, Constant.AUTH_KEY);
            if (!isLegal) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg(Constant.ILLEGAL_REQUEST);
                return result;
            }
            // 手机号不能为空
            if (StringUtils.isEmpty(mobile)) {
                logger.info("用户[{}]手机号不能为空", mobile);
                result.setReturnMsg("手机号不能为空!");
                result.setReturnCode(Constant.FAIL);
                return result;
            }
            MallPcUser mallPcUser = wechatUserService.getMallPcUserByMobile(mobile);
            if (null != mallPcUser) {
                logger.info("同步pc端注册会员[{}]已存在！", mobile);
            } else {
                mallPcUser = new MallPcUser();
                mallPcUser.setMobilePhone(mobile);
                wechatUserService.saveMallPcUser(mallPcUser);
            }
            result.setReturnMsg("同步pc端注册会员成功!");
            result.setReturnCode(Constant.SUCCESS);
        } catch (Exception e) {
            logger.error("同步pc端注册会员失败:", e);
            result.setReturnMsg("同步pc端注册会员失败!");
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        return result;
    }

    /**
     * 用户在商城购买之后的订单同步接口
     *
     * @param timeStamp:时间戳
     * @param signture:签名
     * @param data:订单数据
     * @return
     */
    @RequestMapping(value = "/syncMallOrder", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result syncMallOrder(@RequestParam("timestamp") String timeStamp,
                                @RequestParam("signture") String signture,
                                @RequestParam("mobilePhone") String mobilePhone,
                                @RequestParam("data") String data) {
        logger.info("用户在商城购买之后的订单推送数据timestamp:[{}],signture:[{}],mobilePhone:[{}],data:[{}]!", timeStamp, signture, mobilePhone, data);
        Result result = new BaseResult();
        List<String> orderLst = Lists.newArrayList();
        try {
            boolean isLegal = SignUtil.checkSignature(signture, timeStamp, Constant.AUTH_KEY);
            if (!isLegal) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg(Constant.ILLEGAL_REQUEST);
                return result;
            }
            if (StringUtils.isEmpty(mobilePhone) || StringUtils.isEmpty(data)) {
                result.setReturnMsg("用户手机号或商城购买的订单数据不能为空!");
                result.setReturnCode(Constant.FAIL);
                return result;
            }
            JSONArray jsonArray = JSONArray.fromObject(data);
            Map<String, Class<OrderItem>> classMap = new HashMap<String, Class<OrderItem>>();
            classMap.put("items", OrderItem.class);
            @SuppressWarnings({"unchecked", "deprecation"})
            List<MallOrderVo> list = net.sf.json.JSONArray.toList(jsonArray, MallOrderVo.class, classMap);
            WechatUser wechatUser = wechatUserService.getUserByMobilePhone(mobilePhone);
            if (null == wechatUser) {
                result.setReturnMsg("对不起,该用户不存在!");
                result.setReturnCode(Constant.FAIL);
                return result;
            }
            for (MallOrderVo mallOrderVo : list) {
                List<OrderItem> items = mallOrderVo.getItems();
                String orderId = mallOrderVo.getSn();          // 订单号
                String createDate = mallOrderVo.getCreateDate();  // 下单时间
                List<String> itemLst = Lists.newArrayList();
                List<Product> prodLst = Lists.newArrayList();
                for (OrderItem orderItem : items) {
                    String itemCode = orderItem.getItemSn();     // 产品编码
                    itemLst.add(itemCode);
                }
                String itemCodes = Joiner.on(",").skipNulls().join(itemLst);
                List<ProductItem> prodList = thirdPartyService.getProductList(itemCodes);
                List<FilterLevel> filterLevels = new ArrayList<FilterLevel>();
                for (ProductItem productItem : prodList) {
                    // 通过产品型号查询csm的产品信息
                    Product product = new Product();
                    product.setTypeName(productItem.getBigcName());
                    product.setModelName(productItem.getSpec());
                    product.setItemKind(productItem.getItemKind());
                    product.setLevelId(Long.getLong(productItem.getFilterGradeId() + ""));
                    product.setLevelName(productItem.getFilterGrade());
                    product.setProductName(productItem.getItemName());
                    product.setSaleType(productItem.getFromChannel());
                    product.setShoppingOrder(orderId);             // 产品单号
                    product.setProductCode(productItem.getItemCode());
                    product.setUserId(wechatUser.getUserId());
                    product.setO2o(BuyChannel.ONQINYUAN.getO2o());  // 线上、线下
                    product.setBuyChannel(BuyChannel.ONQINYUAN.getChannelId());  // 购买渠道
                    product.setBuyTime(DateUtil.StringToDate(createDate, DateUtil.YYYY_MM_DD_HH_MM_SS));
                    product.setStatus(1);
                    prodLst.add(product);

                    //增加滤芯级别
                    filterLevels.add(new FilterLevel(product.getLevelId(), product.getLevelName()));
                }
                orderLst.add(orderId);
                // 将数据存入到系统的t_product表中
                if (!prodLst.isEmpty()) {
                    productService.batchSave(prodLst, filterLevels);
                }
            }
            result.setReturnMsg("商城购买订单同步成功!");
            result.setReturnCode(Constant.SUCCESS);
        } catch (Exception e) {
            logger.error("商城购买订单同步失败:", e);
            result.setReturnMsg("商城购买订单同步失败:" + e.getMessage());
            result.setReturnCode(Constant.FAIL);
        }
        result.setData(orderLst);
        return result;
    }

    @RequestMapping(value = {"/syncDispatchDot"}, method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result syncDispatchDot(@RequestBody DispatchDotParam dispatchDotParam) {
        logger.info("同步400派单给网点信息,CSM推送数据dispatchDotParam:[{}]", JSON.toJSON(dispatchDotParam));
        Result result = new BaseResult();
        result.setReturnMsg("400派单给网点同步成功!");
        result.setReturnCode(Constant.SUCCESS);
        try {
            wechatOrdersService.dispatchDot(dispatchDotParam);

        } catch (Exception e) {
            logger.info("400派单给网点同步失败!", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("400派单给网点同步失败[" + e.getMessage() + "]");
        }
        // 派单成功自动同步至商城系统 && 订单是原单原回
        if(result.getReturnCode() == 1 && wechatOrdersService.isYDYHOrder(dispatchDotParam.getAcceptNumber())){
            Map<String,Object> params = new HashMap<>();
            params.put("orderCode",dispatchDotParam.getAcceptNumber());
            Map res = null;
            try {
                res = SyncQYUtil.getResult("QINYUAN",params,"POST",
                        "http://localhost:8080/v2/syncOrder/status");
            }catch (Exception e){
                logger.error("400派单给网点同步成功，但同步订单状态失败！",e);
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("400派单给网点同步成功，但同步订单状态失败！[" + e.getMessage() + "]");
                return result;
            }
            if(!CollectionUtils.isEmpty(res) && (Integer) res.get("errorCode") == 200){
                result.setReturnMsg("400派单给网点同步成功！同步订单状态成功!");
                result.setReturnCode(QYConstans.SUCCESS);
                return result;
            }
            result.setReturnMsg("400派单给网点同步成功，但同步订单状态失败!");
            result.setReturnCode(QYConstans.FAIL);
            return result;
        }
        return result;
    }

    @RequestMapping(value = {"/syncAddAppraise"}, method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result syncAddAppraise(@RequestBody AppraiseParam appraiseParam) {
        logger.info("同步400评价信息内容,syncAddAppraise:[{}]", JSON.toJSON(appraiseParam));
        Result result = new BaseResult();
        result.setReturnMsg("400评价成功!");
        result.setReturnCode(Constant.SUCCESS);
        try {
            wechatOrdersService.syncAddAppraise(appraiseParam);
        } catch (ParamException e) {
            logger.info("400评价失败!", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("400评价失败:[" + e.getMessage() + "]");
        } catch (Exception e) {
            logger.info("400评价失败!", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("400评价失败,其他原因:[" + e.getMessage() + "]");
        }
        return result;
    }


    @RequestMapping(value = "/syncDispatchDot2")
    public void syncDispatchDotTest(@RequestParam("ordersType") Integer ordersType, @RequestParam("mobile") String mobile) {
        wechatOrdersService.testSendSms(ordersType, mobile);
    }

    /**
     * @param dispatchMasterParam
     * @return
     */
    @RequestMapping(value = "/syncDispatchMaster", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result syncDispatchMaster(@RequestBody DispatchMasterParam dispatchMasterParam) {
        logger.info("网点派单给服务师傅信息,CSM推送数据dispatchMasterParam:[{}]!", JSON.toJSON(dispatchMasterParam));
        Result result = new BaseResult();
        result.setReturnMsg("网点派单给服务师傅同步成功!");
        result.setReturnCode(Constant.SUCCESS);
        try {
            wechatOrdersService.dispatchMaster(dispatchMasterParam);
        } catch (Exception e) {
            logger.error("网点派单给服务师傅同步失败:", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("网点派单给服务师傅同步失败[" + e.getMessage() + "]");
        }
        return result;
    }


    /**
     * 同步完工工单
     *
     * @param dispatchOrderParam
     * @return
     */
    @RequestMapping(value = "/syncComleteOrder", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result completeOrder(@RequestBody DispatchOrderParam dispatchOrderParam) {
        logger.info("同步完工工单,CSM推送数据dispatchMasterParam:[{}]!", JSON.toJSON(dispatchOrderParam));
        Result result = new BaseResult();
        result.setReturnMsg("工单完工同步成功!");
        result.setReturnCode(Constant.SUCCESS);
        try {
            wechatOrdersService.dispatchCompleteOrder(dispatchOrderParam);
        } catch (Exception e) {
            logger.error("工单完工同步失败:", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("工单完工同步失败[" + e.getMessage() + "]");
        }
        return result;

    }

    @RequestMapping(value = "/getQrTicket", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result getQrTicket(Long channelId) {
        logger.info("调用获取二维码ticket,参数为:", channelId);
        Result result = new BaseResult();
        String qrTicket = weiXinService.getQRTicket(channelId, appid, secret);
        if (!StringUtils.isEmpty(qrTicket)) {
            result.setReturnCode(Constant.SUCCESS);
            result.setData(qrTicket);
        } else {
            result.setReturnCode(Constant.FAIL);
            result.setData(qrTicket);
        }

        return result;
    }


    @RequestMapping(value = "/sendSms", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result sendSms(@RequestBody MiniappSendSms miniappSendSms) throws Exception {
        logger.info("调用发送短信,参数为:", miniappSendSms.toString());
        Result result = new BaseResult();

        boolean isLegal = SignUtil.checkSignature(miniappSendSms.getSignture(),miniappSendSms.getTimestamp(), Constant.AUTH_KEY);
        if (!isLegal) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg(Constant.ILLEGAL_REQUEST);
            logger.error("调用发送短信失败，校验数据非法,",miniappSendSms.toString());
            return result;
        }

        if (miniappSendSms.getType()==3){//绑定有礼
            String msgContent = "亲爱的会员，恭喜您已成功绑定产品，小沁送您用户尊享满200立减50元滤芯优惠券一张，可进入沁园WX号（qy_serve ），点击<我的沁园>进入<优惠券>即可查看，还有更多用户尊享服务一键预约快速达。即刻开启您的专享优惠通道吧~回复TD退订";
            mobileService.sendContentByEmay(miniappSendSms.getPhone(),msgContent, Constant.BIND_GIFT );
        }else if (miniappSendSms.getType()==4){//注册有礼
//            String msgContent = "嗨，欢迎进入沁园水健康守护基地，恭喜您已完成会员注册。小沁送您会员尊享立减100元优惠券一张，购买微商城内任意净水器通用哦。进入沁园WX号（qy_serve ），点击<要购买-WX商城>进入<我的>即可查看。立即绑定产品，还有更多滤芯优惠券等着您！即刻开启您的专享优惠通道吧~回复TD退订";
            String msgContent = "嗨，欢迎进入沁园水健康守护基地，恭喜您已完成会员注册。小沁送您会员尊享立减100元优惠券一张，购买微商城内任意净水器通用哦。进入沁园WX号（qy_serve ），点击<我的沁园>进入<优惠券>即可查看。即刻开启您的专享优惠通道吧~回复TD退订";
            mobileService.sendContentByEmay(miniappSendSms.getPhone(), msgContent,Constant.REGISTER_GIFT);
        }


        result.setReturnCode(Constant.SUCCESS);
        return result;
    }


    /**
     * 二维码跳转链接
     *
     * @param request
     * @param barcode
     * @return
     */
    @RequestMapping("/barcode")
    public ModelAndView barCodeUrl(HttpServletRequest request, HttpServletRequest response, @RequestParam String barcode, @RequestParam(required = false) String from,
                                   @RequestParam(required = false) Long productId, @RequestParam(required = false) String userId) {
        ModelAndView modelAndView = new ModelAndView();
        if ("qyh".equals(from)) {
            modelAndView.setViewName("/bindProduct/jsp/scanProductDetail");
            modelAndView.addObject("productBarCode", BarCodeConvert.convert(barcode));
            modelAndView.addObject("productId", productId);
            modelAndView.addObject("userId", userId);
        } else {
            String contextPath = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + contextPath;
            String redirect_uri = basePath + "/weixin/check/identity";
            String getCodeUrl = WeChatConstants.SNSAPI_BASE_COMPONENT;
            try {
                String encodeUrl = URLEncoder.encode(redirect_uri, "UTF-8");
                getCodeUrl = getCodeUrl.replace("${REDIRECT_URI}", encodeUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getCodeUrl = getCodeUrl.replace("${APPID}", appid);
            getCodeUrl = getCodeUrl.replace("${STATE}", barcode);
            getCodeUrl = getCodeUrl.replace("{COMPONENT_APPID}", component_appid);
            logger.info("用户授权链接:[{}]", getCodeUrl);
            modelAndView.setViewName("redirect:" + getCodeUrl);
        }
        return modelAndView;
    }

    /**
     * 用户通过二维码扫描后跳转到的页面
     *
     * @return
     */
    @RequestMapping("/check/identity")
    public ModelAndView checkEngineerOrUser(HttpServletRequest request, HttpServletResponse response, @RequestParam String code, @RequestParam String state) {
        logger.info("WechatController----用户扫描，跳转页面!");
        logger.info(code + "---" + state);
        //跳转到用户处理页面
        ModelAndView modelAndView = productService.userScan(request, response, code, state);

        return modelAndView;
    }

    @Value("${open.weixin.component_appid}")
    private String component_appid;



}