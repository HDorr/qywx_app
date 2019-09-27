package com.ziwow.scrmapp.wechat.controller;


import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import com.ziwow.scrmapp.wechat.vo.OauthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WeChatCheckUserRegisterController {

    @Autowired
    private WechatFansService wechatFansService;

    @Autowired
    private WechatUserService wechatUserService;

    @RequestMapping(value = "/checkUserRegister", method = RequestMethod.GET)
    public ModelAndView checkUserRegister(String code,String H5Url) {
      ModelAndView modelAndView = new ModelAndView();

      OauthUser userInfo = wechatFansService.getOAuthUserInfo(code);
      String openid = userInfo.getOpenid();
      WechatUser user = wechatUserService.getUserByOpenId(openid);

      if (user == null){
        //跳转到注册页面
        modelAndView.setViewName("/register/register");
        modelAndView.addObject("url", H5Url);
      }else {
        return new ModelAndView("redirect:/toList");
      }

      return modelAndView;
    }
}
