package com.ziwow.scrmapp.wechat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.tools.utils.HttpClientUtils;
import com.ziwow.scrmapp.tools.utils.MD5;
import com.ziwow.scrmapp.wechat.service.GrantPointService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * User: wangdong
 * Date: 19-6-11.
 * Time: 上午11:27
 * Description: ${DESCRIPTION}
 */
@Service
public class GrantPointServiceImpl implements GrantPointService {

  private static final Logger LOG = LoggerFactory.getLogger(GrantPointServiceImpl.class);
  @Autowired
  private WechatUserService wechatUserService;

  @Value("${miniapp.point.orderInstall}")
  private String orderInstallUrl;
  @Value("${miniapp.point.orderFilter}")
  private String orderFilterUrl;
  @Value("${miniapp.point.orderComment}")
  private String orderCommentUrl;


  /**
   * 发送安装工单积分
   * @param userId 用户id
   * @param orderCode 工单号
   */
  @Async
  @Override
  public void grantOrderInstallPoint(String userId, String orderCode) {
    //调用商城发积分接口
    awardPoint(orderInstallUrl,userId,orderCode,null);

  }

  /**
   * 发送保养工单积分
   * @param userId 用户id
   * @param orderCode 工单号
   */
  @Async
  @Override
  public void grantOrderFilterPoint(String userId, String orderCode) {
    awardPoint(orderFilterUrl,userId,orderCode,null);
  }

  /**
   * 发送工单评价积分
   * @param userId 用户id
   * @param orderCode 工单号
   */
  @Async
  @Override
  public void grantOrderComment(String userId, String orderCode,Integer orderType) {
    awardPoint(orderCommentUrl,userId,orderCode,orderType);
  }
  /**
   * 根据 userId查询unionId
   */
  private String findUnionId(String userId){
    return wechatUserService.loadByUnionId(userId);
  }

  private void awardPoint(String url,String userId,String orderCode,Integer orderType){
    String unionId = findUnionId(userId);
    Map<String, Object> params = new HashMap<String, Object>();
    long timestamp = System.currentTimeMillis();
    params.put("ordersCode", orderCode);
    params.put("timestamp", timestamp);
    params.put("unionId", unionId);
    if(null!=orderType){
      params.put("orderType", orderType);
    }
    params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
    String result = HttpClientUtils
        .postJson(url, JSONObject.toJSONString(params));
    if (StringUtils.isNotBlank(result)) {
      JSONObject o1 = JSONObject.parseObject(result);
      if (o1.containsKey("errorCode") && o1.getInteger("errorCode")==200) {
        String fAid = o1.getString("data");
        LOG.info("积分发送成功");
      } else {
        LOG.error("积分发送失败,moreInfo:{}", o1.getString("moreInfo"));
      }
    }
  }


}
