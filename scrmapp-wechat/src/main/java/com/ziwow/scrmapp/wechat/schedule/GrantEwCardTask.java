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
 * 发放延保卡任务
 * @author songkaiqi
 * @since 2019/08/12/上午10:08
 */
@Component
@JobHandler("GrantEwCardTask")
public class GrantEwCardTask extends AbstractGrantEwCard{

    private static final Logger LOG= LoggerFactory.getLogger(GrantEwCardTask.class);

    @Autowired
    private GrantEwCardRecordService grantEwCardRecordService;

    private volatile boolean flag = true;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        final Integer total = Integer.valueOf(s);

        final AtomicInteger num = new AtomicInteger(0);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<GrantEwCardRecord> records = grantEwCardRecordService.selectRecord();
                XxlJobLogger.log("延保卡查询总数:{}",records.size());
                for (GrantEwCardRecord record : records) {
                    if (flag && total>num.intValue()){
                        final boolean grant = grantEwCard(record.getPhone(), record.getType());
                        if (grant) {
                            grantEwCardRecordService.updateSendByPhone(record.getPhone(), true);
                            XxlJobLogger.log("已发放",num.addAndGet(1),"张延保卡");
                        }
                    }else {
                        LOG.info("发放延保卡子任务被停止");
                        XxlJobLogger.log("发放延保卡子任务被停止");
                        break;
                    }
                }
                synchronized (Thread.currentThread()){
                    Thread.currentThread().notifyAll();
                    XxlJobLogger.log("唤醒延保卡执行主线程");
                }
                flag = true;
            }
        });

        thread.start();

        try {
            synchronized (thread) {
                thread.wait();
                XxlJobLogger.log("延保卡执行主线程被唤醒");
            }
        } catch (InterruptedException e) {
            flag = false;
            XxlJobLogger.log("发放延保卡主任务停止");
        }
        return ReturnT.SUCCESS;
    }

}