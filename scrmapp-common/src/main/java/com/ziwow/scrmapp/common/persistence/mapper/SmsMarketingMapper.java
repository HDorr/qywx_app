package com.ziwow.scrmapp.common.persistence.mapper;

import com.ziwow.scrmapp.common.bean.GenericMapper;
import com.ziwow.scrmapp.common.persistence.entity.SmsMarketing;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2018-04-08 14:04
 */
public interface SmsMarketingMapper extends GenericMapper<SmsMarketing, Long> {
    public List<SmsMarketing> getSmsMarketingLst();
    public SmsMarketing getSmsMarketingByType(@Param("orderType") Integer orderType, @Param("smsType") Integer smsType);
}