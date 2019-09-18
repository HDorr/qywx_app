package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.enums.EwCardStatus;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCardItems;
import com.ziwow.scrmapp.wechat.persistence.mapper.EwCardMapper;
import com.ziwow.scrmapp.wechat.service.EwCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author songkaiqi
 * @since 2019/06/12/下午5:51
 */
@Service
public class EwCardServiceImpl implements EwCardService {

    @Autowired
    private EwCardMapper ewCardMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addEwCard(EwCard ewCard, String[] itemNames, String[] itemCodes) {
        ewCardMapper.saveEwCard(ewCard);
        for (int i = 0; i < itemNames.length; i++) {
            EwCardItems ewCardItem = new EwCardItems();
            ewCardItem.setItemCode(itemCodes[i]);
            ewCardItem.setItemName(itemNames[i]);
            ewCardMapper.addEwCardItems(ewCard.getId(), itemNames[i], itemCodes[i]);
        }
    }

    @Override
    public EwCard selectEwCardByNo(String cardNo) {
        return ewCardMapper.selectEwCardByNo(cardNo);
    }

    @Override
    public List<EwCard> selectEwCardByFansId(Long fansId) {
        return ewCardMapper.selectEwCardByFansId(fansId);
    }

    @Override
    public List<EwCard> selectEwCardByItemName(String itemName, Long fansId) {
        return ewCardMapper.selectEwCardByItemNameAndFansId(itemName, fansId);
    }

    @Override
    public EwCard selectEwCardByBarCode(String barCode) {
        return ewCardMapper.selectEwCardByBarCode(barCode);
    }

    @Override
    public EwCard selectEwCardByBarCodeAndFansId(String barCode, Long fansId) {
        return ewCardMapper.selectEwCardByBarCodeAndFansId(barCode, fansId);
    }

    @Override
    public void useEwCard(String cardNo, String productBarCode, Date purchDate, Date repairTerm, Boolean installList, EwCardStatus ewCardStatus) {
        ewCardMapper.updateCard(cardNo, productBarCode, purchDate, repairTerm, installList, ewCardStatus);
    }

    @Override
    public EwCard selectMyEwCardByNo(String cardNo, Long fansId) {
        return ewCardMapper.selectEwCardByNoAndFansId(cardNo, fansId);
    }

    @Override
    public List<EwCard> selectEwCardByProductCode(String productCode, Long id) {
        final List<Long> cardIds = ewCardMapper.selectEwCardIdByCodeAndFansId(productCode, id);
        if (CollectionUtils.isEmpty(cardIds)) {
            return Collections.EMPTY_LIST;
        } else {
            return ewCardMapper.selectEwCardByCardIds(cardIds);
        }
    }

    @Override
    public EwCard existEwCardByBarCode(String barCode) {
        return ewCardMapper.selectEwCardByBarCode(barCode);
    }

    @Override
    public List<EwCard> selectEwCardsByBarCode(String barCode) {
        return ewCardMapper.selectEwCardsByBarCode(barCode);
    }

    @Override
    public Set<String> selectEwCardsByStatus(EwCardStatus status) {
        return ewCardMapper.selectEwCardsByStatus(status);
    }

    @Override
    public List<EwCard> selectEwCardsByStatusAndInstall(EwCardStatus ewCardStatus, boolean installList) {
        return ewCardMapper.selectEwCardsByStatusAndInstall(ewCardStatus, installList);
    }

    @Override
    public void updateCardStatus(String cardNo, EwCardStatus status) {
        ewCardMapper.updateCardStatus(status, cardNo);
    }

    @Override
    public Long loadByNo(String cardNo) {
        return ewCardMapper.loadEwCardByNo(cardNo);
    }

}
