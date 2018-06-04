package com.ziwow.scrmapp.wechat.schedule;

import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.common.persistence.mapper.QyhUserMapper;
import com.ziwow.scrmapp.common.service.MobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SendMsgToEngineerTask {
    private final Logger logger = LoggerFactory.getLogger(SendMsgToEngineerTask.class);

    @Value("${task.flag}")
    private String flag;
    @Autowired
    private QyhUserMapper qyhUserMapper;

    @Autowired
    private MobileService mobileService;

    // 每天7点执行
    @Scheduled(cron = "0 0 7 * * ?")
    public void SendMsgToEngineer() {
        if (!flag.equals("0")) {
            return;
        }
        logger.info("定时发送短信给沁园工程师......");
        List<QyhUser> users = qyhUserMapper.getActiveUser();
        String content = "美好的一天又开始了，请记得登录“沁园服务之家”微信企业号，查看是否有工单等待您的处理！";
        for (QyhUser qyhUser : users) {
            try {
                mobileService.sendContentByEmay(qyhUser.getMobile(), content, Constant.ENGINEER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
