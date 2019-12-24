package com.ziwow.scrmapp.wechat.controller.servicecenter;

import com.ziwow.scrmapp.common.bean.pojo.WechatOrdersParam;
import com.ziwow.scrmapp.common.bean.pojo.ext.WechatOrdersParamExt;
import com.ziwow.scrmapp.common.bean.vo.ProductVo;
import com.ziwow.scrmapp.common.bean.vo.QyhUserMsgVo;
import com.ziwow.scrmapp.common.bean.vo.WechatOrdersVo;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.SystemConstants;
import com.ziwow.scrmapp.common.enums.DeliveryType;
import com.ziwow.scrmapp.common.exception.BizException;
import com.ziwow.scrmapp.common.pagehelper.Page;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrders;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrdersRecord;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.utils.OrderUtils;
import com.ziwow.scrmapp.common.utils.SignUtil;
import com.ziwow.scrmapp.common.utils.Transformer;
import com.ziwow.scrmapp.tools.queue.EngineerQueue;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.CookieUtil;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.controller.base.BaseController;
import com.ziwow.scrmapp.wechat.params.address.AddressDeleteParam;
import com.ziwow.scrmapp.wechat.params.address.AddressModifyParam;
import com.ziwow.scrmapp.wechat.params.address.AddressSaveParam;
import com.ziwow.scrmapp.wechat.params.common.CenterServiceParam;
import com.ziwow.scrmapp.wechat.params.order.ConfirmOrderParam;
import com.ziwow.scrmapp.wechat.params.order.OrderCancelParam;
import com.ziwow.scrmapp.wechat.params.order.OrderModifyParam;
import com.ziwow.scrmapp.wechat.params.order.OrderSaveParam;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUserAddress;
import com.ziwow.scrmapp.wechat.service.OrdersProRelationsService;
import com.ziwow.scrmapp.wechat.service.ProductService;
import com.ziwow.scrmapp.wechat.service.WechatOrdersRecordService;
import com.ziwow.scrmapp.wechat.service.WechatOrdersService;
import com.ziwow.scrmapp.wechat.service.WechatQyhUserService;
import com.ziwow.scrmapp.wechat.service.WechatUserAddressService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ziwow.scrmapp.common.result.ResultHelper.success;

/**
 * 服务中心数据交互
 *
 * @author: yyc
 * @date: 19-8-30 下午8:02
 */
@RestController
@RequestMapping("/service-center/data")
public class ServiceCenterDataController extends BaseController {

  @Value("${order.detail.url}")
  private String orderDetailUrl;

  private final ProductService productService;
  private final WechatUserService wechatUserService;
  private final WechatUserAddressService wechatUserAddressService;
  private final WechatOrdersService wechatOrdersService;
  private final WechatOrdersRecordService wechatOrdersRecordService;
  private final WechatQyhUserService wechatQyhUserService;
  private final OrdersProRelationsService ordersProRelationsService;

  @Autowired
  public ServiceCenterDataController(
      ProductService productService,
      WechatUserService wechatUserService,
      WechatUserAddressService wechatUserAddressService,
      WechatOrdersService wechatOrdersService,
      WechatOrdersRecordService wechatOrdersRecordService,
      WechatQyhUserService wechatQyhUserService,
      OrdersProRelationsService ordersProRelationsService) {
    this.productService = productService;
    this.wechatUserService = wechatUserService;
    this.wechatUserAddressService = wechatUserAddressService;
    this.wechatOrdersService = wechatOrdersService;
    this.wechatOrdersRecordService = wechatOrdersRecordService;
    this.wechatQyhUserService = wechatQyhUserService;
    this.ordersProRelationsService = ordersProRelationsService;
  }

