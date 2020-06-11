package com.ziwow.scrmapp.wechat.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziwow.scrmapp.common.bean.pojo.EvaluateParam;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.SystemConstants;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.common.persistence.entity.QyhUserAppraisal;
import com.ziwow.scrmapp.common.persistence.entity.QyhUserAppraisalVo;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrdersRecord;
import com.ziwow.scrmapp.common.persistence.mapper.QyhUserAppraisalMapper;
import com.ziwow.scrmapp.common.persistence.mapper.QyhUserMapper;
import com.ziwow.scrmapp.common.persistence.mapper.WechatOrdersRecordMapper;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.tools.utils.HttpClientUtils;
import com.ziwow.scrmapp.tools.utils.MD5;
import com.ziwow.scrmapp.tools.utils.UniqueIDBuilder;
import com.ziwow.scrmapp.wechat.service.*;
import com.ziwow.scrmapp.wechat.vo.QtyUserVO;
import com.ziwow.scrmapp.wechat.vo.WechatUserVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.wechat.persistence.entity.MallPcUser;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.mapper.MallPcUserMapper;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatFansMapper;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatUserMapper;

@Service
public class WechatUserServiceImpl implements WechatUserService {
    private static final Logger logger = LoggerFactory.getLogger(WechatUserServiceImpl.class);
    @Resource
    private WechatUserMapper wechatUserMapper;
    @Autowired
    private WechatFansMapper wechatFansMapper;
    @Autowired
    private MallPcUserMapper mallPcUserMapper;
    @Autowired
    private WechatTemplateService wechatTemplateService;
    private ProductService productService;
    @Value("${miniapp.user.sync}")
    private String syncUserUrl;
    @Autowired
    @Lazy
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public WechatUser getUserByOpenId(String openId) {
        return wechatUserMapper.getUserByOpenId(openId);
    }

