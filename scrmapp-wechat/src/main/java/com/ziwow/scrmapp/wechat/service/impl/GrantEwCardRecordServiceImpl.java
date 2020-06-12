package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.enums.EwCardSendTypeEnum;
import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;
import com.ziwow.scrmapp.wechat.persistence.entity.GrantEwCardRecord;
import com.ziwow.scrmapp.wechat.persistence.mapper.GrantEwCardRecordMapper;
import com.ziwow.scrmapp.wechat.service.GrantEwCardRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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
    public List<GrantEwCardRecord> selectRecord(EwCardSendTypeEnum sendType) {
        return grantEwCardRecordMapper.selectRecord(sendType);
    }

    @Override
    public void updateSendByPhone(String phone, boolean send,EwCardSendTypeEnum sendType) {
        grantEwCardRecordMapper.updateSendByPhone(phone,send,sendType);
    }


    @Override
    public void addEwCardRecord(String mobile, String mask, EwCardTypeEnum type,Boolean send) {
        grantEwCardRecordMapper.addEwCardRecord(mobile,mask,type,send);
    }

    @Override
    public void addMaskByMobile(String mask, String mobile,EwCardSendTypeEnum sendTypeEnum) {
        grantEwCardRecordMapper.addMaskByMobile(mask,mobile,sendTypeEnum);
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
    public List<GrantEwCardRecord> selectRecordByPhone(String phone) {
        return grantEwCardRecordMapper.selectRecordByPhone(phone);
    }

    @Override
    public void resetGrantEwCardRecord(String mask) {
        grantEwCardRecordMapper.resetGrantEwCardRecord(mask);
    }

    @Override
    public boolean selectReceiveRecordByPhone(String mobile) {
        return grantEwCardRecordMapper.selectReceiveRecordByPhone(mobile) == null ? false : true;
    }

    @Override
    public LinkedList<GrantEwCardRecord> selectRecordByDate(String format) {
        return grantEwCardRecordMapper.selectRecordByDate(format);
    }

    @Override
     public void updateMessageSend(String id) {
         grantEwCardRecordMapper.updateMessageSend(id);
    }

    @Override
    public void updateReceiveByMask(String mask, boolean receive) {
        grantEwCardRecordMapper.updateReceiveByMask(mask,receive);
    }

    @Override
    public void updateSendNoTimeByPhone(String mobile, boolean send, EwCardSendTypeEnum sendType) {
        grantEwCardRecordMapper.updateSendNoTimeByPhone(mobile,send,sendType);
    }

    @Override
    public boolean isGrantCard(String mobile) {
        return grantEwCardRecordMapper.selectOrderRecordByPhone(mobile) == null ? false : true;
    }

}
