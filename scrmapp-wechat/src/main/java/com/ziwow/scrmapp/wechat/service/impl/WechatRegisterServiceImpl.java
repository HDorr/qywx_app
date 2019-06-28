package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.wechat.persistence.entity.WechatRegister;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatRegisterMapper;
import com.ziwow.scrmapp.wechat.service.WechatRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: wangdong
 * Date: 19-6-25.
 * Time: 上午10:11
 * Description: ${DESCRIPTION}
 */
@Service
public class WechatRegisterServiceImpl implements WechatRegisterService {

  @Autowired
  private WechatRegisterMapper wechatRegisterMapper;

  @Override
  public Boolean savePullNewRegisterByEngineer(WechatRegister wechatRegister) {
    return wechatRegisterMapper.insertPullNewRegisterByEngineer(wechatRegister)>0;
  }
}
