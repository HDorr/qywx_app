package com.ziwow.scrmapp.wechat.utils.keyword;

import com.ziwow.scrmapp.wechat.enums.KeywordAlias;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: huangrui
 * @Description: 关键词工厂类
 * @Date: Create in 下午4:15 20-7-31
 */
@Component
public class KeywordFactory {
    private static final String DEFAULT_STRATEGY="defaultKeywordStrategy";

    @Autowired
    private  Map<String, KeywordStrategy> keywordMap;

//    根据关键词返回对应实现类
    public KeywordStrategy getKeywordStrategy(String keyword) {
        String className = KeywordAlias.getClassNameByKeyword(keyword);
        if (StringUtils.isEmpty(className)) {
            return keywordMap.get(DEFAULT_STRATEGY);
        }
        KeywordStrategy strategy = keywordMap.get(className);
        if (strategy ==null) {
            return keywordMap.get(DEFAULT_STRATEGY);
        }
        return strategy;
    }
}