  /**
   * 用户预约
   *
   * <p>提交预约生成受理单
   *
   * @param param {@link OrderSaveParam}
   * @return {@link Result}
   */
  @Transactional(rollbackFor = Exception.class)
  @RequestMapping("/order/save")
  public Result saveWeChatOrder(@RequestBody OrderSaveParam param) {
    WechatUser wechatUser = obtainWeChatUser(param);
    WechatOrdersParamExt wechatOrdersParamExt =
        Transformer.fromBean(param, WechatOrdersParamExt.class);
    // 设置订单发货类型
    wechatOrdersParamExt.setDeliveryType(DeliveryType.NORMAL);
    // 设置电话号码
    boolean telephoneBlank = StringUtils.isBlank(wechatOrdersParamExt.getContactsTelephone());
    wechatOrdersParamExt.setTel(
        telephoneBlank
            ? wechatUser.getMobilePhone()
            : wechatOrdersParamExt.getContactsTelephone().replace("-", ""));
    // 调用csm接口生成受理单
    Result result = generateOrderFromCsm(wechatOrdersParamExt);
    // 保存预约单
    Date date = new Date();
    WechatOrders wechatOrders = Transformer.fromBean(wechatOrdersParamExt, WechatOrders.class);
    wechatOrders.setUserId(wechatUser.getUserId());
    wechatOrders.setOrdersCode(result.getData().toString());
    wechatOrders.setStatus(SystemConstants.ORDERS);
    wechatOrders.setCreateTime(date);
    wechatOrders.setUpdateTime(date);
    wechatOrders.setOrderTime(
        DateUtil.StringToDate(wechatOrdersParamExt.getOrderTime(), "yyyy-MM-dd HH"));
    wechatOrders.setSource(SystemConstants.WEIXIN);
    wechatOrders.setDeliveryType(wechatOrdersParamExt.getDeliveryType());
    wechatOrders.setDepartmentName(wechatOrdersParamExt.getDepartmentName());
    wechatOrders =
        wechatOrdersService.saveOrdersMultiProduct(
            wechatOrders, wechatOrdersParamExt.getProductIds());
    if (wechatOrders.getId() == null) {
      throw new BizException("用户预约失败");
    }
    // 推送模板通知和短信，保存工单进度
    pushMessageAndSaveRecord(wechatOrders, date);
    log.info(
        "生成受理单,userId = [{}] , ordersCode = [{}]",
        wechatUser.getUserId(),
        wechatOrders.getOrdersCode());
    return success(wechatOrders);
  }



  /**
   * 工单列表
   *
   * <p>用户预约的工单进度查询
   *
   * @param param {@link CenterServiceParam}
   * @param page {@link Page}
   * @return {@link Result}
   */
  @RequestMapping("/order/list")
  public Result listWeChatOrder(@RequestBody CenterServiceParam param, Page page) {
    WechatUser wechatUser = obtainWeChatUser(param);
    long count = wechatOrdersService.getCountByUserId(wechatUser.getUserId());
    if (count == 0) {
      return toEmptyPageMap();
    }
    List<WechatOrdersVo> wechatOrders =
        wechatOrdersService.pageByUserId(wechatUser.getUserId(), page);
    return toPageMap(wechatOrders, count);
  }


  /**
   * 确认工单是否有重复提交的情况
   * @param param
   * @return
   */
  @RequestMapping("/order/confirm")
  public Result orderConfirm (@RequestBody ConfirmOrderParam param) {
    WechatUser wechatUser = obtainWeChatUser(param);
    boolean isContains = wechatOrdersService.confirmOrder(param,wechatUser.getUserId());
    return success(isContains);
  }


  /**
   * 用户取消预约
   *
   * <p>取消预约同步到csm
   *
   * @param param {@link CenterServiceParam}
   * @return {@link Result}
   */
  @Transactional(rollbackFor = Exception.class)
  @RequestMapping("/order/cancel")
  public Result removeWeChatOrder(@RequestBody OrderCancelParam param) {
    WechatUser wechatUser = obtainWeChatUser(param);
    String userId = wechatUser.getUserId();
    String ordersCode = param.getOrdersCode();
    String contacts = param.getContacts();
    // 根据受理单号查询用户预约单
    WechatOrders wechatOrders = wechatOrdersService.getWechatOrdersByCode(ordersCode);
    if (wechatOrders == null) {
      throw new BizException("预约取消失败，预约单不存在");
    }
    // 检查是否有部分产品完工
    List<ProductVo> productVos = productService.findByOrderId(wechatOrders.getId());
    checkProductFinish(productVos, "师傅已经完工部分产品，无法取消工单");
    // 调用csm取消预约
    Result cancelResult = wechatOrdersService.cancelOrders(ordersCode);
    if (Constant.SUCCESS != cancelResult.getReturnCode()) {
      throw new BizException(cancelResult.getReturnMsg());
    }
    Date date = new Date();
    boolean ret =
        wechatOrdersService.updateOrdersStatus(ordersCode, userId, date, SystemConstants.CANCEL)
            > 0;
    if (!ret) {
      throw new BizException("工单取消失败");
    }
    // 保存工单取消记录
    saveWechatOrderRecord(wechatOrders.getId(), date, "用户取消预约");
    // 给用户发送发送模板消息
    String serverType = OrderUtils.getServiceTypeName(wechatOrders.getOrderType());
    wechatOrdersService.sendOrderCancelTemplateMsg(userId, serverType);
    // 如果服务工程师接单了，用户侧取消需要给服务工程师发送取消通知
    sendMessageToQyhUser(wechatOrders.getQyhUserId(), contacts, serverType, ordersCode);
    // 推送更新到小程序
    wechatOrdersService.cancelMakeAppointment(ordersCode);
    log.info("预约取消,userId = [{}] , ordersCode = [{}]", userId, ordersCode);
    return success(param.getOrdersCode());
  }

