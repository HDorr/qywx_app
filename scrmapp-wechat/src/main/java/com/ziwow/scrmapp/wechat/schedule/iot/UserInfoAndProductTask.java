package com.ziwow.scrmapp.wechat.schedule.iot;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.ziwow.scrmapp.common.iot.IotEquipmentInfo;
import com.ziwow.scrmapp.common.iot.IotUserInfo;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.tools.utils.UniqueIDBuilder;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.*;
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

    @Autowired
    private IotUserInfoService iotUserInfoService;

    @Autowired
    private IotEquipmentInfoService iotEquipmentInfoService;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WechatOrdersService wechatOrdersService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {

        //拉取用户信息
        List<IotUserInfo> userInfos = new ArrayList<>();

        //存储到本地
        iotUserInfoService.saveUserInfos(userInfos);

        //拉取设备信息
        List<IotEquipmentInfo> equipmentInfos = new ArrayList<>();
        //同步
        iotEquipmentInfoService.saveEquipmentInfos(equipmentInfos);

        for (IotEquipmentInfo equipmentInfo : equipmentInfos) {
            String unionId = equipmentInfo.getUnionId();
            //判断是否已经注册过
            WechatUser wechatUser = wechatUserService.getUserByFansUnionIdIgnoreIsCancel(unionId);
            //已经注册
            if (wechatUser != null) {
                //查询用户是否已经绑定了产品
                Product product = productService.getProductsByBarCodeAndUserId(wechatUser.getUserId(), equipmentInfo.getSncode());
                //没有则进行产品绑定
                if (product == null) {
                    //产品自动绑定
                    productService.bindProduct(wechatUser.getUserId(),equipmentInfo.getSncode());
                }
            }else {
                //未进行注册
                IotUserInfo userInfo = iotUserInfoService.queryUserInfoBySncode(equipmentInfo.getSncode());
                //完成注册逻辑

                //产品自动绑定
                productService.bindProduct(wechatUser.getUserId(),equipmentInfo.getSncode());
            }

        }
        return ReturnT.SUCCESS;
    }


    private void re(IotUserInfo iotUserInfo,String unionId){
        WechatFans wechatFans = new WechatFans();
        wechatFans.setIsMember(2);
        wechatFans.setUnionId(unionId);

        // 设置用户信息
        String userId = UniqueIDBuilder.getUniqueIdValue();
        WechatUser wechatUser = new WechatUser();
        wechatUser.setUserId(userId);
        wechatUser.setMobilePhone(iotUserInfo.getPhone().trim());
        wechatUser.setRegisterSrc(1001);
        wechatUserService.syncUserFromMiniApp(wechatFans, wechatUser);

        // 异步同步该用户的历史产品信息
        productService.syncHistroyProductItemFromCemTemp(iotUserInfo.getPhone().trim(), userId);
        // 异步同步该用户的历史受理单信息
        wechatOrdersService.syncHistoryAppInfo(iotUserInfo.getPhone().trim(), userId);
    }

}
