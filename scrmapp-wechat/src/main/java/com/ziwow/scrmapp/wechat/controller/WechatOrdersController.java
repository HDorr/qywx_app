package com.ziwow.scrmapp.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ziwow.scrmapp.common.bean.pojo.EvaluateParam;
import com.ziwow.scrmapp.common.bean.pojo.WechatOrdersParam;
import com.ziwow.scrmapp.common.bean.pojo.ext.WechatOrdersParamExt;
import com.ziwow.scrmapp.common.bean.vo.ProductVo;
import com.ziwow.scrmapp.common.bean.vo.QyhUserMsgVo;
import com.ziwow.scrmapp.common.bean.vo.QyhUserVo;
import com.ziwow.scrmapp.common.bean.vo.WechatOrderVo;
import com.ziwow.scrmapp.common.bean.vo.WechatOrdersVo;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.SystemConstants;
import com.ziwow.scrmapp.common.enums.AppraiseEnum;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.common.persistence.entity.QyhUserAppraisal;
import com.ziwow.scrmapp.common.persistence.entity.QyhUserAppraisalVo;
import com.ziwow.scrmapp.common.persistence.entity.ServiceFeeProduct;
import com.ziwow.scrmapp.common.persistence.entity.SmsMarketing;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrderAppraise;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrderServiceFee;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrders;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrdersRecord;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.utils.OrderUtils;
import com.ziwow.scrmapp.tools.queue.EngineerQueue;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.BeanUtils;
import com.ziwow.scrmapp.tools.utils.CookieUtil;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.enums.SmsMarketingEmus.SmsTypeEnum;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.schedule.SmsMarketingTask;
import com.ziwow.scrmapp.wechat.service.FailRecordService;
import com.ziwow.scrmapp.wechat.service.GrantPointService;
import com.ziwow.scrmapp.wechat.service.OrdersProRelationsService;
import com.ziwow.scrmapp.wechat.service.ProductService;
import com.ziwow.scrmapp.wechat.service.SmsMarketingService;
import com.ziwow.scrmapp.wechat.service.WXPayService;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatOrderServiceFeeService;
import com.ziwow.scrmapp.wechat.service.WechatOrdersRecordService;
import com.ziwow.scrmapp.wechat.service.WechatOrdersService;
import com.ziwow.scrmapp.wechat.service.WechatQyhUserService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xiaohei on 2017/4/7.
 */
@Controller
@RequestMapping(value = "/scrmapp/consumer")
public class WechatOrdersController {
    private final Logger logger = LoggerFactory.getLogger(WechatOrdersController.class);

    @Value("${order.detail.url}")
    private String orderDetailUrl;

    //查询产品服务路径
    @Value("${qinyuan.modelname.service.query.url}")
    private String qinyuanModelnameServiceQuery;

    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private WechatQyhUserService wechatQyhUserService;
    @Autowired
    private WechatOrdersService wechatOrdersService;
    @Autowired
    private FailRecordService failRecordService;
    @Autowired
    private WechatOrdersRecordService wechatOrdersRecordService;
    @Autowired
    private MobileService mobileService;
    @Autowired
    private OrdersProRelationsService relationsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WXPayService wxPayService;
    @Autowired
    private WechatFansService wechatFansService;
    @Autowired
    private WechatOrderServiceFeeService wechatOrderServiceFeeService;
    @Autowired
    private SmsMarketingService smsMarketingService;
    @Autowired
    private GrantPointService grantPointService;


