package com.ziwow.scrmapp.qyh.service;

import com.ziwow.scrmapp.common.bean.vo.ProductVo;
import com.ziwow.scrmapp.common.persistence.entity.Filter;
import com.ziwow.scrmapp.common.persistence.entity.MaintainPrice;
import com.ziwow.scrmapp.common.persistence.entity.Product;

import java.util.List;


/**
 * Created by xiaohei on 2017/5/31.
 */
public interface QyhProductService {

    Product findById(Long productId);

    void updateProduct(Product product);

    List<Filter> findByLevelId(Long levelId);

    void batchSave(String modelName, String productCode);

    void batchSaveMaintainItem(String modelName, String productCode);

    List<MaintainPrice> findByProductCode(String modelName);

    int updateFilterRemind(Product product);

    String queryProductImage(String modelName);

    List<ProductVo> findByOrdersId(Long ordersId);

    int updateProductStatus(Long ordersId,Long productId);

    List<Integer> getAllStatus(Long ordersId);
}
