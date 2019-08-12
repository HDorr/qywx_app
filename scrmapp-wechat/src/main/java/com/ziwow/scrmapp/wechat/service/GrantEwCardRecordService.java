package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.wechat.persistence.entity.GrantEwCardRecord;

import java.util.List;

/**
 * 发放延保卡记录
 * @author songkaiqi
 * @since 2019/08/12/上午10:00
 */
public interface GrantEwCardRecordService {

    /**
     * 查询出没有发放的记录
     * @return
     */
    List<GrantEwCardRecord> selectRecord();


    /**
     * 根据手机号修改发送状态
     * @param phone
     * @param send
     */
    void updateSendByPhone(String phone,boolean send);

}
