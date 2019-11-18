package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.enums.EwCardSendTypeEnum;
import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.utils.EwCardUtil;
import com.ziwow.scrmapp.wechat.controller.EwCardController;
import com.ziwow.scrmapp.wechat.service.EwCardActivityService;
import com.ziwow.scrmapp.wechat.service.GrantEwCardRecordService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

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

    @Autowired
    private GrantEwCardRecordService grantEwCardRecordService;

    @Autowired
    private WechatUserService wechatUserService;

    @Transactional(rollbackFor = Exception.class)
    public boolean grantEwCard(String mobile, EwCardTypeEnum type, String msg, EwCardSendTypeEnum sendType){
        String cardNo = ewCardActivityService.selectCardNo(type);
        if (cardNo == null){
            logger.error("延保卡资源不足,手机号码为{}",mobile);
            XxlJobLogger.log("延保卡资源不足,手机号码为{}",mobile);
            return false;
        }
        if(!sendType.equals(EwCardSendTypeEnum.GIFT)){
            if (grantEwCardRecordService.selectReceiveRecordByPhone(mobile)){
                logger.error("该手机号已发放,手机号码为{}",mobile);
                XxlJobLogger.log("该手机号已发放,手机号码为{}",mobile);
                //修改发送标识，但是不加发送时间(用以区分)
                grantEwCardRecordService.updateSendNoTimeByPhone(mobile,true,sendType);
                return false;
            }
            if (wechatUserService.getUserByMobilePhone(mobile) != null){
                logger.error("该用户是微信会员，手机号码为:{}",mobile);
                XxlJobLogger.log("该用户是微信会员，手机号码为:{}",mobile);
                return false;
            }
        }else {
            if (grantEwCardRecordService.isGrantCard(mobile)){
                logger.error("赠送延保卡，该用户已经完工发放过，手机号码为:{}",mobile);
                System.out.println("赠送延保卡，该用户已经完工发放过，手机号码为 = " + mobile);
                XxlJobLogger.log("赠送延保卡，该用户已经完工发放过:{}",mobile);
                return false;
            }
        }
        final String mask = EwCardUtil.getMask();
        grantEwCardRecordService.addMaskByMobile(mask,mobile,sendType);
        try {
            //发短信
            final String msgContent = MessageFormat.format(msg, type.getPrice(), mask);
            mobileService.sendContentByEmay(mobile,msgContent, Constant.MARKETING);
        } catch (Exception e) {
            logger.error("发送短信失败，手机号码为:{},错误信息为:{}",mobile,e);
            XxlJobLogger.log("发送短信失败，手机号码为:{},错误信息为:{}",mobile,e);
            return false;
        }
        //增加发送时间
        grantEwCardRecordService.updateSendByPhone(mobile,true,sendType);
        XxlJobLogger.log("发送短信成功，手机号码为:{}",mobile);
        return true;
    }
}
