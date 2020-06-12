package com.ziwow.scrmapp.wechat.customer;

import com.ziwow.scrmapp.common.annotation.MiniAuthentication;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;
import com.ziwow.scrmapp.wechat.persistence.entity.GrantEwCardRecord;
import com.ziwow.scrmapp.wechat.service.EwCardActivityService;
import com.ziwow.scrmapp.wechat.service.EwCardService;
import com.ziwow.scrmapp.wechat.service.GrantEwCardRecordService;
import com.ziwow.scrmapp.wechat.service.ProductService;
import com.ziwow.scrmapp.wechat.vo.CardInfoVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 客服相关延保卡
 * @author songkaiqi
 * @since 2020/06/12/上午11:05
 */
@RestController
@RequestMapping("ewCardService")
public class EwCardServiceController {

    @Autowired
    private GrantEwCardRecordService grantEwCardRecordService;

    @Autowired
    private EwCardService ewCardService;

    @Autowired
    private EwCardActivityService ewCardActivityService;

    @Autowired
    private ProductService productService;

    /**
     * 根据手机号查询延保信息
     * @param signture
     * @param timeStamp
     * @param phone 收到短信的手机号
     * @return
     */
    @RequestMapping(value = "cardInfo",method = RequestMethod.GET)
    @MiniAuthentication
    public Result cardInfo(@RequestParam("signture") String signture,
                           @RequestParam("time_stamp") String timeStamp,
                           @RequestParam("fromPhone") String phone){
        Result result = new BaseResult();
        final List<GrantEwCardRecord> grantEwCardRecords = grantEwCardRecordService.selectRecordByPhone(phone);
        if (CollectionUtils.isEmpty(grantEwCardRecords)) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("无该用户的延保卡发放记录");
            return result;
        }

        List<CardInfoVo> cardInfoVos = new ArrayList<>();
        for (GrantEwCardRecord record : grantEwCardRecords) {
            CardInfoVo cardInfoVo = new CardInfoVo();
            cardInfoVo.setSendTime(record.getSendTime());
            cardInfoVo.setReceivePhone(record.getPhone());
            cardInfoVo.setEwCardType(record.getType());
            cardInfoVo.setSrcType(record.getSrcType());
            cardInfoVo.setReceive(record.getRecevice());
            cardInfoVo.setMask(record.getMask());

            //如果已经领取，查询相关使用信息
            if (record.getRecevice()) {
                final String cardNo = ewCardActivityService.selectCardNoByMask(record.getMask());
                cardInfoVo.setEwCardNo(cardNo);
                final EwCard ewCard = ewCardService.selectEwCardByNo(cardNo);
                cardInfoVo.setBarcode(ewCard.getProductBarCodeTwenty());
                cardInfoVo.setValidTime(ewCard.getValidTime()/365);
            }
            cardInfoVos.add(cardInfoVo);
        }

        result.setReturnMsg("查询成功");
        result.setReturnCode(Constant.SUCCESS);
        result.setData(cardInfoVos);
        return result;
    }


    /**
     * 根据条码查询机器的延保信息
     * @param signture
     * @param timeStamp
     * @param barcode 条码
     * @return
     */
    @RequestMapping(value = "productEwInfo",method = RequestMethod.GET)
    @MiniAuthentication
    public Result productEwInfo(@RequestParam("signture") String signture,
                           @RequestParam("time_stamp") String timeStamp,
                           @RequestParam("fromPhone") String barcode){
        Result result = new BaseResult();
        //fixme 数据库 t_product表需要给条码加一个索引
        //查询出
        final Product product = productService.getProductsByBarCode(barcode);


        result.setReturnMsg("查询成功");
        result.setReturnCode(Constant.SUCCESS);

        return result;
    }

}
