package com.ziwow.scrmapp.qyh.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.ziwow.scrmapp.common.bean.pojo.ProductFilterGradeParam;
import com.ziwow.scrmapp.common.bean.vo.ProductVo;
import com.ziwow.scrmapp.common.bean.vo.csm.ProductFilterGrade;
import com.ziwow.scrmapp.common.persistence.entity.*;
import com.ziwow.scrmapp.common.persistence.mapper.*;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.qyh.service.QyhProductService;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaohei on 2017/5/31.
 */
@Service
public class QyhProductServiceImpl implements QyhProductService {
    @Override
    public Product findById(Long productId) {
        return productMapper.selectByPrimaryKey(productId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateProduct(Product product) {
        productMapper.updateByPrimaryKey(product);
    }

    @Override
    public List<Filter> findByLevelId(Long levelId) {
        return filterMapper.findByLevelId(levelId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchSave(String modelName, String productCode) {
        //调用沁园接口查询产品滤芯
        ProductFilterGradeParam productFilterGradeParam = new ProductFilterGradeParam();
        productFilterGradeParam.setSpec(modelName);
        productFilterGradeParam.setItemCode(productCode);
        List<ProductFilterGrade> itemFilterGrade = thirdPartyService.getItemFilterGrade(productFilterGradeParam);
        FilterLevel filterLevel = new FilterLevel();
        if (itemFilterGrade.size() > 0) {
            List<LevelFilterRelations> filterRelations = new ArrayList<LevelFilterRelations>();
            List<Filter> filterList = new ArrayList<Filter>();
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
                relations.setFilterId(filter.getId());
                relations.setLevelId(Long.parseLong(p.getFilterGradeId() + ""));

                filterRelations.add(relations);

                filterLevel.setId(Long.parseLong(p.getFilterGradeId() + ""));
                filterLevel.setLevelName(p.getFilterGrade());
            }
            //保存滤芯级别
            filterLevelMapper.insert(filterLevel);
            //保存滤芯
            filterMapper.batchSave(filterList);
            //保存滤芯与滤芯级别的关系
            levelFilterRelationsMapper.batchSave(filterRelations);
        }
    }

    @Override
    public List<MaintainPrice> findByProductCode(String productCode) {
        return maintainPriceMapper.findByProductCode(productCode);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int updateFilterRemind(Product product) {
        filterChangeRemindMapper.deleteByProductId(product.getId());
        List<FilterChangeRemind> filterChangeReminds = new ArrayList<FilterChangeRemind>();
        List<Filter> filters = filterMapper.findByLevelId(product.getLevelId());
        //遍历滤芯
        for (Filter filter : filters) {
            FilterChangeRemind filterChangeRemind = new FilterChangeRemind();
            filterChangeRemind.setStatus(product.getFilterRemind());
            filterChangeRemind.setProductId(product.getId());
            filterChangeRemind.setFilterId(filter.getId());
            //设置提醒时间
            filterChangeRemind.setRemindTime(DateUtil.getAfterDay(product.getBuyTime(), filter.getMaintain()));
            filterChangeReminds.add(filterChangeRemind);
        }

        return filterChangeRemindMapper.batchSave(filterChangeReminds);
    }

    @Override
    public String queryProductImage(String modelName) {
        //String productImg = thirdPartyService.getProductImg(modelName);
        String productImg = "https://wx.qinyuan.cn/wx/resources/images/defaultPdtImg.jpg";
        if (!StringUtils.isEmpty(productImg)) {
            updateProductImage(modelName, productImg);
        }
        return productImg;
    }

    @Override
    public List<ProductVo> findByOrdersId(Long ordersId) {
        return productMapper.selectByOrdersId(ordersId);
    }

    @Override
    public int updateProductStatus(Long ordersId, Long productId) {
        return productMapper.updateProductStatus(ordersId, productId);
    }

    @Override
    public List<Integer> getAllStatus(Long ordersId) {
        return productMapper.getAllStatus(ordersId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateProductImage(String modelName, String productImg) {
        productMapper.updateProductImg(modelName, productImg);
    }

    /**
     * 批量增加保养项
     *
     * @param productCode
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchSaveMaintainItem(String modelName, String productCode) {
        ProductFilterGradeParam productFilterGradeParam = new ProductFilterGradeParam();
        //接口参数，传入型号
        productFilterGradeParam.setSpec(modelName);
        productFilterGradeParam.setItemCode(productCode);
        //调用沁园接口，查询保养项目
        List<ProductFilterGrade> productFilterGrades = thirdPartyService.cssMaintenanceItem(productFilterGradeParam);
        List<MaintainPrice> maintainPriceList = new ArrayList<MaintainPrice>();
        for (ProductFilterGrade p : productFilterGrades) {
            MaintainPrice maintainPrice = new MaintainPrice();
            maintainPrice.setId(Long.parseLong(p.getGradeSeq()));
            maintainPrice.setMaintainName(p.getGradeName());
            maintainPrice.setMaintainPrice(new BigDecimal(p.getFilterPrice()));
            maintainPriceList.add(maintainPrice);
        }
        if (maintainPriceList.size() > 0) {
            maintainPriceMapper.batchSave(maintainPriceList);
        }
    }

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private FilterMapper filterMapper;

    @Autowired
    private FilterLevelMapper filterLevelMapper;

    @Autowired
    private LevelFilterRelationsMapper levelFilterRelationsMapper;

    @Autowired
    private ThirdPartyService thirdPartyService;

    @Autowired
    private MaintainPriceMapper maintainPriceMapper;

    @Autowired
    private FilterChangeRemindMapper filterChangeRemindMapper;
}
