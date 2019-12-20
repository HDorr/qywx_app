package com.ziwow.scrmapp.common.persistence.mapper;

import java.util.List;

import com.ziwow.scrmapp.common.pagehelper.Page;
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

    List<ProductVo> selectByOrdersCode(@Param("ordersCode") String ordersCode);

    List<ProductVo> selectByOrdersIds(Long[] ordersIds);

    List<ProductFinishVo> getFinishedOrdersDetail(Long ordersId);

    List<Product> getProductsByIds(List<Integer> list);

    @Select("SELECT * FROM t_product WHERE modelName=#{modelName} limit 1")
    Product getProductByModelName(@Param("modelName")String modelName);

    int countProductByUserIdWithoutStatus(@Param("userId") String userId);

    int updateProductStatus(@Param("ordersId") Long ordersId,@Param("productId") Long productId);

    List<Integer> getAllStatus(@Param("ordersId") Long ordersId);

    /**
     * 根据 userId 和 编码查
     * @param userId
     * @param barCode
     * @return
     */
    Product getProductsByBarCodeAndUserId(@Param("userId") String userId, @Param("barCode") String barCode);

    /**
     * 根据型号和用户id查询产品
     * @param itemName
     * @param userId
     * @return
     */
    @Select("SELECT * FROM t_product WHERE modelName=#{itemName} and userId = #{userId}  and status = 1 order by id desc")
    List<Product> getProductByModelNameAndUserId(@Param("itemName") String itemName, @Param("userId") String userId);

    /**
     * 根据编码和用户id查询产品
     * @param productCodes
     * @param userId
     * @return
     */
    List<Product> getProductByProductCodeAndUserId(@Param("productCodes") List<String> productCodes, @Param("userId") String userId);


    /**
     * 根据用户和条码查询产品信息
     * @param userId
     * @param productBarCode
     * @return
     */
    int countProductByUserIdAndproductBarCode(@Param("userId") String userId, @Param("productBarCode") String productBarCode);

    /**
     * 根据产品条码查询产品信息
     * @param barCode
     * @return
     */
    Product getProductsByBarCode(@Param("barCode") String barCode);

    /**
     * 分页查询产品列表
     * @param userId String
     * @return List
     */
    List<Product> selectPageByUserId(@Param("userId") String userId, @Param("page") Page page);

    /**
     * 查询产品总数
     * @param userId String
     * @return long
     */
    long selectCount(@Param("userId") String userId);

    /**
     * 根据用户userId查询产品信息
     * @param userId
     * @return
     */
    @Select("SELECT * FROM t_product  as p WHERE p.userId =#{userId} AND p.status=1 AND p.productBarCode != '' and p.productBarCode IS NOT NULL  ORDER BY id")
    List<Product> selectByUserIdOrderById(@Param("userId") String userId);
}