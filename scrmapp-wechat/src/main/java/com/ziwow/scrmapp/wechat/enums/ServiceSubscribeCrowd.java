package com.ziwow.scrmapp.wechat.enums;

/**
 *  预约服务人群分类
 * @author songkaiqi
 * @since 2019/11/21/下午4:26
 */
public enum ServiceSubscribeCrowd {
    //装机 1-2年  换芯1次
    INSTALL_1_TO_2_RENEW_1,

    //装机 1-2年  换芯2次
    INSTALL_1_TO_2_RENEW_2,

    //装机 1-2年  无换芯无清洗
    INSTALL_1_TO_2_RENEW_0_CLEAN_0,

    //装机 1-2年  无换芯有清洗
    INSTALL_1_TO_2_RENEW_0_CLEAN_Y,

    //装机 1年内  换芯1次
    INSTALL_1_DOWN_RENEW_1,

    //装机 1年内  换芯2次
    INSTALL_1_DOWN_RENEW_2,

    //装机 1年内  无换芯无清洗
    INSTALL_1_DOWN_RENEW_0_CLEAN_0,

    //装机 1年内  无换芯有清洗
    INSTALL_1_DOWN_RENEW_0_CLEAN_Y,

    //装机 2年以上  换芯1次
    INSTALL_2_UP_RENEW_1,

    //装机 2年以上  换芯2次
    INSTALL_2_UP_RENEW_2,

    //装机 2年以上  无换芯无清洗
    INSTALL_2_UP_RENEW_0_CLEAN_0,

    //装机 2年以上  无换芯有清洗
    INSTALL_2_UP_RENEW_0_CLEAN_Y,

    //装机 时间不详  换芯1次
    INSTALL_N_RENEW_1,

    //装机 时间不详  换芯2次
    INSTALL_N_RENEW_2,

    //装机 时间不详  无换芯无清洗
    INSTALL_N_RENEW_0_CLEAN_0,

    //装机 时间不详  无换芯有清洗
    INSTALL_N_RENEW_0_CLEAN_Y,


}
