package com.ziwow.scrmapp.common.persistence.mapper;


import com.ziwow.scrmapp.common.persistence.entity.QyhUserAppraisal;

public interface QyhUserAppraisalMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QyhUserAppraisal record);

    int insertSelective(QyhUserAppraisal record);

    QyhUserAppraisal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QyhUserAppraisal record);

    int updateByPrimaryKey(QyhUserAppraisal record);
}