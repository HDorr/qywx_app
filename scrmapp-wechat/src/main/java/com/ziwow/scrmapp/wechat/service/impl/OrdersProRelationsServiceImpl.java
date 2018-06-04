package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.persistence.entity.OrdersProRelations;
import com.ziwow.scrmapp.common.persistence.mapper.OrdersProRelationsMapper;
import com.ziwow.scrmapp.wechat.service.OrdersProRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdersProRelationsServiceImpl implements OrdersProRelationsService {

    @Autowired
    OrdersProRelationsMapper relationsMapper;

    @Transactional
    @Override
    public int batchSave(Long orderId, Long newOrderId) {
        List<OrdersProRelations> relations = relationsMapper.findByOrderId(orderId);

        for (OrdersProRelations r : relations) {
            r.setOrderId(newOrderId);
        }
        return relationsMapper.batchInsert(relations);
    }
}
