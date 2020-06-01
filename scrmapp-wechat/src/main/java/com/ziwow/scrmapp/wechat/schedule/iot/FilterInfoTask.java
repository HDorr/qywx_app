package com.ziwow.scrmapp.wechat.schedule.iot;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.ziwow.scrmapp.common.iot.IotEquipmentInfo;
import com.ziwow.scrmapp.common.iot.IotFilterInfo;
import com.ziwow.scrmapp.wechat.service.IotEquipmentInfoService;
import com.ziwow.scrmapp.wechat.service.IotFilterInfoService;
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
    private IotEquipmentInfoService iotEquipmentInfoService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        //拉取滤芯寿命信息
        List<IotFilterInfo> iotFilterInfos = new ArrayList<>();

        //组装id
        for (IotFilterInfo iotFilterInfo : iotFilterInfos) {
            if (iotFilterInfo.getFilterLife() == 0) {
                iotFilterInfo.setOverdueDate(new Date());
            }
            IotEquipmentInfo equipmentInfo = iotEquipmentInfoService.queryBySnCode(iotFilterInfo.getSncode());
            iotFilterInfo.setEquipmentInfoId(equipmentInfo.getId());
        }

        iotFilterInfoService.saveFilterInfos(iotFilterInfos);

        return ReturnT.SUCCESS;
    }

}
