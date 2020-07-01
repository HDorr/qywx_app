package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.bean.vo.ProductVo;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.tools.utils.HttpClientUtils;
import com.ziwow.scrmapp.tools.utils.MD5;
import com.ziwow.scrmapp.common.persistence.entity.ServiceComment;
import com.ziwow.scrmapp.wechat.service.GrantPointService;
import com.ziwow.scrmapp.wechat.service.ProductService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
  @Autowired
  private ProductService productService;

  @Value("${miniapp.point.orderInstall}")
  private String orderInstallUrl;
  @Value("${miniapp.point.orderFilter}")
  private String orderFilterUrl;
  @Value("${miniapp.point.orderComment}")
  private String orderCommentUrl;
  // 预约维修单
  @Value("${miniapp.point.orderRepair}")
  private String orderRepairUrl;
  // 预约清洗
  @Value("${miniapp.point.orderWash}")
  private String orderWashUrl;


  /**
   * 完工发积分
   * @param userId 用户id
   * @param orderCode 工单号
   */
  @Async
  @Override
  public void grantOrderFinish(String userId, String orderCode,Integer orderType, String createTime) {
      String url="";
      switch (orderType){
        case 1:url=orderInstallUrl;break;
        case 2:url=orderRepairUrl;break;
        case 3:url=orderFilterUrl;break;
        case 4:url=orderWashUrl;break;
      }
    //调用商城发积分接口
    awardPoint(url,userId,orderCode,orderType,getProductName(orderCode),createTime);
  }

  /**
   * 发送工单评价积分
   * @param userId 用户id
   * @param orderCode 工单号
   */
  @Async
  @Override
  public void grantOrderComment(String userId, String orderCode, Integer orderType, ServiceComment serviceComment) {
    awardPoint(orderCommentUrl,userId,orderCode,orderType,serviceComment);
  }


  /**
   * 拿到工单中所有产品的name
   * @param ordersCode
   * @return
   */
  private StringBuilder getProductName(String ordersCode){
    StringBuilder productName = new StringBuilder();
    List<ProductVo> productVos = productService.selectByOrdersCode(ordersCode);
    for (int i = 0; i < productVos.size(); i++) {
      productName.append(productVos.get(i).getProductName());
      if(i < productVos.size() - 1){
        productName.append(",");
      }
    }
    return productName;
  }


  /**
   * 根据 userId查询unionId
   */
  private String findUnionId(String userId){
    return wechatUserService.loadByUnionId(userId);
  }

  private void awardPoint(String url,String userId,String orderCode,Integer orderType,StringBuilder productName,String createTime){
    String unionId = findUnionId(userId);
    Map<String, Object> params = new HashMap<String, Object>();
    long timestamp = System.currentTimeMillis();
    params.put("ordersCode", orderCode);
    params.put("timestamp", timestamp);
    params.put("unionId", unionId);
    params.put("createTime",createTime);
    if(null!=orderType){
      params.put("orderType", orderType);
    }
    if(null!=productName){
      params.put("productName",productName.toString());
    }
    params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
    String result = HttpClientUtils
        .postJson(url, JSONObject.fromObject(params).toString());
    if (StringUtils.isNotBlank(result)) {
      JSONObject o1 = JSONObject.fromObject(result);
      if (o1.containsKey("errorCode") && o1.getInt("errorCode")==200) {
        String fAid = o1.getString("data");
        LOG.info("积分发送成功");
      } else {
        LOG.error("积分发送失败,moreInfo:{}", o1.getString("moreInfo"));
      }
    }
  }

  /**
   * 仅用于服务评价发放积分
   * @param url
   * @param userId
   * @param orderCode
   * @param orderType
   * @param serviceComment
   */
  private void awardPoint(String url, String userId, String orderCode, Integer orderType, ServiceComment serviceComment){
    String unionId = findUnionId(userId);
    Map<String, Object> params = new HashMap<String, Object>();
    long timestamp = System.currentTimeMillis();
    params.put("ordersCode", orderCode);
    params.put("timestamp", timestamp);
    params.put("unionId", unionId);
    params.put("orderType", orderType);
    params.put("attitude",serviceComment.getAttitude());
    params.put("profession",serviceComment.getProfession());
    params.put("content",serviceComment.getContent());
    params.put("createTime",serviceComment.getOrderTime());
    params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
    String result = HttpClientUtils
            .postJson(url, JSONObject.fromObject(params).toString());
    if (StringUtils.isNotBlank(result)) {
      JSONObject o1 = JSONObject.fromObject(result);
      if (o1.containsKey("errorCode") && o1.getInt("errorCode")==200) {
        String fAid = o1.getString("data");
        LOG.info("积分发送成功");
      } else {
        LOG.error("积分发送失败,moreInfo:{}", o1.getString("moreInfo"));
      }
    }
  }
}
