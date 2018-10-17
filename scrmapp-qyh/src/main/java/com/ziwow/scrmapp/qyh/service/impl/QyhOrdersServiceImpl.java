package com.ziwow.scrmapp.qyh.service.impl;

import com.ziwow.scrmapp.common.constants.CancelConstant;
import com.ziwow.scrmapp.qyh.service.QyhProductService;
import com.ziwow.scrmapp.tools.utils.HttpClientUtils;
import com.ziwow.scrmapp.tools.utils.MD5;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.ziwow.scrmapp.common.bean.pojo.AppealInstallParam;
import com.ziwow.scrmapp.common.bean.pojo.AppealMaintainParam;
import com.ziwow.scrmapp.common.bean.pojo.AppealMaintenanceParam;
import com.ziwow.scrmapp.common.bean.pojo.CancelProductParam;
import com.ziwow.scrmapp.common.bean.pojo.ProductFilterGradeParam;
import com.ziwow.scrmapp.common.bean.vo.CompleteParam;
import com.ziwow.scrmapp.common.bean.vo.ProductFinishVo;
import com.ziwow.scrmapp.common.bean.vo.ProductVo;
import com.ziwow.scrmapp.common.bean.vo.QyhUserOrdersVo;
import com.ziwow.scrmapp.common.bean.vo.WechatOrdersRecordVo;
import com.ziwow.scrmapp.common.bean.vo.WechatOrdersVo;
import com.ziwow.scrmapp.common.bean.vo.csm.ProductFilterGrade;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.SystemConstants;
import com.ziwow.scrmapp.common.persistence.entity.Filter;
import com.ziwow.scrmapp.common.persistence.entity.FilterChangeRecord;
import com.ziwow.scrmapp.common.persistence.entity.FilterChangeRemind;
import com.ziwow.scrmapp.common.persistence.entity.InstallPart;
import com.ziwow.scrmapp.common.persistence.entity.InstallPartRecord;
import com.ziwow.scrmapp.common.persistence.entity.MaintainPrice;
import com.ziwow.scrmapp.common.persistence.entity.MaintainRecord;
import com.ziwow.scrmapp.common.persistence.entity.OrdersProRelations;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.common.persistence.entity.RepairItem;
import com.ziwow.scrmapp.common.persistence.entity.RepairItemRecord;
import com.ziwow.scrmapp.common.persistence.entity.RepairPart;
import com.ziwow.scrmapp.common.persistence.entity.RepairPartRecord;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrders;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrdersRecord;
import com.ziwow.scrmapp.common.persistence.mapper.FilterChangeRecordMapper;
import com.ziwow.scrmapp.common.persistence.mapper.FilterChangeRemindMapper;
import com.ziwow.scrmapp.common.persistence.mapper.FilterMapper;
import com.ziwow.scrmapp.common.persistence.mapper.InstallPartMapper;
import com.ziwow.scrmapp.common.persistence.mapper.InstallPartRecordMapper;
import com.ziwow.scrmapp.common.persistence.mapper.MaintainPriceMapper;
import com.ziwow.scrmapp.common.persistence.mapper.MaintainRecordMapper;
import com.ziwow.scrmapp.common.persistence.mapper.OrdersProRelationsMapper;
import com.ziwow.scrmapp.common.persistence.mapper.ProductMapper;
import com.ziwow.scrmapp.common.persistence.mapper.RepairItemMapper;
import com.ziwow.scrmapp.common.persistence.mapper.RepairItemRecordMapper;
import com.ziwow.scrmapp.common.persistence.mapper.RepairPartMapper;
import com.ziwow.scrmapp.common.persistence.mapper.RepairPartRecordMapper;
import com.ziwow.scrmapp.common.persistence.mapper.WechatOrdersMapper;
import com.ziwow.scrmapp.common.persistence.mapper.WechatOrdersRecordMapper;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.ErrorCode;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.common.utils.OrderUtils;
import com.ziwow.scrmapp.qyh.service.QyhNoticeService;
import com.ziwow.scrmapp.qyh.service.QyhOrdersService;
import com.ziwow.scrmapp.qyh.service.QyhUserService;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xiaohei on 2017/4/21.
 */
@Service
public class QyhOrdersServiceImpl implements QyhOrdersService {

    private static Logger logger = LoggerFactory.getLogger(QyhOrdersServiceImpl.class);

    @Autowired
    private WechatOrdersMapper wechatOrdersMapper;

    @Autowired
    private WechatOrdersRecordMapper wechatOrdersRecordMapper;

    @Autowired
    private FilterMapper filterMapper;

    @Autowired
    private MaintainPriceMapper maintainPriceMapper;

    @Autowired
    private MaintainRecordMapper maintainRecordMapper;

    @Autowired
    private ThirdPartyService thirdPartyService;

    @Autowired
    private FilterChangeRecordMapper filterChangeRecordMapper;

    @Autowired
    private FilterChangeRemindMapper filterChangeRemindMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private InstallPartMapper installPartMapper;

    @Autowired
    private RepairPartMapper repairPartMapper;

    @Autowired
    private RepairItemMapper repairItemMapper;

    @Autowired
    private RepairItemRecordMapper repairItemRecordMapper;

    @Autowired
    private RepairPartRecordMapper repairPartRecordMapper;

    @Autowired
    private InstallPartRecordMapper installPartRecordMapper;

    @Autowired
    private OrdersProRelationsMapper ordersProRelationsMapper;

    @Autowired
    private QyhUserService qyhUserService;

    @Autowired
    private QyhProductService qyhProductService;

    @Autowired
    private MobileService mobileService;

    @Autowired
    private QyhNoticeService qyhNoticeService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //将字符串中的\替换为空字符串
    private Pattern pattern = Pattern.compile("\\\\");

    @Value("${miniapp.product.finishmakeAppointment}")
    private String finishmakeAppointment;


    @Override
    public QyhUserOrdersVo getOrdersCountByQyhUserId(String qyhUserId, String condition) {
        QyhUserOrdersVo qyhUserOrdersVo = wechatOrdersMapper.getOrdersCountByUserId(qyhUserId);
        if (qyhUserOrdersVo != null) {
            List<WechatOrdersVo> detailVo = wechatOrdersMapper.getQyhUserOrdersVo(qyhUserId, condition);
            if (detailVo.size() > 0) {
                List<WechatOrdersVo> orders = getProductVoList(detailVo);
                qyhUserOrdersVo.setWechatOrdersVoList(orders);
            }
        }
        return qyhUserOrdersVo;
    }

    @Override
    public List<WechatOrdersVo> getOrdersListByQyhUserId(String qyhUserId, String condition) {
        List<WechatOrdersVo> detailVo = wechatOrdersMapper.getQyhUserOrdersVo(qyhUserId, condition);
        return getProductVoList(detailVo);
    }

