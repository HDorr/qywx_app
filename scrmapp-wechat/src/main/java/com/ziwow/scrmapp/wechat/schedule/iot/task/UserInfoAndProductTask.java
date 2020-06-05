package com.ziwow.scrmapp.wechat.schedule.iot.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.iot.IotEquipmentInfo;
import com.ziwow.scrmapp.common.iot.IotUserInfo;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步iot系统用户信息以及设备信息
 */
@Component
@JobHandler("userInfoAndProductTask")
public class UserInfoAndProductTask extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoAndProductTask.class);

    @Autowired
    private IotUserInfoService iotUserInfoService;

    @Autowired
    private IotEquipmentInfoService iotEquipmentInfoService;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ThirdPartyService thirdPartyService;

    @Override
    public ReturnT<String> execute(String param) {
        //拉取用户信息
        List<IotUserInfo> userInfos = thirdPartyService.getIotUserInfo(10);
        if (CollectionUtils.isNotEmpty(userInfos)){
            //存储到本地
            iotUserInfoService.saveUserInfos(userInfos);
        }
        //拉取设备信息
        List<IotEquipmentInfo> equipmentInfos = thirdPartyService.getIotEquipmentInfo(10);
        if (CollectionUtils.isNotEmpty(equipmentInfos)){
            //同步
            iotEquipmentInfoService.saveEquipmentInfos(equipmentInfos);
            for (IotEquipmentInfo equipmentInfo : equipmentInfos) {
                try {
                    bindUserAndProduct(equipmentInfo);
                } catch (Exception e) {
                    logger.error("iot同步用户信息，绑定产品信息出错。产品条码为:[{}],错误信息为[{}]",equipmentInfo.getSncode(),e);
                }
            }
        }
        return ReturnT.SUCCESS;
    }

    private void bindUserAndProduct(IotEquipmentInfo equipmentInfo) throws Exception {
        String unionId = equipmentInfo.getUnionId();
        if (StringUtils.isBlank(unionId)){
            XxlJobLogger.log("iot设备信息对应unionid为空,sncode为:[{}]",equipmentInfo.getSncode());
            logger.error("iot设备信息对应unionid为空,sncode为:[{}]",equipmentInfo.getSncode());
            return;
        }
        //判断是否已经注册过
        WechatUser wechatUser = wechatUserService.getUserByFansUnionIdIgnoreIsCancel(unionId);
        //已经注册
        if (wechatUser != null) {
            //查询用户是否已经绑定了产品
            Product product = productService.getProductsByBarCodeAndUserId(wechatUser.getUserId(), StringUtils.substring(equipmentInfo.getSncode(),5));
            //没有则进行产品绑定
            if (product == null) {
                //产品自动绑定
                productService.bindProduct(wechatUser.getUserId(),equipmentInfo.getSncode());
            }
        }else {
            //未进行注册
            IotUserInfo userInfo = iotUserInfoService.queryUserInfoBySncode(equipmentInfo.getSncode());
            if (userInfo == null){
                XxlJobLogger.log("iot自动注册，根据sncode无法找到对应的iot用户信息，sncode为:[{}]",equipmentInfo.getSncode());
                logger.error("iot自动注册，根据sncode无法找到对应的iot用户信息，sncode为:[{}]",equipmentInfo.getSncode());
                return;
            }
            //完成注册逻辑
            String userId = wechatUserService.autoRegister(userInfo.getPhone(),unionId,1001);
            //产品自动绑定
            productService.bindProduct(userId,equipmentInfo.getSncode());
        }
    }
}
