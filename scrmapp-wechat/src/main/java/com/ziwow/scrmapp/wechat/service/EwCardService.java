package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.enums.EwCardStatus;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCardItems;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 延保卡
 * @author songkaiqi
 * @since 2019/06/12/下午5:49
 */
public interface EwCardService {


    /**
     * 添加延保卡
     * @param ewCard
     * @param itemNames
     * @param itemCodes
     * @return 类型编号集合
     */
    void addEwCard(EwCard ewCard, String[] itemNames, String[] itemCodes);

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
     * @return
     */
    EwCard selectEwCardByBarCode(String barCode);

    EwCard selectEwCardByBarCodeAndFansId(String barCode, Long fansId);

    /**
     * 使用延保卡
     * @param cardNo 延保卡号
     * @param productBarCode 产品条码
     * @param purchDate 购买时间
     * @param repairTerm 保修期限
     */
    void useEwCard(String cardNo, String productBarCode, Date purchDate,Date repairTerm, Boolean installList, EwCardStatus ewCardStatus);

    /**
     * 查询根据延保卡号用户未使用的延保卡
     * @param cardNo
     * @param fansId
     * @return
     */
    EwCard selectMyEwCardByNo(String cardNo, Long fansId);

    /**
     * 根据产品编码查询延保卡
     * @param productCode
     * @param id
     * @return
     */
    List<EwCard> selectEwCardByProductCode(String productCode, Long id);

    /**
     * 根据产品条码判断该产品是否使用过延保卡
     * @param barCode
     * @return
     */
    EwCard existEwCardByBarCode(String barCode);

    /**
     * 根据产品条码查询该产品使用的延保卡
     * @param barCode
     * @return
     */
    List<EwCard> selectEwCardsByBarCode(String barCode);

    /**
     * 根据延保卡的状态查询对应的产品条码
     * @param status
     * @return
     */
    Set<String> selectEwCardsByStatus(EwCardStatus status);

    /**
     * 根据延保卡状态和延保卡是否含有安装单查询出延保卡对应的产品条码
     * @param ewCardStatus
     * @param installList
     * @return
     */
    List<EwCard> selectEwCardsByStatusAndInstall(EwCardStatus ewCardStatus, boolean installList);
}
