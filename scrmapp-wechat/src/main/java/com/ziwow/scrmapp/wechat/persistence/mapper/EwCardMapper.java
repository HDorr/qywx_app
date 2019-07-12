package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

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
     * @param cardNo 卡号
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
     * @param itemName 分类名称
     * @param fansId
     * @return
     */
    List<EwCard> selectEwCardByItemNameAndFansId(@Param("itemName") String itemName, @Param("fansId") Long fansId);

    /**
     * 根据卡号id和fansId查询延保卡
     * @param cardNo 卡号
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
     * 根据卡号集合查询产品
     * @param cardIds
     * @return
     */
    List<EwCard> selectEwCardByCardIds(@Param("cardIds") List<Long> cardIds);

    /**
     * 插入延保
     * @param cardId
     * @param itemName
     * @param itemCode
     */
    @Insert("insert into t_ew_card_items(item_name,item_code,ew_card_id) values(#{itemName},#{itemCode},#{cardId})")
    void addEwCardItems(@Param("cardId") Long cardId, @Param("itemName")String itemName, @Param("itemCode")String itemCode);

    /**
     * 查询出符合编号的延保卡id
     * @param productCode
     * @param fansId
     * @return
     */
    List<Long> selectEwCardIdByCodeAndFansId(@Param("productCode")String productCode,@Param("fansId")Long fansId);

    /**
     * 根据产品条码查询对应的延保卡(最新的)
     * @param barCode
     * @return
     */
    @Select("select * from t_ew_card where product_bar_code_twenty = #{barCode} order by repair_term desc limit 1")
    @ResultMap("ewCardMap")
    EwCard selectEwCardByBarCode(@Param("barCode") String barCode);

    /**
     * 根据产品条码查询该产品使用的延保卡
     * @param barCode
     * @return
     */
    @Select("select * from t_ew_card where product_bar_code_twenty = #{barCode} order by repair_term asc")
    @ResultMap("ewCardMap")
    List<EwCard> selectEwCardsByBarCode(String barCode);
}
