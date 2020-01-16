package com.ziwow.scrmapp.wechat.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ziwow.scrmapp.common.bean.pojo.ProductFilterGradeParam;
import com.ziwow.scrmapp.common.bean.pojo.ProductParam;
import com.ziwow.scrmapp.common.bean.vo.SecurityVo;
import com.ziwow.scrmapp.common.bean.vo.cem.BuyDevicesBean;
import com.ziwow.scrmapp.common.bean.vo.cem.CemProductInfo;
import com.ziwow.scrmapp.common.bean.vo.csm.ProductFilterGrade;
import com.ziwow.scrmapp.common.bean.vo.csm.ProductItem;
import com.ziwow.scrmapp.common.bean.vo.mall.MallOrderVo;
import com.ziwow.scrmapp.common.bean.vo.mall.OrderItem;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.SystemConstants;
import com.ziwow.scrmapp.common.pagehelper.Page;
import com.ziwow.scrmapp.common.persistence.entity.Filter;
import com.ziwow.scrmapp.common.persistence.entity.FilterChangeRemind;
import com.ziwow.scrmapp.common.persistence.entity.FilterLevel;
import com.ziwow.scrmapp.common.persistence.entity.LevelFilterRelations;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.mapper.*;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.common.utils.HttpKit;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.BeanUtils;
import com.ziwow.scrmapp.tools.utils.CookieUtil;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.tools.utils.HttpClientUtils;
import com.ziwow.scrmapp.tools.utils.IPUtil;
import com.ziwow.scrmapp.tools.utils.MD5;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.enums.BuyChannel;
import com.ziwow.scrmapp.wechat.enums.SaleType;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCardItems;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatArea;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCity;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatProvince;
import com.ziwow.scrmapp.wechat.persistence.mapper.ProductModelMapper;
import com.ziwow.scrmapp.wechat.persistence.mapper.ProductSeriesMapper;
import com.ziwow.scrmapp.wechat.persistence.mapper.ProductTypeMapper;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatFansMapper;
import com.ziwow.scrmapp.wechat.service.ProductService;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatTemplateService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import com.ziwow.scrmapp.wechat.utils.BarCodeConvert;
import com.ziwow.scrmapp.wechat.vo.ProductVo;
import com.ziwow.scrmapp.wechat.vo.WechatFansVo;
import com.ziwow.scrmapp.wechat.vo.WechatUserVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.SQLDataException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaohei on 2017/4/5.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    WechatUserService wechatUserService;

    @Autowired
    WechatFansMapper wechatFansMapper;

    @Value("${miniapp.product.bind}")
    private String syncProdBindUrl;

    @Value("${miniapp.product.unbind}")
    private String delProdBindUrl;

    @Value("${mine.baseUrl}")
    private String mineBaseUrl;

    @Autowired
    private WechatOrdersMapper wechatOrdersMapper;


//    public List<ProductSeries> getAllProductSeries() {
//        return seriesMapper.findAll();
//    }

//    public List<ProductType> getTypeBySeriesId(Long seriesId) {
//        return typeMapper.findBySeriesId(seriesId);
//    }

