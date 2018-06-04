package com.ziwow.scrmapp.wechat.schedule;

import com.ziwow.scrmapp.common.bean.pojo.EvaluateParam;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.SystemConstants;
import com.ziwow.scrmapp.common.persistence.entity.QyhUserAppraisal;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrders;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrdersRecord;
import com.ziwow.scrmapp.common.persistence.mapper.WechatOrdersMapper;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class AutoAppraisalScheduledTask {
    private final Logger logger = LoggerFactory.getLogger(AutoAppraisalScheduledTask.class);

    @Value("${task.flag}")
    private String flag;

    // 每天0点执行
    @Scheduled(cron = "0 0 0 * * ?")
    //@Scheduled(fixedRate = 100000)
    public void filterChangeReminderMsg() {
        if (!flag.equals("0")) {
            return;
        }
        logger.info("自动评论定时任务开始......");
        //查询7天前没完成评价的工单
        List<WechatOrders> ordersList = wechatOrdersMapper.findBeforeSevenDaysOrders();
        for (WechatOrders wechatOrders : ordersList) {
            try {
                //首先调用沁园同步接口
                EvaluateParam evaluateParam = new EvaluateParam();
                int orderType = 0;
                if (SystemConstants.INSTALL == wechatOrders.getOrderType()) {
                    orderType = SystemConstants.INSTALL;
                } else if (SystemConstants.REPAIR == wechatOrders.getOrderType()) {
                    orderType = SystemConstants.MAINTAIN;
                } else if (SystemConstants.MAINTAIN == wechatOrders.getOrderType()) {
                    orderType = SystemConstants.REPAIR;
                }
                evaluateParam.setNumber_type(orderType);
                evaluateParam.setEvaluate_note("");
                evaluateParam.setNumber(wechatOrders.getOrdersNo());
                Result invokeResult = wechatUserService.invokeCssEvaluate(evaluateParam);

                if (Constant.SUCCESS == invokeResult.getReturnCode()) {
                    Date date = new Date();
                    //保存评价信息
                    QyhUserAppraisal qyhUserAppraisal = new QyhUserAppraisal();
                    qyhUserAppraisal.setOrderId(wechatOrders.getId());
                    qyhUserAppraisal.setAttitude(new BigDecimal(0));
                    qyhUserAppraisal.setIntegrity(new BigDecimal(0));
                    qyhUserAppraisal.setProfession(new BigDecimal(0));
                    qyhUserAppraisal.setRecommend(new BigDecimal(0));
                    qyhUserAppraisal.setContent("");
                    qyhUserAppraisal.setQyhUserId(wechatOrders.getQyhUserId());
                    qyhUserAppraisal.setUserId(wechatOrders.getUserId());

                    WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
                    wechatOrdersRecord.setOrderId(wechatOrders.getId());
                    wechatOrdersRecord.setRecordTime(date);
                    wechatOrdersRecord.setRecordContent("系统自动评价!");

                    wechatUserService.save(wechatOrders.getOrdersCode(), date, qyhUserAppraisal, wechatOrdersRecord);
                }
            } catch (Exception e) {
                logger.error("自动评论定时任务失败:", e);
            }
        }

    }

    @Autowired
    private WechatOrdersMapper wechatOrdersMapper;

    @Autowired
    private WechatUserService wechatUserService;

}
