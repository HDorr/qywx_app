package com.ziwow.scrmapp.common.constants;

/**
 * Created by xiaohei on 2017/4/6.
 */
public class SystemConstants {
    /**
     * 购买渠道
     * <p>
     * ONLINE 线上
     * OFFLINE 线下
     */
    public static final int ONLINE = 1, OFFLINE = 2;


    /**
     * 预约单状态
     * <p>
     * ORDERS 用户下单
     * CANCEL 用户取消
     * REDEAL 重新处理中
     * RECEIVE 师傅已接单
     * COMPLETE 师傅已完工
     * APPRAISE 已评价
     */
    public static final int ORDERS = 1, CANCEL = 2, REDEAL = 3, RECEIVE = 4, COMPLETE = 5, APPRAISE = 6;


    /**
     * 预约单类型
     * <p>
     * INSTALL 安装
     * REPAIR 维修
     * MAINTAIN 保养
     */
    public static final int INSTALL = 1, REPAIR = 2, MAINTAIN = 3;


    /**
     * 滤芯提醒
     * <p>
     * REMIND 提醒
     * CLOSE 关闭
     * DELETE 删除
     */
    public static final int REMIND = 1, CLOSE = 2, DELETE = 3;

    /**
     * 工单来源
     * CSM 沁园
     * WEIXIN 微信
     */
    public static final int CSM = 1, WEIXIN = 2;

    /**
     * 评价界面类型
     * INSTALL_APPRAISE 安装评价
     * REPAIR_APPRAISE 维修评价
     * FILTER_APPRAISE 滤芯评价
     * CLEAN_APPRAISE 清洗评价
     */
    public static final int INSTALL_APPRAISE = 1, REPAIR_APPRAISE = 2, CLEAN_APPRAISE = 3, FILTER_APPRAISE = 4;
}