//    public List<ProductModel> getModelByTypeId(Long typeId) {
//        return modelMapper.findByTypeId(typeId);
//    }

    @Override
    public List<WechatProvince> getAllProductSeries() {
        return seriesMapper.findAll();
    }

    @Override
    public List<WechatCity> getTypeBySeriesId(Long seriesId) {
        return typeMapper.findBySeriesId(seriesId);
    }

    @Override
    public List<WechatArea> getModelByTypeId(Long typeId) {
        return modelMapper.findByTypeId(typeId);
    }

    @Override
    public List<Product> getProductsByUserId(String userId) {
        return productMapper.findByUserId(userId);
    }

    @Override
    public List<Product> pageProductsByUserId(String userId, Page page) {
        return productMapper.selectPageByUserId(userId, page);
    }

    @Override
    public long getCountByUserId(String userId) {
        return productMapper.selectCount(userId);
    }

    /**
     * 通过条形码或者产品型号或产品U9编码查询产品信息
     * （需要调用沁园查询接口）
     *
     * @param modelName
     * @param productBarCode
     * @param productEncode
     * @return
     */
    @Override
    public ProductVo queryProduct(String modelName, String productBarCode,
        String productEncode) {
        ProductVo product = null;
        /*调用沁园查询产品接口方法*/
        ProductItem productItem = thirdPartyService.getProductItem(new ProductParam(modelName, productBarCode,productEncode));

        if (productItem != null) {
            product = new ProductVo();
            //实例化对象
            product.setItemKind(productItem.getItemKind());
            product.setTypeName(productItem.getBigcName());
            product.setSmallcName(productItem.getSmallcName());
            product.setModelName(productItem.getSpec());
            product.setLevelId(productItem.getFilterGradeId());
            product.setLevelName(productItem.getFilterGrade());
            product.setProductName(productItem.getItemName());
            product.setProductCode(productItem.getItemCode());
            if(null!=productBarCode){
                product.setBuyTime(thirdPartyService.getPurchDate(productBarCode));
            }
            if (!StringUtils.isEmpty(productItem.getBarcode())) {
                product.setProductBarCode(productItem.getBarcode());
                product.setSaleType(productItem.getFromChannel());
                product.setSaleTypeName(product.getSaleType() == null ? "无" : SaleType.getNameById(product.getSaleType()));
            }
        } else {
            throw new NullPointerException("未查到该产品!");
        }
        return product;
    }




    @Transactional(rollbackFor = SQLDataException.class)
    @Override
    public Long save(Product product) throws SQLDataException {
        //增加产品
        productMapper.insert(product);
        if (product.getId() == null) {
            throw new SQLDataException("产品绑定失败!");
        }

        //更新产品表中的smallcName字段
//        if (!StringUtils.isEmpty(product.getSmallcName())) {
//            productMapper.updateSmallcNameByOrderCode(product.getSmallcName(), product.getProductCode());
//        }

        //增加滤芯级别
        saveFilterLevel(product.getLevelId(), product.getLevelName());


        ProductVo productVo = new ProductVo();
        BeanUtils.copyProperties(product, productVo);
        productVo.setFilterList(new ArrayList<Filter>());

        List<Filter> filters = filterMapper.findByLevelId(product.getLevelId());
        if (filters.size() == 0) {
            //增加滤芯与级别的关系
            batchSave(productVo);
        }

        if (!"0".equals(product.getLevelId())) {
            filters = filterMapper.findByLevelId(product.getLevelId());
            if (filters.size() > 0) {

                List<FilterChangeRemind> filterChangeReminds = new ArrayList<FilterChangeRemind>();
                for (Filter f : filters) {
                    FilterChangeRemind filterChangeRemind = new FilterChangeRemind();
                    filterChangeRemind.setProductId(product.getId());
                    filterChangeRemind.setFilterId(f.getId());
                    filterChangeRemind.setStatus(product.getFilterRemind());
                    filterChangeRemind.setRemindTime(DateUtil.getAfterDay(product.getBuyTime(), f.getMaintain()));
                    filterChangeReminds.add(filterChangeRemind);
                }

                //增加滤芯提醒
                filterChangeRemindMapper.batchSave(filterChangeReminds);
            }
        }

        return product.getId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFilterLevel(Long levelId, String levelName) {
        filterLevelMapper.insert(new FilterLevel(levelId, levelName));

    }

    /**
     * 显示产品详细信息
     * <p>
     * <if> 滤芯等级id不等于0，同时查询不到对应的滤芯记录调用沁园获取滤芯接口，将滤芯数据保存</if>
     * <p>
     * <else>直接显示数据库中查询的数据</else>
     *
     * @param productId
     * @return
     */
    @Override
    public ProductVo getProductById(Long productId) {
        ProductVo productVo = new ProductVo();
        Product product = productMapper.selectByPrimaryKey(productId);
        BeanUtils.copyProperties(product, productVo);
        productVo.setSaleTypeName(product.getSaleType() == null ? "无" : SaleType.getNameById(product.getSaleType()));
        if (product.getO2o() != null && product.getBuyChannel() != null) {
            productVo.setChannelName(BuyChannel.getBuyChannel(product.getO2o(), product.getBuyChannel()));
        } else {
            productVo.setChannelName("其它");
        }
//        //查询滤芯级别对应的滤芯
//        if (product.getLevelId() != null && product.getLevelId() != 0) {
//            List<Filter> filterList = filterMapper.findByLevelId(product.getLevelId());
//            productVo.setFilterList(filterList);
//            if (filterList.size() == 0) {
//                batchSave(productVo);
//            }
//        }
        return productVo;
    }

    @Override
    public Product getProductPrimaryKey(Long id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchSave(ProductVo product) {
        //调用沁园接口查询产品滤芯
        ProductFilterGradeParam productFilterGradeParam = new ProductFilterGradeParam();
        productFilterGradeParam.setSpec(product.getModelName());
        productFilterGradeParam.setItemCode(product.getProductCode());
        List<ProductFilterGrade> itemFilterGrade = thirdPartyService.getItemFilterGrade(productFilterGradeParam);
        if (itemFilterGrade.size() > 0) {
            List<LevelFilterRelations> filterRelations = new ArrayList<LevelFilterRelations>();
            List<Filter> filterList = product.getFilterList();
            for (ProductFilterGrade p : itemFilterGrade) {
                //添加滤芯
                Filter filter = new Filter();
                filter.setId(Long.parseLong(p.getGradeSeq()));
                filter.setFilterName(p.getGradeName());
                filter.setMaintain(p.getExpectRplDays());
                filter.setPrice(new BigDecimal(p.getFilterPrice()));

                filterList.add(filter);

                //添加级别与滤芯间的关系
                LevelFilterRelations relations = new LevelFilterRelations();
                relations.setLevelId(Long.parseLong(p.getFilterGradeId() + ""));
                relations.setFilterId(filter.getId());

                filterRelations.add(relations);
            }
            filterMapper.batchSave(filterList);
            levelFilterRelationsMapper.batchSave(filterRelations);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int openOrCloseRemind(Long productId) {
        int filterRemind = 0;
        Product product = productMapper.selectByPrimaryKey(productId);
        /**
         * null 从未开启滤芯提醒功能 开启滤芯提醒
         * 1 开启滤芯提醒状态 关闭滤芯提醒
         * 2 关闭滤芯提醒状态 开启提醒状态
         */
        int count = 0;
        if (product.getFilterRemind() == null) {
            List<Filter> filterList = filterMapper.findByLevelId(product.getLevelId());
            List<FilterChangeRemind> reminds = new ArrayList<FilterChangeRemind>();
            for (Filter f : filterList) {
                //产品对应多个滤芯
                FilterChangeRemind remind = new FilterChangeRemind();
                remind.setFilterId(f.getId());
                remind.setProductId(productId);
                remind.setStatus(SystemConstants.REMIND);
                //提醒时间
                remind.setRemindTime(DateUtil.getAfterDay(product.getBuyTime(), f.getMaintain()));
                reminds.add(remind);
            }
            count = filterChangeRemindMapper.batchSave(reminds);
            filterRemind = SystemConstants.REMIND;
        } else if (SystemConstants.REMIND == product.getFilterRemind()) {
            count = filterChangeRemindMapper.updateStatus(productId, SystemConstants.CLOSE);
            filterRemind = SystemConstants.CLOSE;
        } else if (SystemConstants.CLOSE == product.getFilterRemind()) {
            count = filterChangeRemindMapper.updateStatus(productId, SystemConstants.REMIND);
            filterRemind = SystemConstants.REMIND;
        }

        //提醒表有变化时才更新产品表
        if (count > 0) {
            //更新产品提醒状态
            productMapper.updateRemind(productId, filterRemind);
        }

        return filterRemind;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int batchSave(List<Product> products, List<FilterLevel> filterLevels) throws SQLDataException {
        //批量插入产品
        int count = productMapper.batchSave(products);

        if (count > 0) {
            //批量插入滤芯级别
            filterLevelMapper.batchSave(filterLevels);
        }

        return count;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateProductImage(String modelName, String productImg) {
        productMapper.updateProductImg(modelName, productImg);
    }


    @Override
    public String queryProductImage(String modelName) {
        //String productImg = thirdPartyService.getProductImg(modelName);
        String productImg = "https://wx.qinyuan.cn/wx/resources/images/defaultPdtImg.jpg";
//        if (!StringUtils.isEmpty(productImg)) {
//            updateProductImage(modelName, productImg);
//        }
        return productImg;
    }

    @Override
    public ProductVo getBaseProduct(Long productId) {
        Product product = productMapper.findBaseInfoById(productId);
        ProductVo productVo = new ProductVo();
        BeanUtils.copyProperties(product, productVo);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String buyTime = StringUtil.EMPTY_STRING;
        if (product.getBuyTime() != null) {
            buyTime = dateFormat.format(product.getBuyTime());
        } else {
            buyTime = dateFormat.format(new Date());
        }
        productVo.setBuyTime(buyTime);

        if (product.getO2o() != null && product.getBuyChannel() != null) {
            productVo.setChannelName(BuyChannel.getBuyChannel(product.getO2o(), product.getBuyChannel()));
        } else {
            productVo.setChannelName("其它");
        }
        return productVo;
    }

    @Override
    public int deleteProduct(Long productId, String userId) {
        return productMapper.updateStatus(productId, userId);
    }

    @Autowired
    private ProductSeriesMapper seriesMapper;
    @Autowired
    private ProductTypeMapper typeMapper;
    @Autowired
    private ProductModelMapper modelMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private FilterChangeRemindMapper filterChangeRemindMapper;
    @Autowired
    private FilterMapper filterMapper;
    @Autowired
    private FilterLevelMapper filterLevelMapper;
    @Autowired
    private LevelFilterRelationsMapper levelFilterRelationsMapper;
    @Autowired
    private ThirdPartyService thirdPartyService;
    @Autowired
    private WechatTemplateService wechatTemplateService;
    @Autowired
    private WechatFansService wechatFansService;
    @Value("${fwm.security.code.url}")
    private String fwmSecurityCodeUrl;
    @Value("${api.security.code.url}")
    private String apiSecurityCodeUrl;

    /**
     * 用户注册完毕后将历史产品信息同步到系统
     */
    @Async
    @Override
    public void syncHistroyProductItem(String mobilePhone, String userId) {
        try {
            List<Product> prodLst = Lists.newArrayList();
            List<MallOrderVo> mallOrderList = thirdPartyService.queryOrders(mobilePhone);
            List<FilterLevel> filterLevels = new ArrayList<FilterLevel>();
            if (null != mallOrderList && !mallOrderList.isEmpty()) {
                for (MallOrderVo mallOrderVo : mallOrderList) {
                    List<OrderItem> items = mallOrderVo.getItems();
                    String orderId = mallOrderVo.getSn();          // 订单号
                    String createDate = mallOrderVo.getCreateDate();  // 下单时间
                    List<String> itemLst = Lists.newArrayList();
                    for (OrderItem orderItem : items) {
                        String itemCode = orderItem.getItemSn();     // 产品编码
                        itemLst.add(itemCode);
                    }
                    String itemCodes = Joiner.on(",").skipNulls().join(itemLst);
                    List<ProductItem> prodList = thirdPartyService.getProductList(itemCodes);

                    LOG.info("从csm系统的批量获取产品编码为[{}]的产品信息为:[{}]", itemCodes, JSON.toJSONString(prodList));
                    for (ProductItem productItem : prodList) {
                        // 通过产品型号查询csm的产品信息
                        Product product = new Product();
                        product.setTypeName(productItem.getBigcName());
                        product.setModelName(productItem.getSpec());
                        product.setItemKind(productItem.getItemKind());
                        product.setLevelId(Long.getLong(productItem.getFilterGradeId() + ""));
                        product.setLevelName(productItem.getFilterGrade());
                        product.setProductName(productItem.getItemName());
                        product.setSaleType(productItem.getFromChannel());
                        product.setShoppingOrder(orderId);             // 产品单号
                        product.setProductCode(productItem.getItemCode());
                        product.setUserId(userId);
                        product.setO2o(BuyChannel.ONQINYUAN.getO2o());  // 线上、线下
                        product.setBuyChannel(BuyChannel.ONQINYUAN.getChannelId());  // 购买渠道
                        if (StringUtils.isEmpty(createDate)) {
                            product.setBuyTime(new Date());
                        } else {
                            product.setBuyTime(DateUtil.StringToDate(createDate, DateUtil.YYYY_MM_DD));
                        }
                        product.setStatus(1);
                        prodLst.add(product);

                        //增加滤芯级别
                        filterLevels.add(new FilterLevel(product.getLevelId(), product.getLevelName()));
                    }
                }
            }
            // 将数据存入到系统的t_product表中
            if (!prodLst.isEmpty()) {
                batchSave(prodLst, filterLevels);
            }
        } catch (Exception e) {
            LOG.error("同步用户历史购买产品信息接口失败:", e);
        }
    }

    /**
     * 用户注册完毕后将历史产品信息同步到系统
     * fixme 临时接口，等cem数据整理好之后废弃
     */
    @Async
    @Override
    public void syncHistroyProductItemFromCemTemp(String mobilePhone, String userId) {
        try {
            List<Product> prodLst = Lists.newArrayList();

            Result cemAssetsInfo = thirdPartyService.getCemAssetsInfo(mobilePhone);
            List<List<BuyDevicesBean>> devicesBeanList = (List<List<BuyDevicesBean>> ) cemAssetsInfo.getData();

            List<FilterLevel> filterLevels = new ArrayList<FilterLevel>();
            if (null != devicesBeanList && !devicesBeanList.isEmpty()) {
                for (List<BuyDevicesBean> buyDevicesBeanInfoList : devicesBeanList) {

                    String buyTime ="";
                    String productCode= "";
                    for (BuyDevicesBean buyDevicesBean : buyDevicesBeanInfoList) {
                        if ("购买日期".equals(buyDevicesBean.getName())){
                          buyTime=buyDevicesBean.getValue();
                            continue;
                        }else if ("产品编码".equals(buyDevicesBean.getName())){
                            productCode=buyDevicesBean.getValue();
                            continue;
                        }
                    }


                    List<ProductItem> prodList = thirdPartyService.getProductList(productCode);
                    LOG.info("从cem系统获取产品编码为[{}]", productCode);
                    LOG.info("从csm系统获取产品信息为:[{}]", JSON.toJSONString(prodList));
                    for (ProductItem productItem : prodList) {
                        // 通过产品型号查询csm的产品信息
                        Product product = new Product();
                        product.setTypeName(productItem.getBigcName());
                        product.setModelName(productItem.getSpec());
                        product.setItemKind(productItem.getItemKind());
                        product.setLevelId(Long.getLong(productItem.getFilterGradeId() + ""));
                        product.setLevelName(productItem.getFilterGrade());
                        product.setProductName(productItem.getItemName());

                        product.setProductCode(productItem.getItemCode());
                        product.setUserId(userId);
                        product.setO2o(BuyChannel.ONQINYUAN.getO2o());  // 线上、线下
                        product.setBuyChannel(BuyChannel.ONQINYUAN.getChannelId());  // 购买渠道
                        product.setCreateTime(new Date());
                        product.setIsAutoBind(1);//状态1代表自动绑定
                        if (StringUtils.isEmpty(buyTime)) {
                            product.setBuyTime(new Date());
                        } else {
                            product.setBuyTime(DateUtil.StringToDate(buyTime, DateUtil.YYYY_MM_DD));
                        }
                        product.setStatus(1);
                        product.setFilterRemind(2);
                        product.setProductImage(mineBaseUrl+"/resources/images/defaultPdtImg.jpg");

                        prodLst.add(product);

                        //增加滤芯级别
                        filterLevels.add(new FilterLevel(product.getLevelId(), product.getLevelName()));
                    }
                }
            }
            // 将数据存入到系统的t_product表中
            if (!prodLst.isEmpty()) {
                batchSave(prodLst, filterLevels);
                // 绑定产品成功后异步推送给小程序
                for (int i = 0; i < prodLst.size(); i++) {
                    syncProdBindToMiniApp(userId, prodLst.get(i).getProductCode(),i==0,prodLst.get(i).getProductBarCode(), prodLst.get(i).getModelName());
                }
            }
        } catch (Exception e) {
            LOG.error("同步用户历史购买产品信息接口失败:", e);
        }
    }


    /**
     * 用户注册完毕后将历史产品信息同步到系统
     */
    @Async
    @Override
    public void syncHistroyProductItemFromCem(String mobilePhone, String userId) {
        try {
            List<Product> prodLst = Lists.newArrayList();

            Result cemAssetsInfo = thirdPartyService.getCemAssetsInfo(mobilePhone);
            List<List<BuyDevicesBean>> devicesBeanList = (List<List<BuyDevicesBean>> ) cemAssetsInfo.getData();

            List<FilterLevel> filterLevels = new ArrayList<FilterLevel>();
            if (null != devicesBeanList && !devicesBeanList.isEmpty()) {
                for (List<BuyDevicesBean> buyDevicesBeanInfoList : devicesBeanList) {

                    String createDate ="";
                    String productCode= "";
                    String bigType="";
                    String buyChannel="";
                    for (BuyDevicesBean buyDevicesBean : buyDevicesBeanInfoList) {
                        if ("购买日期".equals(buyDevicesBean.getName())){
                            createDate=buyDevicesBean.getValue();
                            continue;
                        }else if ("产品编码".equals(buyDevicesBean.getName())){
                            productCode=buyDevicesBean.getValue();
                            continue;
                        }else if ("产品大类".equals(buyDevicesBean.getName())){
                            bigType=buyDevicesBean.getValue();
                            continue;
                        }else if ("购买渠道".equals(buyDevicesBean.getName())){
                            buyChannel=buyDevicesBean.getValue();
                            continue;
                        }
                    }

                    Result cemProductInfoResult = thirdPartyService.getCemProductInfo(productCode);
                    CemProductInfo productItem = (CemProductInfo) cemProductInfoResult.getData();

                    // 通过产品型号查询cem的产品信息
                    Product product = new Product();
                    product.setTypeName(bigType);
                    product.setModelName(productItem.getModel());
                    product.setItemKind(productItem.getPro_org());
                    product.setProductName(productItem.getName());
//                    product.setSaleType(productItem.getChannel());
                    product.setProductCode(productCode);
                    product.setUserId(userId);
//                    product.setO2o(BuyChannel.ONQINYUAN.getO2o());  // 线上、线下
//                    product.setBuyChannel(BuyChannel.ONQINYUAN.getChannelId());  // 购买渠道
                    if (StringUtils.isEmpty(createDate)) {
                        product.setBuyTime(new Date());
                    } else {
                        product.setBuyTime(DateUtil.StringToDate(createDate, DateUtil.YYYY_MM_DD));
                    }
                    product.setStatus(1);
                    prodLst.add(product);

                    //增加滤芯级别
//                    filterLevels.add(new FilterLevel(product.getLevelId(), product.getLevelName()));
                }
            }
            // 将数据存入到系统的t_product表中
            if (!prodLst.isEmpty()) {
                batchSave(prodLst, filterLevels);
            }
        } catch (Exception e) {
            LOG.error("同步用户历史购买产品信息接口失败:", e);
        }
    }


    @Override
    public void productBindTemplateMsg(Product product) {
        String title = "您刚刚绑定的产品详情如下：";
        String remark = "沁先生教您两招，有效提升净水器使用寿命！1）经常清洗维护净水器，使用得更长久；2）定期更换滤芯，确保水质始终纯净。";
        String userId = product.getUserId();
        WechatFans wechatFans = wechatFansService.getWechatFansByUserId(userId);
        if (null != wechatFans) {
            String openId = wechatFans.getOpenId();
            String productName = product.getProductName();
            String productSpec = product.getModelName();
            wechatTemplateService.bindingToInformTemplateTemplate(openId, null, title, productName, productSpec, remark);
        }
    }

    @Override
    public int checkSecurityCode(HttpServletRequest request, String code) {
        int status = 0;
        if (StringUtil.isNotBlank(code)) {
            Map<String, String> params = Maps.newHashMap();
            params.put("fwm", code.trim());
            try {
                String jsonStr = HttpKit.get(fwmSecurityCodeUrl, params);
                jsonStr = jsonStr.substring(5, jsonStr.length() - 3);
                LOG.info("从fwmJson接口查询防伪码返回信息:[{}]", jsonStr);
                JSONObject jsonObj = JSONObject.parseObject(jsonStr);
                if (null != jsonObj) {
                    // result:0 错误，1正确，2重复
                    status = jsonObj.getIntValue("result");
                }
                // 如果查询结果为0,就调用第二个接口查询
                if (status == 0) {
                    Map<String, String> maps = Maps.newHashMap();
                    maps.put("id", "sqeuiZ8Oq");
                    maps.put("key", "VNqqYYf");
                    maps.put("code", code);
                    maps.put("ip", IPUtil.getIpAddress(request));
                    String resultStr = HttpKit.get(apiSecurityCodeUrl, maps);
                    JSONObject obj = JSONObject.parseObject(resultStr);
                    if (null != obj) {
                        int result = obj.getIntValue("status");
                        // result 0：无效编码；1：查询成功，2：接口出现异常  3 需要验证密码
                        if (result == 1) {
                            status = result;
                        }
                        LOG.info("从api防伪溯源查询防伪码返回信息:[{}]", resultStr);
                    }
                }
            } catch (Exception e) {
                LOG.info("防伪码查询失败，原因:", e);
            }
        }
        return status;
    }

    //用户扫码
    public ModelAndView userScan(HttpServletRequest request, HttpServletResponse response, String code, String barCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        WechatFansVo wechatFansVo = wechatFansService.getOAuthUserInfo(code, request, response);
        try {
            //保存userId到cookie中
            String userId = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            if (!Base64.encode(wechatFansVo.getUserId().getBytes()).equals(userId)) {
                String encode = Base64.encode(wechatFansVo.getUserId().getBytes());
                CookieUtil.writeCookie(request, response, WeChatConstants.SCRMAPP_USER, encode, Integer.MAX_VALUE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取用户信息
        String userId = null;
        if (null != wechatFansVo) {
            userId = wechatFansVo.getUserId();
        }

        if (wechatFansVo.getCode() == 0) {
            //非粉丝,跳转二维码页面,引导关注
            return new ModelAndView("/register/scan_QR_code");
        } else if (wechatFansVo.getCode() == 1) {
            //是粉丝,非会员,跳转注册页面,注册成功后,进入个人中心
            map.put("data", wechatFansVo);
            ModelAndView modelAndView = new ModelAndView("/register/register", map);
            String url = WeChatConstants.USER_HOME_PATH;
            url = request.getContextPath() + url;
            modelAndView.addObject("url", url);
            return modelAndView;
        } else if (wechatFansVo.getCode() == 2) {
            //查询会员是否绑定
            int isBindProduct = productMapper.isBindProduct(userId, BarCodeConvert.convert(barCode));
            if (isBindProduct != 0) {
                //已绑定,跳转真伪查询结果页面
                Result result = this.checkProductIsTrue(request, response, barCode, userId);
                if (result.getReturnCode() == 0) {
                    //鉴定结果为假
                    map.put("data", wechatFansVo.getNickName());
                    return new ModelAndView("/bindProduct/jsp/securityCheckFault", map);
                } else {
                    //鉴定结果为真
                    map.put("data", result.getData());
                    return new ModelAndView("/bindProduct/jsp/securityCheckSuccess", map);
                }
            } else {
                //未绑定,引导绑定
                map.put("data", BarCodeConvert.convert(barCode));
                map.put("securityCode", barCode);
                return new ModelAndView("/bindProduct/jsp/bindPdt", map);
            }
        }
        return new ModelAndView("404");
    }

    @Override
    public List<com.ziwow.scrmapp.common.bean.vo.ProductVo> findByOrderId(Long orderId) {
        return productMapper.selectByOrdersId(orderId);
    }

    @Override
    public List<com.ziwow.scrmapp.common.bean.vo.ProductVo> selectByOrdersCode(String ordersCode){
        return productMapper.selectByOrdersCode(ordersCode);
    }

    @Async
    @Override
    public void syncProdBindToMiniApp(String userId, String productCode, boolean isFirst, String productBarcode, String modelName) {
        // 异步推送给小程序对接方
        WechatFans wechatFans = wechatFansMapper.getWechatFansByUserId(userId);
        String unionId = wechatFans.getUnionId();
        if (null == wechatFans || org.apache.commons.lang.StringUtils.isBlank(unionId)) {
            LOG.info("绑定产品信息同步到小程序失败,unionid不能为空!");
            return;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        long timestamp = System.currentTimeMillis();
        params.put("timestamp", timestamp);
        params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
        params.put("unionId", wechatFans.getUnionId());
        params.put("productCode", productCode);
        params.put("productBarcode", productBarcode);
        params.put("modelName", modelName);
        params.put("isFirst",isFirst);
        LOG.info("绑定产品信息同步到小程序请求参数:{}", JSON.toJSONString(params));
        String result = HttpClientUtils.postJson(syncProdBindUrl, net.sf.json.JSONObject.fromObject(params).toString());
        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            net.sf.json.JSONObject o1 = net.sf.json.JSONObject.fromObject(result);
            if (o1.containsKey("errorCode") && o1.getInt("errorCode") == 200) {
                LOG.info("绑定产品信息同步到小程序成功,unionId:{}", unionId);
            } else {
                LOG.info("绑定产品信息同步到小程序失败,moreInfo:{}", o1.getString("moreInfo"));
            }
        }
    }

    @Async
    @Override
    public void syncProdUnbindToMiniApp(String userId, String productCode) {
// 异步推送给小程序对接方
        WechatFans wechatFans = wechatFansMapper.getWechatFansByUserId(userId);
        String unionId = wechatFans.getUnionId();
        if (null == wechatFans || org.apache.commons.lang.StringUtils.isBlank(unionId)) {
            LOG.info("绑定产品信息同步到小程序失败,unionid不能为空!");
            return;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        long timestamp = System.currentTimeMillis();
        params.put("timestamp", timestamp);
        params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
        params.put("unionId", wechatFans.getUnionId());
        params.put("productCode", productCode);
        String result = HttpClientUtils.postJson(delProdBindUrl, net.sf.json.JSONObject.fromObject(params).toString());
        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            net.sf.json.JSONObject o1 = net.sf.json.JSONObject.fromObject(result);
            if (o1.containsKey("errorCode") && o1.getInt("errorCode") == 200) {
                LOG.info("解绑产品信息同步到小程序成功,unionId:{}", unionId);
            } else {
                LOG.info("解绑产品信息同步到小程序失败,moreInfo:{}", o1.getString("moreInfo"));
            }
        }
    }

    @Override
    public boolean isFirstBindProduct(String userId) {
        return productMapper.countProductByUserIdWithoutStatus(userId)==0;
    }

    /*
     * 鉴定产品真伪接口
     *
     * @param barCode
     *
     * @return Result
     *
     * @title
     */
    public Result checkProductIsTrue(HttpServletRequest request, HttpServletResponse response, String barcode, String userId) {
        Result result = new BaseResult();
        try {
            LOG.info("checkProductIsTrue");
            WechatUserVo wechatUser = wechatUserService.getBaseUserInfoByUserId(userId);
            //用户不存在
            if (wechatUser == null) {
                result.setReturnMsg("用户不存在，请先注册!");
                result.setReturnCode(Constant.FAIL);
                return result;
            }

            //截取省市区
            String area = org.apache.commons.lang.StringUtils.EMPTY;
            if (!StringUtils.isEmpty(wechatUser.getProvinceName())) {
                int position = wechatUser.getProvinceName().indexOf("省");
                if (position != -1) {
                    String province = wechatUser.getProvinceName().substring(0, position);
                    area = province + "-";
                } else {
                    area = wechatUser.getProvinceName() + "-";
                }
                position = wechatUser.getCityName().indexOf("市");
                if (position != -1) {
                    String city = wechatUser.getCityName().substring(0, position);
                    area += city;
                } else {
                    area += wechatUser.getCityName();
                }
            }
            String ciphertext = MD5.toMD5(barcode + "QYFW").toUpperCase();
            result = wechatUserService.securityQuery(barcode, wechatUser.getMobilePhone(), area, ciphertext);
            LOG.info("checkProductIsTrue-----------------" + ((SecurityVo) result.getData()).getDeviceName());
            LOG.info("checkProductIsTrue---------------------" + ((SecurityVo) result.getData()).getProdName());
            LOG.info("checkProductIsTrue----------------" + ((SecurityVo) result.getData()).getBarCode());
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            result.setReturnMsg("查询失败!");
            result.setReturnCode(Constant.FAIL);
        }
        return result;
    }

    @Override
    public List<Product> getProductsByIds(List<Integer> list) {
        return productMapper.getProductsByIds(list);
    }

    @Override
    public Product getProductByModelName(String modelName) {
        return productMapper.getProductByModelName(modelName);
    }


    @Override
    public int updateByPrimaryKeySelective(Product record) {
        return productMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Product getProductsByBarCodeAndUserId(String userId, String barCode) {
        return productMapper.getProductsByBarCodeAndUserId(userId,barCode);
    }


    @Override
    public List<Product> getProductByModelNameAndUserId(String itemName, String userId) {
        return productMapper.getProductByModelNameAndUserId(itemName,userId);
    }

    @Override
    public List<Product> getProductByProductCodeAndUserId(List<EwCardItems> productCode, String userId) {
        List<String> codes = new ArrayList<>(productCode.size());
        for (EwCardItems ewCardItems : productCode) {
            codes.add(ewCardItems.getItemCode());
        }
        return productMapper.getProductByProductCodeAndUserId(codes,userId);
    }

    @Override
    public boolean isOnlyBindProduct(String userId, String productBarCode) {
        return productMapper.countProductByUserIdAndproductBarCode(userId,productBarCode) == 1;
    }

    @Override
    public Product getProductsByBarCode(String productBarCodeTwenty) {
        return productMapper.getProductsByBarCode(productBarCodeTwenty);
    }


    @Override
    public List<com.ziwow.scrmapp.common.bean.vo.ProductVo> getProductByEncode(
        List<String> productEncode) {
        List<com.ziwow.scrmapp.common.bean.vo.ProductVo> list = new ArrayList<>();
        for (String encode : productEncode) {
            final ProductVo productVo = this.queryProduct(null,null,encode);
            com.ziwow.scrmapp.common.bean.vo.ProductVo pv = new com.ziwow.scrmapp.common.bean.vo.ProductVo();
            pv.setProductName(productVo.getProductName());
            pv.setProductBarCode("");
            pv.setModelName(productVo.getModelName());
            pv.setO2o(1);
            pv.setBuyChannel(16);
            pv.setItemKind("1");
            pv.setProductCode(productVo.getProductCode());
            pv.setBuyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            list.add(pv);
        }
        return list;
    }

    @Override
    public Map<String, String> annualReport(String userId) {
        List<Product> products = productMapper.selectByUserIdOrderById(userId);
        Map<String, String> map = null;
        if(CollectionUtils.isNotEmpty(products)){
            int size = products.size();
            Date first = products.get(0).getBuyTime();
            Date last = size != 1 ? products.get(size - 1).getBuyTime() : first;
            String pd = DateUtil.DateToString(first, DateUtil.YYYY_MM_DD);
            String ld = DateUtil.DateToString(last, DateUtil.YYYY_MM_DD);
            String pn = size != 1 ? products.get(size - 1).getProductName()
                : products.get(0).getProductName();
            Map<String, String> maintains = wechatOrdersMapper
                .selectByUserIdMaintainOrders(userId);
            int fn = Integer.parseInt(String.valueOf(maintains.get("2")));
            int mn = Integer.parseInt(String.valueOf(maintains.get("1"))) + Integer
                .parseInt(String.valueOf(maintains.get("0")));
            String wc = "" +DateUtil.DateDiff(new Date(), last)*5;
            String fr = transformRadio(fn);
            String mr = transformRadio(mn);
            String nd = DateUtil.DateToString(new Date(),DateUtil.YYYY_MM_DD);
            map = ImmutableMap.<String, String>builder().put("pd", pd).put("ld", ld).put("pn", pn)
                .put("mn", "" + mn).put("mr", mr).put("fn", "" + fn).put("fr", fr).put("wc", wc)
                .put("nd", nd).put("ib","true").build();
        }
        return map;
    }

    /**
     * 保养次数转换落后比例
     *
     * @param count 次数
     * @return 比例值
     */
    private String transformRadio( int count) {
        double d = 99.9;
        if (count >= 1 && count < 3) {
            d = 83.4;
        } else if (count >= 3 && count < 5) {
            d = 91.3;
        } else if (count >= 5) {
            d = 98.8;
        }
        return "" + d;
    }


}