package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.wechat.enums.ServiceSubscribeCrowd;
import com.ziwow.scrmapp.wechat.persistence.entity.NoticeRoster;

import java.util.List;
import java.util.Map;

/**
 * 发放通知名单
 * @author songkaiqi
 * @since 2019/11/21/上午9:59
 */
public interface NoticeRosterService {

    /**
     * 查询换芯人群
     * @param serviceSubscribeCrowd
     * @return
     */
    List<NoticeRoster> queryByType(ServiceSubscribeCrowd serviceSubscribeCrowd);

    /**
     * 修改发放标识根据id
     * @param id
     */
    void updateSendById(Long id);


    /**
     * 依据手机号查已发放但是并未预约或预约成功的记录
     * @param mobilePhone
     * @return
     */
    boolean isIncludeByPhone(String mobilePhone);


    /**
     * 根据手机号查询id 以及 人群分类
     * @param mobilePhone
     * @return
     */
    Map<String,Object> queryIdAndTypeByPhone(String mobilePhone);

    /**
     * 根据id 增加操作时间和标识
     * @param noticeId
     * @param handleType
     */
    void updateHandleById(Long noticeId,String handleType);

    /**
     * 根据id 增加发放标识（无时间）
     * @param id
     */
    void updateSendNoTimeById(Long id);
}
