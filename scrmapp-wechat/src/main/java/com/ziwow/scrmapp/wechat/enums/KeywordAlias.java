package com.ziwow.scrmapp.wechat.enums;

/**
 * @Author: huangrui
 * @Description: 匹配关键词和对应的实现类
 * @Date: Create in 下午4:01 20-7-31
 */
public enum KeywordAlias {
    CUSTOMER_KEYWORD("人工客服","customerKeywordStrategy"),
    GERM_KEYWORD("除菌去味一步到位","degermingKeywordStrategy"),
    GERM2_KEYWORD("除菌去味一喷到位","degermingKeywordStrategy"),
    GERM3_KEYWORD("卫宝","degermingKeywordStrategy");

    private String keyword;
    private String className;

    KeywordAlias(String keyword, String className) {
        this.keyword = keyword;
        this.className = className;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {

        this.className = className;
    }

    //  根据关键字匹配类
    public static String getClassNameByKeyword(String keyword) {
        for (KeywordAlias alias : values()) {
            if (alias.getKeyword().equals(keyword)) {
                return alias.getClassName();
            }
        }
        return null;
    }

}
