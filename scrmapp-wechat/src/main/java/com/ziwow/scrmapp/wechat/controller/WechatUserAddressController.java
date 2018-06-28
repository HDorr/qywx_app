package com.ziwow.scrmapp.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.tools.thirdParty.SignUtil;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.CookieUtil;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUserAddress;
import com.ziwow.scrmapp.wechat.service.WechatUserAddressService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xiaohei on 2017/3/9.
 */
@Controller
@RequestMapping(value = "/scrmapp/consumer")
public class WechatUserAddressController {
    private final Logger logger = LoggerFactory.getLogger(WechatUserAddressController.class);


    /**
     * 获取某位用户所有地址
     *
     * @param request
     * @param response
     * @return
     * @see com.ziwow.scrmapp.wechat.persistence.entity.WechatUser#userId
     */
    @RequestMapping(value = "/wechatuser/addressList/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result getAddressList(HttpServletRequest request, HttpServletResponse response) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));

            if (!wechatUserService.checkUser(userId)) {
                logger.error("获取用户地址失败，用户不存在 userId = [{}]", userId);
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请重新操作！");
                return result;
            }
            List<WechatUserAddress> wechatUserAddresses = userAddressService.findUserAddresList(userId);
            result.setData(wechatUserAddresses);
            result.setReturnCode(Constant.SUCCESS);
            logger.debug("地址列表获取成功,userId = [{}]", userId);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("列表列表获取失败，请重试!");
            logger.error("地址列表获取失败, 原因[{}]", e);
        }

