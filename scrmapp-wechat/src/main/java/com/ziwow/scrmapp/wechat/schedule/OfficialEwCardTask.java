package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.bean.pojo.CSMEwCardParam;
import com.ziwow.scrmapp.common.bean.pojo.ProductParam;
import com.ziwow.scrmapp.common.bean.vo.WechatOrdersVo;
import com.ziwow.scrmapp.common.bean.vo.csm.BaseCardVo;
import com.ziwow.scrmapp.common.bean.vo.csm.ProductItem;
import com.ziwow.scrmapp.common.constants.ErrorCodeConstants;
import com.ziwow.scrmapp.common.enums.EwCardStatus;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.wechat.controller.EwCardController;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUserAddress;
import com.ziwow.scrmapp.wechat.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 官方购买机器审核定时任务
 * @author songkaiqi
 * @since 2019/07/18/上午11:44
 */
@Component
@JobHandler("officialEwCardTask")
public class OfficialEwCardTask extends AbstractEwCardTask {

    private static final Logger logger = LoggerFactory.getLogger(EwCardController.class);

    @Autowired
    private EwCardService ewCardService;

    @Autowired
    public OfficialEwCardTask(EwCardService ewCardService, WechatOrdersService wechatOrdersService, WechatUserAddressService wechatUserAddressService, ThirdPartyService thirdPartyService, ProductService productService, WechatUserService wechatUserService, WechatFansService wechatFansService) {
        super(ewCardService, wechatOrdersService, wechatUserAddressService, thirdPartyService, productService, wechatUserService, wechatFansService);
    }


    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("（官方）开始执行审核延保卡流程任务");
        logger.info("开始执行校验逻辑(官方)");
        //查询待审核的延保卡记录
        List<EwCard> ewCards = ewCardService.selectEwCardsByStatusAndInstall(EwCardStatus.TO_BE_AUDITED,true);
        for (EwCard ewCard : ewCards) {
            registerEwCard(ewCard);
        }
        return ReturnT.SUCCESS;
    }

}
