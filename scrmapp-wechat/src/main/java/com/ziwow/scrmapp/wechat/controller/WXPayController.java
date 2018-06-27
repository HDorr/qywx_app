package com.ziwow.scrmapp.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;


import com.ning.http.client.AsyncHandler;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.entity.ProductFilter;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.CookieUtil;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.wechat.constants.WXPayConstant;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.ProductService;
import com.ziwow.scrmapp.wechat.service.WXPayService;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;

import com.ziwow.scrmapp.wechat.utils.JsonApache;
import com.ziwow.scrmapp.wechat.utils.RsaUtil;
import com.ziwow.scrmapp.wechat.vo.RefundVO;
import com.ziwow.scrmapp.wechat.wxpay.*;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * 微信服务号支付
 */

@Controller
@RequestMapping("/wx")
public class WXPayController {

    private static final Logger log = LoggerFactory.getLogger(WXPayController.class);

    @Value("${wechat.pay.appid}")
    private String appId;
    @Value("${wechat.pay.appsecret}")
    private String appSecret;
    @Value("${wechat.pay.mchid}")
    private String mchId;
    @Value("${wechat.pay.baseurl}")
    private String baseUrl;
    @Value("${wechat.pay.key}")
    private String key;
    @Value("${wechat.pay.token}")
    private String token;
    @Value("${wechat.pay.tradetype}")
    private String tradeType;
    @Value("${wechat.pay.signtype}")
    private String signType;
    @Value("${wechat.pay.certpath}")
    private String certPath;
    @Value("${wechat.pay.refundurl}")
    private String refundUrl;
    @Value("${wechat.pay.orderpay}")
    private String orderPay;
    @Value("${wechat.pay.orderpayquery}")
    private String orderPayQuery;
    @Value("${wechat.pay.orderclose}")
    private String orderClose;
    @Value("${wechat.pay.orderrefund}")
    private String orderRefund;
    @Value("${wechat.pay.orderrefundquery}")
    private String orderRefundQuery;
    //查询产品服务路径
    @Value("${qinyuan.modelname.service.query.url}")
    private String qinyuanModelnameServiceQuery;
    //购买产品服务后通知后台路径
    @Value("${qinyuan.modelname.service.pay.notify.url}")
    private String qinyuanModelnameServicePayNotifyUrl;

    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private WechatFansService wechatFansService;
    @Autowired
    private WXPayService wxPayService;
    @Autowired
    ProductService productService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView wxPay(Model model) {
        Map<String, String> map = new HashMap<String, String>();
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_updateFilter", map);
        return modelAndView;
    }

