package com.ziwow.scrmapp.wechat.utils;

import java.util.List;

/**
 * @author songkaiqi
 * @since 2019/11/21/下午6:40
 */
public interface SendNotice {

    /**
     * 发放通知
     * @param title
     * @param remark
     * @param type
     * @param param
     * @param phone
     * @return
     */
    boolean sendNotice(String title, String remark, String type, List<String> param, String phone);

}
