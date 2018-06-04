package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.common.persistence.mapper.QyhUserMapper;
import com.ziwow.scrmapp.wechat.service.WechatQyhUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2017-12-21 14:54
 */
@Service
public class WechatQyhUserServiceImpl implements WechatQyhUserService {
    @Autowired
    public QyhUserMapper qyhUserMapper;

    @Value("${qyh.open.corpid}")
    private String corpId;

    @Override
    public QyhUser getQyhUser(String qyhUserId) {
        return qyhUserMapper.getQyhUserByUserIdAndCorpId(qyhUserId, corpId);
    }
}
