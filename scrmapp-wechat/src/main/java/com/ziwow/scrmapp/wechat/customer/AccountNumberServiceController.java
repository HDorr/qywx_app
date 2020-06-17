package com.ziwow.scrmapp.wechat.customer;

import com.ziwow.scrmapp.common.annotation.MiniAuthentication;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.exception.BizException;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.utils.SyncQYUtil;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 客服账号操作
 * @author songkaiqi
 * @since 2020/06/12/上午11:02
 */
@RestController
@RequestMapping("accountNumber")
public class AccountNumberServiceController {

    Logger logger = LoggerFactory.getLogger(AccountNumberServiceController.class);

    @Autowired
    private WechatUserService wechatUserService;

    @Value("${mall.logout.url}")
    private String mallLogoutUrl;

    @Value("${mall.unbound.url}")
    private String mallUnboundUrl;

    @Value("${mall.replace.url}")
    private String mallReplaceUrl;

    /**
     * 注销
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    @MiniAuthentication
    public Result logout(@RequestParam("signture") String signture,
                         @RequestParam("time_stamp") String timeStamp,
                         @RequestParam("phone") String phone){
        Result result = new BaseResult();
        if (wechatUserService.getUserByMobilePhone(phone) == null) {
            result.setReturnMsg("需要注销账号不存在,手机号为:"+phone);
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        wechatUserService.logoutUserByPhone(phone);
        //商城同步注销
        qyscLogout(phone);
        result.setReturnMsg("注销成功,手机号为:"+phone);
        result.setReturnCode(Constant.SUCCESS);
        return result;
    }

    /**
     * 解绑手机号
     * @return
     */
    @RequestMapping(value = "unbound",method = RequestMethod.GET)
    @MiniAuthentication
    public Result unbound(@RequestParam("signture") String signture,
                          @RequestParam("time_stamp") String timeStamp,
                          @RequestParam("phone") String phone){
        Result result = new BaseResult();
        if (wechatUserService.getUserByMobilePhone(phone) == null) {
            result.setReturnMsg("需要解绑的账号不存在,手机号为:"+phone);
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        wechatUserService.unboundByPhone(phone);
        //商城同步解绑手机号
        qyscUnbound(phone);
        result.setReturnMsg("解绑成功,手机号为:"+phone);
        result.setReturnCode(Constant.SUCCESS);
        return result;
    }



    /**
     * 更换手机号
     * @return
     */
    @RequestMapping(value = "replace",method = RequestMethod.GET)
    @MiniAuthentication
    public Result replace(@RequestParam("signture") String signture,
                          @RequestParam("time_stamp") String timeStamp,
                          @RequestParam("fromPhone") String fromPhone,
                          @RequestParam("toPhone") String toPhone){
        Result result = new BaseResult();
        if (wechatUserService.getUserByMobilePhone(fromPhone) == null) {
            result.setReturnMsg("需要更换的账号不存在,手机号为:"+fromPhone);
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        if (wechatUserService.getUserByMobilePhone(toPhone) != null) {
            result.setReturnMsg("更换后的手机号已绑定其他用户,手机号为:"+fromPhone);
            result.setReturnCode(Constant.FAIL);
            return result;
        }
        wechatUserService.replacePhone(fromPhone,toPhone);

        //商城同步更换手机号
        qyscReplace(fromPhone,toPhone);
        result.setReturnMsg("更换成功,更换后的手机号为:"+toPhone);
        result.setReturnCode(Constant.SUCCESS);
        return result;
    }


    public void qyscLogout(String phone){
        Map<String, Object> params = new HashMap<>(3);
        params.put("phone", phone);
        logger.info("开始调用商城同步注销手机号,手机号为：{}", phone);
        Map result1 = SyncQYUtil.getResult("QINYUAN", params, "POST", mallLogoutUrl);
        if ((Integer) result1.get("errorCode") != 200) {
            logger.error("调用商城同步注销手机号失败,注销的手机号为：{}", phone);
            throw new BizException("调用商城同步注销手机号失败");
        }
    }

    public void qyscUnbound(String phone){
        Map<String, Object> params = new HashMap<>(3);
        params.put("phone", phone);
        logger.info("开始调用商城同步解绑手机号,手机号为：{}", phone);
        Map result1 = SyncQYUtil.getResult("QINYUAN", params, "POST", mallUnboundUrl);
        if ((Integer) result1.get("errorCode") != 200) {
            logger.error("调用商城同步解绑手机号失败,注销的手机号为：{}", phone);
            throw new BizException("调用商城同步解绑手机号失败");
        }
    }

    public void qyscReplace(String fromPhone,String toPhone){
        Map<String, Object> params = new HashMap<>(4);
        params.put("fromPhone", fromPhone);
        params.put("toPhone", toPhone);
        logger.info("开始调用商城同步更换手机号,需要更换手机号为：{}，调整后的手机号为:{}", fromPhone,toPhone);
        Map result1 = SyncQYUtil.getResult("QINYUAN", params, "POST", mallReplaceUrl);
        if ((Integer) result1.get("errorCode") != 200) {
            logger.error("调用商城同步更换手机号失败,需要更换手机号为：{}，调整后的手机号为:{}", fromPhone,toPhone);
            throw new BizException("调用商城同步更换手机号失败");
        }
    }

}
