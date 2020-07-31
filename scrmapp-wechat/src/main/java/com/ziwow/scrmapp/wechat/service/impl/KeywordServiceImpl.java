package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.wechat.persistence.mapper.KeywordMapper;
import com.ziwow.scrmapp.wechat.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: huangrui
 * @Description:
 * @Date: Create in 下午4:46 20-7-31
 */
@Service
public class KeywordServiceImpl implements KeywordService {
    @Autowired
    private KeywordMapper keywordMapper;
    @Override
    public String getContentByKeyword(String keyword) {
        return keywordMapper.findReplyByKeyword(keyword);
    }
}
