package com.ziwow.scrmapp.qyh.schedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ziwow.scrmapp.common.bean.vo.QyhUserTomorrowOrderVo;
import com.ziwow.scrmapp.common.persistence.mapper.WechatOrdersMapper;
import com.ziwow.scrmapp.qyh.service.QyhNoticeService;

@Component
public class NoticeMsgScheduledTask {
    private final Logger logger = LoggerFactory.getLogger(NoticeMsgScheduledTask.class);
    @Autowired
    private WechatOrdersMapper wechatOrdersMapper;
    @Autowired
    private QyhNoticeService qyhNoticeService;
    @Value("${engineer.tomorrow.order.url}")
    private String tomorrowOrderUrl;
    @Value("${task.flag}")
    private String flag;

    // 师傅明日待处理订单每天8点执行
    @Scheduled(cron = "0 0 8 * * ?")
    public void tomorrowOrderMsg() {
        if (!flag.equals("0")) {
            return;
        }
        logger.info("服务工程师明日待处理工单定时任务开始......");
        long begin = System.currentTimeMillis();
        List<QyhUserTomorrowOrderVo> list = wechatOrdersMapper.getTomorrowHandleOrder();
        for (QyhUserTomorrowOrderVo qyhUserTomorrowOrderVo : list) {
            String qyhUserId = qyhUserTomorrowOrderVo.getQyhUserId();
            int installNum = qyhUserTomorrowOrderVo.getInstallNum();
            int repairNum = qyhUserTomorrowOrderVo.getRepairNum();
            int maintainNum = qyhUserTomorrowOrderVo.getMaintainNum();
            int totalNum = qyhUserTomorrowOrderVo.getTotalNum();
            // 如果有待处理订单需要通知
            if (totalNum > 0) {
                String url = tomorrowOrderUrl + "?userId=" + qyhUserId + "&condition=tomorrow";
                String content = "明日工单提醒！\n" +
                        "    您明天有" + totalNum + "条工单待处理，工单信息如下：\n" +
                        "    安装工单：" + installNum + "条；\n" +
                        "    维修工单：" + repairNum + "条；\n" +
                        "    保养单：" + maintainNum + "条\n" +
                        "1、点击<a href='" + url + "'>【待处理工单】</a>查看明日工单详情！\n" +
                        "    请提前做好安排，不要错过上门服务时间！";
                qyhNoticeService.qyhSendMsgText(qyhUserId, content);
            }
        }
        long end = System.currentTimeMillis();
        logger.info("服务工程师明日待处理工单定时任务结束,共耗时：[" + (end - begin) / 1000 + "]秒");
    }
}