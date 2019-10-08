package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.wechat.persistence.entity.GrantEwCardRecord;
import com.ziwow.scrmapp.wechat.service.GrantEwCardRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试任务
 * @author songkaiqi
 * @since 2019/08/12/上午10:08
 */
@Component
@JobHandler("testTask")
public class TestTask extends AbstractGrantEwCard{

    private static final Logger LOG= LoggerFactory.getLogger(TestTask.class);


    @Override
    public ReturnT<String> execute(String s) throws Exception {
        final Integer total = Integer.valueOf(s);

        for (int i = 0; i < total ; i++) {
            LOG.info("测试调度平台执行到了第"+i+"次");
        }
        return ReturnT.SUCCESS;
    }

}