    /**
     * 用户预约
     * 1.先调用沁园接口，生成受理单
     * 2.本地数据库增加预约单
     * <p>
     * 2017-12-13 增加一单多产品支持
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/wechat/orders/save", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result addWechatOrders(HttpServletRequest request, HttpServletResponse response, @RequestBody WechatOrdersParamExt wechatOrdersParamExt) {
        Result result = new BaseResult();

        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));

            //用户id不存在
            WechatUser wechatUser = wechatUserService.getUserByUserId(userId);
            if (StringUtils.isEmpty(userId) || null == wechatUser) {
                logger.error("用户预约失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }

            //检查用户是否提交产品
            String productIds = wechatOrdersParamExt.getProductIds();
            if (StringUtil.isBlank(productIds)) {
                throw new RuntimeException("用户未选择产品");
            }

            //根据productId获取产品信息(批量)
            List<ProductVo> list = wechatOrdersService.getProductInfoById(wechatOrdersParamExt.getProductIds());
            wechatOrdersParamExt.setProducts(list);

            // 获取modelName   拼接CSM用
//            List<String> mnList = new ArrayList<String>();
//            for (ProductVo pvo : list) {
//                mnList.add(pvo.getModelName());
//            }


//            long wfId = wechatUser.getWfId();
//            WechatFans wechatFans = wechatFansService.getWechatFansById(wfId);
//            String unionId = wechatFans.getUnionId();
            //下面通过外部接口获取付款金额
//            List<Integer> idList = new ArrayList<Integer>();
//            String[] productArr = productIds.split(",");
//            for (String productId : productArr) {
//                idList.add(Integer.parseInt(productId));
//            }
            //根据productId获取产品信息(批量)
//            List<Product> productList = productService.getProductsByIds(idList);
            //判断产品是否已经购买服务费
            // 小程序无法验证


            //拼接CSM数据到description描述后面
            // 描述格式
            //原用户输入描述:XXXX
            // &产品型号(服务费):XXXXX&金额：XXXXX&关联订单号：XXXXX；
            String description;
            String tem = wechatOrdersParamExt.getDescription();
            if (tem != null) {
                description = tem;
            } else {
                description = "";
            }
            String ext = "";   //保持扩展信息

            String scOrderNos="";
            List<ServiceFeeProduct> serviceFeeProducts = wechatOrdersParamExt.getServiceFeeProducts();
            if (serviceFeeProducts!=null && serviceFeeProducts.size()>0){
                for (ServiceFeeProduct pf : serviceFeeProducts) {
                    if (pf != null) {

                        String scOrderNo = pf.getScOrderNo();
                        BigDecimal b = pf.getServiceFee();

                        String tp = b.toString();
                        //由于产品型号不是唯一值，通过主键ID + 型号拼接
                        ext =ext + " " + pf.getServiceFeeName() + " &金额:" + tp + " &关联订单号:" + scOrderNo;
                        if (StringUtil.isBlank(scOrderNos)){
                          scOrderNos=scOrderNo;
                        }else {
                          scOrderNos=scOrderNos+","+scOrderNo;
                        }

                    }
                }
            }

            logger.info("生成预约单带服务费产品id："+ JSON.toJSONString(serviceFeeProducts));
            logger.info("生成预约单描述："+ext);


            if (StringUtils.isNotEmpty(ext)) {
                wechatOrdersParamExt.setDescription(description + "\n" +  ext);   //关联到描述信息
            }


            if ("".equals(wechatOrdersParamExt.getContactsTelephone()) || null == wechatOrdersParamExt.getContactsTelephone()) {
                wechatOrdersParamExt.setTel(wechatUser.getMobilePhone());
            } else {
                wechatOrdersParamExt.setTel(wechatOrdersParamExt.getContactsTelephone().replace("-", ""));
            }

            // fixme 调用沁园接口，生成受理单   隐藏，随机生成csm单号
            result = wechatOrdersService.geneatorCode(wechatOrdersParamExt);
//            result.setReturnCode(Constant.SUCCESS);
//            result.setData(UUID.randomUUID());
            if (Constant.SUCCESS != result.getReturnCode()) {
                return result;
            }
            String webAppealNo = result.getData().toString();
            //保存预约单
            Date date = new Date();
            WechatOrders wechatOrders = new WechatOrders();
            BeanUtils.copyProperties(wechatOrdersParamExt, wechatOrders);

            wechatOrders.setUserId(userId);
            wechatOrders.setOrdersCode(webAppealNo);
            wechatOrders.setStatus(SystemConstants.ORDERS);
            wechatOrders.setCreateTime(date);
            wechatOrders.setUpdateTime(date);
            wechatOrders.setOrderTime(DateUtil.StringToDate(wechatOrdersParamExt.getOrderTime(), "yyyy-MM-dd HH"));
            //来源是微信
            wechatOrders.setSource(SystemConstants.WEIXIN);
            wechatOrders.setScOrderNo(scOrderNos);

            //新增一单多产品接口
            wechatOrders = wechatOrdersService.saveOrdersMultiProduct(wechatOrders, wechatOrdersParamExt.getProductIds());

            Long id = wechatOrders.getId();
            if (id != null) {

                //保存订单中的服务费信息
                if (serviceFeeProducts!=null && serviceFeeProducts.size()>0){
                    for (ServiceFeeProduct pf : serviceFeeProducts) {
                        if (pf != null) {
                            WechatOrderServiceFee wechatOrderServiceFee = new WechatOrderServiceFee(pf, id);
                            wechatOrderServiceFeeService.insert(wechatOrderServiceFee);
                        }
                    }
                }

                int orderType = wechatOrders.getOrderType();
                // 发送短信通知提醒
                Map<Integer, SmsMarketing> smsTemplateMap = smsMarketingService.getCacheSmsMarketing();
                String serverType = OrderUtils.getServiceTypeName(orderType);
                String mobilePhone = wechatUser.getMobilePhone();
                int originalType = SmsMarketingTask.convertOriginalType(SmsTypeEnum.TODAY.getCode(), orderType);
                SmsMarketing smsMarketing = smsTemplateMap.get(originalType);
                mobileService.sendContentByEmay(mobilePhone, smsMarketing.getSmsContent(), Constant.CUSTOMER);
                // 预约提交成功模板消息提醒
                wechatOrdersService.sendAppointmentTemplateMsg(wechatOrders.getOrdersCode(), serverType);
                WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
                wechatOrdersRecord.setOrderId(wechatOrders.getId());
                wechatOrdersRecord.setRecordTime(date);
                wechatOrdersRecord.setRecordContent("用户下单");
                wechatOrdersRecordService.saveWechatOrdersRecord(wechatOrdersRecord);

                result.setReturnCode(Constant.SUCCESS);
                result.setReturnMsg("预约成功!");
                logger.info("生成受理单,userId = [{}] , ordersCode = [{}]", userId, webAppealNo);

                // 向沁园小程序推送预约成功
                String scOrderItemId = wechatOrdersParamExt.getScOrderItemId();
                String serviceFeeIds = wechatOrdersParamExt.getServiceFeeIds();
                if (StringUtil.isNotBlank(scOrderItemId) || StringUtil.isNotBlank(serviceFeeIds) ){
                    wechatOrdersService
                        .syncMakeAppointment(scOrderItemId, wechatOrders.getOrdersCode(),
                            serviceFeeIds);
//                    if (!syncMakeAppointment){
//                        /*调用沁园接口，取消预约*/
//                        Result cancelResult = wechatOrdersService.cancelOrders(wechatOrders.getOrdersCode());
//                        if (Constant.SUCCESS == cancelResult.getReturnCode()) {
//                            int count = wechatOrdersService.updateOrdersStatus(wechatOrders.getOrdersCode(), userId, date, SystemConstants.CANCEL);
//                            if (count > 0) {
//                                WechatOrdersRecord cancelWechatOrdersRecord = new WechatOrdersRecord();
//                                cancelWechatOrdersRecord.setOrderId(wechatOrders.getId());
//                                cancelWechatOrdersRecord.setRecordTime(date);
//                                cancelWechatOrdersRecord.setRecordContent("用户取消预约");
//                                wechatOrdersRecordService.saveWechatOrdersRecord(cancelWechatOrdersRecord);
//                                logger.info("预约取消,userId = [{}] , ordersCode = [{}]", userId, wechatOrders.getOrdersCode());
//                            }
//                        } else {
//                            return cancelResult;
//                        }
//                    }

                }
            } else {
                throw new SQLException("wechatOrders:" + JSONObject.toJSONString(wechatOrders));
            }
        } catch (SQLException e) {
            logger.error("用户预约未成功,原因[{}]", e);
            failRecordService.saveFailRecord(e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("预约异常,请稍后再试!");
        } catch (RuntimeException e) {
            logger.error("用户预约未成功,原因[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("用户预约异常,原因[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("预约异常,请稍后再试!");
        }
        return result;
    }


    /**
     * 更改预约时间
     * 如果还没有派单给师傅，直接更改时间
     * 否则
     * 1.先调用沁园接口，取消预约
     * 2.同步本地数据库，取消预约单
     * 3.重新生成受理单
     * 4.本地数据库增加预约单
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/wechat/orders/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result updateWechatOrders(HttpServletRequest request, HttpServletResponse response, @RequestParam String ordersCode, @RequestParam String updateTime, @RequestParam String contacts) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));
            //用户id不存在
            WechatUser wechatUser = wechatUserService.getUserByUserId(userId);
            if (StringUtils.isEmpty(userId) || null == wechatUser) {
                logger.error("修改预约失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }

            WechatOrders wechatOrders = wechatOrdersService.getWechatOrdersByCode(ordersCode);
            if (wechatOrders == null) {
                logger.error("更改预约失败，预约单不存在！");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("预约单不存在！");
                return result;
            }
            Date date = new Date();

            //如果此时订单状态还是下单状态，直接修改预约时间
            if (SystemConstants.ORDERS == wechatOrders.getStatus() || SystemConstants.REDEAL == wechatOrders.getStatus()) {
                Result re = wechatOrdersService.updateOrdersTime(ordersCode, date, updateTime);
                if (re.getReturnCode() != 1) {
                    return re;
                }
                WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
                wechatOrdersRecord.setOrderId(wechatOrders.getId());
                wechatOrdersRecord.setRecordTime(date);
                wechatOrdersRecord.setRecordContent("用户更改预约时间");
                wechatOrdersRecordService.saveWechatOrdersRecord(wechatOrdersRecord);
                result.setReturnCode(Constant.SUCCESS);
                result.setReturnMsg("预约时间更改成功!");
                return result;
            }

            //如果有部分产品已经完工，提示用户无法更改用户时间
            WechatOrdersParam wechatOrdersParam = wechatOrdersService.getParamByOrdersCode(ordersCode);
            List<ProductVo> productVos = wechatOrdersParam.getProducts();

            for (ProductVo p : productVos) {
                if (Constant.COMPLETE == p.getStatus()) {
                    result.setReturnCode(Constant.FAIL);
                    result.setReturnMsg("师傅已经完工部分产品，无法更改预约时间!");
                    return result;
                }
            }

            /*调用沁园接口，取消预约*/
            Result cancelResult = wechatOrdersService.cancelOrders(ordersCode);
            if (Constant.SUCCESS == cancelResult.getReturnCode()) {
                int count = wechatOrdersService.updateOrdersStatus(ordersCode, userId, date, SystemConstants.CANCEL);
                if (count > 0) {
                    WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
                    wechatOrdersRecord.setOrderId(wechatOrders.getId());
                    wechatOrdersRecord.setRecordTime(date);
                    wechatOrdersRecord.setRecordContent("用户取消预约");
                    wechatOrdersRecordService.saveWechatOrdersRecord(wechatOrdersRecord);
                    logger.info("预约取消,userId = [{}] , ordersCode = [{}]", userId, ordersCode);
                }
            } else {
                return cancelResult;
            }

            /*  重新生成受理单 查询之前受理单的数据*/
            wechatOrdersParam.setOrderTime(updateTime.substring(0, 13));
            wechatOrdersParam.setTel(wechatUser.getMobilePhone());
            //调用沁园接口，生成受理单
            result = wechatOrdersService.geneatorCode(wechatOrdersParam);
            if (Constant.SUCCESS != result.getReturnCode()) {
                return result;
            }

            String newOrdersCode = result.getData().toString();
            //保存预约单
            WechatOrders newWechatOrders = new WechatOrders();
            BeanUtils.copyProperties(wechatOrdersParam, newWechatOrders);
            newWechatOrders.setUserId(userId);
            newWechatOrders.setOrdersCode(newOrdersCode);
            //设置工单状态为重新处理中
            newWechatOrders.setStatus(SystemConstants.REDEAL);
            //来源是微信
            newWechatOrders.setSource(SystemConstants.WEIXIN);
            newWechatOrders.setOrderTime(DateUtil.StringToDate(updateTime, "yyyy-MM-dd HH"));
            newWechatOrders.setCreateTime(date);
            newWechatOrders.setUpdateTime(date);
            newWechatOrders = wechatOrdersService.saveOrders(newWechatOrders);
            if (newWechatOrders.getId() != null) {
                WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
                wechatOrdersRecord.setOrderId(newWechatOrders.getId());
                wechatOrdersRecord.setRecordTime(date);
                wechatOrdersRecord.setRecordContent("用户重新下单");
                wechatOrdersRecordService.saveWechatOrdersRecord(wechatOrdersRecord);
                relationsService.batchSave(wechatOrders.getId(), newWechatOrders.getId());

                // 发送短信通知提醒
                String engineerId = wechatOrders.getQyhUserId();
                QyhUser qyhUser = wechatQyhUserService.getQyhUser(engineerId);
                String engineerPhone = qyhUser.getMobile();
                String orderType = OrderUtils.getServiceTypeName(wechatOrders.getOrderType());
                //String mobilePhone = wechatUser.getMobilePhone();
                //String orderTime = DateUtil.DateToString(wechatOrders.getOrderTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
                //String engineerName = qyhUser.getName();
                //String msgContent = "亲爱的用户，您预约的" + orderType + "服务已更改服务时间。工程师（姓名:" + engineerName + "，联系方式:" + engineerPhone
                //		+ "）上门服务时间：" + orderTime + "。请保持电话畅通，届时工程师将与您联系。";
                //mobileService.sendContent(mobilePhone, msgContent);
                // 给用户发送模板消息
                //wechatOrdersService.sendReOrderTimeTemplateMsg(wechatOrders);
                // 给服务工程师发送订单关闭通知
                if (StringUtils.isNotEmpty(engineerId)) {
                    String url = orderDetailUrl + "?userId=" + engineerId + "&ordersCode=" + ordersCode;
                    String content = "工单服务时间更改通知！\n" +
                            "    请注意，" + contacts + "用户已将预约的" + orderType + "服务的上门服务时间进行更改，该订单已关闭，待重新派单！\n" +
                            "1、点击<a href='" + url + "'>【已完工单】</a>查看该工单详情！";
                    QyhUserMsgVo qyhUserMsgVo = new QyhUserMsgVo();
                    qyhUserMsgVo.setUserId(engineerId);
                    qyhUserMsgVo.setContent(content);
                    String engineerMsgContent = "请注意，" + contacts + "用户已将预约的" + orderType + "服务的上门服务时间进行更改，该订单已关闭，待重新派单！";
                    qyhUserMsgVo.setMsgContent(engineerMsgContent);
                    qyhUserMsgVo.setQyhUserMobile(engineerPhone);
                    EngineerQueue.getQueueInstance().add(qyhUserMsgVo);
                }
                result.setReturnCode(Constant.SUCCESS);
                result.setReturnMsg("预约时间更改成功!");
                logger.info("重新生成受理单,userId = [{}] , ordersCode = [{}]", userId, newOrdersCode);



                //推送更新到小程序
                wechatOrdersService.updateMakeAppointment(ordersCode,newOrdersCode);
//                if (!updateMakeAppointment){
//                    /*调用沁园接口，取消预约*/
//                    Result cancelResult = wechatOrdersService.cancelOrders(wechatOrders.getOrdersCode());
//                    if (Constant.SUCCESS == cancelResult.getReturnCode()) {
//                        int count = wechatOrdersService.updateOrdersStatus(wechatOrders.getOrdersCode(), userId, date, SystemConstants.CANCEL);
//                        if (count > 0) {
//                            WechatOrdersRecord cancelWechatOrdersRecord = new WechatOrdersRecord();
//                            cancelWechatOrdersRecord.setOrderId(wechatOrders.getId());
//                            cancelWechatOrdersRecord.setRecordTime(date);
//                            cancelWechatOrdersRecord.setRecordContent("用户取消预约");
//                            wechatOrdersRecordService.saveWechatOrdersRecord(cancelWechatOrdersRecord);
//                            logger.info("预约取消,userId = [{}] , ordersCode = [{}]", userId, wechatOrders.getOrdersCode());
//                        }
//                    } else {
//                        return cancelResult;
//                    }
//                }


            }
        } catch (Exception e) {
            logger.error("更改预约失败,原因[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("更改预约异常,请稍后再试!");
        }
        return result;
    }


    /**
     * 取消预约
     * 1.调用沁园取消预约
     * 2.数据库将预约状态改成用户取消
     *
     * @param request
     * @param response
     * @param ordersCode
     * @return
     */
    @RequestMapping(value = "/wechat/orders/cancel", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result cancelOrders(HttpServletRequest request, HttpServletResponse response, @RequestParam String
            ordersCode, @RequestParam String contacts) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));
            //用户id不存在
            WechatUser wechatUser = wechatUserService.getUserByUserId(userId);
            if (StringUtils.isEmpty(userId) || null == wechatUser) {
                logger.info("预约取消失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }
            WechatOrders wechatOrders = wechatOrdersService.getWechatOrdersByCode(ordersCode);

            List<ProductVo> productVos = productService.findByOrderId(wechatOrders.getId());

            for (ProductVo p : productVos) {
                if (Constant.COMPLETE == p.getStatus()) {
                    result.setReturnCode(Constant.FAIL);
                    result.setReturnMsg("师傅已经完工部分产品，无法取消工单!");
                    return result;
                }
            }
            if (wechatOrders == null) {
                logger.info("预约取消失败，预约单不存在！");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("预约单不存在！");
                return result;
            }

            /*调用沁园取消预约接口*/
            Result cancelResult = wechatOrdersService.cancelOrders(ordersCode);

            /*调用自营商城取消预约*/
//            wechatOrdersService.cancelOrders(ordersCode)


            if (Constant.SUCCESS == cancelResult.getReturnCode()) {
                Date date = new Date();
                int count = wechatOrdersService.updateOrdersStatus(ordersCode, userId, date, SystemConstants.CANCEL);
                if (count > 0) {
                    int orderType = wechatOrders.getOrderType();
                    String serverType = OrderUtils.getServiceTypeName(orderType);
                    // 给用户发送发送短信提醒
                    String mobile = wechatUser.getMobilePhone();
                    String msgContent = "亲爱的用户，您预约的" + serverType + "服务已撤销。如须重新预约，您可进入“沁园”官方微信服务号进行操作。";
                    mobileService.sendContentByEmay(mobile, msgContent, Constant.CUSTOMER);
                    // 给用户发送发送模板消息
                    wechatOrdersService.sendOrderCancelTemplateMsg(userId, serverType);
                    // 如果服务工程师接单了，用户侧取消需要给服务工程师发送取消通知
                    String engineerId = wechatOrders.getQyhUserId();
                    if (StringUtils.isNotEmpty(engineerId)) {
                        // 给工程师发送短信通知
                        String engineerMsgContent = "请注意，" + contacts + "用户已取消" + serverType + "服务！您可进入“沁园”WX企业号查看该工单详情！";
                        QyhUser qyhUser = wechatQyhUserService.getQyhUser(engineerId);
                        String qyhUserMobile = (null != qyhUser) ? qyhUser.getMobile() : "";
                        mobileService.sendContentByEmay(qyhUserMobile, engineerMsgContent, Constant.ENGINEER);
                        // 给工程师发送取消公告通知
                        String url = orderDetailUrl + "?userId=" + engineerId + "&ordersCode=" + ordersCode;
                        String content = "工单撤销通知！\n" +
                                "    请注意，" + contacts + "用户已取消" + serverType + "服务！\n" +
                                "1、点击<a href='" + url + "'>【待处理工单】</a>查看该工单详情！";
                        QyhUserMsgVo qyhUserMsgVo = new QyhUserMsgVo();
                        qyhUserMsgVo.setUserId(engineerId);
                        qyhUserMsgVo.setContent(content);
                        qyhUserMsgVo.setMsgContent(engineerMsgContent);
                        qyhUserMsgVo.setQyhUserMobile(qyhUserMobile);
                        EngineerQueue.getQueueInstance().add(qyhUserMsgVo);
                    }
                    WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
                    wechatOrdersRecord.setOrderId(wechatOrders.getId());
                    wechatOrdersRecord.setRecordTime(date);
                    wechatOrdersRecord.setRecordContent("用户取消预约");
                    wechatOrdersRecordService.saveWechatOrdersRecord(wechatOrdersRecord);
                    result.setReturnCode(Constant.SUCCESS);
                    result.setReturnMsg("预约取消成功!");
                    logger.info("预约取消,userId = [{}] , ordersCode = [{}]", userId, ordersCode);


                    //推送更新到小程序
                    wechatOrdersService.cancelMakeAppointment(ordersCode);


                } else {
                    throw new SQLException("ordersCode:" + ordersCode + ",status:" + SystemConstants.CANCEL);
                }
            } else {
                return cancelResult;
            }
        } catch (SQLException e) {
            failRecordService.saveFailRecord(e);
        } catch (Exception e) {
            logger.error("预约更改失败,原因[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("预约更改异常,请稍后再试!");
        }
        return result;
    }


    /**
     * 展示我的预约列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/wechat/orders/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result wechatOrdersList(HttpServletRequest request, HttpServletResponse response) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));

            //用户id不存在
            if (!wechatUserService.checkUser(userId)) {
                logger.info("获取预约列表失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }
            List<WechatOrdersVo> wechatOrders = wechatOrdersService.findByUserId(userId);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(wechatOrders);
        } catch (Exception e) {
            logger.error("获取预约列表失败,原因[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("数据获取失败,请稍后再试!");
        }
        return result;
    }


    /**
     * 显示预约详情
     *
     * @param request
     * @param response
     * @param ordersCode
     * @return
     */
    @RequestMapping(value = "/wechat/orders/detail", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result wechatOrdersDetail(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam String ordersCode,
                                     @RequestParam(required = false, defaultValue = "") String userId) {
        Result result = new BaseResult();
        try {
            if (StringUtils.isBlank(userId)) {
                String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
                userId = new String(Base64.decode(encode));
            }
            //用户id不存在
            if (!wechatUserService.checkUser(userId)) {
                logger.error("获取预约详情失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }

            WechatOrdersVo wechatOrdersVo = wechatOrdersService.getVoByOrdersCode(ordersCode);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(wechatOrdersVo);
            logger.debug("预约详情获取成功!");
        } catch (Exception e) {
            logger.error("获取预约详情记录失败,原因[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("数据获取失败,请稍后再试!");
        }

        return result;
    }


    /**
     * 显示师傅信息
     *
     * @param request
     * @param response
     * @param ordersCode
     * @return
     */
    @RequestMapping(value = "/wechat/orders/qyh/info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result showQyhUserAppraisal(HttpServletRequest request, HttpServletResponse
            response, @RequestParam String ordersCode) {
        Result result = new BaseResult();
        try {
            QyhUserVo qyhUserVo = wechatOrdersService.getQyhUserInfo(ordersCode);
            result.setData(qyhUserVo);
            result.setReturnCode(Constant.SUCCESS);
        } catch (Exception e) {
            logger.error("获取师傅信息失败,原因[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("获取师傅信息失败!");

        }
        return result;
    }


    /**
     * 用户评分
     *
     * @param request
     * @param response
     * @param ordersCode
     * @param attitude   态度
     * @param profession 专业度
     * @param integrity  诚信情况
     * @param recommend  推荐意愿
     * @param content
     * @return
     */
    @RequestMapping(value = "/wechat/orders/user/appraisal", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result userAppraisal(HttpServletRequest request, HttpServletResponse
            response, @RequestParam String ordersCode, @RequestParam BigDecimal attitude, @RequestParam BigDecimal profession, @RequestParam BigDecimal integrity, @RequestParam BigDecimal recommend, @RequestParam String content) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));

            //用户id不存在
            if (!wechatUserService.checkUser(userId)) {
                logger.error("用户评分失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }

            //判断预约单状态是否可以评论
            WechatOrderVo wechatOrders = wechatOrdersService.getWechatOrdersVoByCode(ordersCode);
            if (SystemConstants.COMPLETE != wechatOrders.getStatus()) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("现在不能评分!");
                return result;
            }

            //非本人评论
            if (!userId.equals(wechatOrders.getUserId())) {
                logger.error("非本人工单，不能评论!");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("非本人工单，不能评论!！");
                return result;
            }
            //首先调用沁园同步接口
            EvaluateParam evaluateParam = new EvaluateParam();
            int orderType = 0;
            if (SystemConstants.INSTALL == wechatOrders.getOrderType()) {
                orderType = SystemConstants.INSTALL;
            } else if (SystemConstants.REPAIR == wechatOrders.getOrderType()) {
                orderType = SystemConstants.MAINTAIN;
            } else if (SystemConstants.MAINTAIN == wechatOrders.getOrderType()) {
                orderType = SystemConstants.REPAIR;
            }
            evaluateParam.setNumber_type(orderType);
            evaluateParam.setEvaluate_note(content);
            evaluateParam.setNumber(wechatOrders.getOrdersCode());
            evaluateParam.setIs_attitude(inValidNotNUll(attitude));
            evaluateParam.setIs_specialty(inValidNotNUll(profession));
//            evaluateParam.setIs_integrity(inValidNotNUll(integrity));
//            evaluateParam.setIs_recommend(inValidNotNUll(recommend));
            evaluateParam.setIs_wxgz(inValidNotNUll(wechatOrders.getIs_repair()));
            evaluateParam.setIs_wxzs(inValidNotNUll(wechatOrders.getIs_order()));

            Result invokeResult = wechatUserService.invokeCssEvaluate(evaluateParam);

            if (Constant.SUCCESS == invokeResult.getReturnCode()) {
                //保存评价信息
                QyhUserAppraisal qyhUserAppraisal = new QyhUserAppraisal();
                qyhUserAppraisal.setOrderId(wechatOrders.getId());
                qyhUserAppraisal.setAttitude(attitude);
                qyhUserAppraisal.setIntegrity(integrity);
                qyhUserAppraisal.setProfession(profession);
                qyhUserAppraisal.setRecommend(recommend);
                qyhUserAppraisal.setContent(content);
                qyhUserAppraisal.setQyhUserId(wechatOrders.getQyhUserId());
                qyhUserAppraisal.setUserId(userId);

                int count = wechatUserService.save(qyhUserAppraisal);
                Date date = new Date();
                if (count > 0) {
                    //修改预约单状态为已评价
                    wechatOrdersService.updateOrdersStatus(ordersCode, userId, date, SystemConstants.APPRAISE);
                } else {
                    throw new SQLException("qyhUserAppraisal:" + JSONObject.toJSONString(qyhUserAppraisal));
                }
                WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
                wechatOrdersRecord.setOrderId(wechatOrders.getId());
                wechatOrdersRecord.setRecordTime(date);
                wechatOrdersRecord.setRecordContent("用户评分完成");
                wechatOrdersRecordService.saveWechatOrdersRecord(wechatOrdersRecord);

                result.setReturnCode(Constant.SUCCESS);
                result.setData("评分完成!");

                // 给工程师发送短信通知
                String engineerMsgContent = "您服务的工单" + ordersCode + "，用户已经评价啦，谢谢提供服务！请登录“沁园服务之家”的售后服务个人中心，可以查看评分。";
                QyhUser qyhUser = wechatQyhUserService.getQyhUser(wechatOrders.getQyhUserId());
                String qyhUserMobile = (null != qyhUser) ? qyhUser.getMobile() : "";
                mobileService.sendContentByEmay(qyhUserMobile, engineerMsgContent, Constant.ENGINEER);
            } else {
                return invokeResult;
            }

        } catch (SQLException e) {
            failRecordService.saveFailRecord(e);
        } catch (Exception e) {
            logger.error("用户评分失败,原因[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("用户评分失败!");
        }
        return result;
    }

    private int inValidNotNUll(BigDecimal bigDecimal){
        if(bigDecimal == null){
            return  0;
        }
        return bigDecimal.intValue();
    }

    private int inValidNotNUll(Integer args) {
        if (args == null) {
            return 0;
        }
        return args;
    }


    @RequestMapping(value = "/wechat/orders/service/install/page")
    public ModelAndView toServiceInstallAddPage() {
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_install");
        return modelAndView;
    }

    @RequestMapping(value = "/wechat/orders/service/clean/page")
    public ModelAndView toServiceCleanAddPage() {
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_clean");
        return modelAndView;
    }

    @RequestMapping(value = "/wechat/orders/service/repair/page")
    public ModelAndView toServiceRepairAddPage() {
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_repair");
        return modelAndView;
    }


    /**
     * 添加预约选择产品页面
     *
     * @return
     */
    @RequestMapping(value = "/wechat/orders/service/choice/product")
    public ModelAndView toChoiceProductPage() {
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/chooseProduct");
        return modelAndView;
    }


    /**
     * 添加预约选择地址页面
     *
     * @return
     */
    @RequestMapping(value = "/wechat/orders/service/choice/address")
    public ModelAndView toChoiceAddressPage() {
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/chooseProduct");
        return modelAndView;
    }


    /**
     * 添加预约成功跳转页面
     *
     * @return
     */
    @RequestMapping(value = "/wechat/orders/service/add/success")
    public ModelAndView toServiceAddSuccessPage() {
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveSuccessPrompt");
        return modelAndView;
    }

    /**
     * 我的预约详情页面
     *
     * @return
     */
    @RequestMapping(value = "/wechat/orders/service/detail")
    public ModelAndView toServiceDetailPage(
            @RequestParam(required = false, defaultValue = "") String userId,
            @RequestParam(required = false, defaultValue = "") String ordersCode) {
        String url = "/myOrder/jsp/orderDetail";
        ModelAndView modelAndView = new ModelAndView(url);
        if (StringUtils.isNotBlank(userId)) {
            modelAndView.addObject("userId", userId);
        }
        if (StringUtil.isNotBlank(ordersCode)) {
            modelAndView.addObject("ordersCode", ordersCode);
        }
        return modelAndView;
    }

    /**
     * 预约评价
     *
     * @return
     */
    @RequestMapping(value = "/wechat/orders/appraisal/page")
    public ModelAndView toOrdersAppraisalPage(@RequestParam String ordersCode, @RequestParam Integer orderType, @RequestParam Integer maintType) {
        ModelAndView modelAndView = new ModelAndView("/reserveReview/jsp/appraise");
        int code = getTypeByOtherTypes(orderType, maintType);
        modelAndView.addObject("appraiseType", code);
        modelAndView.addObject("appraiseTypeName", AppraiseEnum.getNameByCode(code));
        modelAndView.addObject("ordersCode", ordersCode);
        return modelAndView;
    }

    /**
     * 新版用户评价
     *
     * @return Result
     */
    @RequestMapping(value = "/wechat/orders/user/newAppraisal", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result newAppraise(@RequestBody WechatOrderAppraise wechatOrderAppraise, HttpServletRequest request, HttpServletResponse response) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));

            //用户id不存在
            if (!wechatUserService.checkUser(userId)) {
                logger.error("用户评分失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }

            //判断预约单状态是否可以评论
            String ordersCode = wechatOrderAppraise.getOrderCode();
            WechatOrders wechatOrders = wechatOrdersService.getWechatOrdersByCode(ordersCode);
            if (SystemConstants.COMPLETE != wechatOrders.getStatus()) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("现在不能评分!");
                return result;
            }

            //非本人评论
            if (!userId.equals(wechatOrders.getUserId())) {
                logger.error("非本人工单，不能评论!");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("非本人工单，不能评论!！");
                return result;
            }

            //fixme 这里调用CSM接口
            Result invokeResult = csmExecute(wechatOrderAppraise);
            if (Constant.SUCCESS == invokeResult.getReturnCode()) {

                //保存评价信息
                BigDecimal attitude = BigDecimal.valueOf(Double.parseDouble(wechatOrderAppraise.getAttitude()));
                BigDecimal profession = BigDecimal.valueOf(Double.parseDouble(wechatOrderAppraise.getProfession()));
                QyhUserAppraisalVo qyhUserAppraisalVo = new QyhUserAppraisalVo();
                qyhUserAppraisalVo.setOrderId(wechatOrders.getId());
                qyhUserAppraisalVo.setAttitude(attitude);
                qyhUserAppraisalVo.setProfession(profession);
                qyhUserAppraisalVo.setContent(wechatOrderAppraise.getContent());
                qyhUserAppraisalVo.setQyhUserId(wechatOrders.getQyhUserId());
                qyhUserAppraisalVo.setUserId(userId);
                qyhUserAppraisalVo.setIs_order(convertBoolean(wechatOrderAppraise.getOrder()));
                if(SystemConstants.REPAIR_APPRAISE == covertStringToInt(wechatOrderAppraise.getAppraiseType())){
                    qyhUserAppraisalVo.setIs_repair(convertBoolean(wechatOrderAppraise.getRepair()));
                }

                int count = wechatUserService.saveVo(qyhUserAppraisalVo);
                Date date = new Date();
                if (count > 0) {
                    //修改预约单状态为已评价
                    wechatOrdersService.updateOrdersStatus(ordersCode, userId, date, SystemConstants.APPRAISE);
                    //发送评价积分
                    grantPointService.grantOrderComment(userId,ordersCode,wechatOrders.getOrderType());

                } else {
                    throw new SQLException("qyhUserAppraisalVo:" + JSONObject.toJSONString(qyhUserAppraisalVo));
                }
                WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
                wechatOrdersRecord.setOrderId(wechatOrders.getId());
                wechatOrdersRecord.setRecordTime(date);
                wechatOrdersRecord.setRecordContent("用户评分完成");
                wechatOrdersRecordService.saveWechatOrdersRecord(wechatOrdersRecord);

                result.setReturnCode(Constant.SUCCESS);
                result.setData("评分完成!");

                // 给工程师发送短信通知
                String engineerMsgContent = "您服务的工单" + ordersCode + "，用户已经评价啦，谢谢提供服务！请登录“沁园服务之家”的售后服务个人中心，可以查看评分。";
                QyhUser qyhUser = wechatQyhUserService.getQyhUser(wechatOrders.getQyhUserId());
                String qyhUserMobile = (null != qyhUser) ? qyhUser.getMobile() : "";
                mobileService.sendContentByEmay(qyhUserMobile, engineerMsgContent, Constant.ENGINEER);
            } else {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户评分失败!");
            }
        } catch (Exception e) {
            logger.error("用户评分失败,原因[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("用户评分失败!");
        }
        return result;
    }

    private Integer covertStringToInt(String str){
        if(StringUtils.isNotEmpty(str)){
            return Integer.parseInt(str);
        }
        return 0;
    }

    private Result csmExecute(WechatOrderAppraise appraise) {
        //首先调用沁园同步接口
        EvaluateParam evaluateParam = new EvaluateParam();
        int orderType = covertStringToInt(appraise.getAppraiseType());
        if (SystemConstants.INSTALL_APPRAISE == orderType) {
            orderType = SystemConstants.INSTALL;
        } else if (SystemConstants.REPAIR_APPRAISE == orderType) {
            orderType = SystemConstants.MAINTAIN;
        } else if (SystemConstants.FILTER_APPRAISE == orderType || SystemConstants.CLEAN_APPRAISE == orderType) {
            orderType = SystemConstants.REPAIR;
        }
        evaluateParam.setNumber_type(orderType);
        evaluateParam.setEvaluate_note(appraise.getContent());
        evaluateParam.setNumber(appraise.getOrderCode());
        evaluateParam.setIs_attitude(covertStringToInt(appraise.getAttitude()));
        evaluateParam.setIs_specialty(covertStringToInt(appraise.getProfession()));
        evaluateParam.setIs_integrity(0);
        evaluateParam.setIs_recommend(0);
        evaluateParam.setIs_wxgz(covertStringToInt(appraise.getRepair()) + 1);
        evaluateParam.setIs_wxzs(covertStringToInt(appraise.getOrder()) + 1);

        return wechatUserService.invokeCssEvaluate(evaluateParam);
    }

    //根据类型判断评价显示的界面
    private int getTypeByOtherTypes(int orderType, int maintType) {
        if (SystemConstants.INSTALL == orderType) {
            return SystemConstants.INSTALL_APPRAISE;
        } else if (SystemConstants.REPAIR == orderType) {
            return SystemConstants.REPAIR_APPRAISE;
        } else if (SystemConstants.MAINTAIN == orderType) {
            if (1 == maintType) {
                return SystemConstants.CLEAN_APPRAISE;
            } else if (2 == maintType) {
                return SystemConstants.FILTER_APPRAISE;
            }
        }
        return -1;
    }

    //0 和 1 布尔值转换
    private Boolean convertBoolean(String number) {
        return "1".equals(number) ? true : false;
    }

}