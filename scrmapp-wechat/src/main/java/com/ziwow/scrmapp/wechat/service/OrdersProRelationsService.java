package com.ziwow.scrmapp.wechat.service;

public interface OrdersProRelationsService {

    int batchSave(Long oldOrderId, Long newOrdersId);
}
