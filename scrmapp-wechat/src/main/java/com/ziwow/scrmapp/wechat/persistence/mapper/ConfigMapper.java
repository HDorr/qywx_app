package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.wechat.persistence.entity.Config;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * 配置信息
 * @author songkaiqi
 * @since 2019/11/18/下午4:17
 */
public interface ConfigMapper {

    /**
     * 根据key 查询配置  注意：内容一定是Json格式
     * @param key
     * @return
     */
    @Results({
            @Result(column = "aginging", property = "aginging"),
            @Result(column = "start_date", property = "startDate"),
            @Result(column = "end_date", property = "endDate"),
            @Result(column = "content", property = "content")
    })
    @Select("select aginging,start_date,end_date,content from t_config where `key` = #{key} and archive = false")
    Config selectConfig(@Param("key") String key);


}
