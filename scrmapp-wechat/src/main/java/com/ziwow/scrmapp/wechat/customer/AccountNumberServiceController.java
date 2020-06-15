package com.ziwow.scrmapp.wechat.customer;

import com.ziwow.scrmapp.common.annotation.MiniAuthentication;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客服账号操作
 * @author songkaiqi
 * @since 2020/06/12/上午11:02
 */
@RestController
@RequestMapping("accountNumber")
public class AccountNumberServiceController {

    @Autowired
    private WechatUserService wechatUserService;

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
        wechatUserService.replacePhone(fromPhone,toPhone);
        //商城同步更换手机号



        result.setReturnMsg("更换成功,更换后的手机号为:"+toPhone);
        result.setReturnCode(Constant.SUCCESS);
        return result;
    }


}
