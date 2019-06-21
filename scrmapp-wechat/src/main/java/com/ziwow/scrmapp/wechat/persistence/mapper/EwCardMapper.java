package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 延保卡
 * @author songkaiqi
 * @since 2019/06/12/下午5:54
 */
public interface EwCardMapper {
    /**
     * 保存延保卡
     * @param ewCard
     */
    void saveEwCard(EwCard ewCard);

    /**
     * 根据延保卡号查询延保卡
     * @param cardNo
     * @return
     */
    EwCard selectEwCardByNo(@Param("cardNo") String cardNo);

    /**
     * 根据fansId查询延保卡
     * @param fansId
     * @return
     */
    List<EwCard> selectEwCardByFansId(@Param("fansId") Long fansId);

    /**
     * 根据类型名称和fansId查询延保卡
     * @param itemName
     * @param fansId
     * @return
     */
    List<EwCard> selectEwCardByItemNameAndFansId(@Param("itemName") String itemName, @Param("fansId") Long fansId);

    /**
     * 根据卡号id和fansId查询延保卡
     * @param cardNo
     * @param fansId
     * @return
     */
    EwCard selectEwCardByNoAndFansId(@Param("cardNo") String cardNo, @Param("fansId") Long fansId);

    /**
     * 修改延保卡的数据
     * @param cardNo 卡号
     * @param productBarCode 产品条码
     * @param purchDate 购买时间
     * @param repairTerm 保修时间
     */
    void updateCard(@Param("cardNo") String cardNo, @Param("productBarCode") String productBarCode, @Param("purchDate") Date purchDate, @Param("repairTerm") Date repairTerm);

    /**
     * 根据产品条码和fansId查询最新的延保卡
     * @param barCode
     * @param fansId
     * @return
     */
    EwCard selectEwCardByBarCodeAndFansId(@Param("barCode") String barCode, @Param("fansId") Long fansId);

    /**
     * 根据产品编码查询产品
     * @param productCode
     * @param id
     * @return
     */
    List<EwCard> selectEwCardByProductCodeAndFansId(@Param("productCode") String productCode, @Param("fansId")Long id);
}
