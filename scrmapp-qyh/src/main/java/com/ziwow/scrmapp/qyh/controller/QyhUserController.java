package com.ziwow.scrmapp.qyh.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.bean.pojo.CancelProductParam;
import com.ziwow.scrmapp.common.bean.vo.*;
import com.ziwow.scrmapp.common.constants.CancelConstant;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.SystemConstants;
import com.ziwow.scrmapp.common.persistence.entity.Filter;
import com.ziwow.scrmapp.common.persistence.entity.MaintainPrice;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.common.persistence.mapper.WechatOrdersMapper;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.utils.OrderUtils;
import com.ziwow.scrmapp.qyh.constants.PointConstant;
import com.ziwow.scrmapp.qyh.service.QyhNoticeService;
import com.ziwow.scrmapp.qyh.service.QyhOrdersService;
import com.ziwow.scrmapp.qyh.service.QyhProductService;
import com.ziwow.scrmapp.qyh.service.QyhUserService;
import com.ziwow.scrmapp.qyh.vo.QyhApiUser;
import com.ziwow.scrmapp.tools.queue.UserQueue;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.HttpClientUtils;
import net.sf.json.JSONObject;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaohei on 2017/4/18.
 */
@Controller
@RequestMapping("/scrmapp/qyhuser")
public class QyhUserController {

