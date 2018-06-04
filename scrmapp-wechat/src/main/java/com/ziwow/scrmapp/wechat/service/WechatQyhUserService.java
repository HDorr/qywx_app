package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.persistence.entity.QyhUser;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2017-12-21 14:52
 */
public interface WechatQyhUserService {
    QyhUser getQyhUser(String qyhUserId);
}
