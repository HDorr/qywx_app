package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.bean.pojo.EvaluateParam;
import com.ziwow.scrmapp.common.persistence.entity.QyhUserAppraisal;
import com.ziwow.scrmapp.common.persistence.entity.QyhUserAppraisalVo;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrdersRecord;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.wechat.persistence.entity.MallPcUser;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.vo.QtyUserVO;
import com.ziwow.scrmapp.wechat.vo.WechatUserVo;

import java.util.Date;
import java.util.List;

public interface WechatUserService {
    public WechatUser getUserByOpenId(String openId);

    public WechatUser getUserByUnionid(String unionId);

    public void saveUser(WechatUser wechatUser, WechatFans wechatFans);

    public WechatUser getUserByMobilePhone(String mobilePhone);

    public MallPcUser getMallPcUserByMobile(String mobile);

    public void saveMallPcUser(MallPcUser mallPcUser);

    Integer updateUser(WechatUser wechatUser, Long wfId);

    WechatUser getUserByUserId(String userId);

    WechatUserVo getBaseUserInfoByUserId(String userId);


    boolean checkUser(String userId);

    public void sendRegisterTemplateMsg(String openId, String nikeName);

    public void sendUserUpdTemplateMsg(String openId, String nikeName);

    int save(QyhUserAppraisal qyhUserAppraisal);

    int saveVo(QyhUserAppraisalVo qyhUserAppraisalVo);

    int save(String ordersCode, Date updateTime, QyhUserAppraisal qyhUserAppraisal, WechatOrdersRecord wechatOrdersRecord);

    /**
     * 调用沁园同步评价接口
     *
     * @param evaluateParam
     * @return
     */
    Result invokeCssEvaluate(EvaluateParam evaluateParam);

    Result securityQuery(String barcode, String userMsg, String area, String ciphertext);

    public void syncUserFromMiniApp(WechatFans fans, WechatUser wechatUser);

    public void syncUserToMiniApp(String unionId, String mobile);

    String loadByUnionId(String userId);


    List<WechatUser> getUserByRegisterSrc(Integer src);
}