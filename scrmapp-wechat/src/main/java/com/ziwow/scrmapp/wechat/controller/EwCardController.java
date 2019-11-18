package com.ziwow.scrmapp.wechat.controller;

import com.ziwow.scrmapp.common.annotation.MiniAuthentication;
import com.ziwow.scrmapp.common.bean.pojo.CSMEwCardParam;
import com.ziwow.scrmapp.common.bean.pojo.EwCardParam;
import com.ziwow.scrmapp.common.bean.pojo.ProductParam;
import com.ziwow.scrmapp.common.bean.vo.WechatOrdersVo;
import com.ziwow.scrmapp.common.bean.vo.csm.*;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.ErrorCodeConstants;
import com.ziwow.scrmapp.common.enums.EwCardStatus;
import com.ziwow.scrmapp.common.enums.Guarantee;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.common.utils.EwCardUtil;
import com.ziwow.scrmapp.tools.utils.BeanUtils;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;
import com.ziwow.scrmapp.wechat.persistence.entity.GrantEwCardRecord;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUserAddress;
import com.ziwow.scrmapp.wechat.service.*;
import com.ziwow.scrmapp.wechat.vo.EwCardDetails;
import com.ziwow.scrmapp.wechat.vo.EwCardInfo;
import com.ziwow.scrmapp.wechat.vo.EwCards;
import com.ziwow.scrmapp.wechat.vo.ServiceRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * 延保卡
 *
 * @author songkaiqi
 * @since 2019/06/09/上午10:38
 */
@RestController
@RequestMapping("/scrmapp/consumer")
public class EwCardController {

    private static final Logger logger = LoggerFactory.getLogger(EwCardController.class);

    @Autowired
    private ThirdPartyService thirdPartyService;

    @Autowired
    private WechatFansService wechatFansService;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private EwCardService ewCardService;

    @Autowired
    private WechatUserAddressService wechatUserAddressService;

    @Autowired
    private WechatOrdersService wechatOrdersService;

    @Autowired
    private ProductService productService;

    @Autowired
    private EwCardActivityService ewCardActivityService;

    @Autowired
    private GrantEwCardRecordService grantEwCardRecordService;

    /**
     * 延保卡限制使用次数
     */
    @Value("${ewcard.limit.num}")
    private Integer limitNum;

    /**
     * 延保卡限制使用年限
     */
    @Value("${ewcard.limit.year}")
    private Integer limitYear;


    /**
     * 根据卡号查询延保卡
     *
     * @param cardNo
     * @return
     */
    @RequestMapping(value = "query/ew_card_by_no", method = RequestMethod.GET)
    @MiniAuthentication
    @Transactional
    public Result queryCardByNo(@RequestParam("signture") String signture,
                                @RequestParam("time_stamp") String timeStamp,
                                @RequestParam("unionId") String unionId,
                                @RequestParam("card_no") String cardNo) {
        Result result = new BaseResult();
        QueryNoEwCardVo ewCardVo = null;
        EwCard ewCard = null;
        //先去本地系统查询
        ewCard = ewCardService.selectEwCardByNo(cardNo);
        if (ewCard != null) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("该卡券已领取");
            logger.info("延保卡已经被注册,{}", cardNo);
            return result;
        }

        boolean isActivity = isActivity(cardNo);
        GrantEwCardRecord gwr = null;
        if (isActivity) {
            gwr = grantEwCardRecordService.selectRecordByMask(cardNo);
            if (gwr == null) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("该卡券已领取或卡号错误");
                return result;
            }
            cardNo = ewCardActivityService.selectCardNo(gwr.getType());

