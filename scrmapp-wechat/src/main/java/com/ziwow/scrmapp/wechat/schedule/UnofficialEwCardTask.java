package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.enums.EwCardStatus;
import com.ziwow.scrmapp.common.utils.EwCardUtil;
import com.ziwow.scrmapp.wechat.controller.EwCardController;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;
import com.ziwow.scrmapp.wechat.service.EwCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author songkaiqi
 * @since 2019/07/19/下午5:28
 */
@Component
@JobHandler("unOfficialEwCardTask")
public class UnofficialEwCardTask extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(EwCardController.class);

    @Autowired
    private EwCardService ewCardService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("（非官方）开始执行审核延保卡流程任务");
        logger.info("开始执行校验逻辑");
        //查询待审核的延保卡记录
        List<EwCard> ewCards = ewCardService.selectEwCardsByStatusAndInstall(EwCardStatus.TO_BE_AUDITED,false);
        for (EwCard ewCard : ewCards) {
            if (EwCardUtil.gtSevenDay(ewCard.getPurchDate())){
                XxlJobLogger.log("推送的延保卡id"+ewCard.getCardNo());
                System.out.println("ewCard = " + ewCard.getCardNo());
            }
        }
        return ReturnT.SUCCESS;
    }

}