    private final Logger logger = LoggerFactory.getLogger(QyhUserController.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${dispatch.mobile}")
    private String dispatchMobile;
    @Value("${qyh.open.corpid}")
    private String corpId;
    @Value("${order.detail.url}")
    private String orderDetailUrl;
    @Value("${mine.baseUrl}")
    private String baseUrl;

    @Autowired
    private WechatOrdersMapper wechatOrdersMapper;
    @Autowired
    private QyhUserService qyhUserService;
    @Autowired
    private QyhOrdersService qyhOrdersService;
    @Autowired
    private QyhProductService qyhProductService;
    @Autowired
    private QyhNoticeService qyhNoticeService;
    @Autowired
    private MobileService mobileService;



    /**
     * 企业号个人中心数据
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result userIndex(HttpServletRequest request, HttpServletResponse response, @RequestParam String userId) {
        Result result = new BaseResult();
        try {
            String qyhUserId = new String(Base64.decode(userId));
            QyhUserVo qyhUserVo = qyhUserService.getQyhUserVoByUserId(qyhUserId);
            result.setData(qyhUserVo);
            result.setReturnCode(Constant.SUCCESS);
        } catch (Exception e) {
            logger.error("请求个人中心数据失败,原因:[{}]", e);
            result.setReturnMsg("请求数据失败,请稍后再试!");
            result.setReturnCode(Constant.FAIL);
        }

        return result;
    }

    /**
     * 获取待处理工单总数和当日待处理工单
     *
     * @return
     */
    @RequestMapping(value = "/orders/condition/count", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result ordersCount(HttpServletRequest request, HttpServletResponse response, @RequestParam String userId, @RequestParam String condition) {
        Result result = new BaseResult();
        try {
            String qyhUserId = new String(Base64.decode(userId));
            QyhUserOrdersVo userOrdersVo = qyhOrdersService.getOrdersCountByQyhUserId(qyhUserId, condition);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(userOrdersVo);
        } catch (Exception e) {
            logger.error("请求待处理工单数据失败,原因:[{}]", e);
            result.setReturnMsg("请求数据失败,请稍后再试!");
            result.setReturnCode(Constant.FAIL);
        }
        return result;
    }


    /**
     * 根据当日，明日，所有条件查询待处理预约单
     *
     * @param request
     * @param response
     * @param condition
     * @return
     */
    @RequestMapping(value = "/orders/condition/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result userTask(HttpServletRequest request, HttpServletResponse response, @RequestParam String userId, @RequestParam(required = false) String condition) {
        Result result = new BaseResult();
        try {
            String qyhUserId = new String(Base64.decode(userId));
            List<WechatOrdersVo> detailVoList = qyhOrdersService.getOrdersListByQyhUserId(qyhUserId, condition);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(detailVoList);
        } catch (Exception e) {
            logger.error("查询待处理工单失败,原因:[{}]", e);
            result.setReturnMsg("请求数据失败,请稍后再试!");
            result.setReturnCode(Constant.FAIL);
        }
        return result;
    }

    /**
     * 师傅已完成工单列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/orders/finished/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result finishedOrdres(HttpServletRequest request, HttpServletResponse response, @RequestParam String userId) {
        Result result = new BaseResult();
        try {
            String qyhUserId = new String(Base64.decode(userId));
            List<WechatOrdersVo> wechatOrdersVo = qyhOrdersService.getFinishedByQyhUserId(qyhUserId);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(wechatOrdersVo);
        } catch (Exception e) {
            logger.error("获取师傅已完成工单异常,原因:[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("请求数据失败,请稍后再试!");
        }

        return result;
    }

    /**
     * 师傅拒绝工单
     *
     * @param request
     * @param response
     * @param ordersId
     * @return
     */
    @RequestMapping(value = "/orders/refuse", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result refuseOrders(HttpServletRequest request, HttpServletResponse response, @RequestParam String userId, @RequestParam Long ordersId, @RequestParam String ordersCode,
                               @RequestParam String contacts, @RequestParam int ordersType, @RequestParam(required = false, defaultValue = "") String reason) {
        logger.info("师傅拒绝工单参数为:userId=[{}],ordersId=[{}],ordersCode=[{}],contacts=[{}],ordersType=[{}],reason=[{}]", userId, ordersId, ordersCode, contacts, ordersType, reason);
        Result result = new BaseResult();
        try {
            String qyhUserId = new String(Base64.decode(userId));
            result = qyhOrdersService.refuseOrders(ordersId, ordersCode, qyhUserId, reason);
            if (Constant.SUCCESS == result.getReturnCode()) {
                // 服务工程师撤销预约给自己发送短信通知提醒
                String serviceType = OrderUtils.getServiceTypeName(ordersType);
                QyhUser qyhUser = qyhUserService.getQyhUserByUserIdAndCorpId(qyhUserId, corpId);
                String mobilePhone = (null != qyhUser) ? qyhUser.getMobile() : "";
                String msgContent = "您已拒绝" + contacts + "用户预约的" + serviceType + "服务，您可进入“沁园”WX企业号查看该工单详情！";
                //短信开口关闭 2019年06月19日
                //mobileService.sendContentByEmay(mobilePhone, msgContent, Constant.ENGINEER);
                // 服务工程师修改预约时间给自己发送通知
                String url = orderDetailUrl + "?userId=" + qyhUserId + "&ordersCode=" + ordersCode;
                String content = "工单撤销通知！\n" +
                        "您已成功拒绝" + contacts + "用户预约的" + serviceType + "服务！\n" +
                        "1、点击<a href='" + url + "'>【待处理工单】</a>查看该工单详情！";
                qyhNoticeService.qyhSendMsgText(qyhUserId, content);
                result.setReturnCode(Constant.SUCCESS);
                result.setReturnMsg("已拒单成功!");
                logger.info(qyhUserId + "师傅拒单操作成功!");
            }
        } catch (Exception e) {
            logger.error("师傅拒绝工单异常,原因:[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("操作失败，请稍后再试!");
        }
        return result;
    }

    /* 工单详情页----师傅取消单个产品  */
    @RequestMapping(value = "/orders/cancel/product", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result cancelProduct(HttpServletRequest request, HttpServletResponse response, @RequestBody CancelProductParam cancelProductParam) {
        Result result = new BaseResult();
        result.setReturnCode(Constant.SUCCESS);
        result.setReturnMsg("取消产品成功!");
        try {
            cancelProductParam.setCorpId(corpId);
            cancelProductParam.setOrderDetailUrl(orderDetailUrl);
            qyhOrdersService.cancelProduct(cancelProductParam);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("操作失败，请稍后再试!");
            logger.error("师傅取消单个产品异常,原因:" + e);
        }
        return result;
    }


    /**
     * 师傅更改预约时间
     *
     * @param request
     * @param response
     * @param ordersCode
     * @param updateTime
     * @return
     */
    @RequestMapping(value = "/orders/change/ordertime", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result changeTime(HttpServletRequest request, HttpServletResponse response, @RequestParam String userId, @RequestParam Long ordersId, @RequestParam String ordersCode,
                             @RequestParam String contacts, @RequestParam int ordersType,
                             @RequestParam String oldTime, @RequestParam String updateTime, @RequestParam(required = false, defaultValue = "") String reason) {
        logger.info("师傅更改预约时间参数为:userId=[{}],ordersId=[{}],ordersCode=[{}],contacts=[{}],ordersType=[{}],oldTime=[{}],updateTime=[{}],reason=[{}]", userId, ordersId, ordersCode, contacts, ordersType, oldTime, updateTime, reason);
        Result result = new BaseResult();
        try {
            String qyhUserId = new String(Base64.decode(userId));
            result = qyhOrdersService.changeOrdersTime(ordersId, ordersCode, qyhUserId, updateTime, reason);
            if (Constant.SUCCESS == result.getReturnCode()) {
                //师傅拒单后保存操作记录
                oldTime = oldTime.replace("~", "-");
                String serviceTypeName = OrderUtils.getServiceTypeName(ordersType);
                // 服务工程师修改预时间给自己发送短信通知提醒
                QyhUser qyhUser = qyhUserService.getQyhUserByUserIdAndCorpId(qyhUserId, corpId);
                String mobilePhone = (null != qyhUser) ? qyhUser.getMobile() : "";
                String qyhUserName = (null != qyhUser) ? qyhUser.getName() : "";
                String msgContent = "您已将" + contacts + "用户预约的" + serviceTypeName + "服务的上门服务时间由" + oldTime + "更改到" + updateTime
                        + "！您可进入“沁园”WX企业号查看该工单详情！";
                //短信开口关闭 2019年06月19日
                //mobileService.sendContentByEmay(mobilePhone, msgContent, Constant.ENGINEER);
                // 服务工程师修改预约时间给自己发送通知
                String url = orderDetailUrl + "?userId=" + qyhUserId + "&ordersCode=" + ordersCode;
                String content = "工单服务时间更改通知！\n" +
                        "    您发起的更改预约已成功！已将" + contacts + "用户预约的" + serviceTypeName + "服务的上门服务时间由" + oldTime + "更改到" + updateTime + "！\n" +
                        "1、点击<a href='" + url + "'>【待处理工单】</a>查看该工单详情！\n" +
                        "    请提前做好安排，不要错过上门服务时间！";
                qyhNoticeService.qyhSendMsgText(qyhUserId, content);
                // 服务工程师更改预约时间给用户发送提醒
                WechatOrdersUserInfo wechatOrdersUserInfo = wechatOrdersMapper.getWechatOrdersUserInfo(ordersCode);
                String userPhone = wechatOrdersUserInfo.getUserPhone();
                String ordUserId = wechatOrdersUserInfo.getUserId();
                if (!userPhone.equalsIgnoreCase(dispatchMobile)) {
                    // 服务工程师更改预约时间给用户发送短信通知提醒
                    String userMsgContent = "亲爱的用户，您预约的" + serviceTypeName + "服务已更改服务时间。工程师（姓名:" + qyhUserName + "，联系方式:"
                            + mobilePhone + "）上门服务时间：" + updateTime + "。请保持电话畅通，届时工程师将与您联系。";
                    //短信开口关闭 2019年06月19日
                    //mobileService.sendContentByEmay(userPhone, userMsgContent, Constant.CUSTOMER);
                    // 服务工程师更改预约时间给用户发送模板消息通知提醒
                    WechatUserMsgVo wechatUserMsgVo = new WechatUserMsgVo();
                    wechatUserMsgVo.setOrdersCode(ordersCode);
                    wechatUserMsgVo.setOrderTime(updateTime);
                    wechatUserMsgVo.setUserId(ordUserId);
                    wechatUserMsgVo.setQyhUserName(qyhUserName);
                    wechatUserMsgVo.setQyhUserPhone(mobilePhone);
                    wechatUserMsgVo.setOrderType(serviceTypeName);
                    wechatUserMsgVo.setType(1);
                    UserQueue.getQueueInstance().add(wechatUserMsgVo);
                }
                result.setReturnCode(Constant.SUCCESS);
                result.setReturnMsg("预约更改成功!");
                logger.info(qyhUserId + "师傅更改预约时间成功!");
            }
        } catch (Exception e) {
            logger.error("师傅更改预约时间异常,原因:[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("操作失败，请稍后再试!");
        }
        return result;
    }

    /**
     * 待处理工单详情
     *
     * @param request
     * @param response
     * @param ordersCode
     * @return
     */
    @RequestMapping(value = "/orders/pending/detail", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result pendingOrdersDetail(HttpServletRequest request, HttpServletResponse response, @RequestParam String ordersCode) {
        Result result = new BaseResult();
        try {
            WechatOrdersVo finishedOrdersDetail = qyhOrdersService.getVoByOrdersCode(ordersCode);
            result.setData(finishedOrdersDetail);
            result.setReturnCode(Constant.SUCCESS);
        } catch (Exception e) {
            logger.error("获取待处理工单详情信息异常,原因:[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("请求数据失败,请稍后再试!");
        }
        return result;
    }

    /**
     * 已完工工单详情
     *
     * @param request
     * @param response
     * @param ordersCode
     * @return
     */
    @RequestMapping(value = "/orders/finished/detail", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result finishOrdersDetail(HttpServletRequest request, HttpServletResponse response, @RequestParam String ordersCode) {
        Result result = new BaseResult();
        try {
            WechatOrdersVo finishedOrdersDetail = qyhOrdersService.getFinishedOrdersDetail(ordersCode);
            result.setData(finishedOrdersDetail);
            result.setReturnCode(Constant.SUCCESS);
        } catch (Exception e) {
            logger.error("获取工单详情信息异常,原因:[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("请求数据失败,请稍后再试!");
        }
        return result;
    }

    /**
     * 师傅点击完成工单展示信息
     *
     * @param request
     * @param response
     * @param ordersCode
     * @return
     */
    @RequestMapping(value = "/orders/finish/detail", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result showFinishOrders(HttpServletRequest request, HttpServletResponse response, @RequestParam String ordersCode) {
        Result result = new BaseResult();
        try {
            WechatOrdersVo finishedOrdersInfo = qyhOrdersService.getFinishDetail(ordersCode);
            result.setData(finishedOrdersInfo);
            result.setReturnCode(Constant.SUCCESS);
        } catch (Exception e) {
            logger.error("获取工单详情信息异常,原因:[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("请求数据失败,请稍后再试!");
        }
        return result;
    }

    /**
     * 给用户发送短信，给师傅发送消息模板
     *
     * @param completeParam
     */
    private void sendTemplate(CompleteParam completeParam) throws Exception {
        String engineerId = completeParam.getQyhUserId() + "";
        String orderCode = completeParam.getOrdersCode();
        String orderTime = completeParam.getOrderTime();
        String regMobile = completeParam.getRegMobile();
        if (!regMobile.equalsIgnoreCase(dispatchMobile)) {
            // 服务工程师提交完工后给用户发送短信通知
            String serviceType = OrderUtils.getServiceTypeName(completeParam.getOrderType());
            String msgContent = "亲爱的用户，您预约的" + serviceType + "服务已完成，欢迎进入“沁园”官方WX服务号对工程师的服务进行评价，谢谢！如已评价，可忽视该消息。";
            //短信开口关闭 2019年06月19日
            //mobileService.sendContentByEmay(regMobile, msgContent, Constant.CUSTOMER);
            // 服务工程师提交完工后给用户发送模板消息
            WechatUserMsgVo wechatUserMsgVo = new WechatUserMsgVo();
            wechatUserMsgVo.setOrdersCode(orderCode);
            wechatUserMsgVo.setOrderTime(orderTime);
            wechatUserMsgVo.setUserId(completeParam.getUserId());
            wechatUserMsgVo.setType(2);
            UserQueue.getQueueInstance().add(wechatUserMsgVo);
        }
        // 服务工程师提交完工后给自己发送短信通知
        String msgContent = "您已成功提交工单，记得让用户给你好评哦！您可进入“沁园”WX企业号查看该工单详情！";
        String engineerPhone = completeParam.getQyhUserPhone();
        //短信开口关闭 2019年06月19日
        //mobileService.sendContentByEmay(engineerPhone, msgContent, Constant.ENGINEER);
        // 服务工程师提交完工后给自己发送公告通知
        String url = orderDetailUrl + "?userId=" + engineerId + "&ordersCode=" + orderCode;
        String content = "您已成功提交工单！\n" +
                "    您已成功提交工单，用户将对您的服务进行评价。\n" +
                "1、点击<a href='" + url + "'>【已完工单】</a>查看该工单详情！";
        qyhNoticeService.qyhSendMsgText(engineerId, content);
    }


    /**
     * 安装单完工
     *
     * @param request
     * @param response
     * @param ordersCode
     * @param userId
     * @param products
     * @return
     */
    @RequestMapping(value = "/orders/finish/install", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result installComplete(HttpServletRequest request, HttpServletResponse response, @RequestParam String ordersCode, @RequestParam String userId,
                                  @RequestParam String products,
                                  @RequestParam(defaultValue = "", required = false) String reasons) {
        logger.info("调用安装单完工接口,ordersCode:[{}],userId:[{}],products:[{}],reasons:[{}]", ordersCode, userId, products, reasons);
        Result result = new BaseResult();
        result.setReturnCode(Constant.SUCCESS);
        result.setReturnMsg("工单提交完成!");
        try {
            CompleteParam completeParam = qyhOrdersService.getCompleteParamByOrdersCode(ordersCode);
            // 完工check
            completeCheck(completeParam, userId);
            // 同步安装单到csm系统
            result = qyhOrdersService.syncInstallWechatOrders(completeParam, products, reasons);
            if (Constant.SUCCESS != result.getReturnCode()) {
                return result;
            }
            Object appealInstallNoObj = result.getData();
            if (appealInstallNoObj == null || StringUtils.isEmpty(appealInstallNoObj.toString())) {
                throw new RuntimeException("工单提交失败!");
            }
            int count = qyhOrdersService.getProductStatus(completeParam.getOrdersId());
            // 发送模板消息
            sendTemplate(completeParam);
            //调用服务号发积分方法,
            logger.info("预约安装完工发放积分！ordersCode = [{}]",ordersCode);
            grantPoint(ordersCode, PointConstant.INSTALL,1,sdf.format(wechatOrdersMapper.getWechatOrdersVoByCode(ordersCode).getCreateTime()));


        } catch (RuntimeException ex) {
            logger.error("师傅点击工单[{}]完工操作出现异常,原因:[{}]", ordersCode, ex);
            logger.error("师傅点击工单完工操作失败:", ex);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg(ex.getMessage());
        } catch (Exception e) {
            logger.error("师傅点击工单[{}]完工操作出现异常,原因:[{}]", ordersCode, e);
            logger.error("师傅点击工单完工操作失败:", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("请求出错,请稍后再试!");
        }
        return result;
    }

    /**
     * 维修单完工
     *
     * @param request
     * @param response
     * @param ordersCode
     * @param userId
     * @param products
     * @param is_replace
     * @return
     */
    @RequestMapping(value = "/orders/finish/repair", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result repairComplete(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam String ordersCode,
                                 @RequestParam String userId,
                                 @RequestParam String products,
                                 @RequestParam(required = false, defaultValue = "0") int is_replace) {
        logger.info("调用维修单完工接口,ordersCode:[{}],userId:[{}],products:[{}],is_replace:[{}]", ordersCode, userId, products, is_replace);
        Result result = new BaseResult();
        result.setReturnCode(Constant.SUCCESS);
        result.setReturnMsg("工单提交完成!");
        try {
            CompleteParam completeParam = qyhOrdersService.getCompleteParamByOrdersCode(ordersCode);
            // 完工check
            completeCheck(completeParam, userId);
            // 同步维修单到csm系统
            result = qyhOrdersService.syncRepairWechatOrders(completeParam, is_replace, products);
            if (Constant.SUCCESS != result.getReturnCode()) {
                return result;
            }
            String appealMaintenanceNo = result.getData().toString();
            if (StringUtils.isEmpty(appealMaintenanceNo)) {
                throw new RuntimeException("工单提交失败!");
            }
            // 全部完工,发送模板消息
//            int count = qyhOrdersService.getProductStatus(completeParam.getOrdersId());
//            if (count == 0) {
//                sendTemplate(completeParam);
//            }
            //如果产品状态全部为取消或完工,则工单算作完工状态,发送短信和消息模板
            if (qyhOrdersService.isFinish(qyhProductService.getAllStatus(completeParam.getOrdersId()))) {
                sendTemplate(completeParam);
                logger.info("预约维修完工发放积分！ordersCode = [{}]",ordersCode);
                grantPoint(ordersCode,PointConstant.REPAIR,2,sdf.format(wechatOrdersMapper.getWechatOrdersVoByCode(ordersCode).getCreateTime()));
            }

        } catch (RuntimeException ex) {
            logger.error("师傅点击工单[{}]完工操作出现异常,原因:[{}]", ordersCode, ex);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg(ex.getMessage());
        } catch (Exception e) {
            logger.error("师傅点击工单[{}]完工操作出现异常,原因:[{}]", ordersCode, e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("请求出错,请稍后再试!");
        }
        return result;
    }

    /**
     * 保养单完工
     *
     * @param request
     * @param response
     * @param ordersCode
     * @param userId
     * @param products
     * @param is_change_filter
     * @return
     */
    @RequestMapping(value = "/orders/finish/maintain", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result maintainComplete(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam String ordersCode,
                                   @RequestParam String userId,
                                   @RequestParam String products,
                                   @RequestParam(required = false, defaultValue = "0") int is_change_filter) {
        logger.info("调用保养单完工接口,ordersCode:[{}],userId:[{}],products:[{}],is_change_filter:[{}]", ordersCode, userId, products, is_change_filter);
        Result result = new BaseResult();
        result.setReturnCode(Constant.SUCCESS);
        result.setReturnMsg("工单提交完成!");

        try {
            CompleteParam completeParam = qyhOrdersService.getCompleteParamByOrdersCode(ordersCode);
            // 完工check
            completeCheck(completeParam, userId);
            // 同步保养单到csm系统
            result = qyhOrdersService.syncMaintainWechatOrders(completeParam, is_change_filter, products);
            if (Constant.SUCCESS != result.getReturnCode()) {
                return result;
            }
            String appealMaintainNo = result.getData().toString();
            if (StringUtils.isEmpty(appealMaintainNo)) {
                throw new RuntimeException("工单提交失败!");
            }
            // 全部完工,发送模板消息
//            int count = qyhOrdersService.getProductStatus(completeParam.getOrdersId());
//            if (count == 0) {
//                sendTemplate(completeParam);
//            }
            //如果产品状态全部为取消或完工,则工单算作完工状态,发送短信和消息模板
            if (qyhOrdersService.isFinish(qyhProductService.getAllStatus(completeParam.getOrdersId()))) {
                sendTemplate(completeParam);
            }
            qyhOrdersService.finishMakeAppointment(completeParam.getOrdersCode());
            Integer orderType = wechatOrdersMapper.getParamByOrdersCode(ordersCode).getMaintType() == 1
                    ? 4 : 3 ;
            Date createTime = wechatOrdersMapper.getWechatOrdersVoByCode(ordersCode).getCreateTime();
            String path = (orderType == 4) ? PointConstant.WASH:PointConstant.FILTER;
            logger.info("预约保养完工发放积分！ordersCode = [{}]",ordersCode);
            grantPoint(ordersCode, path,orderType,sdf.format(createTime));
        } catch (RuntimeException ex) {
            logger.error("师傅点击工单[{}]完工操作出现异常,原因:[{}]", ordersCode, ex);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("请求出错,请稍后再试!");
        } catch (Exception e) {
            logger.error("师傅点击工单[{}]完工操作出现异常,原因:[{}]", ordersCode, e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("请求出错,请稍后再试!");
        }
        return result;
    }

    /**
     * 维修或保养单取消单个产品
     *
     * @param request
     * @param response
     * @param ordersId
     * @param productId
     * @return
     */
    @RequestMapping(value = "/orders/cancelSingle/repair_maintain", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result repairCancelSingle(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam Long ordersId,
                                     @RequestParam Long productId,
                                     @RequestParam String ordersCode) {
        logger.info("调用维修或保养单取消单个产品接口,ordersId={},productId={}", ordersId, productId);
        Result result = new BaseResult();
        result.setReturnCode(Constant.FAIL);
        result.setReturnMsg("取消失败!");
        int code = qyhOrdersService.doCancel(ordersId, productId, ordersCode);
        if (code != CancelConstant.CANCEL_ONLY) {
            CompleteParam completeParam = qyhOrdersService.getCompleteParamByOrdersCode(ordersCode);
            if (code == CancelConstant.CANCEL_REFUND) {
                //调用拒绝工单接口
                refundOrder(completeParam);
            } else if (code == CancelConstant.CANCEL_COMPLETE) {
                try {
                    sendTemplate(completeParam);
                } catch (Exception e) {
                    logger.error("发送短信消息模板失败", e);

                }
            }
        }
        result.setReturnCode(Constant.SUCCESS);
        result.setReturnMsg("取消成功!");
        return result;
    }

    //全部取消产品调用拒绝工单接口
    private void refundOrder(CompleteParam completeParam) {
        String qyhUserId = String.valueOf(completeParam.getQyhUserId());
        Long ordersId = completeParam.getOrdersId();
        String ordersCode = completeParam.getOrdersCode();
        int ordersType = completeParam.getOrderType();
        String contacts = completeParam.getUserName();
        Result result = qyhOrdersService.refuseOrders(ordersId, ordersCode, qyhUserId, "");
        if (Constant.SUCCESS == result.getReturnCode()) {
            // 服务工程师撤销预约给自己发送短信通知提醒
            String serviceType = OrderUtils.getServiceTypeName(ordersType);
            QyhUser qyhUser = qyhUserService.getQyhUserByUserIdAndCorpId(qyhUserId, corpId);
            String mobilePhone = (null != qyhUser) ? qyhUser.getMobile() : "";
            String msgContent = "您已拒绝" + contacts + "用户预约的" + serviceType + "服务，您可进入“沁园”WX企业号查看该工单详情！";
            try {
                //短信开口关闭 2019年06月19日
                //mobileService.sendContentByEmay(mobilePhone, msgContent, Constant.ENGINEER);
            } catch (Exception e) {
                logger.error("发送短信失败", e);
            }
            // 服务工程师修改预约时间给自己发送通知
            String url = orderDetailUrl + "?userId=" + qyhUserId + "&ordersCode=" + ordersCode;
            String content = "工单撤销通知！\n" +
                    "您已成功拒绝" + contacts + "用户预约的" + serviceType + "服务！\n" +
                    "1、点击<a href='" + url + "'>【待处理工单】</a>查看该工单详情！";
            qyhNoticeService.qyhSendMsgText(qyhUserId, content);
            result.setReturnCode(Constant.SUCCESS);
            result.setReturnMsg("已拒单成功!");
            logger.info(qyhUserId + "师傅拒单操作成功!");
        }
    }


    private void completeCheck(CompleteParam completeParam, String userId) throws UnsupportedEncodingException {
        String qyhUserId = new String(Base64.decode(userId));
        if (completeParam == null) {
            throw new RuntimeException("工单信息有误，请先核对!");
        }
        if (!("" + completeParam.getQyhUserId()).equals(qyhUserId)) {
            throw new RuntimeException("师傅信息核对失败,请稍后再试!");
        }
        if (SystemConstants.RECEIVE != completeParam.getStatus()) {
            throw new RuntimeException("工单状态不对，无法提交完工!");
        }
    }

    /**
     * 检验条码
     *
     * @param request
     * @param response
     * @param productId
     * @param productBarCode
     * @param productName
     * @param typeName
     * @param productCode
     * @param modelName
     * @param levelId
     * @param levelName
     * @param saleType
     * @return
     */
    @RequestMapping(value = "/orders/barcode/check", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result checkBarCode(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam Long productId, @RequestParam String itemKind, @RequestParam String productBarCode, @RequestParam String productName, @RequestParam String typeName,
                               @RequestParam String productCode, @RequestParam String modelName, @RequestParam Long levelId, @RequestParam String levelName, @RequestParam int saleType) {
        Result result = new BaseResult();
        try {
            Product product = qyhProductService.findById(productId);
            //如果产品编码不对应
            if (product != null && !productCode.equals(product.getProductCode())) {
                //查询滤芯级别对应的滤芯
                if (levelId != 0 && levelId != product.getLevelId()) {
                    List<Filter> filterList = qyhProductService.findByLevelId(levelId);
                    if (filterList.size() == 0) {
                        //添加滤芯以及滤芯级别关系
                        qyhProductService.batchSave(modelName, productCode);
                    }
                }

                //更新product表
                product.setProductBarCode(productBarCode);
                product.setItemKind(itemKind);
                product.setProductName(productName);
                product.setTypeName(typeName);
                product.setProductCode(productCode);
                product.setModelName(modelName);
                product.setLevelId(levelId);
                product.setLevelName(levelName);
                product.setSaleType(saleType);
                qyhProductService.updateProduct(product);

                //更新图片
                int index = modelName.indexOf("（");
                if (index != -1) {
                    modelName = modelName.substring(0, index);
                }
                qyhProductService.queryProductImage(modelName);

                if (product.getFilterRemind() != null && product.getLevelId() != 0) {
                    //更新滤芯提醒记录
                    qyhProductService.updateFilterRemind(product);
                }

                //获取保养列表
                List<MaintainPrice> maintainPriceList = qyhProductService.findByProductCode(productCode);
                if (maintainPriceList.size() == 0) {
                    //更新滤芯更改项目
                    qyhProductService.batchSaveMaintainItem(modelName, productCode);
                }
            }
            logger.info("师傅录入条码校验成功!");
            result.setReturnMsg("校验成功!");
            result.setReturnCode(Constant.SUCCESS);
        } catch (Exception e) {
            result.setReturnMsg("校验失败!");
            result.setReturnCode(Constant.FAIL);
            logger.error("师傅录入条码校验失败!", e);
        }
        return result;

    }

    /**
     * 通过大类和name查询维修措施
     *
     * @param request
     * @param response
     * @param typeName
     * @param name
     * @return
     */
    @RequestMapping(value = "/orders/repairItem/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result findRepairItem(HttpServletRequest request, HttpServletResponse response, @RequestParam String typeName, @RequestParam String smallcName, @RequestParam(required = false) String name) {
        String typeNameDecode = "";
        String smallcNameDecode = "";
        String nameDecode = "";
        try {
            if (!StringUtils.isEmpty(typeName)) {
                typeNameDecode = URLDecoder.decode(typeName, "utf-8");
            }
            if (!StringUtils.isEmpty(smallcName)) {
                smallcNameDecode = URLDecoder.decode(smallcName, "utf-8");
            }
            if (!StringUtils.isEmpty(name)) {
                nameDecode = URLDecoder.decode(name, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        Result result = qyhOrdersService.findRepairItem(typeNameDecode, smallcNameDecode, nameDecode);
        return result;
    }


    /**
     * 通过型号和名称模糊查询安装配件
     *
     * @param request
     * @param response
     * @param modelName
     * @return
     */
    @RequestMapping(value = "/orders/installPart/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result findInstallPart(HttpServletRequest request, HttpServletResponse response, @RequestParam String modelName, @RequestParam(required = false) String name) {
        String modelNameDecode = "";
        String nameDecode = "";
        try {
            if (!StringUtils.isEmpty(modelName)) {
                modelNameDecode = URLDecoder.decode(modelName, "utf-8");
            }
            if (!StringUtils.isEmpty(name)) {
                nameDecode = URLDecoder.decode(name, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        Result result = qyhOrdersService.findInstallPart(modelNameDecode, nameDecode);
        return result;
    }

    /**
     * 通过型号和名称模糊查询维修配件
     *
     * @param request
     * @param response
     * @param modelName
     * @return
     */
    @RequestMapping(value = "/orders/repairPart/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result findRepairPart(HttpServletRequest request, HttpServletResponse response, @RequestParam String modelName, @RequestParam(required = false) String name) {
        String modelNameDecode = "";
        String nameDecode = "";
        try {
            if (!StringUtils.isEmpty(modelName)) {
                modelNameDecode = URLDecoder.decode(modelName, "utf-8");
            }
            if (!StringUtils.isEmpty(name)) {
                nameDecode = URLDecoder.decode(name, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        Result result = qyhOrdersService.findRepairPart(modelNameDecode, nameDecode);
        return result;
    }


    /**
     * 获取产品滤芯以及保养项
     *
     * @param request
     * @param response
     * @param levelId     滤芯级别id
     * @param productCode 产品编码
     * @return
     */
    @RequestMapping(value = "/orders/miantain/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result findMaintainInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam Long levelId, @RequestParam String productCode) {
        Result result = qyhOrdersService.findMaintainInfo(levelId, productCode);
        return result;
    }

    /**
     * 企业号个人中心页面
     *
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/index/page")
    public ModelAndView qyhuserIndexPage(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam("code") String code) {
        ModelAndView modelAndView = new ModelAndView("/qiYeHao/workOrderOperation/jsp/engineerPersonCenter");
        QyhUser qyhUser = qyhUserService.getOAuthQyhUserInfo(code, request, response);
        logger.info("企业号个人中心页面获取员工授权信息,[{}]", JSON.toJSON(qyhUser));
        String userId = Base64.encode(qyhUser.getUserid().getBytes());
        modelAndView.addObject("userId", userId);

        return modelAndView;
    }

    /**
     * 企业号中师傅已完成订单列表页面
     *
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/orders/finish/list/page")
    public ModelAndView qyhFinishOrdersPage(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam("code") String code) {
        ModelAndView modelAndView = new ModelAndView("/qiYeHao/workOrderOperation/jsp/completedWorkOrder");
        QyhUser qyhUser = qyhUserService.getOAuthQyhUserInfo(code, request, response);

        logger.info("已完成订单列表页面获取员工授权信息,[{}]", JSON.toJSON(qyhUser));
        String userId = Base64.encode(qyhUser.getUserid().getBytes());
        //保存加密后企业号id
        modelAndView.addObject("userId", userId);

        return modelAndView;
    }

    /**
     * 企业号中师傅待处理订单列表页面
     *
     * @param request
     * @param response
     * @param code
     * @return
     */
    @RequestMapping(value = "/orders/pending/list/page", method = RequestMethod.GET)
    public ModelAndView qyhPendingOrdersPage(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(value = "code", required = false) String code, @RequestParam String condition,
                                             @RequestParam(value = "userId", required = false) String qyhUserId) {
        ModelAndView modelAndView = new ModelAndView("/qiYeHao/workOrderOperation/jsp/unfinishedWorkOrders");
        if (StringUtils.isEmpty(qyhUserId)) {
            QyhUser qyhUser = qyhUserService.getOAuthQyhUserInfo(code, request, response);
            logger.info("待处理订单列表页面获取员工授权信息,[{}]", JSON.toJSON(qyhUser));
            //把加密后企业号userId传到页面
            String userId = Base64.encode(qyhUser.getUserid().getBytes());
//            String userId = Base64.encode("72247".getBytes());
            modelAndView.addObject("userId", userId);
        } else {
            modelAndView.addObject("userId", qyhUserId);
        }
        modelAndView.addObject("condition", condition);
        return modelAndView;
    }

    /**
     * 消息模板链接跳转（工单详情）
     *
     * @param request
     * @param response
     * @param qyhUserId
     * @return
     */
    @RequestMapping(value = "/orders/auth/detail/page")
    public ModelAndView qyhOrdersDetailPage(HttpServletRequest request, HttpServletResponse response, @RequestParam("ordersCode") String ordersCode,
                                            @RequestParam(value = "userId") String qyhUserId) {
        ModelAndView modelAndView = new ModelAndView("/qiYeHao/workOrderOperation/jsp/unfinishedwoDetail");
        String userId = Base64.encode(qyhUserId.getBytes());
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("ordersCode", ordersCode);

        return modelAndView;
    }

    /**
     * 工单列表页----点击查看详情
     *
     * @param request
     * @param response
     * @param ordersCode
     * @return
     */
    @RequestMapping(value = "/orders/detail/page")
    public ModelAndView qyhOrdersDetailPage(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam("ordersCode") String ordersCode) {
        ModelAndView modelAndView = new ModelAndView("/qiYeHao/workOrderOperation/jsp/unfinishedwoDetail");
        modelAndView.addObject("ordersCode", ordersCode);
        return modelAndView;
    }

    /**
     * 跳转到已完工详情页面
     *
     * @return
     */
    @RequestMapping(value = "/orders/finish/detail/page")
    public ModelAndView qyhFinishOrdersDetailPage(@RequestParam int orderType) {
        ModelAndView modelAndView = new ModelAndView();
        if (SystemConstants.INSTALL == orderType) {
            modelAndView.setViewName("/qiYeHao/submitOrder/jsp/submit_install");
        } else if (SystemConstants.REPAIR == orderType) {
            modelAndView.setViewName("/qiYeHao/submitOrder/jsp/submit_repair");
        } else if (SystemConstants.MAINTAIN == orderType) {
            modelAndView.setViewName("/qiYeHao/submitOrder/jsp/submit_clean");
        } else {
            modelAndView.setViewName("404");
        }

        return modelAndView;
    }

    /**
     * 跳转到已完工详情页面
     *
     * @return
     */
    @RequestMapping(value = "/orders/finished/detail/page")
    public ModelAndView qyhFinishedOrdersDetailPage(@RequestParam String ordersCode) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/qiYeHao/orderDetail/jsp/order_detail");
        modelAndView.addObject("ordersCode", ordersCode);
        return modelAndView;
    }

    @RequestMapping(value = "/avatar")
    public void getHistoryAvatar(HttpServletRequest request) throws IOException {
        String path = this.getClass().getResource("/").getPath();
        path = URLDecoder.decode(path, "utf-8");
        String fileName = path + "userId.txt";
        File file = new File(fileName);
        List<String> userIds = FileUtils.readLines(file, Charsets.UTF_8);
        for (String userId : userIds) {
            QyhApiUser qyhApiUser = qyhUserService.getUser(userId);
            QyhUser qyhUser = new QyhUser();
            qyhUser.setUserid(userId);
            qyhUser.setCorpid(corpId);
            qyhUser.setAvatar(qyhApiUser.getAvatar());
            qyhUserService.updateQyhUser(qyhUser);
            logger.info("history user:{},avatar:{} update success!", userId, qyhApiUser.getAvatar());
        }
    }

    /**
     * 工单详情页面----安装
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/orders/detail/install")
    public ModelAndView qyhOrdersDetailInstallPage(HttpServletRequest request, HttpServletResponse response,
                                                   @RequestParam("ordersCode") String ordersCode) {
        ModelAndView modelAndView = new ModelAndView("/qiYeHao/workOrderOperation/jsp/unfinishedOrderDetail_install");
        modelAndView.addObject("ordersCode", ordersCode);
        return modelAndView;
    }

    /**
     * 工单详情页面----维修
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/orders/detail/repair")
    public ModelAndView qyhOrdersDetailRepairPage(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestParam("ordersCode") String ordersCode) {
        ModelAndView modelAndView = new ModelAndView("/qiYeHao/workOrderOperation/jsp/unfinishedOrderDetail_repair");
        modelAndView.addObject("ordersCode", ordersCode);
        return modelAndView;
    }

    /**
     * 维修详情页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/orders/repair/detail")
    public ModelAndView repairDetailPage(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam("ordersCode") String ordersCode) {
        ModelAndView modelAndView = new ModelAndView("/qiYeHao/workOrderOperation/jsp/unfinishedOrderDetail_repair_detail");
        modelAndView.addObject("ordersCode", ordersCode);
        return modelAndView;
    }

    /**
     * 工单详情页面----保养
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/orders/detail/maintain")
    public ModelAndView qyhOrdersDetailMaintainPage(HttpServletRequest request, HttpServletResponse response,
                                                    @RequestParam("ordersCode") String ordersCode) {
        ModelAndView modelAndView = new ModelAndView("/qiYeHao/workOrderOperation/jsp/unfinishedOrderDetail_maintain");
        modelAndView.addObject("ordersCode", ordersCode);
        return modelAndView;
    }

    /**
     * 保养详情页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/orders/maintain/detail")
    public ModelAndView maintainepairDetailPage(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam("ordersCode") String ordersCode) {
        ModelAndView modelAndView = new ModelAndView("/qiYeHao/workOrderOperation/jsp/unfinishedOrderDetail_maintain_detail");
        modelAndView.addObject("ordersCode", ordersCode);
        return modelAndView;
    }

    /**
     * 内部调用发积分
     */
    private void grantPoint(String ordersCode, String path, Integer orderType, String createTime){
        WechatOrderVo wechatOrdersVoByCode = wechatOrdersMapper
            .getWechatOrdersVoByCode(ordersCode);
        Map<String,Object> params = new HashMap<String,Object>();
        if(null!=wechatOrdersVoByCode){
            params.put("userId",wechatOrdersVoByCode.getUserId());
            params.put("ordersCode",ordersCode);
            params.put("createTime",createTime);
            if(null!=orderType){
                params.put("orderType",orderType);
            }
            HttpClientUtils
                .postJson(baseUrl+path, JSONObject.fromObject(params).toString());
        }

    }
}