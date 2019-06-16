package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;

import java.util.Date;
import java.util.List;

/**
 * 延保卡
 * @author songkaiqi
 * @since 2019/06/12/下午5:49
 */
public interface EwCardService {


    /**
     * 添加延保卡
     * @param ewCard
     */
    void addEwCard(EwCard ewCard);

    /**
     * 根据卡号查询延保卡信息
     * @param cardNo
     * @return
     */
    EwCard selectEwCardByNo(String cardNo);

    /**
     * 根据fansId查询延保卡
     * @param fansId
     * @return
     */
    List<EwCard> selectEwCardByFansId(Long fansId);

    /**
     * 根据类型查询延保卡信息
     * @param itemName 型号名称
     * @param fansId
     * @return
     */
    List<EwCard> selectEwCardByItemName(String itemName,Long fansId);

    /**
     * 根据产品条码查询最新的延保卡
     * @param barCode
     * @param fansId
     * @return
     */
    EwCard selectEwCardByBarCode(String barCode, Long fansId);

    /**
     * 使用延保卡
     * @param cardNo 延保卡号
     * @param productBarCode 产品条码
     * @param purchDate 购买时间
     * @param repairTerm 保修期限
     */
    void useEwCard(String cardNo, String productBarCode, Date purchDate,Date repairTerm);

    /**
     * 查询根据延保卡号用户未使用的延保卡
     * @param cardNo
     * @param fansId
     * @return
     */
    EwCard selectMyEwCardByNo(String cardNo, Long fansId);
}
