package com.ziwow.scrmapp.wechat.controller;

import com.alibaba.druid.util.StringUtils;
import com.ziwow.scrmapp.common.annotation.MiniAuthentication;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.SystemConstants;
import com.ziwow.scrmapp.common.enums.Guarantee;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.utils.EwCardUtil;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.CookieUtil;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.enums.BuyChannel;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.wechat.persistence.entity.*;
import com.ziwow.scrmapp.wechat.service.*;
import com.ziwow.scrmapp.wechat.utils.BarCodeConvert;
import com.ziwow.scrmapp.wechat.utils.JsonApache;
import com.ziwow.scrmapp.wechat.utils.ProductServiceParamUtil;
import com.ziwow.scrmapp.wechat.vo.EnumVo;
import com.ziwow.scrmapp.wechat.vo.EwCardProductVo;
import com.ziwow.scrmapp.wechat.vo.ProductVo;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
    @Autowired
    private WechatFansService wechatFansService;
    @Value("${wechat.appid}")
    private String appid;
    @Value("${open.weixin.component_appid}")
    private String component_appid;

    //查询产品服务路径
    @Value("${qinyuan.modelname.service.query.url}")
    private String qinyuanModelnameServiceQuery;

    @Value("${scrmapp.protocol}")
    private String protocol;

    @Autowired
    private MobileService mobileService;
    @Autowired
    private WXPayService wxPayService;
    @Autowired
    private EwCardService ewCardService;

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
            logger.error("查询产品系列数据过程中遇到异常，原因[{}]", e);
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
            logger.error("查询产品类型数据过程中遇到异常，原因[{}]", e);
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
            logger.error("查询产品型号数据过程中遇到异常，原因[{}]", e);
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
            if (null != productBarCode || "".equals(productBarCode)) {
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
            logger.error("调用沁园产品绑定失败,原因[{}]", e);
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
            String content = "亲爱的用户，恭喜您成功绑定产品。积分商城全新上线、微商城下单即可享受积分专属抵扣，积分也能当钱花!";
            mobileService.sendContentByEmay(wechatUser.getMobilePhone(), content, Constant.CUSTOMER);

            result.setReturnCode(Constant.SUCCESS);
            result.setReturnMsg("产品绑定成功!");
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("产品绑定失败!");
            logger.error("产品绑定失败,原因[{}]", e);
        }
        return result;
    }



    /**
     * 查询用户绑定所有产品信息
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
            final WechatFans fans = wechatFansService.getWechatFansByUserId(userId);
            List<Product> products = productService.getProductsByUserId(userId);
            sameCodeProduct(products);
            //添加商品的保修状态
            for (Product product : products) {
                //根据产品id获取质保详情
                if (product.getBuyTime() == null){
                    continue;
                }
                final EwCard ewCard = ewCardService.selectEwCardByBarCode(product.getProductBarCode(), fans.getId());
                final Guarantee guarantee = EwCardUtil.getGuarantee(product.getBuyTime(),ewCard == null ? null : ewCard.getRepairTerm());
                product.setGuarantee(guarantee);
            }
            result.setReturnCode(Constant.SUCCESS);
            result.setData(products);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("查询产品列表失败!");
            logger.error("用户查询产品列表失败，原因[{}]", e);
        }
        return result;
    }

    /**
     * 对于一个用户一种型号有多个产品的处理
     * @param products
     */
    private void sameCodeProduct(List<Product> products) {
        Map<String,Integer> map = new HashMap<>();
        for (Product product : products) {
            if (map.get(product.getProductCode()) != null){
                product.setProductName(product.getProductName()+"-"+map.get(product.getProductCode())+1);
                map.put(product.getProductCode(),map.get(product.getProductCode())+1);
            }else {
                map.put(product.getProductCode(),1);
            }
        }
    }

    /**
     *  根据型号查询用户的产品,cardNo 所使用的延保卡号
     * @return
     */
    @RequestMapping(value = "/product/product_code", method = RequestMethod.GET)
    @ResponseBody
    public Result queryUserProductByItem(@RequestParam("signture") String signture,
                                         @RequestParam("time_stamp") String timeStamp,
                                         @RequestParam("product_code") String productCode,
                                         @RequestParam("unionId") String unionId,
                                         @RequestParam("card_no") String cardNo){
        Result result = new BaseResult();
        final WechatUser user = wechatUserService.getUserByUnionid(unionId);
        List<Product> products = productService.getProductByProductCodeAndUserId(productCode,user.getUserId());

        List<Product> collect = new LinkedList<>();
        //筛选
        for (Product product : products) {
            if (product.getBuyTime()!= null && product.getProductBarCode() != null){
                collect.add(product);
            }
        }

        if (CollectionUtils.isEmpty(collect)){
            result.setData("没有符合条件的产品!");
            result.setReturnCode(Constant.FAIL);
            return result;
        }


        List<EwCardProductVo> productVos = new ArrayList<>();
        final EwCard ewCard = ewCardService.selectEwCardByNo(cardNo);
        final WechatFans fans = wechatFansService.getWechatFansByUserId(user.getUserId());

        sameCodeProduct(collect);

        //组装信息
        packageEwCardProductVos(collect, productVos, ewCard, fans);

        result.setData(productVos);
        result.setReturnCode(Constant.SUCCESS);
        return result;
    }

    /**
     * 组装 使用延保卡加载产品列表 返回信息
     * @param collect
     * @param productVos
     * @param ewCard
     * @param fans
     */
    private void packageEwCardProductVos(List<Product> collect, List<EwCardProductVo> productVos, EwCard ewCard, WechatFans fans) {
        Date startDate = null;
        Date endDate = null;
        for (Product product : collect) {
            EwCardProductVo epv = new EwCardProductVo();
            epv.setBarCode(product.getProductBarCode());
            epv.setProductName(product.getProductName());
            final EwCard ec = ewCardService.selectEwCardByBarCode(product.getProductBarCode(), fans.getId());
            if (ec != null){
                //已经用过延保卡,最新延保卡的基础上添加天数
                if (EwCardUtil.isExtend(ec.getRepairTerm())){
                    //在延长延保阶段
                    startDate = ec.getRepairTerm();
                    endDate = EwCardUtil.getExtendRepairTerm(ec.getRepairTerm(),ewCard.getValidTime());
                }else {
                    //已过延保阶段
                    startDate = new Date();
                    endDate = EwCardUtil.getEndRepairTerm(ewCard.getValidTime());
                }
            } else {
                //正常延保阶段
                if (EwCardUtil.isNormal(product.getBuyTime())){
                    //在正常延保期限内
                    startDate = EwCardUtil.getEndNormalRepairTerm(product.getBuyTime());
                    endDate = EwCardUtil.getNormalRepairTerm(product.getBuyTime(), ewCard.getValidTime());
                }else {
                    //过了正常延保期限
                    startDate = new Date();
                    endDate = EwCardUtil.getEndRepairTerm(ewCard.getValidTime());
                }
            }
            epv.setStartDate(startDate);
            epv.setEndDate(endDate);
            productVos.add(epv);
            startDate = null;
            endDate = null;
        }
    }


    /**
     * 查询产品的服务费信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/product/list/serviceFeeStatus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result queryUserProductServiceFeeStatus(HttpServletRequest request, HttpServletResponse response,@RequestBody List<Product> temList) {

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

            WechatUser wechatUser = wechatUserService.getUserByUserId(userId);
            if (wechatUser == null) {
                logger.error("查询用户失败，cookie中userId错误");
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("用户无效，请退出重新操作！");
                return result;
            }
            long wfId = wechatUser.getWfId();
            WechatFans wechatFans = wechatFansService.getWechatFansById(wfId);
            String unionId = wechatFans.getUnionId();
//             temList = productService.getProductsByUserId(userId);
            //System.out.println(temList);
            //读取远程服务费信息，content为JSONARrray [{id:xxx, model_name:xxx,service_fee:xxx,service_status:xxx},{,,}]
            String content = JsonApache.postModelNames(unionId, temList, qinyuanModelnameServiceQuery);
            if (content == null) {
                result.setReturnCode(Constant.FAIL);
                result.setReturnMsg("查询外部产品列表失败!");
                logger.error("用户查询产品列表失败，原因[{}]", "外部查询服务费状态失败");
                return result;
            }
//            JSONObject jsonObject = JSON.parseObject(content);
//            JSONArray jsonArray = jsonObject.getJSONArray("data");
//            List<ProductFilter> productFilterList = new ArrayList<ProductFilter>();

            /**
             * 	 *
             * 	 * 0:已购买滤芯未购买服务费
             * 	 * 1：已购买滤芯已购买服务费
             * 	 *
             * 	 * 5：查到滤芯但是预约次数达到上限
             * 	 * 6：没有产品
             * 	 * 7：没有关联滤芯够买记录
             * 	 * 8：没有购买过产品
             */
