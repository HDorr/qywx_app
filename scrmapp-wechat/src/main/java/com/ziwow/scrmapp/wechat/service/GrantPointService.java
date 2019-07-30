package com.ziwow.scrmapp.wechat.service;

/**
 * User: wangdong
 * Date: 19-6-11.
 * Time: 上午10:41
 * Description: ${调用商城发积分的方法}
 */

public interface GrantPointService {


  void grantOrderInstallPoint(String userId,String orderCode);

  void grantOrderFilterPoint(String userId,String orderCode);

  void grantOrderComment(String userId,String orderCode,Integer orderType);


}
