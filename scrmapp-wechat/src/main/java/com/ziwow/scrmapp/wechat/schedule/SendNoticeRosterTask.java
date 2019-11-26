package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.wechat.enums.ServiceSubscribeCrowd;
import com.ziwow.scrmapp.wechat.persistence.entity.NoticeRoster;
import com.ziwow.scrmapp.wechat.service.ConfigService;
import com.ziwow.scrmapp.wechat.service.NoticeRosterService;
import com.ziwow.scrmapp.wechat.utils.SendNotice;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  发放通知记录任务
 * @author songkaiqi
 * @since 2019/11/21/上午9:50
 */
@JobHandler("sendNoticeRosterTask")
public class SendNoticeRosterTask extends IJobHandler {

    @Autowired
    private ConfigService configService;

    @Autowired
    private NoticeRosterService noticeRosterService;

    @Autowired
    private SendNotice sendNotice;

    @Override
    public ReturnT<String> execute(String params) throws Exception {
        //参数
        final String[] split = params.split(",");
        final int total = Integer.valueOf(split[0]);
        final ServiceSubscribeCrowd serviceSubscribeCrowd = ServiceSubscribeCrowd.valueOf(split[1]);
        //加载判断清洗通知的模板
        final Map<String,Map<String,String>> notice = (Map)configService.getConfig("message_warn_class");
        //符合类型的数据
        List<NoticeRoster> list = noticeRosterService.queryByType(serviceSubscribeCrowd);
        //符合类型的消息消息通知
        final Map<String, String> map = notice.get(split[1]);

        //发放
        AtomicInteger sum = new AtomicInteger(0);
        for (NoticeRoster noticeRoster : list) {
            if (sum.intValue() <= total){
                //发放
                try {
                    List<String> param = new ArrayList<>(2);
                    param.add(DateFormatUtils.format(noticeRoster.getBuyTime(),"YYYY年MM月dd日"));
                    param.add(noticeRoster.getProductCode());
                    final boolean send = sendNotice.sendNotice(map.get("title"),map.get("remark"), "" ,param,noticeRoster.getPhone());
                    if (send){
                        //修改发放标记
                        noticeRosterService.updateSendById(noticeRoster.getId());
                        sum.addAndGet(1);
                    }else {
                        noticeRosterService.updateSendNoTimeById(noticeRoster.getId());
                    }
                } catch (Exception e) {
                    XxlJobLogger.log("发放通知错误，手机号为：[],错误信息为：[]",noticeRoster.getPhone(),e);
                }
            }
        }
        return ReturnT.SUCCESS;
    }


}