  /**
   * 用户更改预约时间
   *
   * @param param {@link OrderModifyParam}
   * @return {@link Result}
   */
  @Transactional(rollbackFor = Exception.class)
  @RequestMapping("/order/modify")
  public Result modifyWeChatOrder(@RequestBody OrderModifyParam param) {
    WechatUser wechatUser = obtainWeChatUser(param);
    String userId = wechatUser.getUserId();
    String ordersCode = param.getOrdersCode();
    String contacts = param.getContacts();
    String updateTime = param.getUpdateTime();
    WechatOrders wechatOrders = wechatOrdersService.getWechatOrdersByCode(ordersCode);
    if (wechatOrders == null) {
      throw new BizException("更改预约失败，预约单不存在");
    }
    Date date = new Date();
    // 如果此时订单状态还是下单状态或者重新处理中，直接修改预约时间
    Long orderId = wechatOrders.getId();
    if (SystemConstants.ORDERS == wechatOrders.getStatus()
        || SystemConstants.REDEAL == wechatOrders.getStatus()) {
      Result result = wechatOrdersService.updateOrdersTime(ordersCode, date, param.getUpdateTime());
      if (result.getReturnCode() != Constant.SUCCESS) {
        throw new BizException(result.getReturnMsg());
      }
      // 保存工单记录
      saveWechatOrderRecord(orderId, date, "用户更改预约时间");
      return success(ordersCode);
    }
    // 检查是否有部分产品完工
    WechatOrdersParam wechatOrdersParam = wechatOrdersService.getParamByOrdersCode(ordersCode);
    checkProductFinish(wechatOrdersParam.getProducts(), "师傅已经完工部分产品，无法更改预约时间");
    // 调用csm接口，修改预约时间
    Result cancelResult = wechatOrdersService.cancelOrders(ordersCode);
    if (Constant.SUCCESS != cancelResult.getReturnCode()) {
      throw new BizException(cancelResult.getReturnMsg());
    }
    boolean ret =
        wechatOrdersService.updateOrdersStatus(ordersCode, userId, date, SystemConstants.CANCEL)
            > 0;
    if (ret) {
      // 保存工单记录
      saveWechatOrderRecord(orderId, date, "用户取消预约");
    }
    /*  重新生成受理单 查询之前受理单的数据*/
    wechatOrdersParam.setOrderTime(updateTime.substring(0, 13));
    wechatOrdersParam.setTel(wechatUser.getMobilePhone());
    // 调用csm接口，生成受理单
    Result result = wechatOrdersService.geneatorCode(wechatOrdersParam);
    if (Constant.SUCCESS != result.getReturnCode()) {
      throw new BizException("重新生成受理单失败");
    }
    String newOrdersCode = result.getData().toString();
    // 保存预约单
    WechatOrders newWechatOrders = Transformer.fromBean(wechatOrdersParam, WechatOrders.class);
    newWechatOrders.setUserId(userId);
    newWechatOrders.setOrdersCode(newOrdersCode);
    // 设置工单状态为重新处理中
    newWechatOrders.setStatus(SystemConstants.REDEAL);
    // 来源是微信
    newWechatOrders.setSource(SystemConstants.WEIXIN);
    newWechatOrders.setOrderTime(DateUtil.StringToDate(updateTime, "yyyy-MM-dd HH"));
    newWechatOrders.setCreateTime(date);
    newWechatOrders.setUpdateTime(date);
    newWechatOrders = wechatOrdersService.saveOrders(newWechatOrders);
    if (newWechatOrders.getId() != null) {
      // 保存工单进度
      saveWechatOrderRecord(newWechatOrders.getId(), date, "用户重新下单");
      ordersProRelationsService.batchSave(orderId, newWechatOrders.getId());
      // 发送短信通知提醒
      String engineerId = wechatOrders.getQyhUserId();
      QyhUser qyhUser = wechatQyhUserService.getQyhUser(engineerId);
      String engineerPhone = qyhUser.getMobile();
      String orderType = OrderUtils.getServiceTypeName(wechatOrders.getOrderType());
      if (StringUtils.isNotEmpty(engineerId)) {
        String url = orderDetailUrl + "?userId=" + engineerId + "&ordersCode=" + ordersCode;
        String content =
            "工单服务时间更改通知！\n"
                + "    请注意，"
                + contacts
                + "用户已将预约的"
                + orderType
                + "服务的上门服务时间进行更改，该订单已关闭，待重新派单！\n"
                + "1、点击<a href='"
                + url
                + "'>【已完工单】</a>查看该工单详情！";
        String engineerMsgContent =
            "请注意，" + contacts + "用户已将预约的" + orderType + "服务的上门服务时间进行更改，该订单已关闭，待重新派单！";
        // 工程师端加入短信和模板通知队列
        addEngineerQueue(engineerId, content, engineerMsgContent, engineerPhone);
      }
      // 推送更新到小程序
      wechatOrdersService.updateMakeAppointment(ordersCode, newOrdersCode);
    }
    log.info("重新生成受理单,userId = [{}] , ordersCode = [{}]", userId, newOrdersCode);
    return success(ordersCode);
  }

