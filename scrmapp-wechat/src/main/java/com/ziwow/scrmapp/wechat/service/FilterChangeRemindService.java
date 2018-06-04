package com.ziwow.scrmapp.wechat.service;

import java.util.List;

import com.ziwow.scrmapp.common.persistence.entity.Filter;
import com.ziwow.scrmapp.common.bean.vo.FilterChangeRemindMsgVo;

/**
 * Created by xiaohei on 2017/4/10.
 */
public interface FilterChangeRemindService {
    int saveFilterRemind(List<Filter> filterList,Long productId);
    List<FilterChangeRemindMsgVo> getFilterChangeReminds();
    void sendFilterChangeReminderTemplateMsg(FilterChangeRemindMsgVo filterChangeRemindVo);
    void sendFilterBeforeExpireReminderTemplateMsg(FilterChangeRemindMsgVo filterChangeRemindVo);
    void sendFilterAfterExpireReminderTemplateMsg(FilterChangeRemindMsgVo filterChangeRemindVo);
}