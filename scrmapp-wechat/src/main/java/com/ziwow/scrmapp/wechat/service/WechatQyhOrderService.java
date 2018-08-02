package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrders;

import java.util.List;

public interface WechatQyhOrderService {
    WechatOrders addWechatQyhOrder(WechatOrders wechatOrders, List<Product> products);

    String getUserIdByPhone(String userPhone);
}
