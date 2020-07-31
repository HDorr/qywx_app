package com.ziwow.scrmapp.wechat.utils.keyword;

import com.ziwow.scrmapp.tools.weixin.InMessage;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: huangrui
 * @Description: 回复关键词逻辑
 * @Date: Create in 下午4:12 20-7-31
 */
public interface KeywordStrategy {
    public boolean getContent(InMessage inMessage, HttpServletResponse response);
}
