package com.ziwow.scrmapp.wechat.service.impl;

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

}
