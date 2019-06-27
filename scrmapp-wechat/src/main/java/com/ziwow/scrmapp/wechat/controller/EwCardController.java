package com.ziwow.scrmapp.wechat.controller;

import com.ziwow.scrmapp.common.annotation.MiniAuthentication;
import com.ziwow.scrmapp.common.bean.pojo.CSMEwCardParam;
import com.ziwow.scrmapp.common.bean.pojo.EwCardParam;
import com.ziwow.scrmapp.common.bean.vo.WechatOrdersVo;
import com.ziwow.scrmapp.common.bean.vo.csm.EwCardItem;
import com.ziwow.scrmapp.common.bean.vo.csm.EwCardVo;
import com.ziwow.scrmapp.common.bean.vo.csm.ProductItem;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.ErrorCodeConstants;
import com.ziwow.scrmapp.common.enums.Guarantee;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.common.utils.EwCardUtil;
import com.ziwow.scrmapp.tools.utils.BeanUtils;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.wechat.persistence.entity.*;
import com.ziwow.scrmapp.wechat.service.*;
import com.ziwow.scrmapp.wechat.vo.EwCardDetails;
import com.ziwow.scrmapp.wechat.vo.EwCardInfo;
import com.ziwow.scrmapp.wechat.vo.ServiceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 卡
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




    /**
     * 根据卡号查询延保卡
     * @param cardNo
     * @return
     */
    @RequestMapping(value = "query/ew_card_by_no",method = RequestMethod.GET)
    @MiniAuthentication
    public Result queryCardByNo(@RequestParam("signture") String signture,
                                @RequestParam("time_stamp") String timeStamp,
                                @RequestParam("unionId") String unionId,
                                @RequestParam("card_no") String cardNo){
        Result result = new BaseResult();
        EwCardVo ewCardVo = null;
        EwCard ewCard = null;
        //先去本地系统查询
        ewCard = ewCardService.selectEwCardByNo(cardNo);
        if(ewCard != null){
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("该延保卡已经被注册");
            return result;
        }

        ewCardVo = thirdPartyService.getEwCardListByNo(cardNo);
        if (ErrorCodeConstants.CODE_E0.equals(ewCardVo.getStatus().getCode()) || ErrorCodeConstants.CODE_E092.equals(ewCardVo.getStatus().getCode())) {
            logger.error("csm调用延保卡失败");
            result.setReturnMsg("查询延保卡失败，请检查卡号或稍后再试！");
            result.setReturnCode(Constant.FAIL);
            result.setData("no");
            return result;
        }
        //查询到结果
        ewCard = new EwCard();
        final EwCardItem items = ewCardVo.getItems();
        BeanUtils.copyProperties(items,ewCard);
        ewCard.setRepairTerm(ewCard.getRepairTerm());
        //根据unionId查询出fans
        final WechatUser user = wechatUserService.getUserByUnionid(unionId);
        final WechatFans fans = wechatFansService.getWechatFansByUserId(user.getUserId());
        ewCard.setFansId(fans.getId());
        ewCard.setUseStatus(false);
        ewCard.setCardNo(cardNo);
        ewCardService.addEwCard(ewCard,ewCardVo.getItems().getItemNames(),ewCardVo.getItems().getItemCodes());

        result.setReturnMsg("查询成功");
        result.setReturnCode(Constant.SUCCESS);
        result.setData("ok");
        return result;
    }

    /**
     * 查询用户的延保卡
     * @return
     */
    @RequestMapping(value = "query/my_ew_card",method = RequestMethod.GET)
    @MiniAuthentication
    public Result queryCardByMobile(@RequestParam("signture") String signture,
                                    @RequestParam("time_stamp") String timeStamp,
                                    @RequestParam("unionId") String unionId){
        Result result = new BaseResult();
        final WechatUser user = wechatUserService.getUserByUnionid(unionId);
        final WechatFans fans = wechatFansService.getWechatFansByUserId(user.getUserId());
        List<EwCard> ewCards = ewCardService.selectEwCardByFansId(fans.getId());
        return getSearchResult(result,ewCards,user.getUserId(),fans.getId());
    }




    /**
     * 根据产品编码查询用户的延保卡
     * @param productCode
     * @return
     */
    @RequestMapping(value = "query/ew_card_by_code",method = RequestMethod.GET)
    @MiniAuthentication
    public Result queryCardByItemName( @RequestParam("signture") String signture,
                                       @RequestParam("time_stamp") String timeStamp,
                                       @RequestParam("product_code") String productCode,
                                       @RequestParam("unionId") String unionId){
        Result result = new BaseResult();
        final WechatUser user = wechatUserService.getUserByUnionid(unionId);
        final WechatFans fans = wechatFansService.getWechatFansByUserId(user.getUserId());
        List<EwCard> ewCards = ewCardService.selectEwCardByProductCode(productCode,fans.getId());
        return getSearchResult(result,ewCards,user.getUserId(),fans.getId());
    }


    /**
     * 注册延保卡
     * @return
     */
    @RequestMapping(value = "register_ew_card",method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @MiniAuthentication
    public Result registerEwCard(@RequestParam("signture") String signture,
                                 @RequestParam("time_stamp") String timeStamp,
                                 @RequestParam("unionId") String unionId,
                                 @RequestBody EwCardParam ewCardParam){
        Result result = new BaseResult();
        final WechatUser wechatUser = wechatUserService.getUserByUnionid(unionId);
        final WechatFans fans = wechatFansService.getWechatFansByUserId(wechatUser.getUserId());

        //校验延保卡号And产品条码的正确性
        final EwCard ewCard = ewCardService.selectMyEwCardByNo(ewCardParam.getCardNo(),fans.getId());
        if(ewCard == null){
            result.setReturnMsg("延保卡号错误!");
            result.setReturnCode(Constant.FAIL);
            return result;
        }

        //该类型用户的产品
        final Product product = productService.getProductsByBarCodeAndUserId(wechatUser.getUserId(),ewCardParam.getBarCode());


//        //根据barcode查询 产品
//        ProductItem productItem = thirdPartyService.getProductItem(new ProductParam(product.getModelName(), product.getProductBarCode()));
//        if(productItem == null){
//            result.setReturnMsg("产品信息错误!");
//            result.setReturnCode(Constant.FAIL);
//            return result;
//        }

        //判断该产品是否可以使用延保卡
        if (!EwCardUtil.isGuantee(product.getBuyTime())){
            result.setReturnMsg("对不起,该产品购买超过2年,不能使用延保服务!");
            result.setReturnCode(Constant.FAIL);
            return result;
        }

//        CSMEwCardParam CSMEwCardParam = getCsmEwCardParam(ewCardParam, wechatUser, productItem);
//
//        //csm注册延保卡
//        final Status status = thirdPartyService.registerEwCard(CSMEwCardParam);
//        if (ErrorCodeConstants.CODE_E09.equals(status.getCode())){
//            logger.error("scm注册延保卡失败,延保卡号为[{}],产品条码为[{}]",ewCardParam.getCardNo(),product.getProductBarCode());
//            result.setReturnCode(Constant.FAIL);
//            result.setReturnMsg(status.getMessage());
//            return result;
//        }
        //使用延保卡
        useEwCard(ewCardParam, fans, ewCard.getValidTime(), product.getBuyTime(),product.getProductBarCode());
        result.setReturnMsg("延保卡注册成功");
        result.setReturnCode(Constant.SUCCESS);
        return result;
}


    /**
     * 延保卡详情
     * @param barCode
     * @param signture
     * @param timeStamp
     * @param unionId
     * @return
     */
    @RequestMapping(value = "query/ew_card_details",method = RequestMethod.GET)
    @MiniAuthentication
    public Result queryEwCardDetails(@RequestParam("signture") String signture,
                                     @RequestParam("time_stamp") String timeStamp,
                                     @RequestParam("bar_code")String barCode,
                                     @RequestParam("unionId") String unionId){
        Result result = new BaseResult();
        final WechatUser wechatUser = wechatUserService.getUserByUnionid(unionId);
        final WechatFans fans = wechatFansService.getWechatFansByUserId(wechatUser.getUserId());
        final EwCard ewCard = ewCardService.selectEwCardByBarCode(barCode, fans.getId());
        Product product = productService.getProductsByBarCodeAndUserId(wechatUser.getUserId(),barCode);
        EwCardDetails ewCardDetails = new EwCardDetails();

        //延保状态
        final Guarantee guarantee = EwCardUtil.getGuarantee(product.getBuyTime(),ewCard == null ? null : ewCard.getRepairTerm());
        ewCardDetails.setGuarantee(guarantee);
        //没有使用延保卡的情况
        if (ewCard == null){
            ewCardDetails.setRepairTerm(EwCardUtil.getEndNormalRepairTerm(product.getBuyTime()));
        }else {
            ewCardDetails.setRepairTerm(ewCard.getRepairTerm());
        }

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
            if (wechatOrdersVo.getStatus() == 5 || wechatOrdersVo.getStatus() == 6){
                //如果是安装订单并且完成安装
                if (wechatOrdersVo.getOrderType() == 1){
                    ServiceRecord serviceRecord = new ServiceRecord();
                    serviceRecord.setServiceType(1);
                    wechatOrdersVo.getStatus();
                    final String dateToString = getServiceDate(wechatOrdersVo);
                    serviceRecord.setMessage("安装时间"+dateToString);
                    serviceRecords.add(serviceRecord);
                }else if (wechatOrdersVo.getOrderType() == 2){
                    //如果是维修
                    maintainDate = getEndDate(maintainDate, wechatOrdersVo);
                }else if (wechatOrdersVo.getOrderType() == 3){
                    //如果是保养
                    repairDate = getEndDate(repairDate, wechatOrdersVo);
                }
            }
        }
        //最新维修时间
        if (maintainDate != null){
            ServiceRecord maintainServiceRecord = new ServiceRecord();
            maintainServiceRecord.setServiceType(2);
            maintainServiceRecord.setMessage("最近维修时间"+DateUtil.DateToString(maintainDate, "yyyy年M月d日"));
            serviceRecords.add(maintainServiceRecord);
        }

        //最新保养时间
        if (repairDate != null){
            ServiceRecord repairServiceRecord = new ServiceRecord();
            repairServiceRecord.setServiceType(3);
            repairServiceRecord.setMessage("最新保养时间"+DateUtil.DateToString(repairDate, "yyyy年M月d日"));
            serviceRecords.add(repairServiceRecord);
        }

        ewCardDetails.setServiceRecords(serviceRecords);

        result.setReturnCode(Constant.SUCCESS);
        result.setData(ewCardDetails);
        result.setReturnMsg("查询成功");
        return result;
    }


    /**
     *  判断是最新的日期并返回
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
     * @param wechatOrdersVo
     * @return
     */
    private String getServiceDate(WechatOrdersVo wechatOrdersVo) {
        final Date date = DateUtil.StringToDate(wechatOrdersVo.getEndTime(), "yyyy-MM-dd");
        return DateUtil.DateToString(date, "yyyy年M月d日");
    }


    /**
     * 更改延保卡的使用状态,以及绑定延保卡的产品条码,延保卡的购买时间,保修的时间
     * @param ewCardParam
     * @param fans
     * @param validTime
     * @param productBarCode
     */
    private void useEwCard(@RequestBody EwCardParam ewCardParam, WechatFans fans, int validTime, Date purchDate,String productBarCode) {
        Date repairTerm = null;
        final EwCard ewCard1 = ewCardService.selectEwCardByBarCode(productBarCode, fans.getId());
        //该机器是否用过延保卡
        repairTerm = getRepairTerm(validTime, purchDate, ewCard1);
        ewCardService.useEwCard(ewCardParam.getCardNo(), productBarCode, purchDate,repairTerm);
    }

    /**
     * 获取延保后的期限
     * @param validTime
     * @param purchDate
     * @param ewCard1
     * @return
     */
    private Date getRepairTerm(int validTime, Date purchDate, EwCard ewCard1) {
        Date repairTerm = null;
        //没有用过
        if (ewCard1 == null){
            if (EwCardUtil.isNormal(purchDate)){
                //在正常延保期限内
                repairTerm = EwCardUtil.getNormalRepairTerm(purchDate, validTime);
            }else {
                //过了正常延保期限
                repairTerm = EwCardUtil.getEndRepairTerm(validTime);
            }
        }else {
            //已经用过延保卡,最新延保卡的基础上添加天数
            if (EwCardUtil.isExtend(ewCard1.getRepairTerm())){
                //在延长延保阶段
                repairTerm = EwCardUtil.getExtendRepairTerm(ewCard1.getRepairTerm(),validTime);
            }else {
                //已过延保阶段
                repairTerm = EwCardUtil.getEndRepairTerm(ewCard1.getValidTime());
            }
        }
        return repairTerm;
    }

    /**
     * 组装延保卡基本信息
     * @param ewCardParam
     * @param wechatUser
     * @param productItem
     * @return
     */
    private CSMEwCardParam getCsmEwCardParam(@RequestBody EwCardParam ewCardParam, WechatUser wechatUser, ProductItem productItem,Long productId) {
        CSMEwCardParam CSMEwCardParam = new CSMEwCardParam();
        CSMEwCardParam.setCardNo(ewCardParam.getCardNo());
        //拼装产品所需信息
        CSMEwCardParam.setBarcode(productItem.getBarcode());
        CSMEwCardParam.setItemCode(productItem.getItemCode());
        CSMEwCardParam.setSpec(productItem.getSpec());
        CSMEwCardParam.setPurchDate(productItem.getPurchDate());
        //获取安装时间
        CSMEwCardParam.setInstallTime("");
        final List<WechatOrdersVo> wechatOrdersVos = wechatOrdersService.getWechatOrdersByProductId(productId);
        for (WechatOrdersVo wechatOrdersVo : wechatOrdersVos) {
            //如果是安装订单并且完成安装
            if (wechatOrdersVo.getOrderType() == 1 && (wechatOrdersVo.getStatus() == 5 || wechatOrdersVo.getStatus() == 6)){
                CSMEwCardParam.setInstallTime(wechatOrdersVo.getEndTime());
                break;
            }
        }

        //用户信息
        CSMEwCardParam.setMobile(wechatUser.getMobilePhone());
        CSMEwCardParam.setEnduserName(wechatUser.getUserName());
        //不传电话,但是字段要有
        CSMEwCardParam.setTel("");
        //省市区
        final WechatUserAddress address = wechatUserAddressService.findUserAddresList(wechatUser.getUserId()).get(0);
        CSMEwCardParam.setProvinceName(address.getProvinceName());
        CSMEwCardParam.setCityName(address.getCityName());
        CSMEwCardParam.setCountyName(address.getAreaName());
        //取的街道地址
        CSMEwCardParam.setEnduserAddress(address.getStreetName());
        return CSMEwCardParam;
    }




    /**
     * 封装查询延保卡返回信息(scrm)
     * @param result
     * @param userId
     * @return
     */
    private Result getSearchResult(Result result,List<EwCard> ewCards,String userId,Long fansId) {
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
