package com.ziwow.scrmapp.wechat.schedule.iot.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.schedule.iot.dto.IotFilterReminder;
import com.ziwow.scrmapp.wechat.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 滤芯过期30提醒
 * @author songkaiqi
 * @since 2020/06/01/上午11:39
 */
@Component
@JobHandler("filterOverdueReminderTask")
public class FilterOverdueReminderTask extends IJobHandler {

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

    @Value("${changeReminderTemplage.id}")
    private String changeReminderTemplateId;

    @Override
    public ReturnT<String> execute(String s) {
        //计算出30天前
        final String date = DateUtil.format(DateUtils.addDays(new Date(), -30), "yyyy-MM-dd");
        //获取过期30天的滤芯信息
        List<IotFilterReminder> filterReminders = iotFilterInfoService.queryByOverdueDate(date);
        //找到滤芯对应的用户。
        List<String> param = new ArrayList<>();
        for (IotFilterReminder filterReminder : filterReminders) {
            final String phone = filterReminder.getPhone();
            final WechatUser user = wechatUserService.getUserByMobilePhone(phone);
            final String openId = wechatFansService.getWechatFansByUserId(user.getUserId()).getOpenId();
            final Product product = productService.getProductsByBarCodeAndUserId(user.getUserId(), StringUtils.substring(filterReminder.getSncode(),5));
            if (product != null){
                param.add(product.getModelName());
                param.add(DateUtil.format(product.getBuyTime(),"yyyy年MM月dd日"));
                param.add(DateUtil.format(filterReminder.getOverdueDate(),"yyyy年MM月dd日"));
                param.add(filterReminder.getFilterName());
                wechatTemplateService.sendTemplateByShortId(openId,null,param,changeReminderTemplateId,false,"","");
                param.clear();
            }
        }
        return ReturnT.SUCCESS;
    }
}
