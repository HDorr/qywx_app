package com.ziwow.scrmapp.wechat.controller;


import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.WechatAESService;
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

    private WechatAESService wechatAESService;
    @Autowired
    public void setWechatAESService(WechatAESService wechatAESService) {
        this.wechatAESService = wechatAESService;
    }


    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/checkUserRegister", method = RequestMethod.GET)
    public ModelAndView checkUserRegister(String H5Url, String code, HttpServletResponse response, HttpServletRequest request) throws Exception {
      ModelAndView modelAndView = new ModelAndView();
      String openid = null;
      log.info("==============code:{}",code);
      log.info("==============h5:{}",H5Url);

      //包含未解密openid
      WechatFansVo wechatFansVo = wechatFansService.getOAuthUserInfo(code, request, response);
      log.info("===============openid:{}",wechatAESService.Decrypt(wechatFansVo.getToken()));

      if (wechatFansVo.getCode() ==1){
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
