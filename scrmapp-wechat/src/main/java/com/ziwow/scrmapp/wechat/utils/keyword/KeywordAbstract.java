package com.ziwow.scrmapp.wechat.utils.keyword;

/**
 * @Author: huangrui
 * @Description:
 * @Date: Create in 下午4:16 20-7-31
 */
public abstract class KeywordAbstract implements KeywordStrategy {
    private StringBuilder msgsb;

    public StringBuilder getMsgsb() {
        return msgsb;
    }

    public void setMsgsb(StringBuilder msgsb) {
        this.msgsb = msgsb;
    }
}
