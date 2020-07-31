package com.ziwow.scrmapp.wechat.utils.keyword;

import com.ziwow.scrmapp.common.redis.RedisService;
import com.ziwow.scrmapp.tools.oss.CallCenterOssUtil;
import com.ziwow.scrmapp.tools.weixin.InMessage;
import com.ziwow.scrmapp.wechat.constants.RedisKeyConstants;
import com.ziwow.scrmapp.wechat.service.WeChatMessageProcessingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

/**
 * @Author: huangrui
 * @Description: 人工客服
 * @Date: Create in 下午4:15 20-7-31
 */
@Component
public class CustomerKeywordStrategy extends KeywordAbstract {
    private Logger LOG = LoggerFactory.getLogger(WeChatMessageProcessingHandler.class);
    @Value("${callCenter.limit.time}")
    private Boolean limitCallCenterWorkingTime;
    @Autowired
    private RedisService redisService;
    @Autowired
    private WeChatMessageProcessingHandler handler;
    @Override
    public boolean getContent(InMessage inMessage, HttpServletResponse response) {
        final boolean inWorkTime= CallCenterOssUtil.checkIsInCallCenterWorkingTime(Calendar.getInstance());
        boolean isPushToCallCenter=false;
        this.setMsgsb(new StringBuilder());
        StringBuilder msgsb = this.getMsgsb();
        if (!limitCallCenterWorkingTime || inWorkTime) {
            msgsb.append("正在为您转接人工客服,请耐心等待！");
            redisService.set(RedisKeyConstants.getScrmappWechatCustomermsg() + inMessage.getFromUserName(), true, 1200L);
            //调用呼叫中心转人工
            LOG.info("调用呼叫中心转人工接口");
            inMessage.setContent("转人工");
            handler.pushMessageToCallCenter(inMessage);//推送消息到呼叫中心
            isPushToCallCenter=true;
        } else {
            msgsb.append("您好，非常抱歉给您带来不便，目前并非客服的工作时间，工作时间为：8:00AM-22:00PM");
        }
        handler.replyMessage(inMessage, response, msgsb);
        return isPushToCallCenter;
    }
}
