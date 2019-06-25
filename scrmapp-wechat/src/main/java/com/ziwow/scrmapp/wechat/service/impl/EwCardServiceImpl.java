package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCardItems;
import com.ziwow.scrmapp.wechat.persistence.mapper.EwCardMapper;
import com.ziwow.scrmapp.wechat.service.EwCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public List<EwCardItems> addEwCard(EwCard ewCard, String[] itemNames, String[] itemCodes) {
        ewCardMapper.saveEwCard(ewCard);
        List<EwCardItems> ewCardItems = new ArrayList<>(itemCodes.length);
        for (int i = 0; i < itemNames.length; i++) {
            EwCardItems ewCardItem = new EwCardItems();
            ewCardItem.setItemCode(itemCodes[i]);
            ewCardItem.setItemName(itemNames[i]);
            ewCardItems.add(ewCardItem);
            ewCardMapper.addEwCardItems(ewCard.getId(),itemNames[i],itemCodes[i]);
        }
        return ewCardItems;
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
        return ewCardMapper.selectEwCardByItemNameAndFansId(itemName,fansId);
    }

    @Override
    public EwCard selectEwCardByBarCode(String barCode, Long fansId) {
        return ewCardMapper.selectEwCardByBarCodeAndFansId(barCode,fansId);
    }

    @Override
    public void useEwCard(String cardNo, String productBarCode, Date purchDate, Date repairTerm) {
        ewCardMapper.updateCard(cardNo,productBarCode,purchDate,repairTerm);
    }

    @Override
    public EwCard selectMyEwCardByNo(String cardNo, Long fansId) {
        return ewCardMapper.selectEwCardByNoAndFansId(cardNo,fansId);
    }

    @Override
    public List<EwCard> selectEwCardByProductCode(String productCode, Long id) {
        final List<Long> cardIds = ewCardMapper.selectEwCardIdByCodeAndFansId(productCode, id);
        if (CollectionUtils.isEmpty(cardIds)){
            return null;
        }else {
            return ewCardMapper.selectEwCardByCardIds(cardIds);
        }
    }

}