        return result;
    }

    /**
     * 获取具体地址信息
     *
     * @param request
     * @param response
     * @param addressId
     * @return
     */
    @RequestMapping(value = "/wechatuser/address/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result getAddress(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Long addressId) {
        Result result = new BaseResult();
        try {
//            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
//            String userId = new String(Base64.decode(encode));
//
//            if (!wechatUserService.checkUser(userId)) {
//                logger.error("获取用户具体地址信息失败，cookie中userId错误!");
//                result.setReturnCode(Constant.FAIL);
//                result.setReturnMsg("用户无效，请重新操作！");
//                return result;
//            }

            WechatUserAddress wechatUserAddresse = userAddressService.findAddress(addressId);
            result.setData(wechatUserAddresse);
            result.setReturnCode(Constant.SUCCESS);
            logger.debug("地址数据获取成功,addressId = [{}]", addressId);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("数据获取失败，请重试!");
            logger.error("地址数据获取失败,addressId = [{}],原因[{}]", addressId, e);
        }

        return result;
    }


    /**
     * 保存用户地址信息
     *
     * @param request
     * @param response
     * @param contacts
     * @param contactsMobile
     * @param provinceId
     * @param provinceName
     * @param cityId
     * @param cityName
     * @param areaId
     * @param areaName
     * @param streetName
     * @param isDefault
     * @param fixedTelephone
     * @return
     */
    @RequestMapping(value = "/wechatuser/address/save", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result saveAddress(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam("contacts") String contacts,
                              @RequestParam("contactsMobile") String contactsMobile,
                              @RequestParam("provinceId") Integer provinceId,
                              @RequestParam("provinceName") String provinceName,
                              @RequestParam("cityId") Integer cityId,
                              @RequestParam("cityName") String cityName,
                              @RequestParam("areaId") Integer areaId,
                              @RequestParam("areaName") String areaName,
                              @RequestParam("streetName") String streetName,
                              @RequestParam(value = "fixedTelephone", required = false) String fixedTelephone,
                              @RequestParam(value = "isDefault", required = false, defaultValue = "2") Integer isDefault) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));

            //没有查到用户
            if (!wechatUserService.checkUser(userId)) {
                logger.error("保存地址失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }
            //实例化地址对象
            WechatUserAddress wechatUserAddress = new WechatUserAddress();
            wechatUserAddress.setUserId(userId);
            wechatUserAddress.setContacts(contacts);
            wechatUserAddress.setContactsMobile(contactsMobile.trim());
            wechatUserAddress.setProvinceId(provinceId);
            wechatUserAddress.setProvinceName(provinceName);
            wechatUserAddress.setCityId(cityId);
            wechatUserAddress.setCityName(cityName);
            wechatUserAddress.setAreaId(areaId);
            wechatUserAddress.setAreaName(areaName);
            wechatUserAddress.setStreetName(streetName);
            wechatUserAddress.setIsDefault(isDefault);
            wechatUserAddress.setFixedTelephone(fixedTelephone);

            //保存地址信息
            userAddressService.saveAddress(wechatUserAddress);
            userAddressService.syncSaveAddressToMiniApp(wechatUserAddress);
            logger.debug("地址信息保存成功,userId = [{}]", userId);
            result.setReturnCode(Constant.SUCCESS);
            result.setReturnMsg("保存成功!");
        } catch (Exception e) {
            logger.error("保存地址信息失败,原因[{}]", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("保存失败!");
        }
        return result;
    }

    /**
     * 同步用户地址信息
     *
     * @param timestamp
     * @param signture
     * @param unionId
     * @param aId
     * @param contacts
     * @param contactsMobile
     * @param provinceId
     * @param provinceName
     * @param cityId
     * @param cityName
     * @param areaId
     * @param areaName
     * @param streetName
     * @return
     */
    @RequestMapping(value = "/wechatuser/syncAddress", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result syncAddressFromMiniApp(@RequestParam("timestamp") String timestamp,
                                         @RequestParam("signture") String signture,
                                         @RequestParam("unionId") String unionId,
                                         @RequestParam("id") String id,
                                         @RequestParam(required = false, value = "aId") Long aId,
                                         @RequestParam("contacts") String contacts,
                                         @RequestParam("contactsMobile") String contactsMobile,
                                         @RequestParam("provinceId") Integer provinceId,
                                         @RequestParam("provinceName") String provinceName,
                                         @RequestParam("cityId") Integer cityId,
                                         @RequestParam("cityName") String cityName,
                                         @RequestParam("areaId") Integer areaId,
                                         @RequestParam("areaName") String areaName,
                                         @RequestParam("streetName") String streetName,
                                         @RequestParam(value = "isDefault", required = false, defaultValue = "2") Integer isDefault) {
        logger.info("同步小程序地址参数,unionId:{},id:{},aId:{},contacts:{},contactsMobile:{},provinceId:{},cityId:{},areaId:{}",
                unionId, id, aId, contacts, contactsMobile, provinceId, cityId, areaId);
        Result result = new BaseResult();
        try {
            boolean isLegal = SignUtil.checkSignature(signture, timestamp, Constant.AUTH_KEY);
            if (!isLegal) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg(Constant.ILLEGAL_REQUEST);
                return result;
            }
            if (StringUtils.isEmpty(contacts) || StringUtils.isEmpty(contactsMobile) ||
                    StringUtils.isEmpty(streetName) || StringUtils.isEmpty(unionId)) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("请求参数有误，请检查后再试！");
                return result;
            }

            WechatUser wechatUser = wechatUserService.getUserByUnionid(unionId);
            //没有查到用户
            if (null == wechatUser) {
                logger.error("保存地址失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }
            String userId = wechatUser.getUserId();
            //实例化地址对象
            WechatUserAddress wechatUserAddress = new WechatUserAddress();
            wechatUserAddress.setUserId(userId);
            wechatUserAddress.setContacts(contacts);
            wechatUserAddress.setContactsMobile(contactsMobile.trim());
            wechatUserAddress.setProvinceId(provinceId);
            wechatUserAddress.setProvinceName(provinceName);
            wechatUserAddress.setCityId(cityId);
            wechatUserAddress.setCityName(cityName);
            wechatUserAddress.setAreaId(areaId);
            wechatUserAddress.setAreaName(areaName);
            wechatUserAddress.setStreetName(streetName);
            wechatUserAddress.setIsDefault(isDefault);
            wechatUserAddress.setfAid(id);
            if (null == aId) {
                userAddressService.saveAddress(wechatUserAddress);
                logger.info("同步地址信息成功,userId:{}", userId);
                result.setReturnCode(Constant.SUCCESS);
                result.setData(wechatUserAddress.getId());
                result.setReturnMsg("保存成功!");
            } else {
                wechatUserAddress.setId(aId);
                userAddressService.updateAddressSelective(wechatUserAddress);
                logger.info("地址信息修改成功,userId:{}, addressId:{}", wechatUserAddress.getUserId(), wechatUserAddress.getId());
                result.setReturnCode(Constant.SUCCESS);
                result.setData(aId);
                result.setReturnMsg("修改成功！");
            }
        } catch (Exception e) {
            logger.error("同步地址信息失败,参数:unionId={}", unionId, e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("同步地址信息失败!");
        }
        return result;
    }

    /**
     * 修改地址信息
     *
     * @param request
     * @param response
     * @param wechatUserAddress
     * @return
     */
    @RequestMapping(value = "/wechatuser/address/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result updateAddress(HttpServletRequest request, HttpServletResponse
            response, @ModelAttribute WechatUserAddress wechatUserAddress) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));

            //用户id不存在
            if (!wechatUserService.checkUser(userId)) {
                logger.error("修改地址失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }
            wechatUserAddress.setUserId(userId);
            wechatUserAddress.setContactsMobile(wechatUserAddress.getContactsMobile().trim());
            logger.info("修改db地址信息参数,wechatUserAddress:{}", JSON.toJSON(wechatUserAddress));
            int count = userAddressService.updateAddress(wechatUserAddress);
            userAddressService.syncUpdateAddressToMiniApp(wechatUserAddress);
            if (count > 0) {
                logger.debug("地址信息修改成功,userId = [{}] , addressId = [{}]", wechatUserAddress.getUserId(), wechatUserAddress.getId());
                result.setReturnCode(Constant.SUCCESS);
                result.setReturnMsg("修改成功！");
            }
        } catch (Exception e) {
            logger.error("修改失败，原因", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("修改失败!");
        }
        return result;
    }


    /**
     * 逻辑删除地址信息
     *
     * @param request
     * @param response
     * @param addressId
     * @return
     */
    @RequestMapping(value = "/wechatuser/address/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result deleteAddress(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Long
            addressId) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));

            //用户id不存在
            if (!wechatUserService.checkUser(userId)) {
                logger.error("删除地址失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }

            //受影响行数
            WechatUserAddress address = userAddressService.findAddress(addressId);
            int count = userAddressService.deleteAddress(userId, addressId);
            // 异步同步给小程序
            String aId = address.getfAid();
            logger.info("删除小程序的用户默认地址id:{}", aId);
            if (StringUtils.isNotBlank(aId)) {
                userAddressService.syncDelAddressToMiniApp(aId);
            }
            if (count > 0) {
                logger.debug("地址删除成功,userId = [{}] , addressId = [{}]", userId, addressId);
                result.setReturnCode(Constant.SUCCESS);
                result.setReturnMsg("删除成功！");
            } else {
                logger.error("地址删除失败,userId = [{}] , addressId = [{}]", userId, addressId);
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("删除失败，地址无效！");
            }
        } catch (Exception e) {
            logger.error("地址删除失败,原因", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("删除失败！");
        }

        return result;
    }

    /**
     * 同步小程序删除用户地址信息
     *
     * @param timestamp
     * @param signture
     * @param aId
     * @return
     */
    @RequestMapping(value = "/wechatuser/syncAddressDel", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result syncAddressDel(@RequestParam("timestamp") String timestamp,
                                 @RequestParam("signture") String signture,
                                 @RequestParam("aId") Long aId) {
        logger.info("同步小程序删除地址参数,aId:{}", aId);
        Result result = new BaseResult();
        try {
            boolean isLegal = SignUtil.checkSignature(signture, timestamp, Constant.AUTH_KEY);
            if (!isLegal) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg(Constant.ILLEGAL_REQUEST);
                return result;
            }

            //地址id不存在
            if (null == userAddressService.findAddress(aId)) {
                logger.error("删除地址失败，未找到地址id:{}的记录", aId);
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("地址id无效，请退出重新操作！");
                return result;
            }
            //逻辑删除
            userAddressService.delAddressById(aId);
            logger.info("地址删除成功,addressId = [{}]", aId);
            result.setReturnCode(Constant.SUCCESS);
            result.setReturnMsg("删除成功！");
        } catch (Exception e) {
            logger.error("地址删除失败,原因:", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("删除失败！");
        }
        return result;
    }

    /**
     * 设置默认地址
     *
     * @param request
     * @param response
     * @param addressId
     * @return
     */
    @RequestMapping(value = "/wechatuser/address/setDefault", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result setDefault(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Long
            addressId) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));

            //用户不存在
            if (!wechatUserService.checkUser(userId)) {
                logger.error("设置默认地址失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }
            boolean re = userAddressService.updateDefault(userId, addressId);
            // 异步同步给小程序
            WechatUserAddress wechatUserAddress = userAddressService.findAddress(addressId);
            String aId = wechatUserAddress.getfAid();
            logger.info("设置小程序的用户默认地址id:{}", aId);
            if (StringUtils.isNotBlank(aId)) {
                userAddressService.syncSetDefaultAddressToMiniApp(aId);
            }
            if (re) {
                logger.debug("设置默认地址成功,userId = [{}] , addressId = [{}]", userId, addressId);
                result.setReturnCode(Constant.SUCCESS);
                result.setReturnMsg("设置成功！");
            }
        } catch (Exception e) {
            logger.error("设置默认地址失败,原因", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("设置失败！");
        }

        return result;
    }

    /**
     * 同步小程序设置默认地址
     *
     * @param timestamp
     * @param signture
     * @param aId
     * @return
     */
    @RequestMapping(value = "/wechatuser/syncSetDefault", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result syncSetDefault(@RequestParam("timestamp") String timestamp,
                                 @RequestParam("signture") String signture,
                                 @RequestParam("aId") Long aId) {
        logger.info("同步小程序删除地址参数,aId:{}", aId);
        Result result = new BaseResult();
        try {
            boolean isLegal = SignUtil.checkSignature(signture, timestamp, Constant.AUTH_KEY);
            if (!isLegal) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg(Constant.ILLEGAL_REQUEST);
                return result;
            }

            //地址id不存在
            WechatUserAddress userAddress = userAddressService.findAddress(aId);
            if (null == userAddress) {
                logger.error("同步设置默认地址失败，未找到地址id:{}的记录", aId);
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("地址id无效，请退出重新操作！");
                return result;
            }

            boolean re = userAddressService.updateDefault(userAddress.getUserId(), aId);
            if (re) {
                logger.info("同步设置默认地址成功,addressId = [{}]", aId);
                result.setReturnCode(Constant.SUCCESS);
                result.setReturnMsg("设置成功！");
            }
        } catch (Exception e) {
            logger.error("设置默认地址失败,原因", e);
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("设置失败！");
        }

        return result;
    }

    /**
     * 跳转到地址列表
     *
     * @return
     */
    @RequestMapping(value = "/wechatuser/address/index")
    public ModelAndView toAddressIndex() {
        ModelAndView modelAndView = new ModelAndView("/addressManage/addressList");
        return modelAndView;
    }


    @Autowired
    private WechatUserAddressService userAddressService;

    @Autowired
    private WechatUserService wechatUserService;
}
