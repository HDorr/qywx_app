package com.ziwow.scrmapp.common.persistence.mapper;


import com.ziwow.scrmapp.common.bean.vo.WechatOrdersRecordVo;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrdersRecord;

import java.util.List;

public interface WechatOrdersRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WechatOrdersRecord record);

    int insertSelective(WechatOrdersRecord record);

    WechatOrdersRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WechatOrdersRecord record);

    int updateByPrimaryKey(WechatOrdersRecord record);

    List<WechatOrdersRecordVo> findByOrdersId(Long orderId);
    
    public int getProductStatus(Long orderId);
}