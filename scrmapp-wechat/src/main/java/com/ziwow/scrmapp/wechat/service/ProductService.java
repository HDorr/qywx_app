package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.persistence.entity.FilterLevel;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCardItems;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatArea;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCity;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatProvince;
import com.ziwow.scrmapp.wechat.vo.ProductVo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLDataException;
import java.util.List;

/**
 * Created by xiaohei on 2017/4/5.
 */
public interface ProductService {
    //    List<ProductSeries> getAllProductSeries();
    List<WechatProvince> getAllProductSeries();

    //List<ProductType> getTypeBySeriesId(Long seriesId);
    List<WechatCity> getTypeBySeriesId(Long seriesId);


    //List<ProductModel> getModelByTypeId(Long typeId);
    List<WechatArea> getModelByTypeId(Long typeId);

    List<Product> getProductsByUserId(String userId);

    ProductVo queryProduct(String modelName, String productBarCode);

    Long save(Product product) throws SQLDataException;

    ProductVo getProductById(Long productId);

    Product getProductPrimaryKey(Long id);

    int openOrCloseRemind(Long productId);

    int batchSave(List<Product> products, List<FilterLevel> filterLevels) throws SQLDataException;

    String queryProductImage(String modelName);

    ProductVo getBaseProduct(Long productId);

    int deleteProduct(Long productId, String userId);

    void syncHistroyProductItem(String mobilePhone, String userId);
    void syncHistroyProductItemFromCem(String mobilePhone, String userId);
    void syncHistroyProductItemFromCemTemp(String mobilePhone, String userId);

    void productBindTemplateMsg(Product product);

    int checkSecurityCode(HttpServletRequest request, String code);

    public ModelAndView userScan(HttpServletRequest request, HttpServletResponse response, String code, String barCode);

    List<com.ziwow.scrmapp.common.bean.vo.ProductVo> findByOrderId(Long orderId);

    void syncProdBindToMiniApp(String userId, String productCode, boolean isFirst);

    void syncProdUnbindToMiniApp(String userId, String productCode);

    boolean isFirstBindProduct(String userId);

    //根据productId获取产品信息(批量)
    List<Product> getProductsByIds(List<Integer> list);

    //根据modelName获取产品
    Product getProductByModelName(String modelName);

    int updateByPrimaryKeySelective(Product record);


    /**
     * 根据userId和产品的条码查询产品
     * @param userId
     * @param barCode
     * @return
     */
    Product getProductsByBarCodeAndUserId(String userId, String barCode);

    /**
     * 根据型号和用户id查询产品
     * @param itemName
     * @param userId
     * @return
     */
    List<Product> getProductByModelNameAndUserId(String itemName, String userId);

    /**
     * 根据编码集合和用户id查询产品
     * @param productCode
     * @param userId
     * @return
     */
    List<Product> getProductByProductCodeAndUserId(List<EwCardItems> productCode, String userId);

    /**
     * 校验用户是否绑定该款产品
     * @param userId
     * @param productBarCode
     * @return  有该产品就是true 反之false
     */
    boolean isOnlyBindProduct(String userId, String productBarCode);
}
