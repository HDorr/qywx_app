package com.ziwow.scrmapp.common.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziwow.scrmapp.common.bean.vo.ProductFinishVo;
import com.ziwow.scrmapp.common.bean.vo.ProductVo;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import org.apache.ibatis.annotations.Select;

public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    List<ProductVo> getProductInfoById(List<Integer> list);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> findByUserId(String userId);

    int batchSave(List<Product> products);

    int updateProductImg(@Param("modelName") String modelName, @Param("productImage") String productImage);

    int updateRemind(@Param("productId") Long productId, @Param("filterRemind") int filterRemind);

    Product findBaseInfoById(Long productId);

    int updateStatus(@Param("productId") Long productId, @Param("userId") String userId);

    int updateBarCode(@Param("productBarCode") String productBarCode, @Param("barCodeImage") String barCodeImage, @Param("productId") Long productId);

    int updateProductBarCode(ProductVo productVo);
    
    int updateBarCodeImage(@Param("barCodeImage") String barCodeImage, @Param("productId") Long productId);

    int isBindProduct(@Param("userId") String userId, @Param("barCode") String barCode);

    int updateUserIdById(@Param("userId") String userId, @Param("productId") Long id);

    int updateSmallcNameByOrderCode(@Param("smallcName") String smallcName, @Param("productCode") String productCode);

    List<ProductVo> selectByOrdersId(Long ordersId);

    List<ProductVo> selectByOrdersIds(Long[] ordersIds);

    List<ProductFinishVo> getFinishedOrdersDetail(Long ordersId);

    List<Product> getProductsByIds(List<Integer> list);

    @Select("SELECT * FROM t_product WHERE modelName=#{modelName}")
    Product getProductByModelName(@Param("modelName")String modelName);

    int countProductByUserIdWithoutStatus(@Param("userId") String userId);

    int updateProductStatus(@Param("ordersId") Long ordersId,@Param("productId") Long productId);

    List<Integer> getAllStatus(@Param("ordersId") Long ordersId);
}