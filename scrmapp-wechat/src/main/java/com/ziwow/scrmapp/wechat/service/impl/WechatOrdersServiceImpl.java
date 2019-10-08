package com.ziwow.scrmapp.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ziwow.scrmapp.common.bean.pojo.*;
import com.ziwow.scrmapp.common.bean.vo.*;
import com.ziwow.scrmapp.common.bean.vo.csm.AppealProduct;
import com.ziwow.scrmapp.common.bean.vo.csm.DispatchDotProductVo;
import com.ziwow.scrmapp.common.bean.vo.csm.ProductAppealVo;
import com.ziwow.scrmapp.common.bean.vo.csm.ProductItem;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.SystemConstants;
import com.ziwow.scrmapp.common.exception.ParamException;
import com.ziwow.scrmapp.common.pagehelper.Page;
import com.ziwow.scrmapp.common.persistence.entity.*;
import com.ziwow.scrmapp.common.persistence.mapper.OrdersProRelationsMapper;
import com.ziwow.scrmapp.common.persistence.mapper.ProductMapper;
import com.ziwow.scrmapp.common.persistence.mapper.WechatOrdersMapper;
import com.ziwow.scrmapp.common.persistence.mapper.WechatOrdersRecordMapper;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.common.utils.OrderUtils;
import com.ziwow.scrmapp.tools.queue.EngineerQueue;
import com.ziwow.scrmapp.tools.thirdParty.SignUtil;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.tools.utils.HttpClientUtils;
import com.ziwow.scrmapp.tools.utils.MD5;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.enums.BuyChannel;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatUserMapper;
import com.ziwow.scrmapp.wechat.service.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLDataException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by xiaohei on 2017/4/10.
 */
@Service
public class WechatOrdersServiceImpl implements WechatOrdersService {

    private static final Logger LOG = LoggerFactory.getLogger(WechatOrdersServiceImpl.class);

    @Autowired
    private WechatOrdersMapper wechatOrdersMapper;
    @Autowired
    private WechatOrdersRecordMapper wechatOrdersRecordMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrdersProRelationsMapper ordersProRelationsMapper;
    @Autowired
    private WechatOrdersRecordService wechatOrdersRecordService;
    @Autowired
    private ThirdPartyService thirdPartyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WechatTemplateService wechatTemplateService;
    @Autowired
    private WechatFansService wechatFansService;
    @Autowired
    private MobileService mobileService;
    @Autowired
    private WechatUserMapper wechatUserMapper;
    @Autowired
    public WechatQyhUserService wechatQyhUserService;
    @Autowired
    private SmsSendRecordService smsSendRecordService;

    @Value("${template.msg.url}")
    private String msgUrl;
    @Value("${order.detail.url}")
    private String orderDetailUrl;
    @Value("${myorder.detail.url}")
    private String myOrderDetailUrl;
    @Value("${wechat.appid}")
    private String appid;
    @Value("${open.weixin.component_appid}")
    private String component_appid;
    @Value("${order.list.url}")
    private String orderlisturl;
    @Value("${dispatch.userId}")
    private String dispatchUserId;
    @Value("${dispatch.mobile}")
    private String dispatchMobile;


    @Value("${miniapp.product.makeAppointment}")
    private String makeAppointmentUrl;

    @Value("${miniapp.product.updatemakeAppointment}")
    private String updatemakeAppointmentUrl;

    @Value("${miniapp.product.cancelmakeAppointment}")
    private String cancelmakeAppointmentUrl;

    @Autowired
    private WechatUserService wechatUserService;



    @Override
    public List<WechatOrdersVo> getWechatOrdersByProductId(Long productId) {
        return wechatOrdersMapper.getWechatOrdersByProductId(productId);
    }


    @Override
    public WechatOrders getWechatOrdersByCode(String ordersCode) {
        return wechatOrdersMapper.getWechatOrdersVoByCode(ordersCode);
    }

    @Override
    public WechatOrderVo getWechatOrdersVoByCode(String ordersCode) {
        return wechatOrdersMapper.getWechatOrdersVoByCode(ordersCode);
    }

    /**
     * 取消预约
     *
     * @return
     */
    @Override
    public Result cancelOrders(String ordersCode) {
        /*调用沁园取消预约接口*/
        return thirdPartyService.cancelCssAppeal(ordersCode);
    }

    @Override
    public int updateOrdersStatus(String ordersCode, String userId, Date updateTime, int status) {
        return wechatOrdersMapper.updateStatus(ordersCode, updateTime, status);
    }

