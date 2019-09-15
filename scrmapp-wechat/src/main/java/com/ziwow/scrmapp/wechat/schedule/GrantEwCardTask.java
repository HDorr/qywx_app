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

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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

    private ThreadPoolExecutor service;

    @PostConstruct
    public void initThreadPool(){
        service = new ThreadPoolExecutor(1,1,5, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(1), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        final Integer total = Integer.valueOf(s);
        XxlJobLogger.log("参数为{}",total);
        final AtomicInteger num = new AtomicInteger(0);
        XxlJobLogger.log("num:{}",num);
        service.submit(new Runnable() {
            @Override
            public void run() {
                List<GrantEwCardRecord> records = grantEwCardRecordService.selectRecord();
                XxlJobLogger.log("records.size:{}",records.size());
                for (GrantEwCardRecord record : records) {
                    if (flag && total>num.intValue()){
                        final boolean grant = grantEwCard(record.getPhone(), record.getType());
                        if (true) {
                            grantEwCardRecordService.updateSendByPhone(record.getPhone(), true);
                            final int sendNum = num.addAndGet(1);
                            LOG.info("已发放{}张延保卡",sendNum);
                            XxlJobLogger.log("已发放{}张延保卡",sendNum);
                        }
                    }else {
                        LOG.info("发放延保卡子任务被停止");
                        XxlJobLogger.log("发放延保卡子任务被停止");
                        break;
                    }
                }
                synchronized (num) {
                    LOG.info("发放延保卡子任务被停止");
                    XxlJobLogger.log("唤醒主线程堵塞");
                    num.notifyAll();
                }
                flag = true;
            }
        });


        try {
            synchronized (num) {
                num.wait();
                LOG.info("主线程堵塞被唤醒");
                XxlJobLogger.log("主线程堵塞被唤醒");
            }
        } catch (InterruptedException e) {
            flag = false;
            LOG.info("发放延保卡主任务停止");
            XxlJobLogger.log("发放延保卡主任务停止");
        }
        service.shutdown();
        return ReturnT.SUCCESS;
    }

}