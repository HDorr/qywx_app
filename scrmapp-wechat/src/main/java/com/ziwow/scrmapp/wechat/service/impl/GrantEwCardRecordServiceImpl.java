package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;
import com.ziwow.scrmapp.wechat.persistence.entity.GrantEwCardRecord;
import com.ziwow.scrmapp.wechat.persistence.mapper.GrantEwCardRecordMapper;
import com.ziwow.scrmapp.wechat.service.GrantEwCardRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author songkaiqi
 * @since 2019/08/12/上午10:54
 */
@Service
public class GrantEwCardRecordServiceImpl implements GrantEwCardRecordService {

    @Autowired
    private GrantEwCardRecordMapper grantEwCardRecordMapper;

    @Override
    public List<GrantEwCardRecord> selectRecord() {
        return grantEwCardRecordMapper.selectRecord();
    }

    @Override
    public void updateSendByPhone(String phone, boolean send) {
        grantEwCardRecordMapper.updateSendByPhone(phone,send);
    }


    @Override
    public void addEwCardRecord(String mobile, String mask, EwCardTypeEnum type,Boolean send) {
        grantEwCardRecordMapper.addEwCardRecord(mobile,mask,type,send);
    }

    @Override
    public void addMaskByMobile(String mask, String mobile) {
        grantEwCardRecordMapper.addMaskByMobile(mask,mobile);
    }

    @Override
    public GrantEwCardRecord selectRecordByMask(String mask) {
        return grantEwCardRecordMapper.selectRecordByMask(mask);
    }

    @Override
    public void updateReceiveByPhone(String phone, boolean receive) {
        grantEwCardRecordMapper.updateReceiveByPhone(phone,receive);
    }

    @Override
    public List<GrantEwCardRecord> selectReceiveRecord(Boolean recevice) {
        return grantEwCardRecordMapper.selectReceiveRecord(recevice);
    }

    @Override
    public void resetGrantEwCardRecord(String phone) {
        grantEwCardRecordMapper.resetGrantEwCardRecord(phone);
    }

    @Override
    public boolean selectReceiveRecordByPhone(String mobile) {
        return grantEwCardRecordMapper.selectReceiveRecordByPhone(mobile) == null ? false : true;
    }

}
