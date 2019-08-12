package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.utils.EwCardUtil;
import com.ziwow.scrmapp.wechat.controller.EwCardController;
import com.ziwow.scrmapp.wechat.service.EwCardActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author songkaiqi
 * @since 2019/08/12/上午10:10
 */
public abstract class AbstractGrantEwCard extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(EwCardController.class);

    @Autowired
    private EwCardActivityService ewCardActivityService;

    @Autowired
    private MobileService mobileService;

    @Transactional(rollbackFor = Exception.class)
    public boolean grantEwCard(String mobile, EwCardTypeEnum type){
        String cardNo = ewCardActivityService.selectCardNo(type);
        if (cardNo == null){
            logger.error("延保卡资源不足,手机号码为{}",mobile);
            XxlJobLogger.log("延保卡资源不足,手机号码为{}",mobile);
            return false;
        }
        final String mask = EwCardUtil.getMask();
        ewCardActivityService.addMaskByCardNo(cardNo, mask);
        try {
            //发短信
            String msgContent = "您近期预约的服务已完成。恭喜您获得限时免费的一年延保卡。您的延保卡号为:【".concat(mask).concat("】。\n" +
                    "\n" +
                    "使用方式：关注沁园公众号-【我的沁园】-【个人中心】-【延保服务】-【领取卡券】，复制券码并绑定至您的机器，即可延长一年质保（点击券码可直接复制）！\n" +
                    "\n" +
                    "卡券码有效期7天，请尽快使用");
            mobileService.sendContentByEmay(mobile,msgContent, Constant.MARKETING);
        } catch (Exception e) {
            logger.error("发送短信失败，手机号码为:{},错误信息为:{}",mobile,e);
            XxlJobLogger.log("发送短信失败，手机号码为:{},错误信息为:{}",mobile,e);
            return false;
        }
        //增加发送时间
        ewCardActivityService.addSendTimeAndPhoneByCardNo(cardNo,mobile);
        return true;
    }

}
