package com.ziwow.scrmapp.common.xxljob;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Service;

/**
 * 审核定时任务
 * @author songkaiqi
 * @since 2019/07/18/上午11:44
 */
@Service
@JobHandler("reviewTask")
public class EwCardTask extends IJobHandler {

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("开始执行审核延保卡流程任务");

        System.out.println("执行了");

        return ReturnT.SUCCESS;
    }


}
