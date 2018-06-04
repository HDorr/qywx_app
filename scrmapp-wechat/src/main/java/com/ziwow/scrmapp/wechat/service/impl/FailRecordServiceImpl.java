package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.persistence.mapper.FailRecordMapper;
import com.ziwow.scrmapp.common.persistence.entity.FailRecord;
import com.ziwow.scrmapp.wechat.service.FailRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * Created by xiaohei on 2017/4/10.
 */
@Service
public class FailRecordServiceImpl implements FailRecordService {

    @Transactional
    @Override
    public int saveFailRecord(SQLException e) {
        //获得异常信息,并保存到异常表
        StackTraceElement[] stackTrace = e.getStackTrace();
        FailRecord failRecord = new FailRecord();
        failRecord.setFailClass(stackTrace[0].getClassName());
        failRecord.setFailMethod(stackTrace[0].getMethodName());
        failRecord.setParameters(e.getMessage());
        failRecord.setIsDeal(1);
        return failRecordMapper.insert(failRecord);
    }


    @Autowired
    private FailRecordMapper failRecordMapper;
}
