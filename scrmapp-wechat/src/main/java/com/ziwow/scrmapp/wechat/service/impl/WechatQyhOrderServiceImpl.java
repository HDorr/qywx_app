package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.persistence.entity.OrdersProRelations;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrders;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrdersRecord;
import com.ziwow.scrmapp.common.persistence.mapper.OrdersProRelationsMapper;
import com.ziwow.scrmapp.common.persistence.mapper.ProductMapper;
import com.ziwow.scrmapp.common.persistence.mapper.WechatOrdersMapper;
import com.ziwow.scrmapp.common.persistence.mapper.WechatOrdersRecordMapper;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.utils.OrderUtils;
import com.ziwow.scrmapp.wechat.service.WechatOrdersService;
import com.ziwow.scrmapp.wechat.service.WechatQyhOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: yiyongchang
 * @Date: 18-7-30 上午9:20
 * @Description: 门店订单保存
 */
@Service
public class WechatQyhOrderServiceImpl implements WechatQyhOrderService {

    private final Logger logger = LoggerFactory.getLogger(WechatQyhOrderServiceImpl.class);

    @Autowired
    private WechatOrdersMapper wechatOrdersMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrdersProRelationsMapper ordersProRelationsMapper;
    @Autowired
    private WechatOrdersRecordMapper wechatOrdersRecordMapper;
    @Autowired
    private MobileService mobileService;
    @Autowired
    private WechatOrdersService wechatOrdersService;

    @Transactional
    @Override
    public WechatOrders addWechatQyhOrder(WechatOrders wechatOrders, List<Product> products) {

        wechatOrdersMapper.insertSelective(wechatOrders);

        List<OrdersProRelations> ordersProRelationsList = new ArrayList<OrdersProRelations>();
        for (Product p : products) {
            productMapper.insertCrmProduct(p);
            OrdersProRelations ordersProRelations = new OrdersProRelations();
            ordersProRelations.setProductId(p.getId());
            ordersProRelations.setOrderId(wechatOrders.getId());
            ordersProRelationsList.add(ordersProRelations);
        }
        //批量插入t_orders_pro_relations
        ordersProRelationsMapper.batchInsert(ordersProRelationsList);

        //门店预约订单生成后发送短信和模板
        String serverType = OrderUtils.getServiceTypeName(wechatOrders.getOrderType());
        String mobilePhone = wechatOrders.getContactsMobile();
        String msgContent = "亲爱的用户，您预约的" + serverType + "服务已成功提交，我们将尽快为您派单。您可进入“沁园”官方微信服务号查看订单状态。";
        //发送短信
        try {
            mobileService.sendContentByEmay(mobilePhone, msgContent, Constant.CUSTOMER);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送短信失败",e);
        }
        //预约提交成功模板消息提醒
        wechatOrdersService.sendAppointmentTemplateMsg(wechatOrders.getOrdersCode(), serverType);
        //保存受理记录
        WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
        wechatOrdersRecord.setOrderId(wechatOrders.getId());
        wechatOrdersRecord.setRecordTime(new Date());
        wechatOrdersRecord.setRecordContent("用户下单");
        wechatOrdersRecordMapper.insert(wechatOrdersRecord);

        return wechatOrders;
    }

    @Override
    public String getUserIdByPhone(String userPhone) {
        return wechatOrdersMapper.getUserIdByPhone(userPhone);
    }
}
