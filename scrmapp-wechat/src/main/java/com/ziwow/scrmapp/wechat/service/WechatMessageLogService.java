package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.tools.weixin.InMessage;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatArea;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCity;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatProvince;
import com.ziwow.scrmapp.wechat.vo.WechatUserVo;
import java.util.List;

public interface WechatMessageLogService {
  void saveLog(InMessage message);
}