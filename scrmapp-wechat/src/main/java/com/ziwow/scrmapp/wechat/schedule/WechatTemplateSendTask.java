package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.enums.TemplateIdentityType;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatTemplateUserGroup;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatTemplateUserSrc;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatTemplateUserMapper;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatTemplateService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信通知模板发送
 * @author jitre
 * @since 2020年02月08日
 */
@Component
@JobHandler("wechatTemplateSendTask")
public class WechatTemplateSendTask extends IJobHandler {

    private static final Logger LOG = LoggerFactory.getLogger(WechatTemplateSendTask.class);

    @Autowired
    private WechatTemplateUserMapper templateUserMapper;
    /**
     * 分页的页面大小
     */
    final static int PAGE_SIZE = 100;

    private WechatTemplateService wechatTemplateService;

    @Autowired
    public void setWechatTemplateService(WechatTemplateService wechatTemplateService) {
        this.wechatTemplateService = wechatTemplateService;
    }

    private WechatFansService wechatFansService;

    @Autowired
    public void setWechatFansService(WechatFansService wechatFansService) {
        this.wechatFansService = wechatFansService;
    }

    private WechatUserService wechatUserService;

    @Autowired
    private void setWechatUserService(WechatUserService wechatUserService) {
        this.wechatUserService = wechatUserService;
    }


    @Override
    public ReturnT<String> execute(String s) throws Exception {
        final String[] str = s.split(",");
        final String groupName = str[0];
        final Integer total = Integer.valueOf(str[1]);
        if (StringUtils.isBlank(groupName) || total <= 0) {
            return ReturnT.FAIL;
        }
        final WechatTemplateUserGroup group = templateUserMapper.selectGroupByName(groupName);
        if (null == group) {
            LOG.info("人群包不存在-{}", groupName);
            XxlJobLogger.log("人群包不存在-{}", groupName);
            return ReturnT.FAIL;
        }
        LOG.info("人群包查询成功-{}", group.toString());
        XxlJobLogger.log("人群包查询成功-{}", group.toString());

        //计算发送次数
        final int pages = total / PAGE_SIZE + 1;
        for (int i = 0; i < pages; i++) {
            List<WechatTemplateUserSrc> users = templateUserMapper
                .selectUnsendTemplateUserSrc(group.getId(), PAGE_SIZE);
            if (CollectionUtils.isEmpty(users) || users.size() < PAGE_SIZE) {
                i = pages;//如果没有查询结果或结果小于一页，则说明已经发完了，无需继续循环
            }
            for (WechatTemplateUserSrc user : users) {
                try {
                    String openId = convertUserSrcToOpenId(user);
                    if (StringUtils.isBlank(openId)) {
                        LOG.error("发送通知模板出错，用户不存在,手机号为：{}", user.getIdentity());
                        XxlJobLogger.log("发送通知模板出错，用户不存在,手机号为：{}", user.getIdentity());
                    } else {
                        List<String> params = handleTemplateParam(group, user);
                        wechatTemplateService.sendTemplateByShortId(
                            openId,
                            StringUtils.isNotBlank(group.getUrl()) ? group.getUrl() : "",
                            params,
                            group.getTemplateId(),
                            group.getToMini(),
                            group.getTitle(),
                            group.getRemark());
                        templateUserMapper.updateSendStatusById(user.getId());
                    }
                } catch (Exception e) {
                    LOG.info("人群包发送出错-{}", groupName, e);
                    XxlJobLogger.log("人群包发送出错-{}", groupName, e);
                }
            }
        }
        return ReturnT.SUCCESS;
    }

    /***
     * 提取userSrc对应的用户openid
     * userSrc的identity有两种类型：手机号和openid，如果类型是openid的则可以直接用于模板推送
     * 而若是手机号的话则需要先找到对应的openid
     * @param src
     * @return
     */
    String convertUserSrcToOpenId(WechatTemplateUserSrc src) {
        if (src.getIdentityType().equals(TemplateIdentityType.PHONE)) {
            // 通过手机号查询fansId
            WechatUser wechatUser = wechatUserService.getUserByMobilePhone(src.getIdentity());
            // 通过union查询用户的openId
            if (wechatUser == null) {
                return "";
            }
            WechatFans wechatFans = wechatFansService.getWechatFansById(wechatUser.getWfId());
            // 没有查到用户
            if (null == wechatFans) {
                return "";
            }
            return wechatFans.getOpenId();
        } else {
            //不是手机类型就是openid类型
            return src.getIdentity();
        }
    }

    /**
     * 根据人群包设置推送的模板参数 参数有两种情况：一种是全局统一参数(group中的centerParam为true)，则每个用户推送的时候用相同的模板参数
     * 还有一种是动态参数(group中的centerParam为false)，每个用户推送时的参数由自己的param1-3来决定
     */
    List<String> handleTemplateParam(WechatTemplateUserGroup group, WechatTemplateUserSrc src) {
        List<String> params = new ArrayList<>();
        if (group.getCenterParam()) {
            params.add(group.getPrarm1());
            if (group.getParamCount() >= 2) {
                params.add(group.getPrarm2());
            if (group.getParamCount() >= 3) {
                    params.add(group.getPrarm3());
                }
            }
        } else {
            params.add(src.getPrarm1());
            if (src.getParamCount() >= 2) {
                params.add(src.getPrarm2());
            }
            if (src.getParamCount() >= 3) {
                params.add(src.getPrarm3());
            }
        }
        return params;
    }
}