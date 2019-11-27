package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.wechat.enums.ServiceSubscribeCrowd;
import com.ziwow.scrmapp.wechat.persistence.entity.NoticeRoster;
import com.ziwow.scrmapp.wechat.persistence.mapper.NoticeRosterMapper;
import com.ziwow.scrmapp.wechat.service.NoticeRosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author songkaiqi
 * @since 2019/11/21/上午10:00
 */
@Service
public class NoticeRosterServiceImpl implements NoticeRosterService {

    @Autowired
    private NoticeRosterMapper noticeRosterMapper;


    @Override
    public List<NoticeRoster> queryByType(ServiceSubscribeCrowd serviceSubscribeCrowd) {
        return noticeRosterMapper.queryNoSendByType(serviceSubscribeCrowd);
    }

    @Override
    public void updateSendById(Long id) {
        noticeRosterMapper.updateSendById(id);
    }

    @Override
    public boolean isIncludeByPhone(String mobilePhone) {
        return noticeRosterMapper.queryNoHandleByPhone(mobilePhone) != null;
    }

    @Override
    public Map<String, Object> queryIdAndTypeByPhone(String mobilePhone) {
        return noticeRosterMapper.queryIdAndTypeByPhone(mobilePhone);
    }

    @Override
    public void updateHandleById(Long noticeId,String handleType) {
        noticeRosterMapper.updateHandleById(noticeId,handleType);
    }

    @Override
    public void updateSendNoTimeById(Long id) {
        noticeRosterMapper.updateSendNoTimeById(id);
    }
}
