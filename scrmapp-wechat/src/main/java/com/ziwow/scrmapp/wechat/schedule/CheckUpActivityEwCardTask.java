package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.utils.EwCardUtil;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCardActivity;
import com.ziwow.scrmapp.wechat.persistence.entity.GrantEwCardRecord;
import com.ziwow.scrmapp.wechat.service.EwCardActivityService;
import com.ziwow.scrmapp.wechat.service.GrantEwCardRecordService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 检查搞活动的延保卡是否过期
 *
 * @author songkaiqi
 * @since 2019/08/09/上午10:52
 */
@Component
@JobHandler("checkUpActivityEwCardTask")
public class CheckUpActivityEwCardTask extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(CheckUpActivityEwCardTask.class);


    @Autowired
    private GrantEwCardRecordService grantEwCardRecordService;


    @Override
    public ReturnT<String> execute(String s) throws Exception {
        //获取已经发送，但是没有领取，未到期的延保卡号
        List<GrantEwCardRecord> grantEwCardRecords = grantEwCardRecordService.selectReceiveRecord(false);
        for (GrantEwCardRecord grantEwCardRecord : grantEwCardRecords) {
            if (StringUtils.isNotBlank(grantEwCardRecord.getPhone()) ||  grantEwCardRecord.getSendTime() != null){

                if (EwCardUtil.gtSevenDay(grantEwCardRecord.getSendTime())) {
                    try {
                        grantEwCardRecordService.resetGrantEwCardRecord(grantEwCardRecord.getPhone());
                    } catch (Exception e) {
                        logger.error("重置数据失败，延保卡号:{},错误信息：{}",grantEwCardRecord.getMask(),e);
                        XxlJobLogger.log("重置数据失败，延保卡号:{}",grantEwCardRecord.getMask());
                    }
                }

            }
        }
        return ReturnT.SUCCESS;
    }
}
