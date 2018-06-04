package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.persistence.entity.Filter;
import com.ziwow.scrmapp.common.persistence.mapper.FilterLevelMapper;
import com.ziwow.scrmapp.wechat.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xiaohei on 2017/4/5.
 */
public class FilterServiceImpl implements FilterService{
    @Override
    public List<Filter> findByLevelId(Long levelId) {
        return null;
    }


    @Autowired
    private FilterLevelMapper levelMapper;
}