  /**
   * 用户地址列表
   *
   * @return CenterServiceParam
   */
  @RequestMapping("/address/list")
  public Result listAddress(@RequestBody CenterServiceParam param, Page page) {
    WechatUser wechatUser = obtainWeChatUser(param);
    long count = wechatUserAddressService.getCountAddress(wechatUser.getUserId());
    if (count == 0) {
      return toEmptyPageMap();
    }
    List<WechatUserAddress> addressList =
        wechatUserAddressService.pageUserAddress(wechatUser.getUserId(), page);
    return toPageMap(addressList, count);
  }

  /**
   * 用户保存地址
   *
   * @param param {@link AddressSaveParam}
   * @return {@link Result}
   */
  @RequestMapping("/address/save")
  public Result saveAddress(@RequestBody AddressSaveParam param) {
    WechatUserAddress address = obtainWeChatUserAddress(param);
    // 保存地址信息
    boolean ret = wechatUserAddressService.saveAddress(address) > 0;
    if (!ret) {
      throw new BizException("地址保存失败");
    }
    // 异步保存地址信息到商城
    wechatUserAddressService.syncSaveAddressToQysc(address);
    return success(address);
  }

  /**
   * 用户修改地址
   *
   * @param param {@link AddressModifyParam}
   * @return {@link Result}
   */
  @RequestMapping("/address/modify")
  public Result modifyAddress(@RequestBody AddressModifyParam param) {
    WechatUserAddress address = obtainWeChatUserAddress(param);
    boolean ret = wechatUserAddressService.updateAddress(address) > 0;
    if (!ret) {
      throw new BizException("地址更新失败");
    }
    // 异步更新地址信息到商城
    wechatUserAddressService.syncUpdateAddressToQysc(address);
    return success(address);
  }

  /**
   * 用户删除地址
   *
   * @param param {@link AddressDeleteParam}
   * @return {@link Result}
   */
  @RequestMapping("/address/delete")
  public Result deleteAddress(@RequestBody AddressDeleteParam param) {
    WechatUser wechatUser = obtainWeChatUser(param);
    WechatUserAddress address = wechatUserAddressService.findAddress(param.getId());
    boolean ret =
        wechatUserAddressService.deleteUserAddress(wechatUser.getUserId(), param.getId()) > 0;
    if (!ret) {
      throw new BizException("地址已删除");
    }
    String aId = address.getfAid();
    if (StringUtils.isNotBlank(aId)) {
      // 异步删除地址信息到商城
      wechatUserAddressService.syncDelAddressToMiniApp(aId);
    }
    return success(address);
  }

