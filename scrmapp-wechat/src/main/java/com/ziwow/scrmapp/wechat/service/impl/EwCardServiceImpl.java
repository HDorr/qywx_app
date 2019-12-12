package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.enums.EwCardStatus;
import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;
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
        //是50g 还是 400g
        EwCardTypeEnum type = ewCardMapper.queryTypeByCode(itemCodes[0]);
        ewCard.setType(type);
        ewCardMapper.saveEwCard(ewCard);
    }

    @Override
    public EwCard selectEwCardByNo(String cardNo) {
        final EwCard ewCard = ewCardMapper.selectEwCardByNo(cardNo);
        if (ewCard == null){
            return null;
        }
        List<EwCardItems> codes = ewCardMapper.queryCodesByType(ewCard.getType());
        ewCard.setEwCardItems(codes);
        return ewCard;
    }

    @Override
    public Set<EwCard> selectEwCardByFansId(Long fansId) {
        final Set<EwCard> ewCards = ewCardMapper.selectEwCardByFansId(fansId);
        for (EwCard ewCard : ewCards) {
            List<EwCardItems> codes = ewCardMapper.queryCodesByType(ewCard.getType());
            ewCard.setEwCardItems(codes);
        }
        return ewCards;
    }



    @Override
    public EwCard selectEwCardByBarCode(String barCode) {
        return ewCardMapper.selectEwCardByBarCode(barCode);
    }


    @Override
    public void useEwCard(String cardNo, String productBarCode, Date purchDate, Date repairTerm, Boolean installList, EwCardStatus ewCardStatus) {
        ewCardMapper.updateCard(cardNo, productBarCode, purchDate, repairTerm, installList, ewCardStatus);
    }

    @Override
    public EwCard selectMyEwCardByNo(String cardNo, Long fansId) {
        final EwCard ewCard = ewCardMapper.selectEwCardByNoAndFansId(cardNo, fansId);
        ewCard.setEwCardItems(ewCardMapper.queryCodesByType(ewCard.getType()));
        return ewCard;
    }

    @Override
    public Set<EwCard> selectEwCardByProductCode(String productCode, Long id) {
        final EwCardTypeEnum ewCardTypeEnum = ewCardMapper.queryTypeByCode(productCode);
        final List<Long> cardIds = ewCardMapper.selectEwCardIdByCodeAndFansId(ewCardTypeEnum, id);
        if (CollectionUtils.isEmpty(cardIds)) {
            return Collections.EMPTY_SET;
        } else {
            final Set<EwCard> ewCards = ewCardMapper.selectEwCardByCardIds(cardIds);
            for (EwCard ewCard : ewCards) {
                List<EwCardItems> codes = ewCardMapper.queryCodesByType(ewCard.getType());
                ewCard.setEwCardItems(codes);
            }
            return ewCards;
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

    @Override
    public void saveEwCard(EwCard ewCard) {
        ewCardMapper.saveCompleteEwCard(ewCard);
    }

}
