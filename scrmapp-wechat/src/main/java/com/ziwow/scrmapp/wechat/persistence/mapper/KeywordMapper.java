package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.common.bean.GenericMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: huangrui
 * @Description:
 * @Date: Create in 下午4:48 20-7-31
 */
public interface KeywordMapper {
    @Select("select content from t_wechat_keyword where keyword=#{keyword}")
    public String findReplyByKeyword(String keyword);
}
