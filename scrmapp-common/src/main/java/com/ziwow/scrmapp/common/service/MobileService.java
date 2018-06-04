package com.ziwow.scrmapp.common.service;

public interface MobileService {
    public boolean sendVerifyCode(String redisKey, String mobile) throws Exception;

    public void sendContent(String mobile, String msgContent) throws Exception;

    //亿美短信
    public boolean sendVerifyCodeByEmay(String redisKey, String mobile) throws Exception;

    /**
     * @param mobile
     * @param msgContent
     * @param type       0为用户 1为师傅
     * @throws Exception
     */
    public boolean sendContentByEmay(String mobile, String msgContent, int type) throws Exception;
}