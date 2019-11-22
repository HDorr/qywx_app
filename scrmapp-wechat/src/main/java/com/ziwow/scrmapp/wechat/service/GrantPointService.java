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


  void grantOrderInstallPoint(String userId, String orderCode, Integer orderType, StringBuilder productName, Date createTime);

  void grantOrderFilterPoint(String userId,String orderCode,Integer orderType,StringBuilder productName, Date createTime);

  void grantOrderComment(String userId, String orderCode, Integer orderType, ServiceComment serviceComment);

  /**
   * 维修单正常完工发送积分
   * @param userId
   * @param orderCode
   */
  void grantFinishRepair(String userId,String orderCode,Integer orderType,StringBuilder productName, Date createTime);

  /**
   * 保养单（清洗、换芯）正常完工发送积分
   * @param userId
   * @param orderCode
   */
  void grantFinishWash(String userId,String orderCode,Integer orderType,StringBuilder productName, Date createTime);


}
