package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.wechat.persistence.entity.WechatRegister;

/**
 * User: wangdong
 * Date: 19-6-25.
 * Time: 上午10:10
 * Description: ${DESCRIPTION}
 */
public interface WechatRegisterService {
  Boolean savePullNewRegisterByEngineer(WechatRegister wechatRegister);
}
