package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.persistence.entity.Filter;

import java.util.List;

/**
 * Created by xiaohei on 2017/4/5.
 */
public interface FilterService {
    List<Filter> findByLevelId(Long levelId);

}
