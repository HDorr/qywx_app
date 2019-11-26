package com.ziwow.scrmapp.wechat.utils.impl;

import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.NoticeRosterService;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatTemplateService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import com.ziwow.scrmapp.wechat.utils.SendNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author songkaiqi
 * @since 2019/11/22/上午9:40
 */
@Service
public class SendNoticeImpl implements SendNotice {

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private WechatFansService wechatFansService;

    @Autowired
    private WechatTemplateService wechatTemplateService;

    @Autowired
    private NoticeRosterService noticeRosterService;

    private String cleanUrl = "/pages/selectProduct?appointmentType=clean";

    private String filterUrl = "/pages/selectProduct?appointmentType=filter";

    @Override
    public boolean sendNotice(String title, String remark, String type, List<String> param, String phone, boolean isClean) {
        if (noticeRosterService.isIncludeByPhone(phone)){
            XxlJobLogger.log("该用户已经发放过通知了,手机号为：[{}]",phone);
            return false;
        }
        try {
            final WechatUser user = wechatUserService.getUserByMobilePhone(phone);
            final WechatFans fans = wechatFansService.getWechatFansById(user.getWfId());
            wechatTemplateService.sendTemplate(fans.getOpenId(),isClean ? cleanUrl : filterUrl,param,type,true,title,remark);
        } catch (Exception e) {
            XxlJobLogger.log("发放通知失败，手机号：{}，错误：{}",phone,title);
            return false;
        }
        return true;
    }
}
