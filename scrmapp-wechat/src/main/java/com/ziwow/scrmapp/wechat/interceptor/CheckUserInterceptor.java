package com.ziwow.scrmapp.wechat.interceptor;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.bean.vo.cem.CemResp;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.tools.utils.MD5;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ziwow.scrmapp.tools.utils.Base64;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ziwow.scrmapp.tools.utils.CookieUtil;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.vo.WechatFansVo;

/**
 * Created by xiaohei on 2017/3/17.
 */
public class CheckUserInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(CheckUserInterceptor.class);


    @Autowired
    private WechatFansService wechatFansService;

    @Autowired
    ThirdPartyService thirdPartyService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //为false时，方法直接return;
        return true;
    }


    /**
     * 对路径中包含filter的请求进行拦截
     * 1、老会员，跳转到二维码页面。提示用户关注服务号
     * 2、未注册用户
     * (1)点击注册会员，跳转到注册页面，注册完成后跳转到个人中心
     * (2)点击非注册会员链接，注册完成后跳转到点击页面
     * 3、已注册会员
     * (1)点击注册会员，直接跳转到个人中心
     * (2)点击其它链接，直接跳转
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //保存实际页面
        String requestURL = request.getRequestURL().toString();

        //获得微信客户端传回的code
        String code = request.getParameter("code");
        logger.info("from url:" + requestURL + ",code : " + code == null ? "code is null " : code);

        WechatFansVo wechatFansVo = wechatFansService.getOAuthUserInfo(code, request, response);


        thirdPartyService.getCemProductInfo("104011-0003");

        /**
         * fixme 测试数据
         * Integer code   2
         * String userId;   eYzE67dT
         * String nickName;  黄晶晶。
         * Integer gender;   1
         */
//        wechatFansVo=new WechatFansVo();
//        wechatFansVo.setCode(2);
//        wechatFansVo.setToken("AlEHYDMnIhvqOyG8n415BuUZgQzaj7Tw2rmObBVEnPY=");
//        wechatFansVo.setUserId("eYzE67dT");
//        wechatFansVo.setNickName("黄晶晶。");
//        wechatFansVo.setGender(1);

        //会员标识
        Integer fanCode = wechatFansVo.getCode();

        String requestURI = request.getRequestURI();
        int index = requestURI.indexOf("register");

        if (0 == fanCode) {
            //跳转到二维码页面
            modelAndView.setViewName("/register/scan_QR_code");
        }

        //用户还未注册
        else if (1 == fanCode) {
            //直接从注册页面过来
            if (index != -1) {
                //获得用户中心链接地址
                String url = WeChatConstants.USER_HOME_PATH;
                url = request.getContextPath() + url;
                modelAndView.addObject("url", url);
            } else {
                //跳转到注册页面
                modelAndView.setViewName("/register/register");

                modelAndView.addObject("url", requestURL);
            }
            modelAndView.addObject("data", wechatFansVo);

        } else if (2 == fanCode) {
            //直接从注册页面过来
            if (index != -1) {
                //进入个人中心
                modelAndView.setViewName("/user_home/user_home");
            }
            modelAndView.addObject("data", wechatFansVo);

            //保存userId到cookie中
            String userId = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            if (!Base64.encode(wechatFansVo.getUserId().getBytes()).equals(userId)) {
                String encode = Base64.encode(wechatFansVo.getUserId().getBytes());
                CookieUtil.writeCookie(request, response, WeChatConstants.SCRMAPP_USER, encode, Integer.MAX_VALUE);
            }

        } else {
            modelAndView.setViewName("404");
        }

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
