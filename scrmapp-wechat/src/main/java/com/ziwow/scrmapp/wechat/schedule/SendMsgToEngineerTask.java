package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.common.persistence.mapper.QyhUserMapper;
import com.ziwow.scrmapp.common.service.MobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author songkaiqi
 * @since 2019/07/22/下午4:28
 */
@Component
@JobHandler("SendMsgToEngineerTask")
public class SendMsgToEngineerTask extends IJobHandler {
    private final Logger logger = LoggerFactory.getLogger(SendMsgToEngineerTask.class);

    @Autowired
    private QyhUserMapper qyhUserMapper;

    @Autowired
    private MobileService mobileService;


    @Override
    public ReturnT<String> execute(String s) throws Exception {
        logger.info("定时发送短信给沁园工程师......");
        List<QyhUser> users = qyhUserMapper.getActiveUser();
        String content = "美好的一天又开始了，请记得登录“沁园服务之家”WX企业号，查看是否有工单等待您的处理！";
        for (QyhUser qyhUser : users) {
            try {
                mobileService.sendContentByEmay(qyhUser.getMobile(), content, Constant.ENGINEER);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("发送短信失败："+e);
                XxlJobLogger.log("发送短信失败："+e);
                return ReturnT.FAIL;
            }
        }
        return ReturnT.SUCCESS;
    }
}