    /**
     * 调用沁园生成受理单接口，将受理单编号返回
     *
     * @return
     */
    @Override
    public Result geneatorCode(WechatOrdersParam wechatOrdersParam) {
        AcceptanceFormParam acceptanceFormParam = new AcceptanceFormParam();
        acceptanceFormParam.setEnduser_name(wechatOrdersParam.getContacts());
        acceptanceFormParam.setMobile(wechatOrdersParam.getContactsMobile());
        acceptanceFormParam.setTel(wechatOrdersParam.getTel());
        acceptanceFormParam.setEnduser_address(wechatOrdersParam.getAddress());
        acceptanceFormParam.setProvince_name(wechatOrdersParam.getProvince());
        acceptanceFormParam.setCity_name(wechatOrdersParam.getCity());
        acceptanceFormParam.setCounty_name(wechatOrdersParam.getArea());
        acceptanceFormParam.setAppeal_kind_id(wechatOrdersParam.getOrderType());
        acceptanceFormParam.setFrom_type(wechatOrdersParam.getDeliveryType().getCode());
        acceptanceFormParam.setKind_name(wechatOrdersParam.getKindName());
        acceptanceFormParam.setKind_name2(wechatOrdersParam.getKindName2());
        String orderTime = wechatOrdersParam.getOrderTime();
        if (StringUtils.isBlank(orderTime)){
            acceptanceFormParam.setAppeal_content(wechatOrdersParam.getDescription());
            acceptanceFormParam.setService_time("");
        }else {
            acceptanceFormParam.setAppeal_content(wechatOrdersParam.getDescription() + " 预约时间：" + orderTime);
            Date date = DateUtil.StringToDate(orderTime, "yyyy-MM-dd HH");
            acceptanceFormParam.setService_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        }

        //产品信息
        List<ProductVo> prodLst = wechatOrdersParam.getProducts();
        List<AcceptanceProductParam> products = Lists.newArrayList();
        for (ProductVo productVo : prodLst) {
            final String saleMarket;
            final String netSaleNo;
            if (StringUtils.isBlank(orderTime)){
                //原单原回
                saleMarket = "微信商城";
                netSaleNo = wechatOrdersParam.getOrderNo();
            }else {
                saleMarket = BuyChannel.getBuyChannel(productVo.getO2o(), productVo.getBuyChannel());
                netSaleNo = productVo.getShoppingOrder();
            }
            String itemKind = productVo.getItemKind();
            int fromChannel = productVo.getSaleType();

            String bigcName = productVo.getTypeName();
            String spec = productVo.getModelName();
            String barCode = productVo.getProductBarCode();
            String purchDate = productVo.getBuyTime();
            String itemCode = productVo.getProductCode();
            AcceptanceProductParam productParam = new AcceptanceProductParam();
            productParam.setItem_kind(itemKind);
            productParam.setItem_code(itemCode);
            productParam.setFrom_channel(fromChannel + "");
            productParam.setSale_market(saleMarket);
            productParam.setNet_sale_no(netSaleNo);
            productParam.setBigc_name(bigcName);
            productParam.setSpec(spec);
            productParam.setBarcode(barCode);
            productParam.setPurch_date(purchDate);
            products.add(productParam);
        }
        acceptanceFormParam.setProducts(products);
        acceptanceFormParam.setFix_org_name(wechatOrdersParam.getDepartmentName() == null ? "": wechatOrdersParam.getDepartmentName());
        acceptanceFormParam.setIs_wxtd(UUID.randomUUID().toString());
        Result result = thirdPartyService.addCssAppeal(acceptanceFormParam);
        if (Constant.SUCCESS == result.getReturnCode()) {
            LOG.info("受理单获取成功,单号为[{}]", result.getData().toString());
        }
        return result;
    }

    @Transactional
    @Override
    public WechatOrders saveOrders(WechatOrders wechatOrders) {
        wechatOrdersMapper.insert(wechatOrders);
        return wechatOrders;
    }

    @Transactional
    @Override
    public WechatOrders saveOrdersMultiProduct(WechatOrders wechatOrders, String productIds) {
        //保存记录到t_wechat_orders
        wechatOrdersMapper.insertSelective(wechatOrders);

        String[] productIdsArray = productIds.split(",");
        List<OrdersProRelations> ordersProRelationsList = new ArrayList<OrdersProRelations>();

        for (String productId : productIdsArray) {
            OrdersProRelations ordersProRelations = new OrdersProRelations();
            ordersProRelations.setProductId(Long.parseLong(productId));
            ordersProRelations.setOrderId(wechatOrders.getId());
            ordersProRelationsList.add(ordersProRelations);
        }

        //批量插入t_orders_pro_relations
        ordersProRelationsMapper.batchInsert(ordersProRelationsList);
        return wechatOrders;
    }


