package com.ziwow.scrmapp.common.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziwow.scrmapp.common.bean.pojo.CancelProductParam;
import com.ziwow.scrmapp.common.persistence.entity.OrdersProRelations;

public interface OrdersProRelationsMapper {
    public int batchInsert(List<OrdersProRelations> list);

    List<OrdersProRelations> findByOrderId(Long orderId);

    int updateStatusByOrdersId(@Param("orderId") Long orderId);

    int updateStatus(@Param("productId") List<Long> productId, @Param("orderId") Long orderId, @Param("qyhUserId") int qyhUserId);

    public int cancelProduct(CancelProductParam cancelProductParam);

    public List<OrdersProRelations> getListByOrderId(Long orderId);
}
