package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCardActivity;
import com.ziwow.scrmapp.wechat.persistence.mapper.EwCardActivityMapper;
import com.ziwow.scrmapp.wechat.service.EwCardActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author songkaiqi
 * @since 2019/08/08/下午5:18
 */
@Service
public class EwCardActivityServiceImpl implements EwCardActivityService {


    private EwCardActivityMapper ewCardActivityMapper;

    @Autowired
    public EwCardActivityServiceImpl(EwCardActivityMapper ewCardActivityMapper) {
        this.ewCardActivityMapper = ewCardActivityMapper;
    }

    @Override
    public String selectCardNoByMask(String cardNo) {
        return ewCardActivityMapper.selectCardNoByMask(cardNo);
    }

    @Override
    public void updateReceiveByCardNo(String cardNo, boolean receive) {
        ewCardActivityMapper.updateReceiveByCardNo(cardNo,receive);
    }

    @Override
    public String selectCardNo(EwCardTypeEnum type) {
        return ewCardActivityMapper.selectCardNo(type);
    }

    @Override
    public void addMaskByCardNo(String cardNo, String mask) {
        ewCardActivityMapper.addMaskByCardNo(cardNo,mask);
    }

    @Override
    public void addSendTimeByCardNo(String cardNo) {
        ewCardActivityMapper.addSendTimeByCardNo(cardNo);
    }

    @Override
    public List<EwCardActivity> selectCardByNoReceive() {
        return ewCardActivityMapper.selectCardByNoReceive();
    }

    @Override
    public void resetActivityCard(String cardNo) {
        ewCardActivityMapper.resetActivityCard(cardNo);
    }
}
