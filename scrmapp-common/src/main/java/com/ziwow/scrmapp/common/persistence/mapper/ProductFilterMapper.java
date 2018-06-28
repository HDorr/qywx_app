package com.ziwow.scrmapp.common.persistence.mapper;


import java.util.List;

import com.ziwow.scrmapp.common.persistence.entity.ProductFilter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ProductFilterMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ProductFilter record);

    int insertSelective(ProductFilter record);

    ProductFilter selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductFilter record);

    int updateByPrimaryKey(ProductFilter record);

    //@Select("SELECT * FROM t_product_filter WHERE order_id=#{orderId}")
    List<ProductFilter> getProductFilterListByOrderId(@Param("orderId")String orderId);

    @Update("update t_product_filter set pay_status=#{payStatus} where order_id=#{orderId}")
    public int updateProductFilterByOrderId(@Param("orderId")String orderId, @Param("payStatus")int payStatus);

    @Update("update t_product_filter set sync_status=#{syncStatus} where order_id=#{orderId}")
    public int syncProductFilterByOrderId(@Param("orderId")String orderId, @Param("syncStatus")int syncStatus);

    @Select("SELECT order_id FROM t_product_filter WHERE model_name=#{modelName}")
    public String getOrderIdFromProductFilterByModelName(@Param("modelName")String modelName);

    @Select("SELECT * FROM t_product_filter WHERE id=#{id}")
    ProductFilter getProductFilterById(@Param("id")Long id);

    @Update("update t_product_filter set pay_status=#{payStatus}, refund_id=#{refundId} where model_name=#{modelName}")
    public int updateProductFilterByModelName(@Param("modelName")String modelName,
                                              @Param("payStatus")int payStatus, @Param("refundId")String refundId);

    @Select("SELECT SUM(service_fee) FROM t_product_filter WHERE order_id=#{orderId}")
    public int getTotalFeeByOrderId(@Param("orderId")String orderId);

    @Select("SELECT * FROM t_product_filter WHERE model_name=#{modelName}")
    ProductFilter getProductFilterByModelName(@Param("modelName")String modelName);

    //@Select("SELECT * FROM t_product_filter WHERE product_id=#{pid}")
    ProductFilter getProductFilterByProductId(@Param("pid")Long pid);
}