  /**
   * 产品列表
   *
   * <p>绑定的产品
   *
   * @param param {@link CenterServiceParam}
   * @param page {@link Page}
   * @return {@link Result}
   */
  @RequestMapping("/product/list")
  public Result listProduct(
      @RequestBody CenterServiceParam param,
      Page page,
      HttpServletRequest request,
      HttpServletResponse response) {
    WechatUser wechatUser = obtainWeChatUser(param);
    String userId = wechatUser.getUserId();
    long count = productService.getCountByUserId(userId);
    if (count == 0) {
      return toEmptyPageMap();
    }
    List<Product> products = productService.pageProductsByUserId(userId, page);
    sameCodeProduct(products);
    // 写入用户的cookie
    //    writeCookie(userId, request, response);
    return toPageMap(products, count);
  }

  /**
   * 判断商城用户否关注了公众号
   * @param param
   * @return
   */
  @RequestMapping("/user/cancle")
  public Result isCancle(@RequestBody CenterServiceParam param){
    WechatUser wechatUser = obtainWeChatUser(param);
    return success(wechatUser);
  }
  /**
   * 保存工单进度
   *
   * @param orderId {@link Long}
   * @param date {@link Date}
   */
  private void saveWechatOrderRecord(Long orderId, Date date, String content) {
    WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
    wechatOrdersRecord.setOrderId(orderId);
    wechatOrdersRecord.setRecordTime(date);
    wechatOrdersRecord.setRecordContent(content);
    boolean ret = wechatOrdersRecordService.saveWechatOrdersRecord(wechatOrdersRecord) > 0;
    if (!ret) {
      throw new BizException("工单进度生成失败");
    }
  }

  /**
   * 检查是否有产品已经完工
   *
   * @param productVos {@link List<ProductVo>}
   * @param errorMsg {@link String}
   */
  private void checkProductFinish(List<ProductVo> productVos, String errorMsg) {
    // 如果有部分产品已经完工，提示用户无法更改用户时间
    for (ProductVo p : productVos) {
      if (Constant.COMPLETE == p.getStatus()) {
        throw new BizException(errorMsg);
      }
    }
  }

  /**
   * 给工程师发送短信和通知
   *
   * @param engineerId {@link String}
   * @param contacts {@link String}
   * @param serverType {@link String}
   * @param ordersCode {@link String}
   */
  private void sendMessageToQyhUser(
      String engineerId, String contacts, String serverType, String ordersCode) {
    if (StringUtils.isNotEmpty(engineerId)) {
      // 短信通知模板
      String engineerMsgContent =
          "请注意，" + contacts + "用户已取消" + serverType + "服务！您可进入“沁园”WX企业号查看该工单详情！";
      QyhUser qyhUser = wechatQyhUserService.getQyhUser(engineerId);
      // 公告通知模板
      String url = orderDetailUrl + "?userId=" + engineerId + "&ordersCode=" + ordersCode;
      String qyhUserMobile = (null != qyhUser) ? qyhUser.getMobile() : "";
      String content =
          "工单撤销通知！\n"
              + "    请注意，"
              + contacts
              + "用户已取消"
              + serverType
              + "服务！\n"
              + "1、点击<a href='"
              + url
              + "'>【待处理工单】</a>查看该工单详情！";
      // 加入短信和模板通知队列
      addEngineerQueue(engineerId, content, engineerMsgContent, qyhUserMobile);
    }
  }

  /**
   * 工程师端加入短信和模板通知队列
   *
   * @param engineerId {@link String}
   * @param content {@link String}
   * @param engineerMsgContent {@link String}
   * @param qyhUserMobile {@link String}
   */
  private void addEngineerQueue(
      String engineerId, String content, String engineerMsgContent, String qyhUserMobile) {
    QyhUserMsgVo qyhUserMsgVo = new QyhUserMsgVo();
    qyhUserMsgVo.setUserId(engineerId);
    qyhUserMsgVo.setContent(content);
    qyhUserMsgVo.setMsgContent(engineerMsgContent);
    qyhUserMsgVo.setQyhUserMobile(qyhUserMobile);
    EngineerQueue.getQueueInstance().add(qyhUserMsgVo);
  }

