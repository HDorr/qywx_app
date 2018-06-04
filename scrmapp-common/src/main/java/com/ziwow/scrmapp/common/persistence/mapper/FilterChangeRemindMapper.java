package com.ziwow.scrmapp.common.persistence.mapper;

import com.ziwow.scrmapp.common.bean.vo.FilterChangeRemindMsgVo;
import com.ziwow.scrmapp.common.persistence.entity.FilterChangeRemind;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilterChangeRemindMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FilterChangeRemind record);

    int insertSelective(FilterChangeRemind record);

    FilterChangeRemind selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FilterChangeRemind record);

    int updateByPrimaryKey(FilterChangeRemind record);

    int batchSave(List<FilterChangeRemind> filterChangeRemindList);

    int updateStatus(@Param("productId") Long productId, @Param("status") int status);

    List<FilterChangeRemindMsgVo> getFilterChangeReminds();

    int updateFilterIdsStatus(@Param("filterIds") String[] filters, @Param("productId") Long productId, @Param("status") int status);

    List<FilterChangeRemind> findByProductId(Long productId);

    int deleteByProductId(Long productId);

}