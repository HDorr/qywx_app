package com.ziwow.scrmapp.wechat.controller.servicecenter;

import com.ziwow.scrmapp.common.exception.BizException;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.utils.Transformer;
import com.ziwow.scrmapp.wechat.params.address.AddressDeleteParam;
import com.ziwow.scrmapp.wechat.params.address.AddressModifyParam;
import com.ziwow.scrmapp.wechat.params.address.AddressSaveParam;
import com.ziwow.scrmapp.wechat.params.common.CenterServiceParam;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUserAddress;
import com.ziwow.scrmapp.wechat.service.ProductService;
import com.ziwow.scrmapp.wechat.service.WechatUserAddressService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @Autowired
  public ServiceCenterDataController(
      ProductService productService,
      WechatUserService wechatUserService,
      WechatUserAddressService wechatUserAddressService) {
    this.productService = productService;
    this.wechatUserService = wechatUserService;
    this.wechatUserAddressService = wechatUserAddressService;
  }

  /**
   * 用户预约
   *
   * <p>提交预约生成受理单
   *
   * @return Result
   */
  @RequestMapping("/order/save")
  public Result saveWeChatOrder(@RequestBody CenterServiceParam param) {

    return null;
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
   * 封装地址参数
   *
   * @param param {@link CenterServiceParam}
   * @return {@link WechatUserAddress}
   */
  private WechatUserAddress obtainWeChatUserAddress(CenterServiceParam param) {
    WechatUser wechatUser = obtainWeChatUser(param.getUnionId());
    WechatUserAddress address = Transformer.fromBean(param, WechatUserAddress.class);
    address.setUserId(wechatUser.getUserId());
    address.setContactsMobile(address.getContactsMobile().trim());
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
