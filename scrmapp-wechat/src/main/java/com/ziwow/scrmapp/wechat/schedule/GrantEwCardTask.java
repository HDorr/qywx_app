package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.wechat.persistence.entity.GrantEwCardRecord;
import com.ziwow.scrmapp.wechat.service.GrantEwCardRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 发放延保卡任务
 * @author songkaiqi
 * @since 2019/08/12/上午10:08
 */
@Component
@JobHandler("GrantEwCardTask")
public class GrantEwCardTask extends AbstractGrantEwCard{

    @Autowired
    private GrantEwCardRecordService grantEwCardRecordService;

    private volatile boolean flag = true;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<GrantEwCardRecord> records = grantEwCardRecordService.selectRecord();
                for (GrantEwCardRecord record : records) {
                    if (flag){
                        final boolean grant = grantEwCard(record.getPhone(), record.getType());
                        if (grant) {
                            grantEwCardRecordService.updateSendByPhone(record.getPhone(), true);
                        }
                    }else {
                        XxlJobLogger.log("发放延保卡子任务停止");
                        break;
                    }
                }
            }
        });

        thread.start();

        try {
            synchronized (thread) {
                thread.wait();
            }
        } catch (InterruptedException e) {
            flag = false;
            XxlJobLogger.log("发放延保卡主任务停止");
        }
        return ReturnT.SUCCESS;
    }

}