    private List<WechatOrdersVo> getProductVoList(List<WechatOrdersVo> detailVo) {
        if (detailVo.size() == 0) {
            return detailVo;
        }
        Map<Long, WechatOrdersVo> map = Maps.uniqueIndex(detailVo, new Function<WechatOrdersVo, Long>() {
            @Override
            public Long apply(WechatOrdersVo vo) {
                return vo.getId();
            }
        });
        //根据工单id查询产品列表
        Long[] ordersIds = map.keySet().toArray(new Long[map.size()]);
        List<ProductVo> products = productMapper.selectByOrdersIds(ordersIds);
        for (ProductVo pro : products) {
            map.get(pro.getOrdersId()).getProducts().add(pro);
        }
        return new ArrayList<WechatOrdersVo>(map.values());
    }

    @Override
    public List<WechatOrdersVo> getFinishedByQyhUserId(String qyhUserId) {
        List<WechatOrdersVo> detailVo = wechatOrdersMapper.getFinishedByQyhUserId(qyhUserId);
        return getProductVoList(detailVo);
    }

    @Override
    @Transactional
    public Result changeOrdersTime(Long ordersId, String ordersCode, String qyhUserId, String updateTime, String reason) {
        Result result = new BaseResult();
        WechatOrders wechatOrders = wechatOrdersMapper.getWechatOrdersVoByCode(ordersCode);
        if (StringUtils.isEmpty(wechatOrders.getQyhUserId()) || !qyhUserId.equals(wechatOrders.getQyhUserId())) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("无法更改预约时间,此工单并非您的工单!");
            return result;
        }

//        List<ProductVo> products = productMapper.selectByOrdersId(ordersId);
//        for (ProductVo pro : products) {
//            if (Constant.COMPLETE == pro.getStatus()) {
//                result.setReturnCode(Constant.FAIL);
//                result.setReturnMsg("已存在完工产品，无法拒单!");
//                return result;
//            }
//        }
        //沁园修改预约时间接口
        result = thirdPartyService.updateCssAppeal(ordersCode, updateTime.substring(0, 10), 2);
        if (Constant.SUCCESS == result.getReturnCode()) {
            Date date = new Date();
            wechatOrdersMapper.updateOrdersTimeByEngineer(ordersCode, qyhUserId, date, updateTime.substring(0, 13), reason);
            WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
            wechatOrdersRecord.setRecordTime(date);
            wechatOrdersRecord.setOrderId(ordersId);
            wechatOrdersRecord.setRecordContent(qyhUserId + "更改预约时间为:" + updateTime);
            wechatOrdersRecordMapper.insert(wechatOrdersRecord);
        }
        return result;
    }

    @Transactional
    @Override
    public Result refuseOrders(Long ordersId, String ordersCode, String qyhUserId, String reason) {
        Result result = new BaseResult();
        WechatOrders wechatOrders = wechatOrdersMapper.getWechatOrdersVoByCode(ordersCode);
        if (StringUtils.isEmpty(wechatOrders.getQyhUserId()) || !qyhUserId.equals(wechatOrders.getQyhUserId())) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("无法拒绝工单,此工单并非您的工单!");
            return result;
        }
