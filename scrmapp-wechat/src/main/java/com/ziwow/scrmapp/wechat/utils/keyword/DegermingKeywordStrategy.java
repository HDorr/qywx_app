package com.ziwow.scrmapp.wechat.utils.keyword;

import com.ziwow.scrmapp.tools.weixin.InMessage;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatRegister;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.WechatRegisterService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: huangrui
 * @Description: 除菌
 * @Date: Create in 下午4:15 20-7-31
 */
@Component
public class DegermingKeywordStrategy extends KeywordAbstract {

    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private WechatRegisterService wechatRegisterService;
    @Override
    public boolean getContent(InMessage inMessage, HttpServletResponse response) {
        WechatRegister register = new WechatRegister();
        register.setOpenId(inMessage.getFromUserName());
        register.setContent(inMessage.getContent());
        //根据openid查询手机号
        WechatUser wechatUser = wechatUserService
                .getUserByOpenId(inMessage.getFromUserName());
        if(null!=wechatUser){
            register.setPhone(wechatUser.getMobilePhone());
            wechatRegisterService.savePullNewRegisterByEngineer(register);
        }
        return  false;
    }

}
