package com.ziwow.scrmapp.wechat.schedule.iot.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.ziwow.scrmapp.common.iot.IotEquipmentInfo;
import com.ziwow.scrmapp.common.iot.IotFilterInfo;
import com.ziwow.scrmapp.common.iot.IotFilterLifeInfo;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.wechat.service.IotEquipmentInfoService;
import com.ziwow.scrmapp.wechat.service.IotFilterInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@JobHandler("filterInfoTask")
public class FilterInfoTask extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoAndProductTask.class);

    @Autowired
    private IotFilterInfoService iotFilterInfoService;

    @Autowired
    private ThirdPartyService thirdPartyService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {

        //拉取滤芯信息并同步
        List<IotFilterInfo> filterInfos = thirdPartyService.getIotFilterInfo(10);
        if (CollectionUtils.isNotEmpty(filterInfos)){
            iotFilterInfoService.saveFilterInfos(filterInfos);
        }

        //拉取滤芯寿命并同步
        List<IotFilterLifeInfo> filterLifeInfos = thirdPartyService.getIotFilterLifeInfo(10);
        if (CollectionUtils.isNotEmpty(filterLifeInfos)){
            //组装id
            for (IotFilterLifeInfo iotFilterInfo : filterLifeInfos) {
                //设置过期时间
                if (iotFilterInfo.getFilterLife() == 0) {
                    //拉取的是昨天的数据，所以过期时间设为昨天
                    iotFilterInfo.setOverdueDate(DateUtils.addDays(new Date(),-1));
                }
            }
            iotFilterInfoService.saveFilterLifeInfos(filterLifeInfos);
        }
        return ReturnT.SUCCESS;
    }

}
