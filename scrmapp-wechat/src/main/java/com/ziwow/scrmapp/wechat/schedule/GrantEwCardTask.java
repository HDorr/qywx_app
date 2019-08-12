package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHandler;
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

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        List<GrantEwCardRecord> records = grantEwCardRecordService.selectRecord();
        for (GrantEwCardRecord record : records) {
            final boolean flag = grantEwCard(record.getPhone(), record.getType());
            if (flag){
                grantEwCardRecordService.updateSendByPhone(record.getPhone(),true);
            }
        }
        return ReturnT.SUCCESS;
    }

}