//            for (int i = 0; i < jsonArray.size(); i++) {
//                JSONObject jo = (JSONObject) jsonArray.get(i);
//                String temId = (String) jo.get("id");  //id为商品主键ID
//                long id = Long.parseLong(temId);
//                String modelName = (String) jo.get("modelName");
//                String serviceFee = (String) jo.get("serviceFeePrice");
//                if (serviceFee == null) {
//                    serviceFee = "0";
//                }
//                //0：已购买滤芯未购买服务 1：已购买滤芯和服务 5：没有滤芯或不能购买滤芯
//                String serviceStatus = (String) jo.get("serviceStatus");
//                String serviceFeeId = (String) jo.get("serviceFeeId");
//                if (serviceFeeId == null) {
//                    serviceFeeId = "0";
//                }
//            }
            //对产品添加服务费和服务状态
            List<Product> products = ProductServiceParamUtil.addServiceParam(temList, content);
            result.setReturnCode(Constant.SUCCESS);
            result.setData(products);
        } catch (Exception e) {
            result.setReturnCode(Constant.FAIL);
            result.setReturnMsg("查询产品列表失败!");
            logger.error("用户查询产品列表失败，原因[{}]", e);
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
            logger.error("用户查询产品详情失败，原因[{}]", e);
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
            logger.error("开闭或开启滤芯提醒异常，原因[{}]", e);
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
            logger.error("调用沁园获取图片失败，原因[{}]", e);
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
            logger.error("用户查询产品基本信息失败，原因[{}]", e);
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
            logger.error("删除产品失败，原因[{}]", e);
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
            logger.error("获取购买渠道数据失败，原因[{}]", e);
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
        String basePath = protocol + "://" + request.getServerName() + contextPath;

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
        String basePath = protocol + "://" + request.getServerName() + contextPath;
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
    public ModelAndView goReserveInstallJsp(@RequestParam(value = "scOrderItemId", required = false) String scOrderItemId) {

        Map<String, String> map = new HashMap<String, String>();
        if (StringUtil.isNotBlank(scOrderItemId)) {
            map.put("scOrderItemId", scOrderItemId);
        }

        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_install", map);
        return modelAndView;
    }

    /**
     * 预约维修页面
     *
     * @return
     */
    @RequestMapping("/product/reserve/maintain")
    public ModelAndView goReserveRepairJsp(@RequestParam(value = "scOrderItemId", required = false) String scOrderItemId) {

        Map<String, String> map = new HashMap<String, String>();
        if (StringUtil.isNotBlank(scOrderItemId)) {
            map.put("scOrderItemId", scOrderItemId);
        }
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_maintain", map);
        return modelAndView;
    }

    /**
     * 预约更换滤芯页面
     *
     * @return
     */
    @RequestMapping("/product/reserve/updateFilter")
    public ModelAndView goReserveMaintainJsp(@RequestParam(value = "scOrderItemId", required = false) String scOrderItemId) {

        Map<String, String> map = new HashMap<String, String>();
        if (StringUtil.isNotBlank(scOrderItemId)) {
            map.put("scOrderItemId", scOrderItemId);
        }
        ModelAndView modelAndView = new ModelAndView("/reserveService/jsp/reserveService_updateFilter", map);
        return modelAndView;
    }
}
