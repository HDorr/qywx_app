package com.ziwow.scrmapp.wechat.service;

import java.sql.SQLException;

/**
 * Created by xiaohei on 2017/4/10.
 */
public interface FailRecordService {

    int saveFailRecord(SQLException e);
}