    //http://dev.99baby.cn/scrmapp/wx/user/auth?productIds=1688
    //微信网页授权获取用户基本信息
    //@RequestMapping(value = "/user/auth", method = RequestMethod.POST)
    //public String userAuth(@RequestBody WechatOrdersParamExt wechatOrdersParamExt, HttpServletResponse response) {
    @RequestMapping(value = "/user/auth", method = RequestMethod.GET)
    public String userAuth(HttpServletRequest request, HttpServletResponse response) {

        String productIds = request.getParameter("productIds");

        //检查用户是否提交产品
        if (StringUtil.isBlank(productIds)) {
            throw new RuntimeException("用户未选择产品");
        }
        log.info("/user/auth接口productIds: " + productIds);
        //下面通过外部接口获取付款金额
        List<Integer> idList = new ArrayList<Integer>();
        String[] productArr = productIds.split(",");
        for (String productId : productArr) {
            idList.add(Integer.parseInt(productId));
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<productArr.length; i++) {
            if(i < (productArr.length - 1)) {
                //根据modelName获取产品
                sb.append(productArr[i] + "_");
            } else {
                sb.append(productArr[i] + "");
            }
        }
        String ids = sb.toString();

        //根据产品ID获取产品
        Product p = productService.getProductPrimaryKey(Long.parseLong(productArr[0]));
        String userId = p.getUserId();

        //根据productId获取产品信息(批量)
        List<Product> list = productService.getProductsByIds(idList);
        log.info("/user/auth接口list数量: " + list.size());
        // 订单编号
        String orderId = UUID.randomUUID().toString().replaceAll("-", "");

        WechatUser wechatUser = wechatUserService.getUserByUserId(userId);
        if (wechatUser == null) {
            log.error("查询用户失败，cookie中userId错误");
            throw new RuntimeException("获取用户信息失败");
            //return null;
        }
        long wfId = wechatUser.getWfId();
        WechatFans wechatFans = wechatFansService.getWechatFansById(wfId);
        String unionId = wechatFans.getUnionId();

        //读取远程服务费信息，content为JSONARrray [{id:xxx, modelName:xxx,serviceFee:xxx,serviceStatus:xxx}, {,,}]
        String content = JsonApache.postModelNames(unionId, list, qinyuanModelnameServiceQuery);
        if (content == null) {
            throw new RuntimeException("获取服务费信息失败");
        }

        log.info("/user/auth接口调用外部接口postModelNames方法content：" + content);

        int totalFee = 0; //保存付款总金额
        JSONObject jsonObject = JSON.parseObject(content);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            String serviceStatus = (String) jo.get("serviceStatus");
            if (serviceStatus.equals("0")) {    //已购买滤芯未购买服务
                String modelName = (String) jo.get("modelName");
                String serviceFee = (String) jo.get("serviceFeePrice");
                int fee;
                if (serviceFee == null) {
                    return null;
                } else {
                    fee = new Double(Double.valueOf(serviceFee) * 100).intValue();
                }
                ProductFilter pf = new ProductFilter();
                for (Product product : list) {
                    if (product.getModelName().equals(modelName)){
                        pf.setProductId(product.getId());
                    }
                }
                pf.setOrderId(orderId);
                pf.setModelName(modelName);
                pf.setServiceFee(fee);
                pf.setPayStatus(WXPayConstant.payStatusZero);  //0未付款，1已经付款，5不能购买
                pf.setCreateTime(new Date());
                wxPayService.insert(pf);
                totalFee = totalFee + fee;
            }
        }
        if (totalFee == 0) {
            throw new RuntimeException("费用错误");
        }

        log.info("/user/auth接口orderId: " + orderId + "-----" + "totalFee: " + totalFee);