    @Override
    public WechatUser getUserByUnionid(String unionId) {
        return wechatUserMapper.getUserByUnionId(unionId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(WechatUser wechatUser, WechatFans wechatFans) {
        wechatUserMapper.saveUser(wechatUser);
        wechatFansMapper.updateWechatFans(wechatFans);
    }

    @Override
    public WechatUser getUserByMobilePhone(String mobilePhone) {
        return wechatUserMapper.getUserByMobilePhone(mobilePhone);
    }

    @Override
    public Integer updateUser(WechatUser wechatUser, Long wfId) {

        return wechatUserMapper.updateUser(wechatUser, wfId);
    }

    @Override
    public WechatUser getUserByUserId(String userId) {
        return wechatUserMapper.getUserByUserId(userId);
    }

    @Override
    public WechatUserVo getBaseUserInfoByUserId(String userId) {
        return wechatUserMapper.getBaseUserInfoByUserId(userId);
    }

    /**
     * 检查用户是否存在
     *
     * @param userId
     * @return
     */
    @Override
    public boolean checkUser(String userId) {
        //用户id不能为空
        if (StringUtils.isEmpty(userId)) {
            return false;
        }
        //数据库中不存在
        WechatUser wechatUser = wechatUserMapper.getUserByUserId(userId);
        if (null == wechatUser) {
            return false;
        }
        return true;
    }

    @Override
    public void sendRegisterTemplateMsg(String openId, String nikeName) {
        String title = "亲爱的" + nikeName + "， 你已成功注册会员！";
        String time = DateUtil.DateToString(new Date(), null);
        String remark = "点击【贴心管家】，即刻预约服务；点击【净享健康】，了解更多产品、活动信息；点击【我的沁园】，查看个人信息。";
        wechatTemplateService.registerTemplate(openId, null, title, nikeName, time, remark);
    }

    @Override
    public void sendUserUpdTemplateMsg(String openId, String nikeName) {
        String title = "亲爱的" + nikeName + "， 您已成功更改个人资料";
        String msgType = "个人资料";
        String time = DateUtil.DateToString(new Date(), null);
        String remark = "点击【贴心管家】，即刻预约服务；点击【净享健康】，了解更多产品、活动信息；点击【我的沁园】，查看个人信息。";
        wechatTemplateService.msgChangeTemplate(openId, null, title, msgType, time, remark);
    }

    @Transactional
    @Override
    public int save(QyhUserAppraisal qyhUserAppraisal) {
        return qyhUserAppraisalMapper.insert(qyhUserAppraisal);
    }

    @Transactional
    @Override
    public int saveVo(QyhUserAppraisalVo qyhUserAppraisalVo) {
        return qyhUserAppraisalMapper.insertVo(qyhUserAppraisalVo);
    }

    @Override
    @Transactional
    public int save(String ordersCode, Date updateTime, QyhUserAppraisal qyhUserAppraisal, WechatOrdersRecord wechatOrdersRecord) {
        int count = 0;
        try {
            count = qyhUserAppraisalMapper.insert(qyhUserAppraisal);
            if (count > 0) {
                count = wechatOrdersService.updateOrdersStatus(ordersCode, qyhUserAppraisal.getUserId(), updateTime, SystemConstants.APPRAISE);
                if (count > 0) {
                    count = wechatOrdersRecordMapper.insert(wechatOrdersRecord);
                    if (count == 0) {
                        throw new Exception("保存失败!");
                    }
                } else {
                    throw new Exception("保存失败!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public Result invokeCssEvaluate(EvaluateParam evaluateParam) {
        return thirdPartyService.cssEvaluate(evaluateParam);
    }


    @Autowired
    private QyhUserAppraisalMapper qyhUserAppraisalMapper;

    @Autowired
    private ThirdPartyService thirdPartyService;

    @Autowired
    private WechatOrdersRecordMapper wechatOrdersRecordMapper;

    @Autowired
    @Lazy(true)
    private WechatOrdersService wechatOrdersService;

    @Override
    public Result securityQuery(String barcode, String userMsg, String area, String ciphertext) {
        return thirdPartyService.securityQuery(barcode, userMsg, area, ciphertext);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncUserFromMiniApp(WechatFans wechatFans, WechatUser wechatUser) {
        if(null != wechatFans) {
            Long wfId = wechatFans.getId();
            if(null != wfId) {
                wechatFansMapper.updateWechatFans(wechatFans);
            } else {
                wechatFansMapper.syncUserFansAndGetId(wechatFans);
                wfId = wechatFans.getId();
            }
            wechatUser.setWfId(wfId);
            wechatUserMapper.saveUser(wechatUser);
        }
    }

    @Override
    public void syncUserToMiniApp(String unionId, String mobile) {
        // 异步推送给小程序
        Map<String, Object> params = new HashMap<String, Object>();
        long timestamp = System.currentTimeMillis();
        params.put("timestamp", timestamp);
        params.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
        params.put("mobile", mobile);
        params.put("unionId", unionId);
        logger.info("注册用户信息同步到小程序,params:{}", JSONObject.fromObject(params).toString());
        String result = HttpClientUtils.postJson(syncUserUrl, JSONObject.fromObject(params).toString());
        if (StringUtils.isNotBlank(result)) {
            JSONObject o1 = JSONObject.fromObject(result);
            if (o1.containsKey("errorCode") && o1.getInt("errorCode") == 200) {
                logger.info("注册用户信息同步到小程序成功,unionId:{}", unionId);
            } else {
                logger.info("注册用户信息同步到小程序失败,moreInfo:{}", o1.getString("moreInfo"));
            }
        }
    }

  @Override
  public String loadByUnionId(String userId) {
      QtyUserVO qytUser = wechatUserMapper.getQtyUserByUserId(userId);
      return qytUser.getUnionid();
  }

  @Override
    public List<WechatUser> getUserByRegisterSrc(Integer src) {
        return wechatUserMapper.getUserByRegisterSrc(src);
    }

  @Override
  public WechatUser getUserByFansUnionId(String unionId) {
    return wechatUserMapper.selectUserByFansUnionId(unionId);
  }

    @Override
    public WechatUser getUserByFansUnionIdIgnoreIsCancel(String unionId) {
        return wechatUserMapper.selectUserByFansUnionIdIgnoreIsCancel(unionId);
    }

    @Override
    public MallPcUser getMallPcUserByMobile(String mobile) {
        return mallPcUserMapper.getMallPcUserByMobile(mobile);
    }

    @Override
    public void saveMallPcUser(MallPcUser mallPcUser) {
        mallPcUserMapper.saveMallPcUser(mallPcUser);
    }

    @Override
    public boolean findUserLuckyByPhone(String mobilePhone) {
        return wechatUserMapper.findUserLuckyByPhone(mobilePhone)>0? true:false;
    }

    @Override
    public String autoRegister(String phone, String unionId,Integer registerSrc) {
        WechatFans wechatFans = new WechatFans();
        wechatFans.setIsMember(2);
        wechatFans.setUnionId(unionId);
        wechatFans.setIsCancel(1);

        // 设置用户信息
        String userId = UniqueIDBuilder.getUniqueIdValue();
        WechatUser wechatUser = new WechatUser();
        wechatUser.setUserId(userId);
        wechatUser.setMobilePhone(phone);
        wechatUser.setRegisterSrc(registerSrc);
        syncUserFromMiniApp(wechatFans, wechatUser);

        // 异步同步该用户的历史产品信息
        productService.syncHistroyProductItemFromCemTemp(phone, userId);
        // 异步同步该用户的历史受理单信息
        wechatOrdersService.syncHistoryAppInfo(phone, userId);
        //推送商城
        syncUserToMiniApp(unionId,phone);
        return userId;
    }
}