//        List<ProductVo> products = productMapper.selectByOrdersId(ordersId);
//        for (ProductVo pro : products) {
//            if (Constant.COMPLETE == pro.getStatus()) {
//                result.setReturnCode(Constant.FAIL);
//                result.setReturnMsg("已存在完工产品，无法拒单!");
//                return result;
//            }
//        }
        result = thirdPartyService.wxSingleBackation(ordersCode, reason);
        if (Constant.SUCCESS == result.getReturnCode()) {
            Date date = new Date();
            wechatOrdersMapper.updateOrdersStatus(ordersId, qyhUserId, date, reason);

            //更新产品关联表中状态
//            ordersProRelationsMapper.updateStatusByOrdersId(ordersId);
            //师傅拒单后保存操作记录
            WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
            wechatOrdersRecord.setRecordTime(date);
            wechatOrdersRecord.setOrderId(ordersId);
            wechatOrdersRecord.setRecordContent(qyhUserId + "已拒单");
            wechatOrdersRecordMapper.insert(wechatOrdersRecord);
        }
        return result;
    }

    @Override
    public WechatOrdersVo getFinishedOrdersDetail(String ordersCode) {
        WechatOrdersVo ordersDetail = wechatOrdersMapper.getFinishedOrdersDetail(ordersCode);
        if (ordersDetail == null) {
            return ordersDetail;
        }
        List<ProductFinishVo> products = productMapper.getFinishedOrdersDetail(ordersDetail.getId());
        for (int i = 0; i < products.size(); i++) {
            String productBarCode = products.get(i).getProductBarCode();
            String productBarCodeTwenty = products.get(i).getProductBarCodeTwenty();
            if (productBarCode.length() == 20
                    && productBarCodeTwenty.length() == 20) {
                products.get(i).setProductBarCode(productBarCode.substring(0, 1) + productBarCodeTwenty.substring(1));
            }
        }
        ordersDetail.setProductFinish(products);
        return ordersDetail;
    }

    @Override
    public WechatOrdersVo getFinishDetail(String ordersCode) {
        WechatOrdersVo ordersInfo = wechatOrdersMapper.getUserOrdersInfo(ordersCode);
        if (ordersInfo == null) {
            return ordersInfo;
        }
        List<ProductVo> products = productMapper.selectByOrdersId(ordersInfo.getId());
        ordersInfo.setProducts(products);
        return ordersInfo;
    }

    @Transactional
    @Override
    public Result syncInstallWechatOrders(CompleteParam completeParam, String products, String cancelRemark) {
        AppealInstallParam installParam = new AppealInstallParam();
        installParam.setEnduser_name(completeParam.getUserName());
        installParam.setMobile(completeParam.getUserMobile());
        installParam.setFix_man_id(completeParam.getQyhUserId());
        installParam.setFix_man_name(completeParam.getGetQyhUserName());
        installParam.setInstall_time(simpleDateFormat.format(new Date()));
        installParam.setWeb_appeal_no(completeParam.getOrdersCode());
        installParam.setIs_replace(0);//"defualt 0"
        installParam.setNote(cancelRemark);
        JSONArray jsonArray = new JSONArray(products);
        List<ProductVo> productVos = completeParam.getProducts();
        Map<Long, ProductVo> productMap = Maps.uniqueIndex(productVos, new Function<ProductVo, Long>() {
            public Long apply(ProductVo vo) {
                return vo.getProductId();
            }
        });
        //传给csm的产品参数
        JSONArray array = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Long productId = jsonObject.getLong("productId");
            ProductVo productVo = productMap.get(productId);
            productVo.setStatus(Constant.COMPLETE);
            String productBarCode = StringUtils.EMPTY;
            String barCodeImage = StringUtils.EMPTY;
            AppealInstallParam.Product product = installParam.new Product();
            product.setItem_code(productVo.getProductCode());
            if (jsonObject.has("productBarCode")) {
                productBarCode = jsonObject.getString("productBarCode");
                product.setBarcode(productBarCode);
                productVo.setProductBarCode(productBarCode);
            }
            if (jsonObject.has("barCodeImage")) {
                barCodeImage = jsonObject.getString("barCodeImage");
                product.setBarcode_image(barCodeImage);
                productVo.setBarCodeImage(barCodeImage);

            }
            JSONObject object = new JSONObject(product);
            array.put(object);
        }
        // 设置安装单对应的产品信息
        installParam.setInstallProdStr(array.toString());
        // 调用csm系统生成安装单
        Result result = thirdPartyService.cssAppealInstallation(installParam);
        if (Constant.SUCCESS == result.getReturnCode()) {
            //更新产品工单关联表状态和工单记录
            try {
                finishWechatOrders(productMap, completeParam, result.getData().toString());
                //更新产品条码和图片
                updateProductBarCode(productMap);
            }catch (Exception e){
                logger.error("师傅点击工单完工操作失败单号:",completeParam.getOrdersCode());
                logger.error("师傅点击工单完工操作失败:",e);
            }
            logger.info("师傅点击工单[{}]完工操作成功!", completeParam.getOrdersCode());
        }
        return result;
    }

    @Transactional
    @Override
    public Result syncRepairWechatOrders(CompleteParam completeParam, int is_replace, String products) {
        AppealMaintenanceParam maintenanceParam = new AppealMaintenanceParam();
        maintenanceParam.setEnduser_address(completeParam.getUserAddress());
        maintenanceParam.setEnduser_name(completeParam.getUserName());
        maintenanceParam.setFix_date(simpleDateFormat.format(new Date()));
        maintenanceParam.setFix_man_id(completeParam.getQyhUserId());
        maintenanceParam.setFix_man_name(completeParam.getGetQyhUserName());
        maintenanceParam.setMobile(completeParam.getUserMobile());
        maintenanceParam.setProblem_name(completeParam.getDescription());
        maintenanceParam.setWeb_appeal_no(completeParam.getOrdersCode());
        maintenanceParam.setIs_replace(is_replace);
        JSONObject jsonObject = new JSONObject(products);
        List<ProductVo> productVos = completeParam.getProducts();
        //将list转为map
        Map<Long, ProductVo> productMap = Maps.uniqueIndex(productVos, new Function<ProductVo, Long>() {
            public Long apply(ProductVo vo) {
                return vo.getProductId();
            }
        });

        List<RepairItemRecord> repairItemRecords = new ArrayList<RepairItemRecord>();
        List<RepairPartRecord> repairPartRecords = new ArrayList<RepairPartRecord>();
        Long productId = jsonObject.getLong("productId");
        ProductVo productVo = productMap.get(productId);
        productVo.setStatus(Constant.COMPLETE);
        AppealMaintenanceParam.Product product = maintenanceParam.new Product();
        product.setItem_code(productVo.getProductCode());
        String productBarCode = StringUtils.EMPTY;
        String barCodeImage = StringUtils.EMPTY;
        if (jsonObject.has("productBarCode")) {
            productBarCode = jsonObject.getString("productBarCode");
            product.setBarcode(productBarCode);
        }
        if (jsonObject.has("barCodeImage")) {
            barCodeImage = jsonObject.getString("barCodeImage");
            product.setBarcode_image(barCodeImage);
        }

        if (jsonObject.has("repairItemStr")) {
            JSONObject repairItem = jsonObject.getJSONObject("repairItemStr");
            int id = repairItem.getInt("id");
            String name = repairItem.getString("name");
            String itemCode = repairItem.getString("itemCode");
            //滤芯列表
            maintenanceParam.setFix_step_id(id);
            maintenanceParam.setFix_step_name(name);
            maintenanceParam.setFix_step_code(itemCode);

            RepairItemRecord repairItemRecord = new RepairItemRecord();
            repairItemRecord.setProductId(productId);
            repairItemRecord.setOrderId(completeParam.getOrdersId());
            repairItemRecord.setRecord(name);

            repairItemRecords.add(repairItemRecord);
        }

        if (jsonObject.has("repairParts")) {
            JSONArray repairPartsArray = jsonObject.getJSONArray("repairParts");
            logger.info("维修配件数组[{}]", repairPartsArray.toString());
            RepairPartRecord repairPartRecord = new RepairPartRecord();
            BigDecimal cost = BigDecimal.ZERO;
            String record = StringUtils.EMPTY;
            //传到csm 维修配件记录
            JSONArray repairArrayParam = new JSONArray();
            for (int j = 0; j < repairPartsArray.length(); j++) {
                JSONObject repairParts = repairPartsArray.getJSONObject(j);
                JSONObject repairParam = new JSONObject();
                double price = repairParts.getDouble("price");
                String name = repairParts.getString("name");
                int number = repairParts.getInt("number");
                cost = cost.add(new BigDecimal(price));
                record += name + "(" + price + "元/" + number + "个)、";

                repairParam.put("css_item_id", repairParts.getInt("id"));
                repairParam.put("css_item_name", name);
                repairParam.put("settle_price", price + "");
                repairParam.put("qty", number);

                repairArrayParam.put(repairParam);
            }
            maintenanceParam.setRepairPartStr(repairArrayParam.toString());
            repairPartRecord.setOrderId(completeParam.getOrdersId());
            repairPartRecord.setRecord(record.substring(0, record.length() - 1));
            repairPartRecord.setCost(cost);
            repairPartRecord.setProductId(productId);
            //保存维修配件记录
            repairPartRecords.add(repairPartRecord);
        }
        productVo.setProductBarCode(productBarCode);
        productVo.setProductImage(barCodeImage);

        //传递产品信息
        JSONArray proinfo = new JSONArray();
        proinfo.put(new JSONObject(product));

        maintenanceParam.setProd_info(proinfo.toString());

        maintenanceParam.setIs_finish(isFinish(productMap));


        Result result = thirdPartyService.cssAppealMaintenance(maintenanceParam);
        if (Constant.SUCCESS == result.getReturnCode()) {
            //更新工单状态和工单记录
            finishWechatOrders(productMap, completeParam, result.getData().toString());

            //更新产品条码和图片
            updateProductBarCodeAndImg(productVo);

            //保存维修措施记录
            if (repairItemRecords.size() > 0) {
                repairItemRecordMapper.batchSave(repairItemRecords);
            }

            //保存维修配件记录
            if (repairPartRecords.size() > 0) {
                repairPartRecordMapper.batchSave(repairPartRecords);
            }
            logger.info("师傅点击维修单[{}]完工操作成功！", completeParam.getOrdersCode());
        }
        return result;
    }

    private int isFinish(Map productMap) {
        Iterator<ProductVo> iterator = productMap.values().iterator();
        AtomicInteger count = new AtomicInteger(0);
        while (iterator.hasNext()) {
            ProductVo vo = iterator.next();
            if (vo.getStatus() == Constant.COMPLETE) {
                count.incrementAndGet();
            }
        }

        if (productMap.size() == count.get()) {
            return Constant.FINISH;
        } else {
            return Constant.DEAL;
        }
    }

    @Transactional
    @Override
    public Result syncMaintainWechatOrders(CompleteParam completeParam, int is_change_filter, String products) {
        AppealMaintainParam maintainParam = new AppealMaintainParam();
        //工单相关信息
        maintainParam.setEnduser_address(completeParam.getUserAddress());
        maintainParam.setEnduser_name(completeParam.getUserName());
        maintainParam.setFix_man_id(completeParam.getQyhUserId());
        maintainParam.setFix_man_name(completeParam.getGetQyhUserName());
        maintainParam.setIs_change_filter(is_change_filter);
        maintainParam.setMobile(completeParam.getUserMobile());
        maintainParam.setService_time(simpleDateFormat.format(new Date()));
        maintainParam.setWeb_appeal_no(completeParam.getOrdersCode());
        JSONObject jsonObject = new JSONObject(products);

        List<FilterChangeRecord> filterChangeRecords = new ArrayList<FilterChangeRecord>();
        List<MaintainRecord> maintainRecords = new ArrayList<MaintainRecord>();

        String[] ids = {};
        Long productId = jsonObject.getLong("productId");
        List<ProductVo> productVos = completeParam.getProducts();
        //将list转为map
        Map<Long, ProductVo> productMap = Maps.uniqueIndex(productVos, new Function<ProductVo, Long>() {
            public Long apply(ProductVo vo) {
                return vo.getProductId();
            }
        });
        ProductVo productVo = productMap.get(productId);
        productVo.setStatus(Constant.COMPLETE);
        AppealMaintainParam.Product product = maintainParam.new Product();
        product.setItem_code(productVo.getProductCode());
        String productBarCode = StringUtils.EMPTY;
        String barCodeImage = StringUtils.EMPTY;
        if (jsonObject.has("productBarCode")) {
            productBarCode = jsonObject.getString("productBarCode");
            product.setBarcode(productBarCode);
        }
        if (jsonObject.has("barCodeImage")) {
            barCodeImage = jsonObject.getString("barCodeImage");
            product.setBarcode_image(barCodeImage);
        }
        productVo.setProductBarCode(productBarCode);
        productVo.setProductImage(barCodeImage);
        //更换滤芯
        if (0 != is_change_filter && jsonObject.has("filters")) {
            JSONArray filtersArray = jsonObject.getJSONArray("filters");
            JSONArray filterParam = new JSONArray();
            BigDecimal changeCost = BigDecimal.ZERO;
            String changeItem = StringUtils.EMPTY;
            ids = new String[filtersArray.length()];
            for (int j = 0; j < filtersArray.length(); j++) {
                JSONObject filter = filtersArray.getJSONObject(j);
                String grade_seq = String.valueOf(filter.getInt("id"));
                String grade_name = filter.getString("filterName");
                double price = filter.getDouble("price");
                //滤芯列表
                JSONObject json = new JSONObject();
                json.put("grade_seq", grade_seq);
                json.put("grade_name", grade_name);
                json.put("price", price + "");
                filterParam.put(json);

                ids[j] = grade_seq;
                //滤芯更换名称以及价格
                DecimalFormat df = new DecimalFormat("#.00");
                changeItem += grade_name + "(" + df.format(price) + ")、";
                changeCost = changeCost.add(new BigDecimal(price));
            }
            maintainParam.setFilterStr(filterParam.toString());

            //保存滤芯更换记录
            FilterChangeRecord filterChangeRecord = new FilterChangeRecord();
            filterChangeRecord.setOrderId(completeParam.getOrdersId());
            filterChangeRecord.setProductId(productId);
            filterChangeRecord.setChangeCost(changeCost);
            filterChangeRecord.setChangeItem(changeItem.substring(0, changeItem.length() - 1));

            filterChangeRecords.add(filterChangeRecord);
        }

        if (jsonObject.has("maintainPrices")) {
            JSONArray maintainPriceArray = jsonObject.getJSONArray("maintainPrices");
            JSONArray maintainParamArray = new JSONArray();
            BigDecimal changeCost = BigDecimal.ZERO;
            String changeItem = StringUtils.EMPTY;
            for (int j = 0; j < maintainPriceArray.length(); j++) {
                JSONObject miantain = maintainPriceArray.getJSONObject(j);
                String grade_seq = String.valueOf(miantain.getInt("id"));
                String grade_name = miantain.getString("maintainName");
                double price = miantain.getDouble("price");
                //滤芯列表
                JSONObject json = new JSONObject();
                json.put("grade_seq", grade_seq);
                json.put("grade_name", grade_name);
                json.put("price", price + "");
                maintainParamArray.put(json);

                //滤芯更换名称以及价格
                DecimalFormat df = new DecimalFormat("#.00");
                changeItem += grade_name + "(" + df.format(price) + ")、";
                changeCost = changeCost.add(new BigDecimal(price));
            }

            maintainParam.setMaintainStr(maintainParamArray.toString());

            //保存保养项记录
            MaintainRecord maintainRecord = new MaintainRecord();
            maintainRecord.setOrderId(completeParam.getOrdersId());
            maintainRecord.setProductId(productId);
            maintainRecord.setMaintainCost(changeCost);
            maintainRecord.setMaintainItem(changeItem.substring(0, changeItem.length() - 1));

            maintainRecords.add(maintainRecord);
        }

        //保养产品信息
        //传递产品信息
        JSONArray proinfo = new JSONArray();
        proinfo.put(new JSONObject(product));

        maintainParam.setProd_info(proinfo.toString());

        maintainParam.setIs_finish(isFinish(productMap));

        Result result = thirdPartyService.cssAppealMaintain(maintainParam);
        if (Constant.SUCCESS == result.getReturnCode()) {
            //更新工单状态和工单记录
            finishWechatOrders(productMap, completeParam, result.getData().toString());
            //更新产品条码和图片
            if (maintainRecords.size() > 0) {
                updateProductBarCodeAndImg(productVo);
                //批量保存保养项
                maintainRecordMapper.batchSave(maintainRecords);
            }

            if (filterChangeRecords.size() > 0) {
                //批量保存滤芯更换记录
                updateFilterChangeInfo(filterChangeRecords, ids);
            }
            logger.info("师傅点击保养单[{}]完工操作成功！", completeParam.getOrdersCode());
        }
        return result;
    }

    @Transactional
    public void updateFilterChangeInfo(List<FilterChangeRecord> filterChangeRecords, String[] ids) {
        filterChangeRecordMapper.batchSave(filterChangeRecords);
        Long productId = filterChangeRecords.get(0).getProductId();
        Product product = productMapper.selectByPrimaryKey(productId);
        List<FilterChangeRemind> filterChangeReminds = filterChangeRemindMapper.findByProductId(productId);

        List<Filter> filterList = filterMapper.findFilterByIds(ids);
        //如果滤芯提醒表中存在记录，则替换前的滤芯需要删除,然后增加新滤芯更换提醒
        if (filterChangeReminds.size() > 0) {
            //修改换掉的滤芯为已删除
            filterChangeRemindMapper.updateFilterIdsStatus(ids, productId, SystemConstants.DELETE);

            List<FilterChangeRemind> insertReminds = new ArrayList<FilterChangeRemind>();
            //遍历更换滤芯list
            for (Filter filter : filterList) {
                //重新添加滤芯提醒
                FilterChangeRemind filterChangeRemind = new FilterChangeRemind();
                filterChangeRemind.setFilterId(filter.getId());
                filterChangeRemind.setProductId(productId);
                filterChangeRemind.setStatus(product.getFilterRemind());
                filterChangeRemind.setRemindTime(DateUtil.getAfterDay(new Date(), filter.getMaintain()));
                insertReminds.add(filterChangeRemind);
            }
            filterChangeRemindMapper.batchSave(insertReminds);

        } else {
            //获得产品所有滤芯
            List<Filter> productFilterList = filterMapper.findByLevelId(product.getLevelId());
            if (productFilterList.size() > 0) {
                //将更换的滤芯list转为map
                Map<Long, Filter> filterMap = Maps.uniqueIndex(filterList, new Function<Filter, Long>() {
                    public Long apply(Filter filter) {
                        return filter.getId();
                    }
                });

                //增加滤芯提醒list
                List<FilterChangeRemind> insertReminds = new ArrayList<FilterChangeRemind>();

                //遍历所有滤芯
                for (Filter filter : productFilterList) {
                    FilterChangeRemind filterChangeRemind = new FilterChangeRemind();
                    filterChangeRemind.setFilterId(filter.getId());
                    filterChangeRemind.setProductId(productId);
                    filterChangeRemind.setStatus(SystemConstants.CLOSE);
                    //如果存在key，则提醒时间从当前时间开始计算,否则从购买时间开始计算
                    if (filterMap.containsKey(filter.getId())) {
                        filterChangeRemind.setRemindTime(DateUtil.getAfterDay(new Date(), filter.getMaintain()));
                    } else {
                        filterChangeRemind.setRemindTime(DateUtil.getAfterDay(product.getBuyTime(), filter.getMaintain()));
                    }
                    insertReminds.add(filterChangeRemind);
                }

                filterChangeRemindMapper.batchSave(insertReminds);
                //更改绑定产品为未提醒
                productMapper.updateRemind(productId, SystemConstants.CLOSE);
            }
        }
    }

    @Transactional
    public void updateProductBarCode(Map<Long, ProductVo> productMap) {
        Iterator<ProductVo> iterator = productMap.values().iterator();
        while (iterator.hasNext()) {
            ProductVo productVo = iterator.next();
            updateProductBarCodeAndImg(productVo);
        }
    }

    public int updateProductBarCodeAndImg(ProductVo productVo) {
        Product product = productMapper.selectByPrimaryKey(productVo.getProductId());
        //数据库中存的条码
        String oldBarCode = product.getProductBarCode();
        //师傅输入的条码
        String newBarCode = productVo.getProductBarCode();

        if ("".equals(oldBarCode) || null == oldBarCode) {
            productVo.setProductBarCodeTwenty(newBarCode);
        }

        return productMapper.updateProductBarCode(productVo);
    }

    @Transactional
    public int finishWechatOrders(Map<Long, ProductVo> productMap, CompleteParam completeParam, String ordersNo) {
      int count = 0;
        try {
          Iterator<ProductVo> iterator = productMap.values().iterator();
          List<Long> productId = new ArrayList<Long>();
          boolean flag = true;
          while (iterator.hasNext()) {
            ProductVo productVo = iterator.next();
            if (Constant.NORMAL == productVo.getStatus()) {
              flag = false;
            } else if (Constant.COMPLETE == productVo.getStatus()) {
              productId.add(productVo.getProductId());
            }
          }

          if (productId.size() > 0) {
            //更新工单与产品关联表status
            ordersProRelationsMapper.updateStatus(productId, completeParam.getOrdersId(), completeParam.getQyhUserId());
          }

          if ((flag && productMap.size() > 0) || SystemConstants.INSTALL == completeParam.getOrderType()) {
            Date date = new Date();
            count = wechatOrdersMapper.updateOrdersNoAndStatus(completeParam.getOrdersCode(), ordersNo, date, SystemConstants.COMPLETE);
            if (count > 0) {
              WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
              wechatOrdersRecord.setRecordTime(date);
              wechatOrdersRecord.setRecordContent("师傅点击完工!");
              wechatOrdersRecord.setOrderId(completeParam.getOrdersId());
              wechatOrdersRecordMapper.insert(wechatOrdersRecord);
            }
          }
        }catch (Exception e){
          logger.error("师傅点击工单[{}]完工操作出现异常,原因:[{}]", completeParam.getOrdersCode(), e);
          logger.error("师傅点击工单完工操作失败:",e);
          throw new RuntimeException("工单提交失败!");
        }
      return count;
    }

    @Override
    @Transactional
    public void saveMatainRecord(Long ordersId, Long productId, String filters, String maintainPrices) {
        if (!StringUtils.isEmpty(filters)) {
            JSONArray filterArray = new JSONArray(filters);
            String[] ids = new String[filterArray.length()];
            //格式化价格，保留两位小数
            DecimalFormat df = new DecimalFormat("#.00");
            //保存到更换滤芯记录表中
            String changeItem = "";
            BigDecimal changeCost = BigDecimal.ZERO;

            //遍历滤芯
            for (int i = 0; i < filterArray.length(); i++) {
                String price = df.format(filterArray.getJSONObject(i).getDouble("price"));
                changeItem += filterArray.getJSONObject(i).getString("filterName") + "(" + price + ")、";
                changeCost = changeCost.add(new BigDecimal(price));
                ids[i] = String.valueOf(filterArray.getJSONObject(i).getInt("id"));
            }

            FilterChangeRecord filterChangeRecord = new FilterChangeRecord();
            filterChangeRecord.setOrderId(ordersId);
            filterChangeRecord.setProductId(productId);
            filterChangeRecord.setChangeItem(changeItem.substring(0, changeItem.length() - 1));
            filterChangeRecord.setChangeCost(changeCost);

            //保存更换滤芯记录
            filterChangeRecordMapper.insert(filterChangeRecord);

            Product product = productMapper.selectByPrimaryKey(productId);
            List<FilterChangeRemind> filterChangeReminds = filterChangeRemindMapper.findByProductId(productId);

            List<Filter> filterList = filterMapper.findFilterByIds(ids);
            //如果滤芯提醒表中存在记录，则替换前的滤芯需要删除,然后增加新滤芯更换提醒
            if (filterChangeReminds.size() > 0) {
                //修改换掉的滤芯为已删除
                filterChangeRemindMapper.updateFilterIdsStatus(ids, productId, SystemConstants.DELETE);

                List<FilterChangeRemind> insertReminds = new ArrayList<FilterChangeRemind>();
                //遍历更换滤芯list
                for (Filter filter : filterList) {
                    //重新添加滤芯提醒
                    FilterChangeRemind filterChangeRemind = new FilterChangeRemind();
                    filterChangeRemind.setFilterId(filter.getId());
                    filterChangeRemind.setProductId(productId);
                    filterChangeRemind.setStatus(product.getFilterRemind());
                    filterChangeRemind.setRemindTime(DateUtil.getAfterDay(new Date(), filter.getMaintain()));
                    insertReminds.add(filterChangeRemind);
                }
                filterChangeRemindMapper.batchSave(insertReminds);

            } else {
                //获得产品所有滤芯
                List<Filter> productFilterList = filterMapper.findByLevelId(product.getLevelId());
                if (productFilterList.size() > 0) {
                    //将更换的滤芯list转为map
                    Map<Long, Filter> filterMap = Maps.uniqueIndex(filterList, new Function<Filter, Long>() {
                        public Long apply(Filter filter) {
                            return filter.getId();
                        }
                    });

                    //增加滤芯提醒list
                    List<FilterChangeRemind> insertReminds = new ArrayList<FilterChangeRemind>();

                    //遍历所有滤芯
                    for (Filter filter : productFilterList) {
                        FilterChangeRemind filterChangeRemind = new FilterChangeRemind();
                        filterChangeRemind.setFilterId(filter.getId());
                        filterChangeRemind.setProductId(productId);
                        filterChangeRemind.setStatus(SystemConstants.CLOSE);
                        //如果存在key，则提醒时间从当前时间开始计算,否则从购买时间开始计算
                        if (filterMap.containsKey(filter.getId())) {
                            filterChangeRemind.setRemindTime(DateUtil.getAfterDay(new Date(), filter.getMaintain()));
                        } else {
                            filterChangeRemind.setRemindTime(DateUtil.getAfterDay(product.getBuyTime(), filter.getMaintain()));
                        }
                        insertReminds.add(filterChangeRemind);
                    }

                    filterChangeRemindMapper.batchSave(insertReminds);
                    //更改绑定产品为未提醒
                    productMapper.updateRemind(productId, SystemConstants.CLOSE);
                }

            }
        }

        if (!StringUtils.isEmpty(maintainPrices)) {
            JSONArray maintainArray = new JSONArray(maintainPrices);

            String mainItem = "";
            BigDecimal mainCost = BigDecimal.ZERO;
            DecimalFormat df = new DecimalFormat("#.00");
            //遍历保养项
            for (int i = 0; i < maintainArray.length(); i++) {
                String price = df.format(maintainArray.getJSONObject(i).getDouble("price"));
                mainItem += maintainArray.getJSONObject(i).get("maintainName") + "(" + price + ")、";
                mainCost = mainCost.add(new BigDecimal(price));
            }

            logger.info(mainItem);
            MaintainRecord record = new MaintainRecord();
            record.setMaintainItem(mainItem.substring(0, mainItem.length() - 1));
            record.setProductId(productId);
            record.setOrderId(ordersId);
            record.setMaintainCost(mainCost);
            //保存保养项信息
            maintainRecordMapper.insert(record);
        }

    }

    @Override
    public void saveInstallRecord(Long ordersId, String installParts) {
        if (!StringUtils.isEmpty(installParts)) {
            JSONArray jsonArray = new JSONArray(installParts);
            InstallPartRecord installPartRecord = new InstallPartRecord();
            BigDecimal cost = BigDecimal.ZERO;
            String record = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                double price = jsonObject.getDouble("price");
                int number = jsonObject.getInt("number");
                String name = jsonObject.getString("name");
                cost = cost.add(new BigDecimal(price));
                record += name + "(" + price + "元/" + number + "个)、";
            }
            logger.info(record);
            installPartRecord.setOrderId(ordersId);
            installPartRecord.setRecord(record.substring(0, record.length() - 1));
            installPartRecord.setCost(cost);
            //保存安装配件记录
            installPartRecordMapper.save(installPartRecord);
        }
    }

    @Override
    public CompleteParam getCompleteParamByOrdersCode(String ordersCode) {
        CompleteParam completeParam = wechatOrdersMapper.getCompleteParamByOrdersCode(ordersCode);
        if (completeParam != null) {
            List<ProductVo> products = productMapper.selectByOrdersId(completeParam.getOrdersId());
            completeParam.setProducts(products);
        }
        return completeParam;
    }

    @Override
    public WechatOrdersVo getVoByOrdersCode(String ordersCode) {
        //订单详情
        WechatOrdersVo wechatOrdersVo = wechatOrdersMapper.getByOrdersCode(ordersCode);
        if (wechatOrdersVo == null) {
            return wechatOrdersVo;
        }
        if (SystemConstants.RECEIVE == wechatOrdersVo.getStatus()) {
            wechatOrdersVo.setOrderStatus("待处理");
        } else if (SystemConstants.COMPLETE == wechatOrdersVo.getStatus() || SystemConstants.APPRAISE == wechatOrdersVo.getStatus()) {
            wechatOrdersVo.setOrderStatus("已完工");
        } else {
            wechatOrdersVo.setOrderStatus("待处理");
        }

        if (SystemConstants.RECEIVE == wechatOrdersVo.getStatus() && StringUtils.isEmpty(wechatOrdersVo.getQyhUserId())) {
            wechatOrdersVo.setBtnHidden(1);
        }
        wechatOrdersVo.setQyhUserId("");
        //查询受理记录
        List<WechatOrdersRecordVo> wechatOrdersRecordList = wechatOrdersRecordMapper.findByOrdersId(wechatOrdersVo.getId());
        wechatOrdersVo.setWechatOrdersRecordList(wechatOrdersRecordList);

        List<ProductVo> products = productMapper.selectByOrdersIds(new Long[]{wechatOrdersVo.getId()});
        wechatOrdersVo.setProducts(products);
        return wechatOrdersVo;
    }

    @Transactional
    @Override
    public void updateBarCode(String productBarCode, String barCodeImage, Long productId) {
        productMapper.updateBarCode(productBarCode, barCodeImage, productId);
    }

    @Override
    public void updateBarCodeImage(String barCodeImage, Long productId) {
        productMapper.updateBarCodeImage(barCodeImage, productId);
    }

    @Override
    public Result findRepairItem(String typeName, String smallcName, String name) {
        Result result = new BaseResult();
        //如果name为空，调用沁园接口,不为空则本地查询
        if (StringUtils.isEmpty(name)) {
            logger.info("调用沁园接口查询维修配件");
            if (StringUtils.isNotEmpty(smallcName)) {
                //调用维修措施接口
                result = thirdPartyService.getStep(null, smallcName);
                logger.info("调用沁园接口查询维修配件====smallcName:" + smallcName + ",result" + result);
            } else {
                result = thirdPartyService.getStep(typeName, "");
                logger.info("调用沁园接口查询维修配件====typeName:" + typeName + ",result" + result);
            }
            if (Constant.SUCCESS == result.getReturnCode()) {
                List<RepairItem> repairItemList = (List<RepairItem>) result.getData();
                batchSaveRepairItem(repairItemList);
            }
        } else {
            List<RepairItem> repairItemList;
            if (StringUtils.isNotEmpty(smallcName)) {
                //调用本地查询
                repairItemList = repairItemMapper.findByName(typeName, smallcName, name);
            } else {
                repairItemList = repairItemMapper.findByName(typeName, null, name);
            }
            result.setReturnCode(Constant.SUCCESS);
            result.setData(repairItemList);
        }

        return result;
    }

    @Override
    public Result findInstallPart(String modelName, String name) {
        Result result = new BaseResult();
        //如果本地查询为空，则调用csm接口
        if (StringUtils.isEmpty(name)) {
            List<InstallPart> installParts = installPartMapper.findByModelName(modelName);
            if (installParts.size() == 0) {
                result = thirdPartyService.getInstallPart(modelName);
                //查询结果不为空
                if (Constant.SUCCESS == result.getReturnCode()) {
                    installParts = (List<InstallPart>) result.getData();
                    //批量增加安装配件
                    batchSaveInstallPart(installParts);
                }
            } else {
                result.setReturnCode(Constant.SUCCESS);
                result.setData(installParts);
            }
        } else {
            //模糊匹配查询安装配件
            List<InstallPart> installPartList = installPartMapper.findByName(modelName, name);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(installPartList);
        }

        return result;
    }


    @Override
    public Result findRepairPart(String modelName, String name) {
        Result result = new BaseResult();
        //如果本地查询为空，则调用csm接口
        if (StringUtils.isEmpty(name)) {
            List<RepairPart> repairPartList = repairPartMapper.findByModelName(modelName);
            if (repairPartList.size() == 0) {
                result = thirdPartyService.getRepairPart(modelName);
                //查询结果不为空
                if (Constant.SUCCESS == result.getReturnCode()) {
                    repairPartList = (List<RepairPart>) result.getData();
                    //批量增加安装配件
                    batchSaveRepairPart(repairPartList);
                }
            } else {
                result.setReturnCode(Constant.SUCCESS);
                result.setData(repairPartList);
            }
        } else {
            //模糊匹配查询安装配件
            List<RepairPart> repairPartList = repairPartMapper.findByName(modelName, name);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(repairPartList);
        }

        return result;
    }

    @Override
    public Result findMaintainInfo(Long levelId, String productCode) {
        Result result = new BaseResult();
        Map<String, List> map = new HashMap<String, List>();
        List<Filter> filterList = new ArrayList<Filter>();
        //如果是保养单，展示保养项目和滤芯项目
        if (0 != levelId) {
            //获取滤芯列表
            filterList = filterMapper.findByLevelId(levelId);
        }
        map.put("filterList", filterList);

        //获取保养列表
        List<MaintainPrice> maintainPriceList = maintainPriceMapper.findByProductCode(productCode);
        if (maintainPriceList.size() == 0) {
            maintainPriceList = batchSaveMaintainItem(productCode);
        }

        map.put("maintainPriceList", maintainPriceList);
        result.setReturnCode(ErrorCode.SUCCESS);
        result.setData(map);
        return result;
    }

    @Override
    public Result cancelProduct(CancelProductParam cancelProductParam) throws Exception {
        Result result = new BaseResult();

        //更改产品状态
        ordersProRelationsMapper.cancelProduct(cancelProductParam);

        List<OrdersProRelations> list = ordersProRelationsMapper.getListByOrderId(cancelProductParam.getOrderId());

        int normal = 0, cancel = 0, finished = 0;

        for (OrdersProRelations ordersProRelations : list) {
            int status = Integer.parseInt(ordersProRelations.getStatus());
            switch (status) {
                case 1:
                    normal += 1;
                    break;
                case 2:
                    cancel += 1;
                    break;
                case 3:
                    finished += 1;
                    break;
                default:
                    break;
            }
        }
        Long orderId = cancelProductParam.getOrderId();
        String orderCode = cancelProductParam.getOrderCode();
        String qyhUserId = new String(Base64.decode(cancelProductParam.getUserId()));
        String reason = cancelProductParam.getReason();
        String contacts = cancelProductParam.getContacts();
        String corpId = cancelProductParam.getCorpId();
        String orderDetailUrl = cancelProductParam.getOrderDetailUrl();

        //工单中的产品全是取消,则为师傅拒单,调用拒单接口
        if (normal == 0 && finished == 0 && cancel > 0) {
            result = refuseOrders(orderId, orderCode, qyhUserId, reason);

            if (Constant.SUCCESS == result.getReturnCode()) {
                // 服务工程师撤销预约给自己发送短信通知提醒
                String serviceType = OrderUtils.getServiceTypeName(cancelProductParam.getOrderType());
                QyhUser qyhUser = qyhUserService.getQyhUserByUserIdAndCorpId(qyhUserId, corpId);
                String mobilePhone = (null != qyhUser) ? qyhUser.getMobile() : "";
                String msgContent = "您已拒绝" + contacts + "用户预约的" + serviceType + "服务，您可进入“沁园”WX企业号查看该工单详情！";
                mobileService.sendContentByEmay(mobilePhone, msgContent, Constant.ENGINEER);
                // 服务工程师修改预约时间给自己发送通知
                String url = orderDetailUrl + "?userId=" + qyhUserId + "&ordersCode=" + orderCode;
                String content = "工单撤销通知！\n" +
                        "您已成功拒绝" + contacts + "用户预约的" + serviceType + "服务！\n" +
                        "1、点击<a href='" + url + "'>【待处理工单】</a>查看该工单详情！";
                qyhNoticeService.qyhSendMsgText(qyhUserId, content);
                result.setReturnCode(Constant.SUCCESS);
                result.setReturnMsg("已拒单成功!");
                logger.info(qyhUserId + "师傅拒单操作成功!");
            }
        }
        //工单中的产品全是完工和取消,则为完工,调用完工借口
        else if (normal == 0 && finished > 0) {
            if (cancelProductParam.getOrderType() != 1) {
                Date date = new Date();
                int count = wechatOrdersMapper.updateStatus(orderCode, date, SystemConstants.COMPLETE);
                if (count > 0) {
                    WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
                    wechatOrdersRecord.setRecordTime(date);
                    wechatOrdersRecord.setRecordContent("师傅取消最后一个未处理产品,且其他产品状态为已完工!");
                    wechatOrdersRecord.setOrderId(orderId);
                    wechatOrdersRecordMapper.insert(wechatOrdersRecord);
                }
            }
        }
        return result;
    }

    public int getProductStatus(Long orderId) {
        return wechatOrdersRecordMapper.getProductStatus(orderId);
    }

    @Override
    public void finishMakeAppointment(String ordersCode) {
        logger.info("预约完工信息同步到小程序,ordersCode:{}", ordersCode);
        // 异步推送给小程序对接方
        Map<String, Object> params = new HashMap<String, Object>();
        long timestamp = System.currentTimeMillis();
        params.put("ordersCode", ordersCode);
        params.put("timestamp", timestamp);
        params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
        String result = HttpClientUtils
            .postJson(finishmakeAppointment, net.sf.json.JSONObject.fromObject(params).toString());
        if (StringUtils.isNotBlank(result)) {
            net.sf.json.JSONObject o1 = net.sf.json.JSONObject.fromObject(result);
            if (o1.containsKey("errorCode") && o1.getInt("errorCode") == 200) {
                String fAid = o1.getString("data");
                logger.info("预约完工信息同步到小程序成功,aid:{}", fAid);
            } else {
                logger.error("预约完工信息同步到小程序失败,moreInfo:{}", o1.getString("moreInfo"));
            }
        }
    }

    /**
     * 批量增加保养项
     *
     * @param productCode
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<MaintainPrice> batchSaveMaintainItem(String productCode) {
        ProductFilterGradeParam productFilterGradeParam = new ProductFilterGradeParam();
        //接口参数，传入型号
        productFilterGradeParam.setItemCode(productCode);
        //调用沁园接口，查询保养项目
        List<ProductFilterGrade> productFilterGrades = thirdPartyService.cssMaintenanceItem(productFilterGradeParam);
        List<MaintainPrice> maintainPriceList = new ArrayList<MaintainPrice>();
        for (ProductFilterGrade p : productFilterGrades) {
            MaintainPrice maintainPrice = new MaintainPrice();
            maintainPrice.setId(Long.parseLong(p.getGradeSeq()));
            maintainPrice.setMaintainName(p.getGradeName());
            maintainPrice.setProductCode(productCode);
            maintainPrice.setMaintainPrice(new BigDecimal(p.getFilterPrice()));
            maintainPriceList.add(maintainPrice);
        }
        if (maintainPriceList.size() > 0) {
            maintainPriceMapper.batchSave(maintainPriceList);
        }
        return maintainPriceList;
    }

    /**
     * 批量增加安装配件
     *
     * @param installPartList
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchSaveInstallPart(List<InstallPart> installPartList) {
        if (installPartList.size() > 0) {
            installPartMapper.batchSave(installPartList);
        }
    }

    /**
     * 批量增加维修配件
     *
     * @param repairPartList
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchSaveRepairPart(List<RepairPart> repairPartList) {
        if (repairPartList.size() > 0) {
            repairPartMapper.batchSave(repairPartList);
        }
    }


    /**
     * 批量增加维修措施项
     *
     * @param repairItemList
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchSaveRepairItem(List<RepairItem> repairItemList) {
        if (repairItemList.size() > 0) {
            repairItemMapper.batchSave(repairItemList);
        }
    }

    @Transactional
    @Override
    public int doCancel(Long ordersId, Long productId,String ordersCode) {
        if (ordersId == null || productId == null) {
            throw new RuntimeException("入参为空");
        }
        //更新工单对应产品的状态
        qyhProductService.updateProductStatus(ordersId, productId);
        //获取当前工单产品所有状态
        List<Integer> allStatus = qyhProductService.getAllStatus(ordersId);
        if(isAllCancel(allStatus)){
            //工单产品全部取消,则改变工单状态为重新处理,工单状态为3代表重新处理,即拒绝工单
            wechatOrdersMapper.updateOrderStatus(ordersId, SystemConstants.REDEAL, new Date());
            return CancelConstant.CANCEL_REFUND;
        }
        //如果当前工单产品都为取消且有一个以上完工,则工单算作完工
        if(isFinish(allStatus)){
            wechatOrdersMapper.updateOrderStatus(ordersId, SystemConstants.COMPLETE, new Date());
            CompleteParam completeParam = getCompleteParamByOrdersCode(ordersCode);
            updateOrderRecord(completeParam);
            return CancelConstant.CANCEL_COMPLETE;
        }
        return CancelConstant.CANCEL_ONLY;
    }

    /**
     * 工单产品是否全部取消
     * @param list
     * @return
     */
    private boolean isAllCancel(List<Integer> list){
        for (Integer status : list){
            //如果产品有状态不为"取消"
            if(status != Constant.CANCEL){
                return false;
            }
        }
        return true;
    }

    /**
     * 工单是否算完工
     * @param productStatus
     * @return
     */
    @Override
    public boolean isFinish(List<Integer> productStatus){
        boolean isFinish = false;
        for (Integer status : productStatus){
            if(status == Constant.COMPLETE){
                isFinish = true;
                continue;
            }
            if(!(status == Constant.CANCEL || status == Constant.COMPLETE)){
                return false;
            }
        }
        return isFinish;
    }

    /**
     * 更新工单记录
     * @param completeParam
     */
    public void updateOrderRecord(CompleteParam completeParam) {
        WechatOrdersRecord wechatOrdersRecord = new WechatOrdersRecord();
        wechatOrdersRecord.setRecordTime(new Date());
        wechatOrdersRecord.setRecordContent("师傅点击完工!");
        wechatOrdersRecord.setOrderId(completeParam.getOrdersId());
        wechatOrdersRecordMapper.insert(wechatOrdersRecord);
    }


}
