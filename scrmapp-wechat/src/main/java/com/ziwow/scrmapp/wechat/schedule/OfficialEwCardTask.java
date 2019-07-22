package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.enums.EwCardStatus;
import com.ziwow.scrmapp.wechat.controller.EwCardController;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;
import com.ziwow.scrmapp.wechat.service.EwCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 官方购买机器审核定时任务
 * @author songkaiqi
 * @since 2019/07/18/上午11:44
 */
@Component
@JobHandler("officialEwCardTask")
public class OfficialEwCardTask extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(EwCardController.class);

    @Autowired
    private EwCardService ewCardService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("（官方）开始执行审核延保卡流程任务");
        logger.info("开始执行校验逻辑");
        //查询待审核的延保卡记录
        List<EwCard> ewCards = ewCardService.selectEwCardsByStatusAndInstall(EwCardStatus.TO_BE_AUDITED,true);
        for (EwCard ewCard : ewCards) {
            System.out.println("ewCard.getCardNo() = " + ewCard.getCardNo());
            XxlJobLogger.log("ewCard.getCardNo() = " + ewCard.getCardNo());
        }
        return ReturnT.SUCCESS;
    }

}
