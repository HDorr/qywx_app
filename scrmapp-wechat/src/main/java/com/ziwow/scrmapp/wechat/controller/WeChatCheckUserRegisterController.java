package com.ziwow.scrmapp.wechat.controller;


import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import com.ziwow.scrmapp.wechat.vo.OauthUser;
import com.ziwow.scrmapp.wechat.vo.WechatFansVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WeChatCheckUserRegisterController {

    @Autowired
    private WechatFansService wechatFansService;

    @Autowired
    private WechatUserService wechatUserService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/checkUserRegister", method = RequestMethod.GET)
    public ModelAndView checkUserRegister(String H5Url, String code, HttpServletResponse response, HttpServletRequest request) {
      ModelAndView modelAndView = new ModelAndView();
      String openid = null;
      log.info("==============code："+code);

      //包含未解密openid
      WechatFansVo wechatFansVo = wechatFansService.getOAuthUserInfo(code, request, response);
      log.info("===============未解密openid："+wechatFansVo.getToken());

      //包含解密openid
      OauthUser oauthUser = wechatFansService.getOAuthUserInfo(code);
      log.info("==============解密openid："+oauthUser.getOpenid());

      //获取解密后的openid
      if (oauthUser != null){
        openid = oauthUser.getOpenid();
      }

      WechatUser user1 = wechatUserService.getUserByOpenId(wechatFansVo.getToken());
      log.info("==============未解密openid取到的用户user："+user1);

      WechatUser user = wechatUserService.getUserByOpenId(openid);
      log.info("===============已解密openid取到的用户user："+user);

      if (user == null){
        //跳转到注册页面
        modelAndView.setViewName("/register/register");
        modelAndView.addObject("url", H5Url);
        modelAndView.addObject("data",wechatFansVo);
      }else {
        return new ModelAndView("redirect:"+H5Url);
      }
      return modelAndView;
    }
}