            if (StringUtils.isBlank(cardNo)) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("对不起，延保卡发放完毕");
                return result;
            }
        }

        ewCardVo = thirdPartyService.getEwCardListByNo(cardNo);
        if (ErrorCodeConstants.CODE_E094.equals(ewCardVo.getStatus().getCode()) || ErrorCodeConstants.CODE_E092.equals(ewCardVo.getStatus().getCode()) || "已注册".equals(ewCardVo.getItems().getCardStat())) {
            logger.error("csm查询延保卡失败,{}", cardNo);
            result.setReturnMsg("查询延保卡失败，请检查卡号或稍后再试！");
            result.setData("no");
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        //查询到结果
        ewCard = new EwCard();
        final EwCardItem items = ewCardVo.getItems();
        BeanUtils.copyProperties(items, ewCard);
        ewCard.setRepairTerm(ewCard.getRepairTerm());
        //根据unionId查询出fans
        final WechatUser user = wechatUserService.getUserByUnionid(unionId);
        ewCard.setFansId(user.getWfId());
        ewCard.setCardStatus(EwCardStatus.NOT_USE);
        ewCard.setCardNo(cardNo);
        ewCardService.addEwCard(ewCard, ewCardVo.getItems().getItemNames(), ewCardVo.getItems().getItemCodes());

        //如果是活动送的卡
        if (isActivity) {
            grantEwCardRecordService.updateReceiveByMask(gwr.getMask(), true);
            ewCardActivityService.addPhoneByCardNo(cardNo, gwr.getPhone());
        }
        result.setReturnMsg("查询成功");
        result.setReturnCode(Constant.SUCCESS);
        result.setData("ok");
        return result;
    }

    /**
     * 判断是否是掩码
     *
     * @param cardNo
     * @return
     */
    private boolean isActivity(String cardNo) {
        return StringUtils.startsWith(cardNo, "16");
    }

    /**
     * 查询用户的延保卡
     *
     * @return
     */
    @RequestMapping(value = "query/my_ew_card", method = RequestMethod.GET)
    @MiniAuthentication
    public Result queryCardByMobile(@RequestParam("signture") String signture,
                                    @RequestParam("time_stamp") String timeStamp,
                                    @RequestParam("unionId") String unionId) {
        Result result = new BaseResult();
        final WechatUser user = wechatUserService.getUserByUnionid(unionId);
        Set<EwCard> ewCards = ewCardService.selectEwCardByFansId(user.getWfId());
        return getSearchResult(result, ewCards, user.getUserId(), user.getWfId());
    }

    /**
     * 校验wx手机号与csm是否一致
     *
     * @return
     */
    @RequestMapping(value = "query/phone_by_barcode", method = RequestMethod.GET)
    @MiniAuthentication
    public Result queryMobileByBarcode(@RequestParam("signture") String signture,
                                       @RequestParam("time_stamp") String timeStamp,
                                       @RequestParam("unionId") String unionId,
                                       @RequestParam("barcode") String barcode) {

        final QueryBarCodeEwCardVo ewCardVo = thirdPartyService.getBindPhoneAndCardByBarcode(barcode);
        final WechatUser user = wechatUserService.getUserByUnionid(unionId);
        Result result = new BaseResult();
        String mobile;
        mobile = ewCardVo.getMobile();
        if (StringUtils.isBlank(mobile)){
            mobile = ewCardVo.getItems().get(0).getMobile();
        }
        if (user.getMobilePhone().equals(mobile)) {
            result.setReturnMsg("查询成功");
            result.setReturnCode(Constant.SUCCESS);
            result.setData("ok");
        } else {
            result.setReturnMsg("手机号不一致");
            result.setReturnCode(Constant.FAIL);
            result.setData(mobile);
        }
        return result;
    }

    /**
     * 根据条码查询 同步csm中的延保信息
     *
     * @return
     */
    private void qyscMobileByBarcode( String barcode ,Long fansId) {

        final QueryBarCodeEwCardVo ewCardVo = thirdPartyService.getBindPhoneAndCardByBarcode(barcode);
        final List<EwCard> ewCards = ewCardService.selectEwCardsByBarCode(barcode);
        List<EwCardItem> ewCardItemList = new ArrayList<>();



        for (EwCard ewCard : ewCards) {
            EwCardItem ewCardItem = new EwCardItem();
            ewCardItem.setCardNo(ewCard.getCardNo());
            ewCardItemList.add(ewCardItem);
        }

        final List<EwCardItem> items = ewCardVo.getItems();
        if (CollectionUtils.isEmpty(items)){
            return;
        }


        //记录延保卡用了几年
        int i = 0;
        for (EwCardItem item : items) {
            if (!ewCardItemList.contains(item)) {
                //csm中存在。微信不存在，保存到数据库
                try {
                    EwCard ewCard = new EwCard();
                    ewCard.setCardStatus(EwCardStatus.ENTERED_INTO_FORCE);
                    ewCard.setInstallList(true);
                    ewCard.setCardNo(item.getCardNo());
                    ewCard.setProductBarCodeTwenty(barcode);
                    ewCard.setFansId(fansId);
                    ewCard.setValidTime(item.getValidTime());
                    Date repairTerm = DateUtils.parseDate(item.getRepairTerm(), "yyyy-MM-dd HH:mm:ss");
                    final Date date = DateUtils.addYears(repairTerm, -i);
                    ewCard.setRepairTerm(date);
                    ewCard.setPurchDate(DateUtils.parseDate(item.getPurchDate(), "yyyy-MM-dd HH:mm:ss"));
                    ewCardService.saveEwCard(ewCard);
                    i += ewCard.getValidTime()/365;
                } catch (Exception e) {
                    logger.error("csm同步微信延保卡保存失败：[{e}],产品条码为：[{}]", e ,barcode);
                }
            }
        }

    }


    /**
     * 根据产品编码查询用户的延保卡
     *
     * @param productCode
     * @return
     */
    @RequestMapping(value = "query/ew_card_by_code", method = RequestMethod.GET)
    @MiniAuthentication
    public Result queryCardByItemName(@RequestParam("signture") String signture,
                                      @RequestParam("time_stamp") String timeStamp,
                                      @RequestParam("product_code") String productCode,
                                      @RequestParam("unionId") String unionId) {
        Result result = new BaseResult();
        final WechatUser user = wechatUserService.getUserByUnionid(unionId);
        Set<EwCard> ewCards = ewCardService.selectEwCardByProductCode(productCode, user.getWfId());
        return getSearchResult(result, ewCards, user.getUserId(), user.getWfId());
    }

    /**
     * 注册延保卡
     *
     * @return
     */
    @RequestMapping(value = "register_ew_card", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @MiniAuthentication
    public Result registerEwCard(@RequestParam("signture") String signture,
                                 @RequestParam("time_stamp") String timeStamp,
                                 @RequestParam("unionId") String unionId,
                                 @RequestBody EwCardParam ewCardParam) {
        Result result = new BaseResult();
        final WechatUser wechatUser = wechatUserService.getUserByUnionid(unionId);

        //校验延保卡号And产品条码的正确性
        final EwCard ewCard = ewCardService.selectMyEwCardByNo(ewCardParam.getCardNo(), wechatUser.getWfId());
        if (ewCard == null) {
            result.setReturnMsg("延保卡号错误!");
            result.setReturnCode(Constant.FAIL);
            logger.info("延保卡错误,卡号:{},用户:{}", ewCardParam.getCardNo(), wechatUser.getMobilePhone());
            return result;
        }

        //该类型用户的产品
        final Product product = productService.getProductsByBarCodeAndUserId(wechatUser.getUserId(), ewCardParam.getBarCode());
        if (product == null || product.getBuyTime() == null || StringUtils.isBlank(product.getProductBarCode())) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("产品信息错误!");
            logger.info("当前用户不存在此产品或无购买时间,卡号:{},用户:{}", ewCardParam.getCardNo(), wechatUser.getMobilePhone());
            return result;
        }

        //根据barcode查询 产品
        ProductItem productItem = thirdPartyService.getProductItem(new ProductParam(product.getModelName(), product.getProductBarCode()));

        if (productItem == null) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("产品信息错误!");
            logger.info("CSM不存在改产品或产品有误,卡号:{},用户:{}", ewCardParam.getCardNo(), wechatUser.getMobilePhone());
            return result;
        }

        //判断该产品是否可以使用延保卡
        if (!EwCardUtil.isGuantee(product.getBuyTime())) {
            result.setReturnMsg("对不起,该产品购买超过2年,不能使用延保服务!");
            result.setReturnCode(Constant.FAIL);
            logger.info("该产品购买超过2年,不能使用延保服务,卡号:{},用户:{}", ewCardParam.getCardNo(), wechatUser.getMobilePhone());
            return result;
        }

        // 判断用户是否满足使用延保卡的条件
        final List<EwCard> ewCards = ewCardService.selectEwCardsByBarCode(ewCardParam.getBarCode());
        final int ewCardYear = getEwCardYear(ewCards);
        final String message = EwCardUtil.contentUseCard(limitNum, limitYear, ewCards.size() + 1, ewCardYear + (ewCard.getValidTime() / EwCardUtil.Dates.YEAR.getDay()));
        if (message != null) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg(message);
            logger.info("用户不满足延保卡使用条件,卡号:{},用户:{},问题:{}", ewCardParam.getCardNo(), wechatUser.getMobilePhone(), message);
            return result;
        }

        CSMEwCardParam CSMEwCardParam = getCsmEwCardParam(ewCardParam.getCardNo(), wechatUser, productItem, product.getId(), product.getBuyTime());

        //判断是否存在安装工单
        ewCard.setCardStatus(EwCardStatus.TO_BE_AUDITED);
        BaseCardVo baseCardVo = null;
        if (thirdPartyService.existInstallList(wechatUser.getMobilePhone())) {
            ewCard.setInstallList(true);
            baseCardVo = thirdPartyService.registerEwCard(CSMEwCardParam);
        } else {
            ewCard.setInstallList(false);
            if (EwCardUtil.gtSevenDay(product.getBuyTime())) {
                //非官方 大于7天
                baseCardVo = thirdPartyService.registerEwCard(CSMEwCardParam);
            }
        }

        if (baseCardVo != null && baseCardVo.getStatus().getCode().equals(ErrorCodeConstants.CODE_E0)) {
            ewCard.setCardStatus(EwCardStatus.ENTERED_INTO_FORCE);
        } else if (baseCardVo != null && ErrorCodeConstants.CODE_E09.equals(baseCardVo.getStatus().getCode()) && !"资产未属实,待资产属实后再绑定,请耐心等待。".equals(baseCardVo.getData())) {
            logger.error("csm注册延保卡失败,延保卡号为[{}],产品条码为[{}]", ewCardParam.getCardNo(), product.getProductBarCode());
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg(baseCardVo.getData());
            return result;
        }

        //使用延保卡
        useEwCard(ewCardParam, ewCard.getValidTime(), product.getBuyTime(), product.getProductBarCode(), ewCard.getInstallList(), ewCard.getCardStatus());
        result.setReturnMsg("延保卡注册成功");
        logger.info("延保卡注册成功:延保卡号为[{}],产品条码为[{}]", ewCardParam.getCardNo(), product.getProductBarCode());
        result.setReturnCode(Constant.SUCCESS);
        return result;
    }


    /**
     * 延保卡详情
     *
     * @param barCode
     * @param signture
     * @param timeStamp
     * @param unionId
     * @return
     */
    @RequestMapping(value = "query/ew_card_details", method = RequestMethod.GET)
    @MiniAuthentication
    public Result queryEwCardDetails(@RequestParam("signture") String signture,
                                     @RequestParam("time_stamp") String timeStamp,
                                     @RequestParam("bar_code") String barCode,
                                     @RequestParam("unionId") String unionId) {
        Result result = new BaseResult();
        final WechatUser wechatUser = wechatUserService.getUserByUnionid(unionId);
        //同步csm延保信息
        try {
            qyscMobileByBarcode(barCode,wechatUser.getWfId());
        } catch (Exception e) {
            logger.error("同步csm延保信息失败。错误信息为[{}],产品条码为：[{}]",e,barCode);
        }

        final EwCard ewCard = ewCardService.selectEwCardByBarCode(barCode);
        Product product = productService.getProductsByBarCodeAndUserId(wechatUser.getUserId(), barCode);
        EwCardDetails ewCardDetails = new EwCardDetails();

        if (product == null) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("您没有绑定该产品");
            return result;
        }

        //延保状态
        final Guarantee guarantee = EwCardUtil.getGuarantee(product.getBuyTime(), ewCard == null ? null : ewCard.getRepairTerm());
        ewCardDetails.setGuarantee(guarantee);

        List<EwCard> ewCards = ewCardService.selectEwCardsByBarCode(barCode);
        List<EwCards> collect = new ArrayList<>(ewCards.size());
        for (EwCard card : ewCards) {
            EwCards ew = new EwCards();
            ew.setCardNo(card.getCardNo());
            ew.setRairTerm(card.getRepairTerm());
            ew.setStartTime(EwCardUtil.getMStartDate(card.getRepairTerm(), card.getValidTime()));
            ew.setCardAttributes(EwCardUtil.getEwDate(card.getValidTime()));
            ew.setCardStatus(card.getCardStatus().getMessage());
            collect.add(ew);
        }

        if (ewCards.size() != 0) {
            ewCardDetails.setCards(collect);
        }
        //是否可以继续使用延保卡
        ewCardDetails.setCanUseCard(canUseCard(barCode));


        ewCardDetails.setNormal(EwCardUtil.getEndNormalRepairTerm(product.getBuyTime()));

        // 商品相关信息
        ewCardDetails.setProductImg(product.getProductImage());
        ewCardDetails.setProductName(product.getProductName());
        ewCardDetails.setBarCode(product.getProductBarCode());
        ewCardDetails.setItemName(product.getModelName());
        ewCardDetails.setBuyTime(product.getBuyTime());
        ewCardDetails.setProductCode(product.getProductCode());
        //服务记录
        //安装时间
        List<ServiceRecord> serviceRecords = new ArrayList<>(3);
        Date maintainDate = null;
        Date repairDate = null;
        List<WechatOrdersVo> orders = wechatOrdersService.getWechatOrdersByProductId(product.getId());
        for (WechatOrdersVo wechatOrdersVo : orders) {
            //订单必须是已经完成的或者完成评价
            if (wechatOrdersVo.getStatus() == 5 || wechatOrdersVo.getStatus() == 6) {
                //如果是安装订单并且完成安装
                if (wechatOrdersVo.getOrderType() == 1) {
                    ServiceRecord serviceRecord = new ServiceRecord();
                    serviceRecord.setServiceType(1);
                    wechatOrdersVo.getStatus();
                    final String dateToString = getServiceDate(wechatOrdersVo);
                    serviceRecord.setMessage("安装时间" + dateToString);
                    serviceRecords.add(serviceRecord);
                } else if (wechatOrdersVo.getOrderType() == 2) {
                    //如果是维修
                    maintainDate = getEndDate(maintainDate, wechatOrdersVo);
                } else if (wechatOrdersVo.getOrderType() == 3) {
                    //如果是保养
                    repairDate = getEndDate(repairDate, wechatOrdersVo);
                }
            }
        }
        //最新维修时间
        if (maintainDate != null) {
            ServiceRecord maintainServiceRecord = new ServiceRecord();
            maintainServiceRecord.setServiceType(2);
            maintainServiceRecord.setMessage("最近维修时间" + DateUtil.DateToString(maintainDate, "yyyy年M月d日"));
            serviceRecords.add(maintainServiceRecord);
        }

        //最新保养时间
        if (repairDate != null) {
            ServiceRecord repairServiceRecord = new ServiceRecord();
            repairServiceRecord.setServiceType(3);
            repairServiceRecord.setMessage("最新保养时间" + DateUtil.DateToString(repairDate, "yyyy年M月d日"));
            serviceRecords.add(repairServiceRecord);
        }

        ewCardDetails.setServiceRecords(serviceRecords);

        result.setReturnCode(Constant.SUCCESS);
        result.setData(ewCardDetails);
        result.setReturnMsg("查询成功");
        return result;
    }


    /**
     * 判断是否满足使用延保卡的条件
     *
     * @param barCode
     * @return
     */
    public boolean canUseCard(String barCode) {
        final List<EwCard> ewCards = ewCardService.selectEwCardsByBarCode(barCode);
        return EwCardUtil.isCanUseCard(limitNum, limitYear, ewCards.size(), getEwCardYear(ewCards));
    }

    /**
     * 计算延保卡的总年数
     *
     * @param ewCards
     * @return
     */
    public int getEwCardYear(List<EwCard> ewCards) {
        int sum = 0;
        for (EwCard ewCard : ewCards) {
            sum += (ewCard.getValidTime() / EwCardUtil.Dates.YEAR.getDay());
        }
        return sum;
    }

    /**
     * 判断是最新的日期并返回
     *
     * @param repairDate
     * @param wechatOrdersVo
     * @return
     */
    private Date getEndDate(Date repairDate, WechatOrdersVo wechatOrdersVo) {
        final Date date = DateUtil.StringToDate(wechatOrdersVo.getEndTime(), "yyyy-MM-dd");
        if (repairDate == null) {
            repairDate = date;
        } else {
            if (repairDate.getTime() < date.getTime()) {
                repairDate = date;
            }
        }
        return repairDate;
    }

    /**
     * 转换页面上显示的时间
     *
     * @param wechatOrdersVo
     * @return
     */
    private String getServiceDate(WechatOrdersVo wechatOrdersVo) {
        final Date date = DateUtil.StringToDate(wechatOrdersVo.getEndTime(), "yyyy-MM-dd");
        return DateUtil.DateToString(date, "yyyy年M月d日");
    }


    /**
     * 更改延保卡的使用状态,以及绑定延保卡的产品条码,延保卡的购买时间,保修的时间
     *
     * @param ewCardParam
     * @param validTime
     * @param productBarCode
     */
    private void useEwCard(EwCardParam ewCardParam, int validTime, Date purchDate, String productBarCode, Boolean installlList, EwCardStatus ewCardStatus) {
        final EwCard ewCard = ewCardService.selectEwCardByBarCode(productBarCode);
        ewCardService.useEwCard(ewCardParam.getCardNo(), productBarCode, purchDate, getRepairTerm(validTime, purchDate, ewCard), installlList, ewCardStatus);
    }

    /**
     * 获取延保后的期限
     *
     * @param validTime
     * @param purchDate
     * @return
     */
    private Date getRepairTerm(int validTime, Date purchDate, EwCard ewCard1) {
        final Date repairTerm;
        if (ewCard1 == null) {
            //没有用过
            repairTerm = EwCardUtil.getEndRepairTerm(purchDate, validTime);
        } else {
            //用过延保卡的情况
            repairTerm = EwCardUtil.getEwEndRepairTerm(ewCard1.getRepairTerm(), validTime);
        }
        return repairTerm;
    }

    /**
     * 组装延保卡基本信息
     *
     * @param wechatUser
     * @param productItem
     * @return
     */
    private CSMEwCardParam getCsmEwCardParam(String cardNo, WechatUser wechatUser, ProductItem productItem, Long productId, Date buyTime) {
        CSMEwCardParam CSMEwCardParam = new CSMEwCardParam();
        CSMEwCardParam.setCardNo(cardNo);
        //拼装产品所需信息
        CSMEwCardParam.setBarcode(productItem.getBarcode());
        CSMEwCardParam.setItemCode(productItem.getItemCode() == null ? "" : productItem.getItemCode());
        CSMEwCardParam.setSpec(productItem.getSpec() == null ? "" : productItem.getSpec());
        CSMEwCardParam.setPurchDate(buyTime);
        //获取安装时间
        CSMEwCardParam.setInstallTime("");
        final List<WechatOrdersVo> wechatOrdersVos = wechatOrdersService.getWechatOrdersByProductId(productId);
        for (WechatOrdersVo wechatOrdersVo : wechatOrdersVos) {
            //如果是安装订单并且完成安装
            if (wechatOrdersVo.getOrderType() == 1 && (wechatOrdersVo.getStatus() == 5 || wechatOrdersVo.getStatus() == 6)) {
                CSMEwCardParam.setInstallTime(wechatOrdersVo.getEndTime());
                break;
            }
        }

        //用户信息
        CSMEwCardParam.setMobile(wechatUser.getMobilePhone() == null ? "" : wechatUser.getMobilePhone());
        CSMEwCardParam.setEnduserName(wechatUser.getUserName() == null ? "" : wechatUser.getUserName());
        //不传电话,但是字段要有
        CSMEwCardParam.setTel("");
        //省市区
        WechatUserAddress address = null;
        final List<WechatUserAddress> userAddresList = wechatUserAddressService.findUserAddresList(wechatUser.getUserId());
        if (CollectionUtils.isEmpty(userAddresList)) {
            address = new WechatUserAddress();
        } else {
            address = userAddresList.get(0);
        }
        CSMEwCardParam.setProvinceName(address.getProvinceName() == null ? "" : address.getProvinceName());
        CSMEwCardParam.setCityName(address.getCityName() == null ? "" : address.getCityName());
        CSMEwCardParam.setCountyName(address.getAreaName() == null ? "" : address.getAreaName());
        //取的街道地址
        CSMEwCardParam.setEnduserAddress(address.getStreetName() == null ? "" : address.getStreetName());
        return CSMEwCardParam;
    }


    /**
     * 封装查询延保卡返回信息(scrm)
     *
     * @param result
     * @param userId
     * @return
     */
    private Result getSearchResult(Result result, Set<EwCard> ewCards, String userId, Long fansId) {
        List<EwCardInfo> ewCardInfos = new ArrayList<>();

        //组装 EwCardInfo
        for (EwCard ewCard : ewCards) {
            EwCardInfo ewCardInfo = new EwCardInfo();
            ewCardInfo.setCardNo(ewCard.getCardNo());
            ewCardInfo.setValidTime(ewCard.getValidTime());
            ewCardInfo.setEwCardItems(ewCard.getEwCardItems());
            ewCardInfos.add(ewCardInfo);
        }
        result.setData(ewCardInfos);
        result.setReturnCode(Constant.SUCCESS);
        result.setReturnMsg("查询成功");
        return result;
    }

}
