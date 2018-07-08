package com.ziwow.scrmapp.wechat.controller;

import com.alibaba.druid.util.StringUtils;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.SystemConstants;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.CookieUtil;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.enums.BuyChannel;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatArea;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCity;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatProvince;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.ProductService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import com.ziwow.scrmapp.wechat.utils.BarCodeConvert;
import com.ziwow.scrmapp.wechat.vo.EnumVo;
import com.ziwow.scrmapp.wechat.vo.ProductVo;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by xiaohei on 2017/4/5.
 */
@Controller
@RequestMapping(value = "/scrmapp/consumer")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private WechatUserService wechatUserService;
    @Value("${wechat.appid}")
    private String appid;
    @Value("${open.weixin.component_appid}")
    private String component_appid;
    @Autowired
    private MobileService mobileService;

    /**
     * 展示产品系列列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/product/series", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result productSeries(HttpServletRequest request, HttpServletResponse response) {
        Result result = new BaseResult();
        try {
            //List<ProductSeries> productSeries = productService.getAllProductSeries();
            List<WechatProvince> productSeries = productService.getAllProductSeries();
            if (productSeries.size() == 0) {
                throw new Exception("productSeries list size == 0");
            }
            result.setReturnCode(Constant.SUCCESS);
            result.setData(productSeries);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("数据获取失败!");
            logger.error("查询产品系列数据过程中遇到异常，原因[{}]", e.getMessage());
        }
        return result;
    }

    /**
     * 展示产品类型列表
     *
     * @param request
     * @param response
     * @param seriesId
     * @return
     */
    @RequestMapping(value = "/product/type", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result productType(HttpServletRequest request, HttpServletResponse response, @RequestParam Long seriesId) {
        Result result = new BaseResult();
        try {
            //List<ProductType> productTypes = productService.getTypeBySeriesId(seriesId);

            List<WechatCity> productTypes = productService.getTypeBySeriesId(seriesId);
            if (productTypes.size() == 0) {
                throw new Exception("productTypes list size == 0");
            }
            result.setReturnCode(Constant.SUCCESS);
            result.setData(productTypes);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("数据获取失败!");
            logger.error("查询产品类型数据过程中遇到异常，原因[{}]", e.getMessage());
        }
        return result;
    }

    /**
     * 展示产品型号列表
     *
     * @param request
     * @param response
     * @param typeId
     * @return
     */
    @RequestMapping(value = "/product/model", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result productModel(HttpServletRequest request, HttpServletResponse response, @RequestParam Long typeId) {
        Result result = new BaseResult();
        try {
            //List<ProductModel> productModels = productService.getModelByTypeId(typeId);
            List<WechatArea> productModels = productService.getModelByTypeId(typeId);
            if (productModels.size() == 0) {
                throw new Exception("productModels list size == 0");
            }
            result.setReturnCode(Constant.SUCCESS);
            result.setData(productModels);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("数据获取失败!");
            logger.error("查询产品型号数据过程中遇到异常，原因[{}]", e.getMessage());
        }
        return result;
    }


    /**
     * 调用沁园接口查询产品信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/product/query", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result queryProduct(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String modelName, @RequestParam(required = false) String productBarCode) {
        Result result = new BaseResult();
        try {
            if (StringUtils.isEmpty(modelName) && StringUtils.isEmpty(productBarCode)) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("型号和条码不能同时为空!");
                return result;
            }
            logger.info("modelName:[{}],productBarCode:[{}]", modelName, productBarCode);
            if (StringUtil.isNotBlank(productBarCode) && 20 == productBarCode.length()) {
                productBarCode = BarCodeConvert.convert(productBarCode);
            }
            ProductVo product = productService.queryProduct(modelName, productBarCode);
            if(null != productBarCode || "".equals(productBarCode)){
            	product.setProductBarCode(productBarCode);
            }
            String image = productService.queryProductImage(product.getModelName());
            product.setProductImage(image);
            result.setData(product);
            result.setReturnCode(Constant.SUCCESS);
            logger.debug("调用沁园接口成功!");
        } catch (NullPointerException e) {
            result.setReturnCode(Constant.SUCCESS_NULL);
            result.setReturnMsg("未查到该产品!");
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("产品查询失败!");
            logger.error("调用沁园产品绑定失败,原因[{}]", e.getMessage());
        }
        return result;
    }


    /**
     * 绑定产品信息
     *
     * @param request
     * @param response
     * @param product
     * @return
     */
    @RequestMapping(value = "/product/save", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result bindProduct(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Product product) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));
            WechatUser wechatUser = wechatUserService.getUserByUserId(userId);
            if (null == wechatUser) {
                logger.error("绑定产品失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }
            product.setUserId(userId);
            product.setStatus(1);
            product.setCreateTime(new Date());
            product.setFilterRemind(SystemConstants.REMIND);

            boolean isFirst=productService.isFirstBindProduct(userId);

            productService.save(product);
            // 绑定产品成功后异步推送给小程序
            productService.syncProdBindToMiniApp(userId, product.getProductCode(),isFirst);

            // 产品绑定后发送模板消息
            productService.productBindTemplateMsg(product);
            String content = "亲爱的用户，恭喜您成功绑定产品。您可以即刻开启滤芯更换提醒功能，及时了解滤芯使用情况。";
            mobileService.sendContentByEmay(wechatUser.getMobilePhone(), content, Constant.CUSTOMER);

            result.setReturnCode(Constant.SUCCESS);
            result.setReturnMsg("产品绑定成功!");
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("产品绑定失败!");
            logger.error("产品绑定失败,原因[{}]", e.getMessage());
        }

        return result;
    }

    /**
     * 查询用户绑定所有产品信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/product/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result queryUserProduct(HttpServletRequest request, HttpServletResponse response) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));

            //用户id不存在
            if (!wechatUserService.checkUser(userId)) {
                logger.error("查询用户产品列表失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }
            List<Product> products = productService.getProductsByUserId(userId);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(products);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("查询产品列表失败!");
            logger.error("用户查询产品列表失败，原因[{}]", e.getMessage());
        }
        return result;

    }

    /**
     * 查询产品详情信息
     *
     * @param request
     * @param response
     * @param productId
     * @return
     */
    @RequestMapping(value = "/product/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result productDetail(HttpServletRequest request, HttpServletResponse response, @RequestParam Long productId) {
        Result result = new BaseResult();
        try {
            ProductVo product = productService.getProductById(productId);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(product);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("查询产品详情失败!");
            logger.error("用户查询产品详情失败，原因[{}]", e.getMessage());
        }
        return result;
    }

    /**
     * 开启或关闭滤芯提醒
     *
     * @param request
     * @param response
     * @param productId
     * @return
     */
    @RequestMapping(value = "/product/remind", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result openOrCloseRemind(HttpServletRequest request, HttpServletResponse response, @RequestParam Long productId) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));
            //用户id不存在
            if (!wechatUserService.checkUser(userId)) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }
            int status = productService.openOrCloseRemind(productId);
            result.setData(status);
            result.setReturnCode(Constant.SUCCESS);
            result.setReturnMsg("操作成功!");
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("操作失败!");
            logger.error("开闭或开启滤芯提醒异常，原因[{}]", e.getMessage());
        }
        return result;
    }


    /**
     * 通过产品编号查询图片
     *
     * @param request
     * @param response
     * @param modelName
     * @return
     */
    @RequestMapping(value = "/product/getImage", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result getProductImg(HttpServletRequest request, HttpServletResponse response, @RequestParam String modelName) {
        Result result = new BaseResult();
        try {
            //截取括号的内容
            int index = modelName.indexOf("（");
            if (index != -1) {
                modelName = modelName.substring(0, index);
            }
            String image = productService.queryProductImage(modelName);
            if (StringUtils.isEmpty(image)) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("图片获取失败!");
            } else {
                result.setReturnCode(Constant.SUCCESS);
                result.setData(image);
            }
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("图片获取失败!");
            logger.error("调用沁园获取图片失败，原因[{}]", e.getMessage());
        }
        return result;
    }


    /**
     * 获得产品基本信息
     *
     * @param request
     * @param response
     * @param productId
     * @return
     */
    @RequestMapping(value = "/product/get/baseInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result getBaseInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam Long productId) {
        Result result = new BaseResult();
        try {
            ProductVo product = productService.getBaseProduct(productId);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(product);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("查询产品信息失败!");
            logger.error("用户查询产品基本信息失败，原因[{}]", e.getMessage());
        }
        return result;
    }


    /**
     * 删除产品
     *
     * @param request
     * @param response
     * @param productId
     * @return
     */
    @RequestMapping(value = "/product/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result deleteProduct(HttpServletRequest request, HttpServletResponse response, @RequestParam Long productId) {
        Result result = new BaseResult();
        try {
            String encode = CookieUtil.readCookie(request, response, WeChatConstants.SCRMAPP_USER);
            String userId = new String(Base64.decode(encode));
            //用户id不存在
            if (!wechatUserService.checkUser(userId)) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }

            ProductVo p = productService.getProductById(productId);
            int count = productService.deleteProduct(productId, userId);
            // 异步推送给小程序解除绑定
            productService.syncProdUnbindToMiniApp(userId, p.getProductCode());
            if (count == 0) {
                throw new Exception();
            }
            result.setReturnCode(Constant.SUCCESS);
            result.setReturnMsg("删除成功!");
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("删除产品失败!");
            logger.error("删除产品失败，原因[{}]", e.getMessage());
        }
        return result;
    }


    /**
     * 选择购买渠道
     *
     * @param request
     * @param response
     * @param o2o
     * @return
     */
    @RequestMapping(value = "/product/choice", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result choiceBuyChannel(HttpServletRequest request, HttpServletResponse response, @RequestParam int o2o) {
        Result result = new BaseResult();
        try {
            List<EnumVo> channel = BuyChannel.getChannel(o2o);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(channel);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("数据获取失败!");
            logger.error("获取购买渠道数据失败，原因[{}]", e.getMessage());
        }
        return result;
    }


    @RequestMapping("/test")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView("/test/test");
        return modelAndView;
    }

    @RequestMapping("/test1")
    public ModelAndView test1() {
        ModelAndView modelAndView = new ModelAndView("/test/test1");
        return modelAndView;
    }

    @RequestMapping("/product/index")
    public ModelAndView toServiceIndex(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/index/index");

        //用户授权链接
        String url = WeChatConstants.SNSAPI_BASE_COMPONENT;
        url = url.replace("${APPID}", appid);
        url = url.replace("${STATE}", appid);
        url = url.replace("{COMPONENT_APPID}", component_appid);

        String contextPath = request.getContextPath();
        String basePath = "https" + "://" + request.getServerName() + contextPath;
//        String basePath = request.getScheme() +"://" + request.getServerName() + contextPath;

        
        String intallBaseUrl = basePath + "/scrmapp/consumer/user/filter/reserveService/jsp/chooseProduct?orderType=1";
        String maintainBaseUrl = basePath + "/scrmapp/consumer/user/filter/reserveService/jsp/chooseProduct?orderType=2";
        String updateFilterBaseUrl = basePath + "/scrmapp/consumer/user/filter/reserveService/jsp/chooseProduct?orderType=4";
        String cleanBaseUrl = basePath + "/scrmapp/consumer/user/filter/reserveService/jsp/chooseProduct?orderType=3";

        String changeRemindBaseUrl = basePath + "/scrmapp/consumer/user/filter/myProducts/jsp/myPdtList";

        String securityCheckBaseUrl = basePath + "/scrmapp/consumer/user/filter/securityCheck/jsp/securityCheck";

        try {

            String intall_redirect_uri = URLEncoder.encode(intallBaseUrl, "UTF-8");
            String update_filter_redirect_uri = URLEncoder.encode(updateFilterBaseUrl, "UTF-8");
            String maintain_redirect_uri = URLEncoder.encode(maintainBaseUrl, "UTF-8");
            String clean_redirect_uri = URLEncoder.encode(cleanBaseUrl, "UTF-8");
            String changeRemind_redirect_uri = URLEncoder.encode(changeRemindBaseUrl, "UTF-8");
            String securityCheck_redirect_uri = URLEncoder.encode(securityCheckBaseUrl, "UTF-8");

            String installUrl = url.replace("${REDIRECT_URI}", intall_redirect_uri);
            String updateFilterUrl = url.replace("${REDIRECT_URI}", update_filter_redirect_uri);
            String maintainUrl = url.replace("${REDIRECT_URI}", maintain_redirect_uri);
            String cleanUrl = url.replace("${REDIRECT_URI}", clean_redirect_uri);
            String changeRemindUrl = url.replace("${REDIRECT_URI}", changeRemind_redirect_uri);
            String securityCheckUrl = url.replace("${REDIRECT_URI}", securityCheck_redirect_uri);

            modelAndView.addObject("installUrl", installUrl);
            modelAndView.addObject("updateFilterUrl", updateFilterUrl);
            modelAndView.addObject("maintainUrl", maintainUrl);
            modelAndView.addObject("cleanUrl", cleanUrl);
            modelAndView.addObject("changeRemindUrl", changeRemindUrl);
            modelAndView.addObject("securityCheckUrl", securityCheckUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    @RequestMapping("/product/bind/scan/page")
    public ModelAndView toBindProductPage() {
        ModelAndView modelAndView = new ModelAndView("/bindProduct/jsp/scanQrCodeBindPdt");
        return modelAndView;
    }

    @RequestMapping("/product/bind/noscan/page")
    public ModelAndView toBindProductNoScanPage() {
        ModelAndView modelAndView = new ModelAndView("/bindProduct/jsp/noQrCodeBindPdt");
        return modelAndView;
    }

    @RequestMapping("/product/query/page")
    public ModelAndView productConditionQuery(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String modelName,
                                              @RequestParam(required = false) String productBarCode) {
        ModelAndView modelAndView = new ModelAndView("/bindProduct/jsp/bindPdt");
        if (!StringUtils.isEmpty(modelName)) {
            modelAndView.addObject("modelName", modelName);
        }
        if (!StringUtils.isEmpty(productBarCode)) {
            modelAndView.addObject("productBarCode", productBarCode);
        }

        return modelAndView;
    }

    @RequestMapping("/product/list/page")
    public ModelAndView toProductListPage() {
        ModelAndView modelAndView = new ModelAndView("/myProducts/jsp/myPdtList");
        return modelAndView;
    }

    @RequestMapping("/product/detail/page")
    public ModelAndView toProductDetailPage() {
        ModelAndView modelAndView = new ModelAndView("/myProducts/jsp/productDetail");
        return modelAndView;
    }

    @RequestMapping("/product/securityCheck/page")
    public ModelAndView toSecurityCheckPage() {
        ModelAndView modelAndView = new ModelAndView("/securityCheck/jsp/securityCheck");
        return modelAndView;
    }

    @RequestMapping("/product/securityCheck/result/page")
    public ModelAndView toSecurityCheckResultPage() {
        ModelAndView modelAndView = new ModelAndView("/securityCheck/jsp/securityCheckResult");
        return modelAndView;
    }


    /**
     * 绑定型号失败
     *
     * @return
     */
    @RequestMapping("/product/bindModel/fail/page")
    public ModelAndView bindModelFailPage() {
        ModelAndView modelAndView = new ModelAndView("/bindProduct/jsp/bindModelFail");
        return modelAndView;
    }

    /**
     * 扫描绑定失败
     *
     * @return
     */
    @RequestMapping("/product/bindQrCode/fail/page")
    public ModelAndView bindQrCodeFailPage() {
        ModelAndView modelAndView = new ModelAndView("/bindProduct/jsp/bindQrCodeFail");
        return modelAndView;
    }

    /**
     * 绑定成功
     *
     * @return
     */
    @RequestMapping("/product/bind/success/page")
    public ModelAndView bindSuccessPage(HttpServletRequest request,@RequestParam(value = "scOrderItemId",required = false)String scOrderItemId) {
        ModelAndView modelAndView = new ModelAndView("/bindProduct/jsp/bindSuccess");

        //用户授权链接
        String url = WeChatConstants.SNSAPI_BASE_COMPONENT;
        url = url.replace("${APPID}", appid);
        url = url.replace("${STATE}", appid);
        url = url.replace("{COMPONENT_APPID}", component_appid);

        String contextPath = request.getContextPath();
        String basePath = "https" + "://" + request.getServerName() + contextPath;
//        String basePath = request.getScheme() +"://" + request.getServerName() + contextPath;
        if (StringUtil.isBlank(scOrderItemId)){
            scOrderItemId="";
        }

        String intallBaseUrl = basePath + "/scrmapp/consumer/user/filter/reserveService/jsp/chooseProduct?orderType=1&scOrderItemId="+scOrderItemId;
        String maintainBaseUrl = basePath + "/scrmapp/consumer/user/filter/reserveService/jsp/chooseProduct?orderType=2";
        String updateFilterBaseUrl = basePath + "/scrmapp/consumer/user/filter/reserveService/jsp/chooseProduct?orderType=4&scOrderItemId="+scOrderItemId;
        String cleanBaseUrl = basePath + "/scrmapp/consumer/user/filter/reserveService/jsp/chooseProduct?orderType=3";

        String changeRemindBaseUrl = basePath + "/scrmapp/consumer/user/filter/myProducts/jsp/myPdtList";

        String securityCheckBaseUrl = basePath + "/scrmapp/consumer/user/filter/securityCheck/jsp/securityCheck";

        try {

            String intall_redirect_uri = URLEncoder.encode(intallBaseUrl, "UTF-8");
            String update_filter_redirect_uri = URLEncoder.encode(updateFilterBaseUrl, "UTF-8");
            String maintain_redirect_uri = URLEncoder.encode(maintainBaseUrl, "UTF-8");
            String clean_redirect_uri = URLEncoder.encode(cleanBaseUrl, "UTF-8");
            String changeRemind_redirect_uri = URLEncoder.encode(changeRemindBaseUrl, "UTF-8");
            String securityCheck_redirect_uri = URLEncoder.encode(securityCheckBaseUrl, "UTF-8");

            String installUrl = url.replace("${REDIRECT_URI}", intall_redirect_uri);
            String updateFilterUrl = url.replace("${REDIRECT_URI}", update_filter_redirect_uri);
            String maintainUrl = url.replace("${REDIRECT_URI}", maintain_redirect_uri);
            String cleanUrl = url.replace("${REDIRECT_URI}", clean_redirect_uri);
            String changeRemindUrl = url.replace("${REDIRECT_URI}", changeRemind_redirect_uri);
            String securityCheckUrl = url.replace("${REDIRECT_URI}", securityCheck_redirect_uri);

            modelAndView.addObject("installUrl", installUrl);
            modelAndView.addObject("updateFilterUrl", updateFilterUrl);
            modelAndView.addObject("maintainUrl", maintainUrl);
            modelAndView.addObject("cleanUrl", cleanUrl);
            modelAndView.addObject("changeRemindUrl", changeRemindUrl);
            modelAndView.addObject("securityCheckUrl", securityCheckUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 预约清洗页面
     *
     * @return
     */
    @RequestMapping("/product/reserve/clean")
    public ModelAndView goReserveCleanJsp() {
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_clean");
        return modelAndView;
    }

    /**
     * 预约安装页面
     *
     * @return
     */
    @RequestMapping("/product/reserve/install")
    public ModelAndView goReserveInstallJsp(@RequestParam(value = "scOrderItemId",required = false) String scOrderItemId) {

        Map<String,String> map=new HashMap<String, String>();
        if (StringUtil.isNotBlank(scOrderItemId)){
            map.put("scOrderItemId",scOrderItemId);
        }

        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_install",map);
        return modelAndView;
    }

    /**
     * 预约维修页面
     *
     * @return
     */
    @RequestMapping("/product/reserve/maintain")
    public ModelAndView goReserveRepairJsp(@RequestParam(value = "scOrderItemId",required = false) String scOrderItemId) {

        Map<String,String> map=new HashMap<String, String>();
        if (StringUtil.isNotBlank(scOrderItemId)){
            map.put("scOrderItemId",scOrderItemId);
        }
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_maintain",map);
        return modelAndView;
    }

    /**
     * 预约更换滤芯页面
     *
     * @return
     */
    @RequestMapping("/product/reserve/updateFilter")
    public ModelAndView goReserveMaintainJsp(@RequestParam(value = "scOrderItemId",required = false) String scOrderItemId) {

        Map<String,String> map=new HashMap<String, String>();
        if (StringUtil.isNotBlank(scOrderItemId)){
            map.put("scOrderItemId",scOrderItemId);
        }
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_updateFilter",map);
        return modelAndView;
    }
}
