package com.ziwow.scrmapp.wechat.schedule.iot.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.schedule.iot.dto.IotFilterReminder;
import com.ziwow.scrmapp.wechat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 依据iot数据，滤芯即将到期提醒
 * @author songkaiqi
 * @since 2020/06/02/下午3:22
 */
@Component
@JobHandler("filerSoonExpireReminderTask")
public class FilerSoonExpireReminderTask extends IJobHandler {

    @Autowired
    private IotFilterInfoService iotFilterInfoService;

    @Autowired
    private WechatTemplateService wechatTemplateService;

    @Autowired
    private WechatFansService wechatFansService;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private ProductService productService;


    @Override
    public ReturnT<String> execute(String s) {
        //拉取还有30天到期的滤芯
        final List<IotFilterReminder> filterReminders = iotFilterInfoService.queryByFilterLife(30);
        for (IotFilterReminder filterReminder : filterReminders) {
            final String phone = filterReminder.getPhone();
            final WechatUser user = wechatUserService.getUserByMobilePhone(phone);
            final String openId = wechatFansService.getWechatFansByUserId(user.getUserId()).getOpenId();
            final Product product = productService.getProductsByBarCodeAndUserId(user.getUserId(), filterReminder.getSncode());
            if (product != null){
                wechatTemplateService.expirationReminderTemplate(openId,"","",product.getModelName(),
                        DateUtil.format(product.getBuyTime(),"yyyy-MM-dd"),"");
            }
        }
        return ReturnT.SUCCESS;
    }
}
