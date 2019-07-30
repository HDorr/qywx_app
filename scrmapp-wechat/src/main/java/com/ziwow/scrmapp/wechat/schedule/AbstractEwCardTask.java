package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.bean.pojo.CSMEwCardParam;
import com.ziwow.scrmapp.common.bean.pojo.ProductParam;
import com.ziwow.scrmapp.common.bean.vo.WechatOrdersVo;
import com.ziwow.scrmapp.common.bean.vo.csm.BaseCardVo;
import com.ziwow.scrmapp.common.bean.vo.csm.ProductItem;
import com.ziwow.scrmapp.common.constants.ErrorCodeConstants;
import com.ziwow.scrmapp.common.enums.EwCardStatus;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.wechat.controller.EwCardController;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUserAddress;
import com.ziwow.scrmapp.wechat.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author songkaiqi
 * @since 2019/07/30/上午10:05
 */
public abstract class AbstractEwCardTask extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(EwCardController.class);

    private EwCardService ewCardService;

    private WechatOrdersService wechatOrdersService;

    private WechatUserAddressService wechatUserAddressService;

    private ThirdPartyService thirdPartyService;

    private ProductService productService;

    private WechatUserService wechatUserService;

    private WechatFansService wechatFansService;

    public AbstractEwCardTask(EwCardService ewCardService, WechatOrdersService wechatOrdersService, WechatUserAddressService wechatUserAddressService, ThirdPartyService thirdPartyService, ProductService productService, WechatUserService wechatUserService, WechatFansService wechatFansService) {
        this.ewCardService = ewCardService;
        this.wechatOrdersService = wechatOrdersService;
        this.wechatUserAddressService = wechatUserAddressService;
        this.thirdPartyService = thirdPartyService;
        this.productService = productService;
        this.wechatUserService = wechatUserService;
        this.wechatFansService = wechatFansService;
    }

    public void registerEwCard(EwCard ewCard){
        try {
            XxlJobLogger.log("产品条码=====" + ewCard.getProductBarCodeTwenty());
            XxlJobLogger.log("延保卡号=====" + ewCard.getCardNo());
            final WechatUser wechatUser = wechatUserService.getUserByOpenId(wechatFansService.getWechatFansById(ewCard.getFansId()).getOpenId());
            XxlJobLogger.log("userId =====" + wechatUser.getUserId());
            final Product product = productService.getProductsByBarCode(ewCard.getProductBarCodeTwenty());
            ProductItem productItem = thirdPartyService.getProductItem(new ProductParam(product.getModelName(), product.getProductBarCode()));
            final BaseCardVo baseCardVo = thirdPartyService.registerEwCard(getCsmEwCardParam(ewCard.getCardNo(), wechatUser, productItem, product.getId(), ewCard.getPurchDate()));
            if (baseCardVo.getStatus().getCode().equals(ErrorCodeConstants.CODE_E0)){
                //修改资产状态为使用
                ewCardService.updateCardStatus(ewCard.getCardNo(), EwCardStatus.ENTERED_INTO_FORCE);
                XxlJobLogger.log("注册成功");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            XxlJobLogger.log(e);
            XxlJobLogger.log("推送失败，产品条码为+",ewCard.getProductBarCodeTwenty(),"延保卡号为"+ewCard.getCardNo());
        }
    }

    /**
     * 组装延保卡基本信息
     * @param cardNo
     * @param wechatUser
     * @param productItem
     * @return
     */
    private CSMEwCardParam getCsmEwCardParam(String cardNo, WechatUser wechatUser, ProductItem productItem, Long productId, Date buyTime) {
        CSMEwCardParam CSMEwCardParam = new CSMEwCardParam();
        CSMEwCardParam.setCardNo(cardNo);
        //拼装产品所需信息
        CSMEwCardParam.setBarcode(productItem.getBarcode());
        CSMEwCardParam.setItemCode(productItem.getItemCode() == null ? "" : productItem.getItemCode());
        CSMEwCardParam.setSpec(productItem.getSpec() == null ? "" : productItem.getSpec());
        CSMEwCardParam.setPurchDate(buyTime);
        //获取安装时间
        CSMEwCardParam.setInstallTime("");
        final List<WechatOrdersVo> wechatOrdersVos = wechatOrdersService.getWechatOrdersByProductId(productId);
        for (WechatOrdersVo wechatOrdersVo : wechatOrdersVos) {
            //如果是安装订单并且完成安装
            if (wechatOrdersVo.getOrderType() == 1 && (wechatOrdersVo.getStatus() == 5 || wechatOrdersVo.getStatus() == 6)){
                CSMEwCardParam.setInstallTime(wechatOrdersVo.getEndTime());
                break;
            }
        }

        //用户信息
        CSMEwCardParam.setMobile(wechatUser.getMobilePhone() == null ? "" : wechatUser.getMobilePhone());
        CSMEwCardParam.setEnduserName(wechatUser.getUserName() == null ? "" : wechatUser.getUserName());
        //不传电话,但是字段要有
        CSMEwCardParam.setTel("");
        //省市区
        WechatUserAddress address = null;
        final List<WechatUserAddress> userAddresList = wechatUserAddressService.findUserAddresList(wechatUser.getUserId());
        if (CollectionUtils.isEmpty(userAddresList)){
            address = new WechatUserAddress();
        }else {
            address = userAddresList.get(0);
        }
        CSMEwCardParam.setProvinceName(address.getProvinceName() == null ? "" : address.getProvinceName());
        CSMEwCardParam.setCityName(address.getCityName() == null ? "" : address.getCityName());
        CSMEwCardParam.setCountyName(address.getAreaName() == null ? "" : address.getAreaName());
        //取的街道地址
        CSMEwCardParam.setEnduserAddress(address.getStreetName() == null ? "" : address.getStreetName());
        return CSMEwCardParam;
    }

}