        //授权后要跳转的链接
        String backUri = baseUrl + "/wx/pay";
        backUri = backUri + "?orderId=" + orderId + "&totalFee=" + totalFee + "&ids=" + ids;
        //URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
        try {
            backUri = URLEncoder.encode(backUri, "UTF-8");
            //scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
            //https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
            //公众号支付 -- 接口规则 -- 获取openid -- 网页授权获取用户openid接口文档
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                    "appid=" + appId +
                    "&redirect_uri=" + backUri +
                    "&response_type=code&scope=snsapi_userinfo&state=12345&connect_redirect=1#wechat_redirect";
            log.info("/user/auth接口url：" + url);
            response.sendRedirect(url);
        } catch (IOException e) {
            log.error("/user/auth接口异常：" + e.getMessage());
            throw new RuntimeException("获取微信支付用户认证失败");
        }
        return null;  //可以指定错误页面
    }

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public String toPay(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            //订单编号
            String orderId = request.getParameter("orderId");
            //支付金额
            String totalFee = request.getParameter("totalFee");
            //商品ID(下划线分隔)
            String ids = request.getParameter("ids");
            ids.replaceAll("_", ",");
            log.info("/pay接口orderId: " + orderId + "totalFee: " + totalFee);
            //网页授权后获取传递的参数
            String userId = request.getParameter("userId");
            String code = request.getParameter("code");
            log.info("/pay接口code: " + code);
            log.info("/pay接口userId: " + userId);

            //获取统一下单需要的openid
            String openId = "";
            String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                    + appId
                    + "&secret=" + appSecret
                    + "&code=" + code + "&grant_type=authorization_code";
            log.info("/pay接口URL：" + URL);
            JSONObject jsonObject = OpenIdUtil.httpsRequest(URL, "GET", null);
            if (null != jsonObject) {
                openId = jsonObject.getString("openid");
                log.info("/pay接口openId：" + openId);
            } else {
                log.info("/pay接口获取openId异常");
            }

            if (openId == null) {
                String oi = (String) request.getSession().getAttribute("openId");
                if (oi != null) {
                    openId = oi;
                }
            }

            //获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
            //随机数
            //String nonce_str = "1add1a30ac87aa2db72f57a2375d8fec";
            String nonce_str = WXPayUtil.generateNonceStr();
            log.info("/pay接口nonce_str：" + nonce_str);
            //商品描述
            String body = orderId;
            //订单生成的机器 IP
            String spbill_create_ip = IpUtil.getRemoteIp(request);

            //这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
            String notify_url = baseUrl + "/wx/notify/url";

            SortedMap<String, String> data = new TreeMap<String, String>();
            data.put("appid", appId);
            data.put("mch_id", mchId);
            data.put("nonce_str", nonce_str);
            data.put("body", body);
            data.put("out_trade_no", orderId);
            data.put("total_fee", totalFee);
            data.put("spbill_create_ip", spbill_create_ip);
            data.put("notify_url", notify_url);
            data.put("trade_type", tradeType);
            data.put("openid", openId);

            RequestHandler reqHandler = new RequestHandler(request, response);
            reqHandler.init(appId, appSecret, key);

            String sign = reqHandler.createSign(data, key);
            log.info("/pay接口sign: " + sign);
            String xml = "<xml>" +
                    "<appid>" + appId + "</appid>" +
                    "<mch_id>" + mchId + "</mch_id>" +
                    "<nonce_str>" + nonce_str + "</nonce_str>" +
                    "<sign>" + sign + "</sign>" +
                    "<body><![CDATA[" + body + "]]></body>" +
                    "<out_trade_no>" + orderId + "</out_trade_no>" +
                    "<total_fee>" + totalFee + "" + "</total_fee>" +
                    "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>" +
                    "<notify_url>" + notify_url + "</notify_url>" +
                    "<trade_type>" + tradeType + "</trade_type>" +
                    "<openid>" + openId + "</openid>" +
                    "</xml>";

            String prepay_id = "";
            try {
                prepay_id = WeixinPayUtil.getPayNo(mchId, certPath, orderPay, xml);
                log.info("prepay_id:" + prepay_id);
                if (prepay_id.equals("")) {
                    request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
                    response.sendRedirect("error");
                }
            } catch (Exception e1) {
                log.error(e1.getMessage());
            }
            SortedMap<String, String> finalpackage = new TreeMap<String, String>();
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            String packages = "prepay_id=" + prepay_id;
            finalpackage.put("appId", appId);
            finalpackage.put("timeStamp", timestamp);
            finalpackage.put("nonceStr", nonce_str);
            finalpackage.put("package", packages);
            finalpackage.put("signType", signType);
            String finalsign = reqHandler.createSign(finalpackage, key);
            log.info("/jsapi?appid=" + appId +
                    "&timeStamp=" + timestamp +
                    "&nonceStr=" + nonce_str +
                    "&package=" + packages +
                    "&sign=" + finalsign);
            model.addAttribute("appid", appId);
            model.addAttribute("timeStamp", timestamp);
            model.addAttribute("nonceStr", nonce_str);
            model.addAttribute("packageValue", packages);
            model.addAttribute("sign", finalsign);
            model.addAttribute("bizOrderId", orderId);
            model.addAttribute("orderId", orderId);
            model.addAttribute("payPrice", totalFee);

            model.addAttribute("ids", ids);
            return "/reserveService/jsp/jsapi";
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }
        return null;    //可以指定错误页面

    }


    //微信异步回调
    @RequestMapping("/notify/url")
    public String weixinReceive(HttpServletRequest request, HttpServletResponse response) {
        log.info("==开始进入h5支付回调方法==");

        String xml_review_result = RequestUtil.getXmlRequest(request);
        log.info("/notify/url接口微信支付结果：" + xml_review_result);
        Map resultMap = null;
        try {
            resultMap = WXPayUtil.xmlToMap(xml_review_result);
            if (resultMap != null && resultMap.size() > 0) {
                boolean b = WXPayUtil.isSignatureValid(resultMap, key);
                //签名校验成功
                if (b) {
                    log.info("/notify/url接口签名校验成功");
                    try {
                        //获得返回结果
                        String return_code = (String) resultMap.get("return_code");

                        if ("SUCCESS".equals(return_code)) {
                            String orderId = (String) resultMap.get("out_trade_no");
                            int totalFee = Integer.parseInt((String) resultMap.get("total_fee"));  // 微信支付订单总金额分
                            BigDecimal bg = new BigDecimal(totalFee / 100);
                            String serviceFee = bg.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

                            log.info("/notify/url接口out_trade_no：" + orderId + "---" + "total_fee: " + totalFee);
                            //处理支付成功以后的逻辑
                            //1、根据订单编号更新t_product_filter表
                            wxPayService.updateProductFilterByOrderId(orderId, 1);
                            //2、同步数据到后台
                            List<ProductFilter> productFilterList = wxPayService.getProductFilterListByOrderId(orderId);
                            ProductFilter pf = productFilterList.get(0);
                            long productId = pf.getProductId();
                            Product p = productService.getProductPrimaryKey(productId);
                            String userId = p.getUserId();
                            WechatUser wechatUser = wechatUserService.getUserByUserId(userId);
                            long wfId = wechatUser.getWfId();
                            WechatFans wechatFans = wechatFansService.getWechatFansById(wfId);
                            String unionId = wechatFans.getUnionId();
                            List<Product> productList = new ArrayList<Product>();
                            for (ProductFilter pFilter : productFilterList) {
                                //根据modelName获取Product
                                Product product = productService.getProductPrimaryKey(productId);
                                productList.add(product);
                            }
                            //{"moreInfo":null,"data":true,"error":"Success","errorCode":200}
                            String result = JsonApache.notifyBackPlatform(unionId, productList, productFilterList, qinyuanModelnameServicePayNotifyUrl);
                            JSONObject jsonObject = JSON.parseObject(result);
                            boolean flag = jsonObject.getBoolean("data");
                            if (flag) {
                                wxPayService.syncProductFilterByOrderId(orderId, 1);   //0同步失败，1同步成功
                            }
                            //暂时省略
                        } else {
                            log.error("支付失败，订单号：" + return_code);
                        }
                    } catch (Exception e) {
                        log.error("/notify/url接口异常：" + e.getMessage());
                    }
                } else {
                    //签名校验失败
                    log.info("/notify/url接口签名校验失败");
                }
            }
        } catch (Exception e) {
            log.error("/notify/url接口异常：" + e.getMessage());
        }
        return null;
    }


    //页面js返回支付成功后，查询微信后台是否支付成功，然后跳转结果页面
    @RequestMapping(value = "/order/query", method = RequestMethod.GET)
    public ModelAndView toWXPayQuery(HttpServletRequest request,
                                     HttpServletResponse response, Model model) throws IOException {
        String orderId = request.getParameter("orderId");  //订单号
        log.info("/order/query接口orderId: " + orderId);
        MyConfig config = null;
        try {
            config = new MyConfig();
        } catch (Exception e) {
            log.error("/order/query接口异常：" + e.getMessage());
        }
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", orderId);
        try {
            Map<String, String> resp = wxpay.orderQuery(data);
            log.info("交易查询：" + resp);
            String return_code = (String) resp.get("return_code");
            String result_code = (String) resp.get("result_code");
            log.info("return_code:" + return_code + ",result_code:" + result_code);
            if ("SUCCESS".equals(return_code)) {
                if ("SUCCESS".equals(result_code)) {
                    //根据orderId获取ProductFilter
                    List<ProductFilter> list = wxPayService.getProductFilterListByOrderId(orderId);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        ProductFilter pf = list.get(i);
                        //根据productId获取产品
                        Product p = productService.getProductPrimaryKey(pf.getProductId());
                        if (p != null) {
                            if (i < (list.size() - 1)) {
                                sb.append(p.getId() + ",");
                            } else {
                                sb.append(p.getId() + "");
                            }
                        }
                    }
                    String ids = "";
                    if (list.size() > 0) {
                        ids = sb.toString();
                    } else {
                        ids = "55555";
                    }
                    //log.info("/order/query接口productIds值：" + sb.toString());
                    model.addAttribute("ids", ids);
                    model.addAttribute("code", "200");

                    ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_updateFilter");
                    return modelAndView;

                } else {
                    String err_code = (String) resp.get("err_code");
                    String err_code_des = (String) resp.get("err_code_des");
                    log.info("交易失败：" + result_code + ",err_code:" + err_code + ",err_code_des:" + err_code_des);

                    model.addAttribute("code", "300");
                    ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_updateFilter");
                    return modelAndView;

                }
            } else {
                model.addAttribute("code", "400");
                ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_updateFilter");
                return modelAndView;
            }
        } catch (Exception e) {
            log.error("/pay/check接口异常：" + e.getMessage());
        }
        model.addAttribute("code", "500");
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_updateFilter");
        return modelAndView;
    }

    //取消支付或支付失败
    @RequestMapping(value = "/pay/fail", method = RequestMethod.GET)
    public ModelAndView toWXPayFail(HttpServletRequest request,
                                    HttpServletResponse response, Model model) throws IOException {
        model.addAttribute("code", "500");
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_updateFilter");
        return modelAndView;
    }


    // 退款
    @RequestMapping(value = "/order/refund", method = RequestMethod.POST)
    public void toWXPayRefund(@RequestBody RefundVO refundVO, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        JSONObject result = new JSONObject();
        String temId = refundVO.getId();  //退款单号为id属性，既主键ID
        long id = Long.parseLong(temId);
        String token = refundVO.getToken();
        if (token != null) {
            String code = RsaUtil.decryptData(token);
            if (!code.equals(WXPayConstant.code)) {
                result.put("status", "5");
                writer.println(result.toJSONString());
                writer.close();
                return;
            }
        }
        ProductFilter pf = wxPayService.selectByPrimaryKey(id);
        String orderId = pf.getOrderId();
        //String modelName = productFilter.getModelName();
        int totalFee = wxPayService.getTotalFeeByOrderId(orderId);
        int refundFee = pf.getServiceFee();
        //退款号
        String refundId = UUID.randomUUID().toString().replaceAll("-", "");

        MyConfig config = null;
        try {
            config = new MyConfig();
        } catch (Exception e) {
            log.error("异常：" + e.getMessage());
        }
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", orderId);
        data.put("out_refund_no", refundId);
        data.put("total_fee", String.valueOf(totalFee));
        data.put("refund_fee", String.valueOf(refundFee));
        try {
            Map<String, String> resp = wxpay.refund(data);
            log.info("退款：" + resp);
            String return_code = (String) resp.get("return_code");
            String result_code = (String) resp.get("result_code");
            log.info("return_code:" + return_code + ",result_code:" + result_code);
            if ("SUCCESS".equals(return_code)) {
                if ("SUCCESS".equals(result_code)) {
                    log.info("退款成功");
                    //退款成功后修改产品状态为退货
                    //根据滤芯ID修改为退款状态，指定退款单号
                    ProductFilter productFilter = new ProductFilter();
                    productFilter.setId(id);
                    productFilter.setPayStatus(WXPayConstant.payStatusRefund);
                    productFilter.setRefundId(refundId);
                    wxPayService.updateByPrimaryKeySelective(productFilter);
                    result.put("status", "1");
                    writer.println(result.toJSONString());
                    writer.close();
                    return;
                } else {
                    String err_code = (String) resp.get("err_code");
                    String err_code_des = (String) resp.get("err_code_des");
                    log.info("退款失败：" + result_code + ",err_code:" + err_code + ",err_code_des:" + err_code_des);
                    result.put("status", "0");
                    writer.println(result.toJSONString());
                    writer.close();
                    return;
                }
            } else {
                log.info("退款失败", "通信错误");
                result.put("status", "0");
                writer.println(result.toJSONString());
                writer.close();
                return;
            }
        } catch (Exception e) {
            log.error("退款异常：" + e.getMessage());
            result.put("status", "0");
            writer.println(result.toJSONString());
            writer.close();
        }
    }

}
