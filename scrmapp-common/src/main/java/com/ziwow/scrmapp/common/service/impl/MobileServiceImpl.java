package com.ziwow.scrmapp.common.service.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.ziwow.scrmapp.common.constants.Constant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ziwow.scrmapp.common.redis.RedisService;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.utils.ChuangLanMsgSender;
import com.ziwow.scrmapp.common.utils.MobilePhoneUtil;
import com.ziwow.scrmapp.common.utils.RandomNumUtil;

/**
 * @包名 com.ziwow.scrmapp.api.core.user.service.impl
 * @文件名 MobileServiceImpl.java
 * @作者 john.chen
 * @创建日期 2017-2-14
 * @版本 V 1.0
 */
@Service
public class MobileServiceImpl implements MobileService {
    Logger logger = LoggerFactory.getLogger(MobileServiceImpl.class);

    @Value("${message.chuanglan.password}")
    private String msgPassword;

    @Value("${message.chuanglan.username}")
    private String msgUserName;

    @Value("${message.emay.username}")
    private String msgEmayUserName;

    @Value("${message.emay.password}")
    private String msgEmayPassword;

    @Value("${message.emay.qyh.username}")
    private String msgEmayQyhUserName;

    @Value("${message.emay.qyh.password}")
    private String msgEmayQyhPassword;

    @Value("${message.emay.marketing.username}")
    private String msgEmayMarketingUserName;

    @Value("${message.emay.marketing.password}")
    private String msgEmayMarketingPassword;

    @Resource
    RedisService redisService;

    @Override
    public boolean sendVerifyCode(String redisKey, String mobile) throws Exception {
        Random random = new Random();
        StringBuilder msgContent = new StringBuilder();
        StringBuilder sRand = new StringBuilder();
        msgContent.append("验证码:");
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(9);
            sRand.append(String.valueOf(RandomNumUtil.randomSequence2[index]));
            msgContent.append(String.valueOf(RandomNumUtil.randomSequence2[index]));
        }
        // 缓存验证码
        redisService.setKeyExpire(redisKey, sRand.toString(), (long) 30, TimeUnit.MINUTES);
        msgContent.append("，请于30分钟内输入，工作人员不会向您索取，请勿泄露。");
        // 调用接口发送短信
        String result_mt = ChuangLanMsgSender.batchSend(msgUserName, msgPassword, mobile, msgContent.toString());
        logger.info("返回结果:[{}],参数un=[{}],pw=[{}],mobile=[{}],msgcontent=[{}]", result_mt, msgUserName, msgPassword, mobile, msgContent);
        String rtncode = result_mt.split(",")[1].trim().substring(0, 1);
        if ("0".equals(rtncode)) {
            logger.info("创蓝注册短信发送手机:[{}]成功,返回值为:[{}],验证码为:[{}]", mobile, rtncode, sRand);
            // 记录发送短信的明细
            // mapper.insertMsgRegisterRecord(mobile, MessageType.REGISTER.getCode());
            return true;
        } else {
            logger.info("创蓝注册短信发送失败！返回值为：" + rtncode + "请查看webservice返回值对照表");
            return false;
        }
    }

    @Override
    public void sendContent(String mobile, String msgContent) throws Exception {
        if (StringUtils.isEmpty(mobile) || !MobilePhoneUtil.isPhoneLegal(mobile)) {
            return;
        }
        if (StringUtils.isEmpty(msgContent)) {
            return;
        }
        // 调用接口发送短信
        String result_mt = ChuangLanMsgSender.batchSend(msgUserName, msgPassword, mobile, msgContent);
        logger.info("方法:sendContent的返回结果:[{}],参数un=[{}],pw=[{}],mobile=[{}],msgcontent=[{}]", result_mt, msgUserName, msgPassword, mobile, msgContent);
        String rtncode = result_mt.split(",")[1].trim().substring(0, 1);
        if ("0".equals(rtncode)) {
            logger.info("创蓝业务短信发送手机:[{}]成功,返回值为:[{}]", mobile, rtncode);
            return;
        } else {
            logger.info("创蓝业务短信发送失败！返回值为：" + rtncode + "请查看webservice返回值对照表");
            return;
        }
    }

    @Override
    public boolean sendVerifyCodeByEmay(String redisKey, String mobile) throws Exception {
        Random random = new Random();
        StringBuilder msgContent = new StringBuilder();
        StringBuilder sRand = new StringBuilder();
        msgContent.append("【沁园集团】验证码:");
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(9);
            sRand.append(String.valueOf(RandomNumUtil.randomSequence2[index]));
            msgContent.append(String.valueOf(RandomNumUtil.randomSequence2[index]));
        }
        // 缓存验证码
        redisService.setKeyExpire(redisKey, sRand.toString(), (long) 30, TimeUnit.MINUTES);
        msgContent.append("，请于30分钟内输入，工作人员不会向您索取，请勿泄露。");

        // 调用接口发送短信
        String result = ChuangLanMsgSender.batchSendByEmay(msgEmayUserName, msgEmayPassword, mobile, msgContent.toString());

        if ("0".equals(result)) {
            logger.info("亿美注册短信发送手机:[{}]成功,返回值为:[{}],验证码为:[{}]", mobile, result, sRand);
            // 记录发送短信的明细
            // mapper.insertMsgRegisterRecord(mobile, MessageType.REGISTER.getCode());
            return true;
        } else {
            logger.info("亿美注册短信发送失败！返回值为：" + result + "请查看webservice返回值对照表");
            return false;
        }
    }

    @Override
    public boolean sendContentByEmay(String mobile, String msgContent, int type) throws Exception {
        if (StringUtils.isEmpty(mobile) || !MobilePhoneUtil.isPhoneLegal(mobile)) {
            return false;
        }
        if ("17189330178".equals(mobile)) {
            return false;
        }
        if (StringUtils.isEmpty(msgContent)) {
            return false;
        }
        String username = StringUtils.EMPTY;
        String password = StringUtils.EMPTY;
        String content = StringUtils.EMPTY;
        content = "【沁园集团】".concat(msgContent);
        if (Constant.CUSTOMER == type) {
            username = msgEmayUserName;
            password = msgEmayPassword;
        } else if (Constant.ENGINEER == type) {
            username = msgEmayQyhUserName;
            password = msgEmayQyhPassword;
        } else if(Constant.MARKETING == type) {
            username = msgEmayMarketingUserName;
            password = msgEmayMarketingPassword;
            content = content.concat("回复TD退订");
        } else {
            return false;
        }

        // 调用接口发送短信
        String result = ChuangLanMsgSender.batchSendByEmay(username, password, mobile, content);
        if ("0".equals(result)) {
            logger.info("亿美业务短信发送手机:[{}]成功,返回值为:[{}]", mobile, result);
            return true;
        } else {
            logger.info("亿美业务短信发送失败！返回值为：" + result + "请查看webservice返回值对照表");
            return false;
        }
    }
}