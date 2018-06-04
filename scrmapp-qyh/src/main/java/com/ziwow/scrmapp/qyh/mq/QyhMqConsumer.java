package com.ziwow.scrmapp.qyh.mq;

import com.ziwow.scrmapp.common.constants.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.bean.vo.QyhUserMsgVo;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.qyh.service.QyhNoticeService;
import com.ziwow.scrmapp.tools.queue.QueueListener;

public class QyhMqConsumer implements QueueListener<QyhUserMsgVo> {
    private static final Logger LOGGER = LoggerFactory.getLogger(QyhMqConsumer.class);
    @Autowired
    private QyhNoticeService qyhNoticeService;
    @Autowired
    private MobileService mobileService;

    @Override
    public void onMessage(QyhUserMsgVo qyhUserMsgVo) {
        LOGGER.info("服务工程师侧消费者listener获取的值为:" + JSON.toJSONString(qyhUserMsgVo));
        if (null != qyhUserMsgVo) {
            // 发送短信通知提醒
            String msgContent = qyhUserMsgVo.getMsgContent();
            String engineerPhone = qyhUserMsgVo.getQyhUserMobile();
            try {
                mobileService.sendContentByEmay(engineerPhone, msgContent, Constant.ENGINEER);
            } catch (Exception e) {
                LOGGER.error("服务工程师通知短信提醒失败:", e);
            }
            // 发送公告通知提醒
            String toUser = qyhUserMsgVo.getUserId();
            String content = qyhUserMsgVo.getContent();
            qyhNoticeService.qyhSendMsgText(toUser, content);
        }
    }
}