    /**
     * 通过userId 查找用户预约信息列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<WechatOrdersVo> findByUserId(String userId) {
        List<WechatOrdersVo> detailVo = wechatOrdersMapper.getOrdersByUserId(userId);
        if (detailVo.size() == 0) {
            return new ArrayList<WechatOrdersVo>();
        }
        Map<Long, WechatOrdersVo> map = new LinkedHashMap<Long, WechatOrdersVo>();
        //遍历工单列表，保存至map
        for (WechatOrdersVo vo : detailVo) {
            map.put(vo.getId(), vo);
        }
        //根据工单id查询产品列表
        Long[] ordersIds = map.keySet().toArray(new Long[map.size()]);
        List<ProductVo> products = productMapper.selectByOrdersIds(ordersIds);
        for (ProductVo pro : products) {
            map.get(pro.getOrdersId()).getProducts().add(pro);
        }
        return new ArrayList<WechatOrdersVo>(map.values());
    }

  @Override
  public List<WechatOrdersVo> pageByUserId(String userId, Page page) {
    List<WechatOrdersVo> detailVo = wechatOrdersMapper.pageOrdersByUserId(userId, page);
    if (detailVo.size() == 0) {
      return new ArrayList<>();
    }
    Map<Long, WechatOrdersVo> map = new LinkedHashMap<>();
    //遍历工单列表，保存至map
    for (WechatOrdersVo vo : detailVo) {
      map.put(vo.getId(), vo);
      List<WechatOrdersRecordVo> wechatOrdersRecordList = wechatOrdersRecordMapper
          .findByOrdersId(vo.getId());
      Collections.sort(wechatOrdersRecordList, new Comparator<WechatOrdersRecordVo>() {
        @Override
        public int compare(WechatOrdersRecordVo o1, WechatOrdersRecordVo o2) {
          return o2.getRecordTime().compareTo(o1.getRecordTime());
        }
      });
      vo.setWechatOrdersRecordList(wechatOrdersRecordList);
    }
    //根据工单id查询产品列表
    Long[] ordersIds = map.keySet().toArray(new Long[0]);
    List<ProductVo> products = productMapper.selectByOrdersIds(ordersIds);
    for (ProductVo pro : products) {
      map.get(pro.getOrdersId()).getProducts().add(pro);
    }
    return new ArrayList<>(map.values());
  }


  @Override
  public long getCountByUserId(String userId) {
    return wechatOrdersMapper.selectCountByUserId(userId);
  }

  @Override
  public WechatOrdersVo getVoByOrdersCode(String ordersCode) {
    //订单详情
    WechatOrdersVo wechatOrdersVo = wechatOrdersMapper.getByOrdersCode(ordersCode);

        //查询受理记录
        List<WechatOrdersRecordVo> wechatOrdersRecordList = wechatOrdersRecordMapper.findByOrdersId(wechatOrdersVo.getId());
        wechatOrdersVo.setWechatOrdersRecordList(wechatOrdersRecordList);

        //产品列表
        List<ProductVo> productVos = productMapper.selectByOrdersIds(new Long[]{wechatOrdersVo.getId()});
        for (int i = 0; i < productVos.size(); i++) {
            String productBarCode = productVos.get(i).getProductBarCode();
            String productBarCodeTwenty = productVos.get(i).getProductBarCodeTwenty();
            if (StringUtil.isNotBlank(productBarCode) && StringUtil.isNotBlank(productBarCodeTwenty) && productBarCode.length() == 20
                    && productBarCodeTwenty.length() == 20) {
                productVos.get(i).setProductBarCodeTwenty(productBarCode.substring(0, 1) + productBarCodeTwenty.substring(1));
            }
        }
        wechatOrdersVo.setProducts(productVos);
        return wechatOrdersVo;
    }

    /**
     * 同步用户历史受理记录
     */
    @Async
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void syncHistoryAppInfo(String mobilePhone, String userId) {
        try {
            // 3:安装单  5:保养单  2:维修单
            List<Integer> list = Lists.newArrayList(3, 5, 2);
            for (int type : list) {
                List<ProductAppealVo> appealList = thirdPartyService.getCssHistoryAppInfo(mobilePhone, type);
                LOG.info("从csm系统获取的type为[{}]的历史受理记录信息为:[{}]", type, JSON.toJSONString(appealList));
                if (null != appealList && !appealList.isEmpty()) {
                    for (ProductAppealVo appealVo : appealList) {
                        //判断工单号在数据库是否存在，如果存在。修改工单所属用户
                        String ordersCode = appealVo.getAppealNo();
                        WechatOrders wechatOrders = wechatOrdersMapper.getWechatOrdersVoByCode(ordersCode);
                        List<AppealProduct> products = appealVo.getProducts();
                        if (null == wechatOrders) {
                            Long orderId = null;
                            // 将记录录入t_wechat_orders表
                            wechatOrders = new WechatOrders();
                            wechatOrders.setOrdersCode(ordersCode);
                            //wechatOrders.setProductId(productId);
                            wechatOrders.setContacts(appealVo.getEnduserName());
                            wechatOrders.setContactsMobile(appealVo.getMobile());
                            wechatOrders.setProvince(appealVo.getProvinceName());
                            wechatOrders.setCity(appealVo.getCityName());
                            wechatOrders.setArea(appealVo.getCountyName());
                            wechatOrders.setAddress(appealVo.getEnduserAddress());
                            if (type == 3) {
                                wechatOrders.setOrderType(1);
                            } else if (type == 2) {
                                wechatOrders.setOrderType(2);
                            } else if (type == 5) {
                                wechatOrders.setOrderType(3);
                            }
                            wechatOrders.setDescription(appealVo.getAppealContent());
                            wechatOrders.setUserId(userId);
                            wechatOrders.setCreateTime(DateUtil.StringToDate(appealVo.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                            wechatOrders.setUpdateTime(DateUtil.StringToDate(appealVo.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                            wechatOrders.setOrderTime(DateUtil.StringToDate(appealVo.getServiceTime(), DateUtil.YYYY_MM_DD_HH));
                            wechatOrders.setStatus(OrderUtils.csmOrderStatusConvert(appealVo.getOrderStatus()));
                            wechatOrders.setSource(SystemConstants.CSM);
                            wechatOrders.setQyhUserId(appealVo.getQyhUserId());
                            wechatOrdersMapper.insert(wechatOrders);
                            orderId = wechatOrders.getId();

                            // 将记录录入t_wechat_orders_record表
                            WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
                            wechatOrdersRecord.setOrderId(orderId);
                            wechatOrdersRecord.setRecordTime(DateUtil.StringToDate(appealVo.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                            wechatOrdersRecord.setRecordContent(appealVo.getAppealContent());
                            wechatOrdersRecordMapper.insert(wechatOrdersRecord);

                            // 入库产品信息
                            List<OrdersProRelations> ordersProRelations = Lists.newArrayList();
                            for (AppealProduct appealProduct : products) {
                                String spec = appealProduct.getSpec();
                                String item_code = appealProduct.getItem_code();
                                ProductParam productParam = new ProductParam();
                                if (StringUtils.isNotEmpty(item_code)) {
                                    productParam.setItem_code(item_code);
                                } else if (StringUtils.isNotEmpty(spec)) {
                                    productParam.setSpec(spec);
                                } else {
                                    continue;
                                }
                                ProductItem productItem = thirdPartyService.getProductItem(productParam);
                                if (null != productItem) {
                                    Product product = new Product();
                                    product.setTypeName(productItem.getBigcName());
                                    product.setSmallcName(productItem.getSmallcName());
                                    product.setModelName(productItem.getSpec());
                                    product.setItemKind(productItem.getItemKind());
                                    product.setLevelId(Long.parseLong(productItem.getFilterGradeId() + ""));
                                    product.setLevelName(productItem.getFilterGrade());
                                    product.setProductName(productItem.getItemName());
                                    product.setSaleType(productItem.getFromChannel());
                                    product.setShoppingOrder(appealProduct.getNetSaleNo());             // 产品单号
                                    product.setProductCode(productItem.getItemCode());
                                    product.setUserId(userId);
                                    product.setCreateTime(new Date());
                                    product.setFilterRemind(SystemConstants.REMIND);
                                    String purchDate = appealProduct.getPurchDate();
                                    if (StringUtils.isEmpty(purchDate)) {
                                        product.setBuyTime(new Date());
                                    } else {
                                        product.setBuyTime(DateUtil.StringToDate(purchDate, DateUtil.YYYY_MM_DD));
                                    }
                                    product.setStatus(1);
                                    productService.save(product);
                                    Long productId = product.getId();

                                    // 保存订单和产品关系j
                                    OrdersProRelations proRelations = new OrdersProRelations();
                                    proRelations.setOrderId(orderId);
                                    proRelations.setProductId(productId);
                                    ordersProRelations.add(proRelations);
                                }
                            }
                            // 入库订单和商品的关系表
                            ordersProRelationsMapper.batchInsert(ordersProRelations);
                        } else {
                            wechatOrders.setUserId(userId);
                            wechatOrders.setQyhUserId(appealVo.getQyhUserId());

                            //修改工单信息
                            wechatOrdersMapper.updateByPrimaryKeySelective(wechatOrders);

                            //修改产品信息
                            productMapper.updateUserIdById(userId, wechatOrders.getProductId());

                        }

                    }
                }
            }
        } catch (Exception e) {
            LOG.error("同步用户历史受理记录失败:", e);
        }
    }

    @Override
    public WechatOrdersParam getParamByOrdersCode(String ordersCode) {
        WechatOrdersParam wechatOrdersParam = wechatOrdersMapper.getParamByOrdersCode(ordersCode);
        List<ProductVo> products = productMapper.selectByOrdersId(wechatOrdersParam.getOrdersId());
        wechatOrdersParam.setProducts(products);
        return wechatOrdersParam;
    }

    @Override
    public Result updateOrdersTime(String ordersCode, Date date, String ordersTime) {
        Result updateResult = thirdPartyService.updateCssAppeal(ordersCode, ordersTime.substring(0, 10), 1);
        if (Constant.SUCCESS == updateResult.getReturnCode()) {
            wechatOrdersMapper.updateOrdersTime(ordersCode, date, ordersTime.substring(0, 13));
        }
        return updateResult;
    }

    @Override
    public QyhUserVo getQyhUserInfo(String ordersCode) {
        return wechatOrdersMapper.getQyhUserVoByOrdersCode(ordersCode);
    }

    @Override
    public List<WechatOrderMsgVo> getWechatOrderMsgVo(String mobilePhone) {
        return wechatOrdersMapper.getWechatOrderMsgVo(mobilePhone);
    }

    @Override
    public void sendWechatOrderTemplateMsg(WechatOrderMsgVo wechatOrderMsgVo) {
        String title = "亲爱的" + wechatOrderMsgVo.getNickName() + "，我们的工程师正在向您赶来，明日即将上门为您服务。";
        String remark = "点击【我的预约】了解详细状态，保持电话畅通，工程师会尽快与您联系。";
        String openId = wechatOrderMsgVo.getOpenId();
        String name = wechatOrderMsgVo.getContacts();
        int type = wechatOrderMsgVo.getOrderType();
        String serverType = OrderUtils.getServiceTypeName(type);
        String serverTime = wechatOrderMsgVo.getOrderTime();
        wechatTemplateService.reservationServiceRemind(openId, msgUrl, title, name, serverType, serverTime, remark);
    }

  @Override
  public void sendAppointmentTemplateMsg(String ordersCode, String serverType) {
    AppointmentMsgVo appointmentMsgVo = wechatOrdersMapper.getAppointmentMsgVo(ordersCode);
    String openId = appointmentMsgVo.getOpenId();
    String nickName = appointmentMsgVo.getNickName();
    String name = appointmentMsgVo.getContacts();
    String phone = appointmentMsgVo.getMobilePhone();
    String address = appointmentMsgVo.getMyAddress();
    String title = "亲爱的" + nickName + "，您的预约已成功提交！";
    String subscribeResult = "已成功提交";
    //String remark = "点击【我的预约】查看订单状态，希望这份健康呵护尽快抵达您家！";
    String[] params = {name, phone, address, serverType, subscribeResult};
    //wechatTemplateService.subscribeResultNoticeTemplate(openId, getOrdersListPageOauthUrl(), title, name, phone, address, serverType, subscribeResult, remark);
    wechatTemplateService
        .sendTemplate(openId, "pages/queryProgress?from=metinfo", Arrays.asList(params),
            "subscribeResultNoticeTemplate", true, title);

  }

  @Override
  public void sendOrderCancelTemplateMsg(String userId, String serverType) {
    WechatFans wechatFans = wechatFansService.getWechatFansByUserId(userId);
    if (null != wechatFans) {
      String openId = wechatFans.getOpenId();
      String nickName = wechatFans.getWfNickName();
      String title = "亲爱的" + nickName + "，您的撤销请求沁先生已经收到并已成功处理。";
      // String remark = "如需重新预约，点击【一键服务】进行操作即可，沁先生会继续派出工程师为您解决烦恼。";
      String time = DateUtil.DateToString(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
      String[] params = {serverType, time, time};
      // wechatTemplateService.serviceOrCancellationTemplate(openId, getOrdersListPageOauthUrl(), title, serverType, time, time, remark);
      wechatTemplateService
          .sendTemplate(openId, "pages/queryProgress?from=metinfo", Arrays.asList(params),
              "serviceOrCancellationTemplate", true, title);
    }
  }

  @Override
  public void sendOrderFinishTemplateMsg(String ordersCode, String userId, String orderTime) {
    WechatFans wechatFans = wechatFansService.getWechatFansByUserId(userId);
    if (null != wechatFans) {
      String openId = wechatFans.getOpenId();
      String nickName = wechatFans.getWfNickName();
      String title = "亲爱的" + nickName + "，您的预约服务已完成！";
      //String remark = "欢迎点击【我的预约】对工程师的服务进行评价，它会成为下一个顾客的参考，也会帮助沁先生更好地为您服务！如已评价，可忽略该消息。";
      String[] params = {ordersCode,orderTime};
      wechatTemplateService
          .sendTemplate(openId, "pages/queryProgress?from=metinfo", Arrays.asList(params),
              "serviceEvaluationToRemindTemplate", true, title);
      //wechatTemplateService.serviceEvaluationToRemindTemplate(openId, getOrdersListPageOauthUrl(), title, ordersCode, orderTime, remark);
    }
  }

    @Override
    public int dispatch(String ordersCode, String qyhUserId, Date updateTime, int status) {
        return wechatOrdersMapper.updateQyhUserIdAndStatus(ordersCode, qyhUserId, updateTime, status);
    }

  @Override
  public void sendDispatchMasterTemplateMsg(WechatOrders wechatOrders, String engineerName,
      String mobilePhone) {
    String userId = wechatOrders.getUserId();
    WechatFans wechatFans = wechatFansService.getWechatFansByUserId(userId);
    if (null != wechatFans) {
      String openId = wechatFans.getOpenId();
      String nickName = wechatFans.getWfNickName();
      String orderType = OrderUtils.getServiceTypeName(wechatOrders.getOrderType());
      String orderCode = wechatOrders.getOrdersCode();
      String orderTime = DateUtil
          .DateToString(wechatOrders.getOrderTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
      String title = "亲爱的" + nickName + "，沁先生已成功派单，工程师会按时上门服务。";
      //String remark = "点击【我的预约】了解详细状态，保持电话畅通，工程师会尽快与您联系。";
      String[] params = {orderType, orderCode, orderTime, engineerName};
      wechatTemplateService
          .sendTemplate(openId, "pages/queryProgress?from=metinfo", Arrays.asList(params),
              "servicesToNoticeTemplate", true, title);
      //String url = myOrderDetailUrl + "?userId=" + userId + "&ordersCode=" + orderCode;
      //wechatTemplateService.servicesToNoticeTemplate(openId, getOrdersListPageOauthUrl(), title, orderType, orderCode, orderTime, engineerName, mobilePhone, remark);
    }
  }

    /*@Override
    public void sendReOrderTimeTemplateMsg(WechatOrders wechatOrders) {
        String userId = wechatOrders.getUserId();
        String qyhUserId = wechatOrders.getQyhUserId();
        WechatFans wechatFans = wechatFansService.getWechatFansByUserId(userId);
        QyhUser qyhUser = wechatUserService.getQyhUser(qyhUserId);
        String engineerName = qyhUser.getName();
        String mobilePhone = qyhUser.getMobile();
        String openId = wechatFans.getOpenId();
        String nickName = wechatFans.getWfNickName();
        String orderType = OrderUtils.getServiceTypeName(wechatOrders.getOrderType());
        String orderCode = wechatOrders.getOrdersCode();
        String orderTime = DateUtil.DateToString(wechatOrders.getOrderTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        String title = "亲爱的" + nickName + "，沁先生已成功处理您发起的更改预约。为您送上新的订单信息：";
        String remark = "记得保持电话畅通，我们的工程师傅会尽快与您联系。 ";
        wechatTemplateService.servicesToNoticeTemplate(openId, null, title, orderType, orderCode, orderTime, engineerName, mobilePhone, remark);
    }*/

    //检查是否为空
    private <T> T checkNull(T o) throws ParamException {
        if (o == null) {
            throw new ParamException("接口参数为空");
        } else if (o.getClass() == String.class) {
            if ("".equals((String) o)) {
                throw new ParamException("接口必填字段不能为空");
            }
        }
        return o;
    }

    //0和1布尔值转换
    private Boolean convertBoolean(String number) {
        return "1".equals(number) ? true : false;
    }

    @Override
    public void syncAddAppraise(AppraiseParam appraiseParam) throws ParamException {
        boolean isLegal = SignUtil.checkSignature(appraiseParam.getSignature(), appraiseParam.getTimeStamp(), Constant.AUTH_KEY);
        if (!(isLegal)) {
            throw new ParamException(Constant.ILLEGAL_REQUEST);
        }
        appraiseParam = checkNull(appraiseParam);
        String ordersCode = checkNull(appraiseParam.getOrderCode());
//        String is_order = checkNull(appraiseParam.getIs_order());
        WechatOrders wechatOrders = this.getWechatOrdersByCode(ordersCode);
        if (wechatOrders == null) {
            throw new ParamException("受理单号在微信端不存在");
        }
        //判断预约单状态是否可以评论
        if (SystemConstants.COMPLETE != wechatOrders.getStatus()) {
            throw new ParamException("该订单的状态不是完成状态,不能进行评价");
        }

        //保存评价信息
        String userId = wechatOrders.getUserId();
        QyhUserAppraisalVo qyhUserAppraisalVo = new QyhUserAppraisalVo();
        qyhUserAppraisalVo.setOrderId(wechatOrders.getId());
        qyhUserAppraisalVo.setQyhUserId(wechatOrders.getQyhUserId());
        qyhUserAppraisalVo.setUserId(userId);
        String is_order = appraiseParam.getIs_order();
        qyhUserAppraisalVo.setIs_order(StringUtils.isEmpty(is_order) ? null : convertBoolean(is_order));//400回访is_order为非必填字段
        qyhUserAppraisalVo.setIs_source(1);//400评价来源默认是1
        int count = wechatUserService.saveVo(qyhUserAppraisalVo);
        Date date = new Date();
        if (count > 0) {
            //修改预约单状态为已评价
            this.updateOrdersStatus(ordersCode, userId, date, SystemConstants.APPRAISE);
            WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
            wechatOrdersRecord.setOrderId(wechatOrders.getId());
            wechatOrdersRecord.setRecordTime(date);
            wechatOrdersRecord.setRecordContent("用户评分完成");
            wechatOrdersRecordService.saveWechatOrdersRecord(wechatOrdersRecord);
        }
        // 给工程师发送短信通知
        String engineerMsgContent = "您服务的工单" + ordersCode + "，用户已经评价啦，谢谢提供服务！请登录“沁园服务之家”的售后服务个人中心，可以查看评分。";
        QyhUser qyhUser = wechatQyhUserService.getQyhUser(wechatOrders.getQyhUserId());
        String qyhUserMobile = (null != qyhUser) ? qyhUser.getMobile() : "";
        try {
            //短信开口关闭 2019年06月19日
            //mobileService.sendContentByEmay(qyhUserMobile, engineerMsgContent, Constant.ENGINEER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void sendEngineerMsgText(String engineerId, String engineerPhone, WechatOrders wechatOrders) {
        String ordersCode = wechatOrders.getOrdersCode();
        String serviceType = OrderUtils.getServiceTypeName(wechatOrders.getOrderType());
        String orderTime = DateUtil.DateToString(wechatOrders.getOrderTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        String url = orderDetailUrl + "?userId=" + engineerId + "&ordersCode=" + ordersCode;
        String content = "新工单通知！\n" +
                "    您收到一条新的工单！\n" +
                "1、点击<a href='" + url + "'>【待处理工单】</a>查看该工单详情！\n" +
                "    如需拒绝工单，请尽快操作，避免影响再次派单；如需更改预约，请提前跟消费者电话沟通协商。\n" +
                "    请提前做好安排，不要错过上门服务时间！ ";
        QyhUserMsgVo qyhUserMsgVo = new QyhUserMsgVo();
        qyhUserMsgVo.setUserId(engineerId);
        qyhUserMsgVo.setContent(content);
        qyhUserMsgVo.setQyhUserMobile(engineerPhone);
        String msgContent = "您收到一条新工单！类型：" + serviceType + "；服务时间：" + orderTime + "。您可进入“沁园”WX企业号查看该工单详情！";
        qyhUserMsgVo.setMsgContent(msgContent);
        EngineerQueue.getQueueInstance().add(qyhUserMsgVo);
    }

    @Override
    public void changeAppointmentTemplate(String userId, String orderType, String ordersCode, String orderTime, String qyhUserName, String qyhUserPhone) {
        WechatFans wechatFans = wechatFansService.getWechatFansByUserId(userId);
        if (null != wechatFans) {
            String openId = wechatFans.getOpenId();
            String nickName = wechatFans.getWfNickName();
            String title = "亲爱的" + nickName + "，沁先生已成功处理您的工程师发起的更改预约。为您送上新的订单信息：";
            String remark = "记得保持电话畅通，我们的工程师傅会尽快与您联系。如工程师更改预约前未跟您电话沟通，您可拨打电话400-111-1222，沁先生的好朋友会帮您处理。";
            wechatTemplateService.changeAppointmentTemplate(openId, null, title, orderType, ordersCode, orderTime, qyhUserName, qyhUserPhone, remark);
        }
    }

    @Override
    public void dispatchDot(DispatchDotParam dispatchDotParam) throws SQLDataException {
        if (null == dispatchDotParam) {
            throw new ParamException("请求参数不合法!");
        }
        String signture = dispatchDotParam.getSignture();
        String timeStamp = dispatchDotParam.getTimeStamp();
        String acceptNumber = dispatchDotParam.getAcceptNumber();
        String deptName = dispatchDotParam.getDeptName();
        String mobile = dispatchDotParam.getMobile();
        String contacts = dispatchDotParam.getContacts();
        String provinceName = dispatchDotParam.getProvinceName();
        String cityName = dispatchDotParam.getCityName();
        String areaName = dispatchDotParam.getAreaName();
        String street = dispatchDotParam.getStreet();
        String ordersTime = dispatchDotParam.getOrdersTime();
        String description = dispatchDotParam.getDescription();
        Integer ordersType = dispatchDotParam.getOrdersType();
        boolean isLegal = SignUtil.checkSignature(signture, timeStamp, Constant.AUTH_KEY);
        if (!(isLegal)) {
            throw new ParamException(Constant.ILLEGAL_REQUEST);
        }
        if ((StringUtils.isBlank(acceptNumber)) || (StringUtils.isBlank(deptName))) {
            LOG.info("受理单号[{}]或网点名称[{}]为空", acceptNumber, deptName);
            throw new ParamException("受理单号或网点名称不能为空!");
        }
        WechatOrders wechatOrders = this.getWechatOrdersByCode(acceptNumber);
        Date date = new Date();
        if (wechatOrders == null) {
            mobile = mobile.trim();
            String userId = "";
            WechatUser wechatUser = wechatUserMapper.getUserByMobilePhone(mobile);
            if (wechatUser == null)
                userId = this.dispatchUserId;
            else {
                userId = wechatUser.getUserId();
            }
            // 入库订单信息
            wechatOrders = new WechatOrders();
            //wechatOrders.setProductId(productId);
            wechatOrders.setContacts(contacts);
            wechatOrders.setContactsMobile(mobile);
            wechatOrders.setProvince(provinceName);
            wechatOrders.setCity(cityName);
            wechatOrders.setArea(areaName);
            wechatOrders.setAddress(street);
            wechatOrders.setOrdersCode(acceptNumber);
            wechatOrders.setOrderType(ordersType);
            wechatOrders.setCreateTime(date);
            wechatOrders.setUpdateTime(date);
            date.setMinutes(0);
            date.setSeconds(0);
            wechatOrders.setOrderTime((StringUtils.isEmpty(ordersTime)) ? date : DateUtil.StringToDate(ordersTime, "yyyy-MM-dd"));
            wechatOrders.setDescription((StringUtils.isEmpty(description)) ? "" : description);
            wechatOrders.setStatus(SystemConstants.ORDERS);
            wechatOrders.setSource(SystemConstants.CSM);
            wechatOrders.setUserId(userId);
            WechatOrders wechatOrd = this.saveOrders(wechatOrders);
            Long ordPid = wechatOrd.getId();

            // 入库产品信息
            List<OrdersProRelations> ordersProRelationLst = new ArrayList<OrdersProRelations>();
            List<DispatchDotProductVo> products = dispatchDotParam.getProducts();
            for (DispatchDotProductVo dotProductVo : products) {
                Product product = new Product();
                String buyTime = dotProductVo.getBuyTime();
                String buyChannel = dotProductVo.getBuyChannel();
                Integer o2o = dotProductVo.getO2o();
                product.setItemKind(dotProductVo.getItemKind());
                product.setTypeName(dotProductVo.getBigcName());
                product.setSmallcName(dotProductVo.getSmallcName());
                product.setModelName(dotProductVo.getSpec());
                product.setLevelId(new Long(dotProductVo.getFilterGradeId()));
                product.setLevelName(dotProductVo.getFilterGrade());
                product.setProductName(dotProductVo.getItemName());
                product.setProductCode(dotProductVo.getItemCode());
                if (!(StringUtils.isBlank(dotProductVo.getBarCode()))) {
                    product.setProductBarCode(dotProductVo.getBarCode());
                    product.setSaleType(dotProductVo.getFromChannel());
                }
                product.setO2o(o2o);
                product.setCreateTime(new Date());
                product.setBuyTime(DateUtil.StringToDate(buyTime, "yyyy-MM-dd"));
                product.setStatus(SystemConstants.ORDERS);
                product.setBuyChannel(BuyChannel.getBuyChannelId(o2o.intValue(), buyChannel));
                product.setUserId(userId);
                product.setFilterRemind(SystemConstants.REMIND);
                Long productId = productService.save(product);

                // 保存订单和产品的关系信息
                OrdersProRelations relations = new OrdersProRelations();
                relations.setOrderId(ordPid);
                relations.setProductId(productId);
                ordersProRelationLst.add(relations);
            }
            // 保存订单和产品关系信息
            ordersProRelationsMapper.batchInsert(ordersProRelationLst);
            // 异步发送营销短信
            smsSendRecordService.sendDispatchSms(ordersType, mobile);
        }

        WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
        wechatOrdersRecord.setOrderId(wechatOrders.getId());
        wechatOrdersRecord.setRecordContent("总部派单给网点:" + deptName);
        wechatOrdersRecord.setRecordTime(date);
        wechatOrdersRecordService.saveWechatOrdersRecord(wechatOrdersRecord);
    }

    @Override
    public void dispatchMaster(DispatchMasterParam dispatchMasterParam) throws Exception {
        if (null == dispatchMasterParam) {
            throw new ParamException("请求参数不合法!");
        }
        String signture = dispatchMasterParam.getSignture();
        String timeStamp = dispatchMasterParam.getTimeStamp();
        String acceptNumber = dispatchMasterParam.getAcceptNumber();
        String engineerId = dispatchMasterParam.getEngineerId();
        String engineerName = dispatchMasterParam.getEngineerName();
        boolean isLegal = SignUtil.checkSignature(signture, timeStamp, Constant.AUTH_KEY);
        if (!isLegal) {
            throw new ParamException(Constant.ILLEGAL_REQUEST);
        }
        if (StringUtils.isEmpty(acceptNumber) || StringUtils.isEmpty(engineerId) || StringUtils.isEmpty(engineerName)) {
            LOG.info("受理单号、服务工程师ID或服务工程师名称为空", acceptNumber, engineerId, engineerName);
            throw new ParamException("受理单号、服务工程师ID或服务工程师名称不能为空!");
        }
        // 校验受理单是否存在
        WechatOrders wechatOrders = this.getWechatOrdersByCode(acceptNumber);
        if (wechatOrders == null) {
            LOG.info("受理单号[{}]无效", acceptNumber);
            throw new ParamException("受理单号无效!");
        }
        // 校验服务工程师是否存在
        QyhUser qyhUser = wechatQyhUserService.getQyhUser(engineerId);
        if (qyhUser == null) {
            LOG.info("服务工程师[{}]在微信端系统中不存在", engineerId);
            throw new ParamException("服务工程师在微信端系统中不存在!");
        }
        // 更新该受理单的师傅信息
        int status = SystemConstants.RECEIVE;
        Date date = new Date();
        this.dispatch(acceptNumber, engineerId, date, status);
        //更新产品关联表
        ordersProRelationsMapper.updateStatusByOrdersId(wechatOrders.getId());
        // 录入受理记录表
        WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
        wechatOrdersRecord.setOrderId(wechatOrders.getId());
        wechatOrdersRecord.setRecordContent(engineerName + "已接单");
        wechatOrdersRecord.setRecordTime(date);
        wechatOrdersRecordService.saveWechatOrdersRecord(wechatOrdersRecord);
        // 发送短信通知提醒
        WechatUser wechatUser = wechatUserMapper.getUserByUserId(wechatOrders.getUserId());
        String mobilePhone = (null != wechatUser) ? wechatUser.getMobilePhone() : "";
        String engineerPhone = qyhUser.getMobile();
        if (!mobilePhone.equalsIgnoreCase(dispatchMobile)) {
            String serviceType = OrderUtils.getServiceTypeName(wechatOrders.getOrderType());
            String orderTime = DateUtil.DateToString(wechatOrders.getOrderTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
            String msgContent = "亲爱的用户，您预约的" + serviceType + "服务已成功派单。工程师上门服务时间:"
                    + orderTime + "。请保持电话畅通，届时工程师将与您联系沟通具体上门服务时间。";
            //短信开口关闭 2019年06月19日
            //mobileService.sendContentByEmay(mobilePhone, msgContent, Constant.CUSTOMER);
        }
        // 派单给师傅后给用户发送模板消息
        String userId = wechatOrders.getUserId();
        if (!userId.equalsIgnoreCase(dispatchUserId)) {
            sendDispatchMasterTemplateMsg(wechatOrders, engineerName, engineerPhone);
        }
        // 派单给师傅后给工程师发送通知
        sendEngineerMsgText(engineerId, engineerPhone, wechatOrders);
    }

    @Transactional
    @Override
    public void dispatchCompleteOrder(DispatchOrderParam dispatchOrderParam) throws Exception {
        if (null == dispatchOrderParam) {
            throw new ParamException("请求参数不合法!");
        }
        String signture = dispatchOrderParam.getSignture();
        String timeStamp = dispatchOrderParam.getTimeStamp();

        boolean isLegal = SignUtil.checkSignature(signture, timeStamp, Constant.AUTH_KEY);
        if (!isLegal) {
            throw new ParamException(Constant.ILLEGAL_REQUEST);
        }

        String acceptNumber = dispatchOrderParam.getAcceptNumber();
        String finishNumber = dispatchOrderParam.getFinishNumber();

        if (StringUtils.isEmpty(acceptNumber) || StringUtils.isEmpty(finishNumber)) {
            LOG.info("受理单号和完工单号名称为空", acceptNumber, finishNumber);
            throw new ParamException("受理单号和完工单号名称不能为空!");
        }
        // 校验受理单是否存在
        WechatOrders wechatOrders = this.getWechatOrdersByCode(acceptNumber);
        if (wechatOrders == null) {
            LOG.info("受理单号[{}]无效", acceptNumber);
            throw new ParamException("受理单号无效!");
        }

        Date date = new Date();

        int count = wechatOrdersMapper.updateOrdersNoAndStatus(acceptNumber, finishNumber, date, SystemConstants.COMPLETE);
        if (count > 0) {
            // 录入受理记录表
            WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
            wechatOrdersRecord.setOrderId(wechatOrders.getId());
            wechatOrdersRecord.setRecordContent("csm端完工!");
            wechatOrdersRecord.setRecordTime(date);
            wechatOrdersRecordService.saveWechatOrdersRecord(wechatOrdersRecord);
        } else {
            throw new ParamException("完工失败,该工单在微信端系统中不存在!");
        }
    }

    @Override
    public int getDispatchOrderNumByDate(String orderDate) {
        return wechatOrdersMapper.getDispatchOrderNumByDate(orderDate);
    }

    @Override
    public void testSendSms(Integer ordersType, String mobile) {
        // 异步发送营销短信
        smsSendRecordService.sendDispatchSms(ordersType, mobile);
    }

    //给小程序推送预约成功
    @Override
    @Async
    public void syncMakeAppointment(String scOrderItemId, String ordersCode,
                                    String serviceFeeIds) {
        LOG.info("预约信息同步到小程序,scOrderItemId:" + scOrderItemId + "   ordersCode:" + ordersCode + "   serviceFeeIds:" + serviceFeeIds);
        // 异步推送给小程序对接方
        Map<String, Object> params = new HashMap<String, Object>();
        long timestamp = System.currentTimeMillis();
        params.put("orderItemId", scOrderItemId);
        params.put("serviceFeeIds", serviceFeeIds);
        params.put("ordersCode", ordersCode);
        params.put("timestamp", timestamp);
        params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
        String result = HttpClientUtils.postJson(makeAppointmentUrl, JSONObject.fromObject(params).toString());
        if (StringUtils.isNotBlank(result)) {
            JSONObject o1 = JSONObject.fromObject(result);
            if (o1.containsKey("errorCode") && o1.getInt("errorCode") == 200) {
                String fAid = o1.getString("data");
                LOG.info("预约信息同步到小程序成功,aid:{}", fAid);
            } else {
                LOG.error("预约信息信息同步到小程序失败,moreInfo:{}", o1.getString("moreInfo"));
            }
        }
    }

    @Override
    public void updateMakeAppointment(String oldOrdersCode, String newOrdersCode) {
        LOG.info("预约信息更改同步到小程序,oldOrdersCode:{}", oldOrdersCode);
        LOG.info("预约信息更改同步到小程序,newOrdersCode:{}", newOrdersCode);
        // 异步推送给小程序对接方
        Map<String, Object> params = new HashMap<String, Object>();
        long timestamp = System.currentTimeMillis();
        params.put("oldOrdersCode", oldOrdersCode);
        params.put("newOrdersCode", newOrdersCode);
        params.put("timestamp", timestamp);
        params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
        String result = HttpClientUtils.postJson(updatemakeAppointmentUrl, JSONObject.fromObject(params).toString());
        if (StringUtils.isNotBlank(result)) {
            JSONObject o1 = JSONObject.fromObject(result);
            if (o1.containsKey("errorCode") && o1.getInt("errorCode") == 200) {
                String fAid = o1.getString("data");
                LOG.info("预约信息更改同步到小程序成功,aid:{}", fAid);
            } else {
                LOG.error("预约信息信息更改同步到小程序失败,moreInfo:{}", o1.getString("moreInfo"));
            }
        }
    }

    @Override
    public void cancelMakeAppointment(String ordersCode) {
        LOG.info("取消预约信息同步到小程序,ordersCode:{}", ordersCode);
        // 异步推送给小程序对接方
        Map<String, Object> params = new HashMap<String, Object>();
        long timestamp = System.currentTimeMillis();
        params.put("ordersCode", ordersCode);
        params.put("timestamp", timestamp);
        params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
        String result = HttpClientUtils.postJson(cancelmakeAppointmentUrl, JSONObject.fromObject(params).toString());
        if (StringUtils.isNotBlank(result)) {
            JSONObject o1 = JSONObject.fromObject(result);
            if (o1.containsKey("errorCode") && o1.getInt("errorCode") == 200) {
                String fAid = o1.getString("data");
                LOG.info("取消预约信息同步到小程序成功,aid:{}", fAid);
            } else {
                LOG.error("取消预约信息同步到小程序失败,moreInfo:{}", o1.getString("moreInfo"));
            }
        }
    }

    //获得我的预约列表授权链接
    private String getOrdersListPageOauthUrl() {
        String url = StringUtils.replace(WeChatConstants.SNSAPI_BASE_COMPONENT, "${APPID}", appid);
        url = StringUtils.replace(url, "${STATE}", appid);
        url = StringUtils.replace(url, "{COMPONENT_APPID}", component_appid);
        try {
            String encode = URLEncoder.encode(orderlisturl, "UTF-8");
            url = StringUtils.replace(url, "${REDIRECT_URI}", encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 根据productId获取产品信息(批量)
     * @param productIds
     * @return
     */
    @Override
    public List<ProductVo> getProductInfoById(String productIds) {
        List<Integer> list = new ArrayList<Integer>();

        String[] productArr = productIds.split(",");

        for (String productId : productArr) {
            list.add(Integer.parseInt(productId));
        }
        return productMapper.getProductInfoById(list);
    }

    @Override
    public boolean isYDYHOrder(String orderCode){
        return wechatOrdersMapper.isYDYHOrder(orderCode) != null;
    }

}