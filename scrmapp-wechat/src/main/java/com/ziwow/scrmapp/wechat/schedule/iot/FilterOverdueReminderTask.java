package com.ziwow.scrmapp.wechat.schedule.iot;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.ziwow.scrmapp.wechat.service.IotFilterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 滤芯过期30提醒
 * @author songkaiqi
 * @since 2020/06/01/上午11:39
 */
@Component
@JobHandler("filterOverdueReminderTask")
public class FilterOverdueReminderTask extends IJobHandler {

    @Autowired
    private IotFilterInfoService iotFilterInfoService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {

        //获取=30的滤芯
        iotFilterInfoService.queryByFilterLife(30);
        //找到滤芯对应的用户。

        //进行推送

        return null;
    }
}
