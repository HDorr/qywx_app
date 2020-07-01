package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.persistence.entity.ServiceComment;

import java.util.Date;

/**
 * User: wangdong
 * Date: 19-6-11.
 * Time: 上午10:41
 * Description: ${调用商城发积分的方法}
 */

public interface GrantPointService {


  void grantOrderComment(String userId, String orderCode, Integer orderType, ServiceComment serviceComment);

  void grantOrderFinish(String userId,String orderCode,Integer orderType,String createTime);


}
