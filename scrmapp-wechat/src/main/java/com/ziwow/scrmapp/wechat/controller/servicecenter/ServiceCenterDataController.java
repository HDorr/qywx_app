package com.ziwow.scrmapp.wechat.controller.servicecenter;

import com.ziwow.scrmapp.common.bean.pojo.WechatOrdersParam;
import com.ziwow.scrmapp.common.bean.vo.ProductVo;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.SystemConstants;
import com.ziwow.scrmapp.common.exception.BizException;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrders;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrdersRecord;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.utils.OrderUtils;
import com.ziwow.scrmapp.common.utils.Transformer;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.wechat.params.address.AddressDeleteParam;
import com.ziwow.scrmapp.wechat.params.address.AddressModifyParam;
import com.ziwow.scrmapp.wechat.params.address.AddressSaveParam;
import com.ziwow.scrmapp.wechat.params.common.CenterServiceParam;
import com.ziwow.scrmapp.wechat.params.order.OrderSaveParam;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUserAddress;
import com.ziwow.scrmapp.wechat.service.ProductService;
import com.ziwow.scrmapp.wechat.service.SmsMarketingService;
import com.ziwow.scrmapp.wechat.service.WechatOrderServiceFeeService;
import com.ziwow.scrmapp.wechat.service.WechatOrdersRecordService;
import com.ziwow.scrmapp.wechat.service.WechatOrdersService;
import com.ziwow.scrmapp.wechat.service.WechatUserAddressService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class ServiceCenterDataController {

  private static Logger log = LoggerFactory.getLogger(ServiceCenterDataController.class);

  private final ProductService productService;
  private final WechatUserService wechatUserService;
  private final WechatUserAddressService wechatUserAddressService;
  private final WechatOrdersService wechatOrdersService;
  private final WechatOrdersRecordService wechatOrdersRecordService;
  private final WechatOrderServiceFeeService wechatOrderServiceFeeService;
  private final SmsMarketingService smsMarketingService;

  @Autowired
  public ServiceCenterDataController(
      ProductService productService,
      WechatUserService wechatUserService,
      WechatUserAddressService wechatUserAddressService,
      WechatOrdersService wechatOrdersService,
      WechatOrdersRecordService wechatOrdersRecordService,
      WechatOrderServiceFeeService wechatOrderServiceFeeService,
      SmsMarketingService smsMarketingService) {
    this.productService = productService;
    this.wechatUserService = wechatUserService;
    this.wechatUserAddressService = wechatUserAddressService;
    this.wechatOrdersService = wechatOrdersService;
    this.wechatOrdersRecordService = wechatOrdersRecordService;
    this.wechatOrderServiceFeeService = wechatOrderServiceFeeService;
    this.smsMarketingService = smsMarketingService;
  }

  /**
   * 用户预约
   *
   * <p>提交预约生成受理单
   *
   * @param param {@link OrderSaveParam}
   * @return Result
   */
  @Transactional(rollbackFor = Exception.class)
  @RequestMapping("/order/save")
  public Result saveWeChatOrder(@RequestBody OrderSaveParam param) {
    WechatUser wechatUser = obtainWeChatUser(param.getUnionId());
    // 调用csm接口生成受理单
    Result result = generateOrderFromCsm(param, wechatUser.getMobilePhone());
    String webAppealNo = result.getData().toString();
    // 保存预约单
    Date date = new Date();
    WechatOrders wechatOrders = Transformer.fromBean(param, WechatOrders.class);
    wechatOrders.setUserId(wechatUser.getUserId());
    wechatOrders.setOrdersCode(webAppealNo);
    wechatOrders.setStatus(SystemConstants.ORDERS);
    wechatOrders.setCreateTime(date);
    wechatOrders.setUpdateTime(date);
    wechatOrders.setOrderTime(DateUtil.StringToDate(param.getOrderTime(), "yyyy-MM-dd HH"));
    wechatOrders.setSource(SystemConstants.WEIXIN);
    // 新增一单多产品接口
    wechatOrders = wechatOrdersService.saveOrdersMultiProduct(wechatOrders, param.getProductIds());
    if (wechatOrders.getId() == null) {
      throw new BizException("用户预约失败");
    }
    // 推送模板，保存工单记录
    pushMessageAndSaveRecord(wechatOrders, wechatUser, date);
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
   * @return Result
   */
  @RequestMapping("/order/list")
  public Result listWeChatOrder(@RequestBody CenterServiceParam param) {
    return null;
  }

  /**
   * 用户取消预约
   *
   * <p>取消预约同步到csm
   *
   * @return Result
   */
  @RequestMapping("/order/cancel")
  public Result removeWeChatOrder(@RequestBody CenterServiceParam param) {
    return null;
  }

  /**
   * 用户更改预约时间
   *
   * @return Result
   */
  @RequestMapping("/order/modify")
  public Result modifyWeChatOrder(@RequestBody CenterServiceParam param) {
    return null;
  }

  /**
   * 用户地址列表
   *
   * @return CenterServiceParam
   */
  @RequestMapping("/address/list")
  public Result listAddress(@RequestBody CenterServiceParam param) {
    WechatUser wechatUser = obtainWeChatUser(param.getUnionId());
    List<WechatUserAddress> addressList =
        wechatUserAddressService.findUserAddresList(wechatUser.getUserId());
    return success(addressList);
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
    //    wechatUserAddressService.syncSaveAddressToMiniApp(address);
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
    //    wechatUserAddressService.syncUpdateAddressToMiniApp(address);
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
    WechatUser wechatUser = obtainWeChatUser(param.getUnionId());
    WechatUserAddress address = wechatUserAddressService.findAddress(param.getAddressId());
    boolean ret =
        wechatUserAddressService.deleteAddress(wechatUser.getUserId(), param.getAddressId()) > 0;
    if (!ret) {
      throw new BizException("地址删除失败");
    }
    String aId = address.getfAid();
    if (StringUtils.isNotBlank(aId)) {
      // 异步删除地址信息到商城
      //      wechatUserAddressService.syncDelAddressToMiniApp(aId);
    }
    return success(address);
  }

  /**
   * 产品列表
   *
   * <p>绑定的产品
   *
   * @param param {@link CenterServiceParam}
   * @return {@link Result}
   */
  @RequestMapping("/product/list")
  public Result listProduct(@RequestBody CenterServiceParam param) {
    WechatUser wechatUser = obtainWeChatUser(param.getUnionId());
    List<Product> products = productService.getProductsByUserId(wechatUser.getUserId());
    sameCodeProduct(products);
    return success(products);
  }

  /**
   * 发送短信和模板，保存预约进度信息
   *
   * @param wechatOrders {@link WechatOrders}
   * @param wechatUser {@link WechatUser}
   * @param date {@link Date}
   */
  private void pushMessageAndSaveRecord(
      WechatOrders wechatOrders, WechatUser wechatUser, Date date) {
    int orderType = wechatOrders.getOrderType();
    // 发送短信通知提醒
    String serverType = OrderUtils.getServiceTypeName(orderType);
    // 预约提交成功模板消息提醒
    wechatOrdersService.sendAppointmentTemplateMsg(wechatOrders.getOrdersCode(), serverType);
    WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
    wechatOrdersRecord.setOrderId(wechatOrders.getId());
    wechatOrdersRecord.setRecordTime(date);
    wechatOrdersRecord.setRecordContent("用户下单");
    boolean ret = wechatOrdersRecordService.saveWechatOrdersRecord(wechatOrdersRecord) > 0;
    if (!ret) {
      throw new BizException("用户预约进度生成失败");
    }
  }

  /**
   * 从csm生成受理单
   *
   * @param param {@link OrderSaveParam}
   * @param mobile {@link String}
   * @return {@link Result}
   */
  private Result generateOrderFromCsm(OrderSaveParam param, String mobile) {
    WechatOrdersParam wechatOrdersParam = Transformer.fromBean(param, WechatOrdersParam.class);
    // 查询产品信息
    List<ProductVo> productVos = wechatOrdersService.getProductInfoById(param.getProductIds());
    wechatOrdersParam.setProducts(productVos);
    // 设置电话号码
    boolean telephoneBlank = StringUtils.isBlank(wechatOrdersParam.getContactsTelephone());
    wechatOrdersParam.setTel(
        telephoneBlank ? mobile : param.getContactsTelephone().replace("-", ""));
    // 调用csm接口，生成受理单，随机生成csm单号
    Result result = wechatOrdersService.geneatorCode(wechatOrdersParam);
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
    WechatUser wechatUser = obtainWeChatUser(param.getUnionId());
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
   * @param unionId {@link String}
   * @return {@link WechatUser}
   */
  private WechatUser obtainWeChatUser(String unionId) {
    // 根据unionId查询用户
    WechatUser wechatUser = wechatUserService.getUserByFansUnionId(unionId);
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
}