  /**
   * 发送短信和模板，保存预约进度信息
   *
   * @param wechatOrders {@link WechatOrders}
   * @param date {@link Date}
   */
  private void pushMessageAndSaveRecord(WechatOrders wechatOrders, Date date) {
    int orderType = wechatOrders.getOrderType();
    // 发送短信通知提醒
    String serverType = OrderUtils.getServiceTypeName(orderType);
    // 预约提交成功模板消息提醒
    wechatOrdersService.sendAppointmentTemplateMsg(wechatOrders.getOrdersCode(), serverType);
    // 保存工单进度
    saveWechatOrderRecord(wechatOrders.getId(), date, "用户下单");
  }

  /**
   * 从csm生成受理单
   *
   * @param wechatOrdersParamExt {@link OrderSaveParam}
   * @return {@link Result}
   */
  private Result generateOrderFromCsm(WechatOrdersParamExt wechatOrdersParamExt) {
    // 查询产品信息
    List<ProductVo> productVos =
        wechatOrdersService.getProductInfoById(wechatOrdersParamExt.getProductIds());
    wechatOrdersParamExt.setProducts(productVos);
    // 调用csm接口，生成受理单，随机生成csm单号
    Result result = wechatOrdersService.geneatorCode(wechatOrdersParamExt);
    if (Constant.SUCCESS != result.getReturnCode()) {
      throw new BizException(result.getReturnMsg());
    }
    return result;
  }

  /**
   * 封装地址参数
   *
   * @param param {@link CenterServiceParam}
   * @return {@link WechatUserAddress}
   */
  private WechatUserAddress obtainWeChatUserAddress(CenterServiceParam param) {
    WechatUser wechatUser = obtainWeChatUser(param);
    WechatUserAddress address = Transformer.fromBean(param, WechatUserAddress.class);
    address.setUserId(wechatUser.getUserId());
    if (StringUtils.isNotBlank(address.getContactsMobile())) {
      address.setContactsMobile(address.getContactsMobile().trim());
    }
    return address;
  }

  /**
   * 根据unionId获取微信用户
   *
   * @param param {@link CenterServiceParam}
   * @return {@link WechatUser}
   */
  private WechatUser obtainWeChatUser(CenterServiceParam param) {
    boolean checkSignature =
        SignUtil.checkSignature(param.getSignature(), param.getTimeStamp(), Constant.AUTH_KEY);
    if (!checkSignature) {
      throw new BizException("当前用户没有权限访问");
    }
    // 根据unionId查询用户
    WechatUser wechatUser = wechatUserService.getUserByFansUnionId(param.getUnionId());
    if (wechatUser == null) {
      throw new BizException("用户认证失败");
    }
    return wechatUser;
  }

  /**
   * 对于一个用户一种型号有多个产品的处理
   *
   * @param products List
   */
  private void sameCodeProduct(List<Product> products) {
    Map<String, Integer> map = new HashMap<>();
    for (int i = 0; i < products.size(); i++) {
      Product product = products.get(i);
      String key = product.getProductName() + product.getProductCode();
      Integer value = map.get(key);
      if (value != null) {
        map.put(key, value + 1);
        if (value == 2) {
          products
              .get(i - 1)
              .setProductName(StringUtils.join(1, "-", products.get(i - 1).getProductName()));
        }
        product.setProductName(StringUtils.join(value, "-", product.getProductName()));
      } else {
        map.put(key, 1);
      }
    }
  }

  /**
   * 写入cookie
   *
   * @param userId {@link String}
   * @param request {@link HttpServletRequest}
   * @param response {@link HttpServletResponse}
   */
  private void writeCookie(
      String userId, HttpServletRequest request, HttpServletResponse response) {
    String encode = Base64.encode(userId.getBytes());
    try {
      CookieUtil.writeCookie(
          request, response, WeChatConstants.SCRMAPP_USER, encode, Integer.MAX_VALUE);
    } catch (ServletException | IOException e) {
      log.error("用户写入cookie失败：", e);
      throw new BizException("用户写入cookie失败");
    }
  }
}
