package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.enums.EwCardSendTypeEnum;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCardSendType;
import com.ziwow.scrmapp.wechat.persistence.entity.GrantEwCardRecord;
import com.ziwow.scrmapp.wechat.service.GrantEwCardRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
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

    private HashMap<String, EwCardSendType> params = new HashMap<>();

    @PostConstruct
    private void InitParams(){
        params.put("直播",new EwCardSendType("亲爱的沁粉，恭喜您成为幸运用户，获得直播间福利。您已获赠限量免费的一年延保卡，您的延保卡号为{1}（点击券码可直接复制！）\n\n使用方式：关注沁园公众号-【我的沁园】-【个人中心】-【延保服务】-【领取卡券】，复制券码并绑定至您的机器，即可延长一年质保。绑定时请扫描机身条形码，即可识别机器！\n\n如对操作有疑问，可点击公众号左下角小键盘符号，回复【延保卡】，查看绑定教程。卡券码有效期7天，请尽快使用。"
                , EwCardSendTypeEnum.LIVESTREMING));
        params.put("普通",new EwCardSendType("亲爱的沁粉，您的保修将于半月内过期。恭喜您成为幸运用户，获赠限量免费的一年延保卡（价值{0}元），您的延保卡号为{1}。（点击券码可直接复制）！\n\n使用方式：关注沁园公众号-【我的沁园】-【个人中心】-【延保服务】-【领取卡券】，复制券码并绑定至您的机器，即可延长一年质保，绑定时请扫描机身条形码，即可识别机器！\n\n如对操作有疑问，可点击公众号左下角小键盘符号，回复【延保卡】，查看绑定教程。卡券码有效期7天，请尽快使用。"
                , EwCardSendTypeEnum.SMS));
    }

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        final String[] str = s.split(",");
        final Integer total = Integer.valueOf(str[0]);
        final EwCardSendType ewCardSendType = params.get(str[1]);
        final AtomicInteger num = new AtomicInteger(0);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<GrantEwCardRecord> records = grantEwCardRecordService.selectRecord(ewCardSendType.getType());
                XxlJobLogger.log("延保卡查询总数:{}",records.size());
                for (GrantEwCardRecord record : records) {
                    if (flag && total>num.intValue()){
                        final boolean grant = grantEwCard(record.getPhone(), record.getType(), ewCardSendType.getMsg());
                        if (grant) {
                            grantEwCardRecordService.updateSendByPhone(record.getPhone(), true);
                            XxlJobLogger.log("已发放{}张延保卡",num.addAndGet(1));
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