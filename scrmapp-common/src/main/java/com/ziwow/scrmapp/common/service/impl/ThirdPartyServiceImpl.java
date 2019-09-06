package com.ziwow.scrmapp.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sinocc.service.*;
import com.ziwow.scrmapp.common.aop.LoginRequired;
import com.ziwow.scrmapp.common.bean.pojo.*;
import com.ziwow.scrmapp.common.bean.vo.SecurityVo;
import com.ziwow.scrmapp.common.bean.vo.cem.CemAssertInfo;
import com.ziwow.scrmapp.common.bean.vo.cem.CemProductInfo;
import com.ziwow.scrmapp.common.bean.vo.cem.CemResp;
import com.ziwow.scrmapp.common.bean.vo.csm.*;
import com.ziwow.scrmapp.common.bean.vo.mall.MallOrderVo;
import com.ziwow.scrmapp.common.bean.vo.mall.OrderItem;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.constants.ErrorCodeConstants;
import com.ziwow.scrmapp.common.exception.ThirdException;
import com.ziwow.scrmapp.common.persistence.entity.InstallPart;
import com.ziwow.scrmapp.common.persistence.entity.RepairItem;
import com.ziwow.scrmapp.common.persistence.entity.RepairPart;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.common.service.ThirdPartyService;
import com.ziwow.scrmapp.common.utils.HttpKit;
import com.ziwow.scrmapp.common.utils.JsonUtil;
import com.ziwow.scrmapp.common.utils.MD5;
import java.io.IOException;
import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.axis.client.Call;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {
    private static final Logger LOG = LoggerFactory.getLogger(ThirdPartyServiceImpl.class);
    // 商城系统url
    @Value("${mall.registercheck.url}")
    private String mallRegisterCheckUrl;
    @Value("${mall.registermember.url}")
    private String mallRegisterMemberUrl;
    @Value("${mall.queryorder.url}")
    private String mallQueryOrderUrl;
    @Value("${mall.productimg.url}")
    private String mallProductImgUrl;
    // csm系统url
    /**
     * 查询延保卡url
     */
    @Value("${csm.queryewcard.url}")
    private String queryEwCardUrl;
    /**
     * 注册延保卡
     */
    @Value("${csm.registerewcard.url}")
    private String registerEwCardUrl;
    /**
     * 查询是否存在安装单
     */
    @Value("${csm.existinstalllisturl.url}")
    private String existInstallListUrl;
    /**
     * 根据条码查询购买时间
     */
    @Value("${csm.purchdateurl.url}")
    private String purchDateUrl;

    @Value("${csm.csswx.url}")
    private String cssWxUrl;
    @Value("${csm.cssappealWx.url}")
    private String cssAppealWxUrl;
    @Value("${csm.csswsrp.url}")
    private String csmCsswsRpUrl;
    @Value("${csm.client.auth.username}")
    private String authUserName;
    @Value("${csm.client.auth.password}")
    private String authPassword;

    @Value("${cem.aid}")
    private String cemAid;
    @Value("${cem.secretKey}")
    private String cemSecretKey;
    @Value("${cem.segmentId}")
    private String cemSegmentId;
    @Value("${cem.assets.url}")
    private String cemAssetsUrl;
    @Value("${cem.productInfo.url}")
    private String cemProductInfoUrl;
    /**
     * 读取超时时间
     */
    @Value("${csm.readtimeout}")
    private int readTimeout;
    /**
     * 连接超时时间
     */
    @Value("${csm.connecttimeout}")
    private int connectTimeout;

    private RestTemplate restTemplate;

    @PostConstruct
    public void restTemplate(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(readTimeout);
        requestFactory.setConnectTimeout(connectTimeout);
        restTemplate = new RestTemplate(requestFactory);
    }

    @Override
    public EwCardVo getEwCardListByNo(String cardNo){
        LOG.info("第三方CSM系统根据卡号查询延保卡,cardNo:[{}]", cardNo);
        EwCardVo ewCardVo = null;
        try {
            final String s = restTemplate.postForObject(queryEwCardUrl, JsonUtil.object2Json(ImmutableMap.of("card_no",cardNo,"mobile","")), String.class);
            LOG.info("第三方CSM系统根据卡号查询延保卡,收到csm的数据:[{}]",s);
            ewCardVo = JsonUtil.json2Object(s, EwCardVo.class);
        } catch (IOException e) {
            throw new ThirdException("调用第三方CSM系统根据卡号查询延保卡失败","查询延保卡失败，请稍后再试",e);
        } catch (Exception e) {
            throw new ThirdException("调用第三方CSM系统根据卡号查询延保卡失败","查询延保卡失败，请稍后再试",e);
        }
        return ewCardVo;
    }


    @Override
    public BaseCardVo registerEwCard(CSMEwCardParam CSMEwCardParam) {
        LOG.info("第三方CSM系统注册延保卡信息,CSMEwCardParam:[{}]", CSMEwCardParam);
        BaseCardVo baseCardVo = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            final String s1 = JsonUtil.object2Json(CSMEwCardParam);
            org.springframework.http.HttpEntity<String> formEntity = new org.springframework.http.HttpEntity<String>(s1, headers);

            final String s = restTemplate.postForObject(registerEwCardUrl, formEntity , String.class);
            LOG.info("第三方CSM系统注册延保卡信息,卡号为:{},收到csm的数据:[{}]",CSMEwCardParam.getCardNo(),s);
            baseCardVo = JsonUtil.json2Object(s, BaseCardVo.class);
        } catch (IOException e) {
            throw new ThirdException("调用第三方CSM系统注册延保卡信息失败", "注册延保卡失败，请稍后再试",e);
        } catch (Exception e) {
            throw new ThirdException("调用第三方CSM系统注册延保卡信息失败", "注册延保卡失败，请稍后再试",e);
        }
        return baseCardVo;
    }

    @Override
    public boolean existInstallList(String mobile) {
        LOG.info("第三方CSM系统是否存在安装单,productBarCode:[{}]", mobile);
        ExistInstallVo existInstallVo = null;
        try {
            final String s = restTemplate.postForObject(existInstallListUrl, JsonUtil.object2Json(ImmutableMap.of("mobile",mobile)), String.class);
            LOG.info("第三方CSM系统是否存在安装单,收到csm的数据:[{}]",s);
            existInstallVo = JsonUtil.json2Object(s, ExistInstallVo.class);
        } catch (IOException e) {
            throw new ThirdException("调用第三方CSM系统是否存在安装单失败", "注册延保卡失败，请稍后再试",e);
        } catch (Exception e) {
            throw new ThirdException("调用第三方CSM系统是否存在安装单失败", "注册延保卡失败，请稍后再试",e);
        }
        return existInstallVo.getStatus().getCode().equals(ErrorCodeConstants.CODE_E0);
    }

    @Override
    public String getPurchDate(String productBarCode) {
        LOG.info("第三方CSM系统根据产品条码查询安装单时间,productBarCode:[{}]", productBarCode);
        try {
            final String s = restTemplate.postForObject(purchDateUrl, JsonUtil.object2Json(ImmutableMap.of("barcode",productBarCode)), String.class);
            LOG.info("收到csm的数据:[{}]",s);
            net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(s);
            final JSONObject status = jsonObj.getJSONObject("status");
            final String code = status.getString("code");
            if (ErrorCodeConstants.CODE_E0.equals(code)){
                return JsonUtil.json2Object(s, PurchDateVo.class).getItems().getPurchDate();
            }
        } catch (IOException e) {
            throw new ThirdException("调用第三方CSM系统根据产品条码查询安装单时间失败", "查询产品失败，请稍后再试",e);
        } catch (Exception e) {
            throw new ThirdException("调用第三方CSM系统根据产品条码查询安装单时间失败", "查询产品失败，请稍后再试",e);
        }
        return "";
    }


    @Override
    public boolean registerCheck(String userName) {
        LOG.info("第三方商城系统判断该用户是否注册参数,userName:[{}]", userName);
        Map<String, String> params = Maps.newHashMap();
        params.put("username", userName);
        try {
            String result = HttpKit.get(mallRegisterCheckUrl, params);
            LOG.info("获取第三方商城判断用户是否注册结果:[{}]", result);
            return Boolean.parseBoolean(result);
        } catch (Exception e) {
            throw new ThirdException("调用第三方商城系统判断该用户是否注册参数失败" ,"系统繁忙，请稍后再试",e);
        }
    }

    @Override
    @Async
    @LoginRequired
    public void registerMember(String mobile, String password, String openId) {
        LOG.info("将注册信息同步到第三方商城系统参数,mobile:[{}],password:[{}]", mobile, password);
        Map<String, String> params = Maps.newHashMap();
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("openid", openId);
        try {
            String result = HttpKit.get(mallRegisterMemberUrl, params);
            LOG.info("微信端会员[{}],密码[{}],openId[{}]注册信息同步结果:[{}]", mobile, password, openId, result);
        } catch (Exception e) {
            throw new ThirdException("将注册信息同步到第三方商城系统参数失败", "系统繁忙，请稍后再试",e);
        }
    }

    @Override
    @LoginRequired
    public List<MallOrderVo> queryOrders(String mobilePhone) {
        LOG.info("查询该用户在第三方商城系统的订单信息参数,mobilePhone:[{}]", mobilePhone);
        Map<String, String> params = Maps.newHashMap();
        params.put("username", mobilePhone);
        try {
            String result = HttpKit.get(mallQueryOrderUrl, params);
            LOG.info("查询该用户在第三方商城的订单信息结果:[{}]", result);
            if (StringUtils.isNotEmpty(result)) {
                net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(result);
                int state = jsonObj.getInt("state");
                if (state == 0) { // 1:该用户没有已支付的订单  2:查询失败
                    net.sf.json.JSONArray jsonArray = jsonObj.getJSONArray("data");
                    Map<String, Class<OrderItem>> classMap = new HashMap<String, Class<OrderItem>>();
                    classMap.put("items", OrderItem.class);
                    @SuppressWarnings({"unchecked", "deprecation"})
                    List<MallOrderVo> list = net.sf.json.JSONArray.toList(jsonArray, MallOrderVo.class, classMap);
                    return list;
                }
            }
        } catch (Exception e) {
            throw new ThirdException("获取第三方商城判断用户注册接口失败", "系统繁忙，请稍后再试",e);
        }
        return null;
    }

    @Override
    @LoginRequired
    public String getProductImg(String spec) {
        LOG.info("第三方商城系统根据产品型号获取产品图片接口参数,spec:[{}]", spec);
        String imgUrl = StringUtils.EMPTY;
        Map<String, String> params = Maps.newHashMap();
        params.put("sn", spec);
        try {
            String result = HttpKit.get(mallProductImgUrl, params);
            LOG.info("查询该用户在第三方商城的订单信息结果:[{}]", result);
            if (StringUtils.isNotEmpty(result)) {
                JSONObject jsonObj = JSONObject.fromObject(result);
                int state = jsonObj.getInt("state");  // 1:产品型号为空  2:未找到商品
                if (state == 0) {
                    imgUrl = jsonObj.getString("img");
                }
            }
        } catch (Exception e) {
            throw new ThirdException("第三方商城系统根据产品型号获取产品图片接口失败", "系统繁忙，请稍后再试",e);
        }
        return imgUrl;
    }


    /**
     * 获取csswx服务
     *
     * @return
     */
    private CssWxService getCssWxService() {
        CssWxService service = null;
        try {
            org.codehaus.xfire.service.Service serviceModel = new ObjectServiceFactory().create(CssWxService.class);
            XFireProxyFactory serviceFactory = new XFireProxyFactory();
            service = (CssWxService) serviceFactory.create(serviceModel, cssWxUrl);
        } catch (Exception e) {
            LOG.error("获取csswx服务失败:", e);
        }
        return service;
    }

    /**
     * 获取cssAppealWx服务
     *
     * @return
     */
    private CssAppealWxService getCssAppealWxService() {
        CssAppealWxService service = null;
        try {
            org.codehaus.xfire.service.Service serviceModel = new ObjectServiceFactory().create(CssAppealWxService.class);
            XFireProxyFactory serviceFactory = new XFireProxyFactory();
            service = (CssAppealWxService) serviceFactory.create(serviceModel, cssAppealWxUrl);
        } catch (Exception e) {
            LOG.error("获取cssAppealWx服务失败:", e);
        }
        return service;
    }

    /**
     * 获取cssWsRp服务
     *
     * @return
     */
    private CssWsRPService getCssWsRPService() {
        CssWsRPService service = null;
        try {
            org.codehaus.xfire.service.Service serviceModel = new ObjectServiceFactory().create(CssWsRPService.class);
            XFireProxyFactory serviceFactory = new XFireProxyFactory();
            service = (CssWsRPService) serviceFactory.create(serviceModel, csmCsswsRpUrl);
        } catch (Exception e) {
            LOG.error("获取cssWsRp服务失败:", e);
        }
        return service;
    }


    private Client getClient(XFireProxy proxy) {
        Client client = proxy.getClient();
        client.addOutHandler(new ClientAuthenticationHandler(authUserName, authPassword));
        client.setTimeout(readTimeout);
        client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, String.valueOf( connectTimeout ));
        return client;
    }

    /**
     * 根据产品条码或产品型号查询产品信息
     */
    @Override
    @LoginRequired
    public ProductItem getProductItem(ProductParam productParam) {
        CssWxRequest result = null;
        Client client = null;
        ProductItem productItem = null;
        try {
            LOG.info("============开始调用csm系统的产品信息查询接口================");
            CssWxService service = this.getCssWxService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            // 传条件 二选一不能都为空
            CssWxRequest req = new CssWxRequest();
            if (StringUtils.isNotEmpty(productParam.getItem_code())) {
                req.setItem_Code(productParam.getItem_code());
            } else if (StringUtils.isNotEmpty(productParam.getBarCode())) {
                req.setBarCode(productParam.getBarCode());
            } else if (StringUtils.isNotEmpty(productParam.getSpec())) {
                // 产品型号(例:FR600（新1）（电子商务专供）)
                req.setSpec(productParam.getSpec());
            }
            // 查询请求
            result = service.getItem(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("查询产品返回信息status:[{}],message[{}]", status, message);
                if (status == 1) {
                    JSONArray myJsonArray = JSONArray.fromObject(result.getOne_V_Msg());
                    JSONObject dataObj = myJsonArray.getJSONObject(0);
                    productItem = new ProductItem();
                    productItem.setItemKind(dataObj.getString("item_kind")); // 产品组织(0:请选择 1:净水机 2:饮水机 3:净水宝)
                    productItem.setItemCode(dataObj.getString("item_code")); // 产品编码
                    productItem.setItemName(dataObj.getString("item_name")); // 产品名称
                    productItem.setBigcName(dataObj.getString("bigc_name")); // 产品大类
                    productItem.setSpec(dataObj.getString("spec"));        // 产品型号
                    productItem.setSmallcName(dataObj.getString("smallc_name"));    // 产品小类
                    productItem.setFilterGradeId(dataObj.getInt("filter_grade_id"));    // 滤芯级别ID
                    productItem.setFilterGrade(dataObj.getString("filter_grade"));    // 滤芯级别名称
                    if (StringUtils.isNotEmpty(req.getBarCode())) {
                        productItem.setBarcode(dataObj.getString("barcode"));  // 产品条码
                        productItem.setFromChannel(dataObj.getInt("from_channel")); // 销售类型(1:家用,2:商用,3:电商,4:战略部)
                    }
                }
            }
        } catch (Exception e) {
            throw new ThirdException("根据产品条码或产品型号查询产品信息失败", "查询产品失败，请稍后再试",e);
        } finally {
            client.close();
        }
        return productItem;
    }

    /**
     * 根据产品编码批量获取产品信息
     */
    @Override
    @LoginRequired
    public List<ProductItem> getProductList(String itemCodes) {
        CssWxRequest result = null;
        Client client = null;
        List<ProductItem> list = Lists.newArrayList();
        try {
            LOG.info("============开始调用csm系统的批量获取产品信息查询接口================");
            CssWxService service = this.getCssWxService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            // 传条件 二选一不能都为空
            CssWxRequest req = new CssWxRequest();
            req.setItem_Code(itemCodes);
            result = service.getItemMore(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("批量获取产品信息返回信息status:[{}],message[{}]", status, message);
                if (status == 1) {
                    JSONArray myJsonArray = JSONArray.fromObject(result.getOne_V_Msg());
                    for (int i = 0; i < myJsonArray.size(); i++) {
                        JSONObject dataObj = myJsonArray.getJSONObject(i);
                        ProductItem productItem = new ProductItem();
                        productItem.setItemKind(dataObj.getString("item_kind")); // 产品组织(0:请选择 1:净水机 2:饮水机 3:净水宝)
                        productItem.setItemCode(dataObj.getString("item_code")); // 产品编码
                        productItem.setItemName(dataObj.getString("item_name")); // 产品名称
                        productItem.setBigcName(dataObj.getString("bigc_name")); // 产品大类
                        productItem.setSpec(dataObj.getString("spec"));        // 产品型号
                        productItem.setSmallcName(dataObj.getString("smallc_name"));    // 产品小类
                        productItem.setFilterGradeId(dataObj.getInt("filter_grade_id"));    // 滤芯级别ID
                        productItem.setFilterGrade(dataObj.getString("filter_grade"));    // 滤芯级别名称
                        if (StringUtils.isNotEmpty(req.getBarCode())) {
                            productItem.setBarcode(dataObj.getString("barcode"));  // 产品条码
                            productItem.setFromChannel(dataObj.getInt("from_channel")); // 销售类型(1:家用,2:商用,3:电商,4:战略部)
                        }
                        list.add(productItem);
                    }
                }
            }
        } catch (Exception e) {
            throw new ThirdException("批量获取产品信息接口失败", "系统繁忙，请稍后再试",e);
        } finally {
            client.close();
        }
        return list;
    }

    /**
     * 根据产品获取滤芯
     */
    @Override
    @LoginRequired
    public List<ProductFilterGrade> getItemFilterGrade(ProductFilterGradeParam productFilterGradeParam) {
        CssWxRequest result = null;
        Client client = null;
        List<ProductFilterGrade> list = Lists.newArrayList();
        try {
            //调用WebService请求
            LOG.info("============开始调用csm系统的根据产品获取滤芯接口================");
            CssWxService service = this.getCssWxService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            // 传条件 二选一 不能都为空
            CssWxRequest req = new CssWxRequest();
            if (StringUtils.isNotEmpty(productFilterGradeParam.getItemCode())) {
                req.setItem_Code(productFilterGradeParam.getItemCode());  // 产品编码
            } else if (StringUtils.isNotEmpty(productFilterGradeParam.getSpec())) {
                req.setSpec(productFilterGradeParam.getSpec()); //产品型号
            }
            // 查询请求
            result = service.getItemFilterGrade(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("查询产品滤芯信息status:[{}],message[{}]", status, message);
                if (result.getStatus() == 1) {
                    JSONArray myJsonArray = JSONArray.fromObject(result.getOne_V_Msg());
                    for (int i = 0; i < myJsonArray.size(); i++) {
                        JSONObject dataObj = myJsonArray.getJSONObject(i);
                        int filterGradeId = dataObj.getInt("filter_grade_id");//滤芯等级ID
                        String filterGrade = dataObj.getString("filter_grade"); // 滤芯等级名称
                        String gradeSeq = dataObj.getString("grade_seq"); // 滤芯序号
                        String gradeName = dataObj.getString("grade_name"); // 滤芯名称
                        String filterPrice = dataObj.getString("filter_price"); // 滤芯指导价
                        int expectRplDays = dataObj.getInt("expect_rpl_days");// 标准更换周期(天)
                        ProductFilterGrade productFilterGrade = new ProductFilterGrade();
                        productFilterGrade.setFilterGradeId(filterGradeId);
                        productFilterGrade.setExpectRplDays(expectRplDays);
                        productFilterGrade.setFilterGrade(filterGrade);
                        productFilterGrade.setGradeName(gradeName);
                        productFilterGrade.setFilterPrice(filterPrice);
                        productFilterGrade.setGradeSeq(gradeSeq);
                        list.add(productFilterGrade);
                    }
                }
            }
        } catch (Exception e) {
            throw new ThirdException("根据产品获取滤芯信息失败", "取滤芯信息失败，请稍后再试",e);
        } finally {
            client.close();
        }
        return list;
    }

    /**
     * 预约生成受理单号
     */
    @Override
    @LoginRequired
    public Result addCssAppeal(AcceptanceFormParam acceptanceFormParam) {
        CssWxRequest result = null;
        Client client = null;
        String webAppealNo = StringUtils.EMPTY;
        Result rtnResult = new BaseResult();
        try {
            LOG.info("调用csm系统的预约生成受理单接口，参数为:[{}]", JSON.toJSONString(acceptanceFormParam));
            CssAppealWxService service = this.getCssAppealWxService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            // 传条件 不能为空
            CssWxRequest req = new CssWxRequest();
            req.setFlag(1);//操作类型  1:新增  2:修改预约时间 3:取消预约
            req.setOne_V_Msg(JSONArray.fromObject(acceptanceFormParam).toString());//数据集
            req.setTwo_V_Msg(JSONArray.fromObject(acceptanceFormParam.getProducts()).toString());
            // 查询请求
            result = service.addCssAppeal(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("预约生成受理单号接口返回信息status:[{}],message[{}]", status, message);
                if (status == 1) {
                    webAppealNo = result.getNo();
                    rtnResult.setReturnCode(Constant.SUCCESS);
                    rtnResult.setData(webAppealNo);
                } else {
                    rtnResult.setReturnCode(Constant.FAIL);
                    rtnResult.setReturnMsg(message);
                }
            }
        } catch (Exception e) {
            throw new ThirdException("预约生成受理单号接口失败", "预约生成受理单号失败，请稍后再试",e);
        } finally {
            client.close();
        }
        return rtnResult;
    }

    /**
     * 变更预约时间
     */
    @Override
    @LoginRequired
    public Result updateCssAppeal(String webAppealNo, String serviceTime, int isUser) {
        CssWxRequest result = null;
        Client client = null;
        Result rtnResult = new BaseResult();
        try {
            LOG.info("============开始调用csm系统的变更预约时间接口================");
            CssAppealWxService service = this.getCssAppealWxService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            JSONArray jsonArray = new JSONArray();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("no", webAppealNo);// CSM返回的单据号
            map.put("service_time", serviceTime);// 预约时间
            map.put("is_user", isUser);
            jsonArray.add(map);
            // 传条件 不能为空
            CssWxRequest req = new CssWxRequest();
            req.setFlag(2);   //操作类型  1新增  2修改预约时间 3取消预约
            req.setOne_V_Msg(jsonArray.toString());//数据集
            // 查询请求
            result = service.addCssAppeal(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("变更预约时间接口返回信息status:[{}],message[{}]", status, message);
                if (status == 1) {
                    rtnResult.setReturnCode(Constant.SUCCESS);
                    rtnResult.setData(status);
                } else {
                    rtnResult.setReturnCode(Constant.FAIL);
                    rtnResult.setReturnMsg(message);
                }
            }
        } catch (Exception e) {
            throw new ThirdException("变更预约时间接口失败", "变更预约时间失败，请稍后再试",e);
        } finally {
            client.close();
        }
        return rtnResult;
    }

    /**
     * 取消预约单
     */
    @Override
    @LoginRequired
    public Result cancelCssAppeal(String webAppealNo) {
        CssWxRequest result = null;
        Client client = null;
        Result rtnResult = new BaseResult();
        try {
            LOG.info("============开始调用csm系统的取消预约单接口================");
            CssAppealWxService service = this.getCssAppealWxService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            JSONArray jsonArray = new JSONArray();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("no", webAppealNo);// CSM返回的单据号
            jsonArray.add(map);
            // 传条件 不能为空
            CssWxRequest req = new CssWxRequest();
            req.setFlag(3);   //操作类型  1新增  2修改预约时间 3取消预约
            req.setOne_V_Msg(jsonArray.toString());//数据集
            // 查询请求
            result = service.addCssAppeal(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("取消预约单接口返回信息status:[{}],message[{}]", status, message);
                if (status == 1) {
                    rtnResult.setReturnCode(Constant.SUCCESS);
                    rtnResult.setData(status);
                } else {
                    rtnResult.setReturnCode(Constant.FAIL);
                    rtnResult.setReturnMsg(message);
                }
            }
        } catch (Exception e) {
            throw new ThirdException("取消预约单接口失败", "取消预约单失败，请稍后再试",e);
        } finally {
            client.close();
        }
        return rtnResult;
    }

    /**
     * 师傅拒绝预约单
     */
    @Override
    @LoginRequired
    public Result wxSingleBackation(String webAppealNo, String reject_season) {
        CssWxRequest result = null;
        Client client = null;
        Result rtnResult = new BaseResult();
        try {
            LOG.info("============开始调用csm系统的师傅拒绝预约单接口================");
            CssWsRPService service = this.getCssWsRPService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("appeal_no", webAppealNo);// 师傅拒绝工单单号
            jsonObject.put("reject_season", reject_season);// 师傅拒绝工单单号
            // 传条件 不能为空
            CssWxRequest req = new CssWxRequest();
            req.setOne_V_Msg(jsonObject.toString());//数据集
            // 查询请求
            result = service.WxSingleBackation(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("师傅拒绝预约单接口返回信息status:[{}],message[{}]", status, message);
                if (status == 1) {
                    rtnResult.setReturnCode(Constant.SUCCESS);
                    rtnResult.setData(status);
                } else {
                    rtnResult.setReturnCode(Constant.FAIL);
                    rtnResult.setReturnMsg(message);
                }
            }
        } catch (Exception e) {
            throw new ThirdException("师傅拒绝预约单接口失败", "拒绝预约单失败，请稍后再试",e);
        } finally {
            client.close();
        }
        return rtnResult;
    }



    /**
     * 安装单同步接口
     */
    @Override
    @LoginRequired
    public Result cssAppealInstallation(AppealInstallParam appealInstallParam) {
        CssWxRequest result = null;
        Client client = null;
        String appealInstallNo = StringUtils.EMPTY;
        Result rtnResult = new BaseResult();
        LOG.info("调用csm安装单同步接口，参数为:[{}]", JSON.toJSONString(appealInstallParam));
        try {
            CssWsRPService service = this.getCssWsRPService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            // One_V_Msg传安装单信息
            CssWxRequest req = new CssWxRequest();
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setExcludes(new String[]{"installProdStr"});
            req.setOne_V_Msg(JSONArray.fromObject(appealInstallParam, jsonConfig).toString());// 数据集
            if (StringUtils.isNotBlank(appealInstallParam.getInstallProdStr())) {
                // Four_V_Msg传产品信息
                req.setFour_V_Msg(appealInstallParam.getInstallProdStr());
            }
            // 查询请求
            result = service.CssAppealInstallationList(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("安装单同步接口返回信息status:[{}],message[{}]", status, message);
                if (status == 1) {
                    appealInstallNo = result.getOne_V_Msg();
                    rtnResult.setReturnCode(Constant.SUCCESS);
                    rtnResult.setData(appealInstallNo);
                } else {
                    rtnResult.setReturnCode(Constant.FAIL);
                    rtnResult.setReturnMsg(message);
                }
            }
        } catch (Exception e) {
            throw new ThirdException("安装单同步接口失败", "系统繁忙，请稍后再试",e);
        } finally {
            client.close();
        }
        return rtnResult;
    }

    /**
     * 保养单同步接口
     */
    @Override
    @LoginRequired
    public Result cssAppealMaintain(AppealMaintainParam appealMaintainParam) {
        CssWxRequest result = null;
        Client client = null;
        String appealMaintainNo = StringUtils.EMPTY;
        Result rtnResult = new BaseResult();
        LOG.info("调用csm保养单同步接口,参数为:[{}]", JSON.toJSON(appealMaintainParam));
        try {
            CssWsRPService service = this.getCssWsRPService();
            client = getClient(client, service);
            // 传条件 不能为空
            CssWxRequest req = new CssWxRequest();
            req.setOne_V_Msg(JSONArray.fromObject(appealMaintainParam).toString());// 数据集
            if (!StringUtils.isEmpty(appealMaintainParam.getMaintainStr())) {
                req.setThree_V_Msg(appealMaintainParam.getMaintainStr());
            }
            if (!StringUtils.isEmpty(appealMaintainParam.getFilterStr())) {
                req.setTwo_V_Msg(appealMaintainParam.getFilterStr());
            }
            req.setFour_V_Msg(appealMaintainParam.getProd_info());
            // 查询请求
            result = service.CssAppealMaintain(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("保养单同步接口返回信息status:[{}],message[{}]", status, message);
                if (status == 1) {
                    appealMaintainNo = result.getOne_V_Msg();
                    rtnResult.setReturnCode(Constant.SUCCESS);
                    rtnResult.setData(appealMaintainNo);
                } else {
                    rtnResult.setReturnCode(Constant.FAIL);
                    rtnResult.setReturnMsg(message);
                }
            }
        } catch (Exception e) {
            throw new ThirdException("保养单同步接口失败", "系统繁忙，请稍后再试",e);
        } finally {
            client.close();
        }
        return rtnResult;
    }

    private Client getClient(Client client, CssWsRPService service) {
        XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
        client = getClient(proxy);
        return client;
    }

    /**
     * 维修单同步接口
     */
    @Override
    @LoginRequired
    public Result cssAppealMaintenance(AppealMaintenanceParam appealMaintenanceParam) {
        CssWxRequest result = null;
        Client client = null;
        String appealMaintenanceNo = StringUtils.EMPTY;
        Result rtnResult = new BaseResult();
        LOG.info("调用csm维修单同步接口,参数为:[{}]", JSONObject.fromObject(appealMaintenanceParam).toString());
        try {
            LOG.info("============开始调用csm系统的维修单同步接口================");
            CssWsRPService service = this.getCssWsRPService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            // 传条件 不能为空
            CssWxRequest req = new CssWxRequest();
            req.setOne_V_Msg(JSONArray.fromObject(appealMaintenanceParam).toString());// 数据集
            if (!StringUtils.isEmpty(appealMaintenanceParam.getRepairPartStr())) {
                req.setThree_V_Msg(appealMaintenanceParam.getRepairPartStr());
            }
            req.setFour_V_Msg(appealMaintenanceParam.getProd_info());
            // 查询请求
            result = service.CssAppealMaintenanceList(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("维修单同步接口返回信息status:[{}],message[{}]", status, message);
                if (status == 1) {
                    appealMaintenanceNo = result.getOne_V_Msg();
                    rtnResult.setReturnCode(Constant.SUCCESS);
                    rtnResult.setData(appealMaintenanceNo);
                } else {
                    rtnResult.setReturnCode(Constant.FAIL);
                    rtnResult.setReturnMsg(message);
                }
            }
        } catch (Exception e) {
            throw new ThirdException("维修单同步接口失败", "系统繁忙，请稍后再试",e);
        } finally {
            client.close();
        }
        return rtnResult;
    }

    /**
     * 评价同步接口
     */
    @Override
    @LoginRequired
    public Result cssEvaluate(EvaluateParam evaluateParam) {
        CssWxRequest result = null;
        Client client = null;
        Result rtnResult = new BaseResult();
        try {
            LOG.info("============开始调用csm系统的评价同步接口================");
            CssWsRPService service = this.getCssWsRPService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            // 传条件 不能为空
            CssWxRequest req = new CssWxRequest();
            req.setOne_V_Msg(JSON.toJSONString(evaluateParam));// 数据集
            // 查询请求
            result = service.CssServiceEvaluateSearch(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("评价同步接口返回信息status:[{}],message[{}]", status, message);
                if (status == 1) {
                    rtnResult.setReturnCode(Constant.SUCCESS);
                    rtnResult.setData(status);
                } else {
                    rtnResult.setReturnCode(Constant.FAIL);
                    rtnResult.setReturnMsg(message);
                }
            }
        } catch (Exception e) {
            throw new ThirdException("评价同步接口失败", "系统繁忙，请稍后再试",e);
        } finally {
            client.close();
        }
        return rtnResult;
    }

    /**
     * 根据产品获取保养项
     */
    @Override
    @LoginRequired
    public List<ProductFilterGrade> cssMaintenanceItem(ProductFilterGradeParam productFilterGradeParam) {
        CssWxRequest result = null;
        Client client = null;
        List<ProductFilterGrade> list = Lists.newArrayList();
        try {
            //调用WebService请求
            LOG.info("============开始调用csm系统的根据产品获取保养项接口================");
            CssWsRPService service = this.getCssWsRPService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            // 传条件 二选一 不能都为空
            CssWxRequest req = new CssWxRequest();
            if (StringUtils.isNotEmpty(productFilterGradeParam.getItemCode())) {
                req.setItem_Code(productFilterGradeParam.getItemCode());  // 产品编码
                req.setSpec("");  // 产品编码
            } else if (StringUtils.isNotEmpty(productFilterGradeParam.getSpec())) {
                req.setItem_Code("");  // 产品编码
                req.setSpec(productFilterGradeParam.getSpec()); //产品型号
            }
            // 查询请求
            result = service.CssMaintenanceItemSearch(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("查询产品保养项信息status:[{}],message[{}]", status, message);
                if (result.getStatus() == 1) {
                    JSONArray myJsonArray = JSONArray.fromObject(result.getOne_V_Msg());
                    for (int i = 0; i < myJsonArray.size(); i++) {
                        JSONObject dataObj = myJsonArray.getJSONObject(i);
                        int filterGradeId = dataObj.getInt("filter_grade_id");//滤芯等级ID
                        String filterGrade = dataObj.getString("filter_grade"); // 滤芯等级名称
                        String gradeSeq = dataObj.getString("grade_seq"); // 保养项序号
                        String gradeName = dataObj.getString("grade_name"); // 保养项名称
                        String filterPrice = dataObj.getString("filter_price"); // 保养项指导价
                        int expectRplDays = dataObj.getInt("expect_rpl_days");// 标准更换周期(天)
                        ProductFilterGrade productFilterGrade = new ProductFilterGrade();
                        productFilterGrade.setFilterGradeId(filterGradeId);
                        productFilterGrade.setFilterGrade(filterGrade);
                        productFilterGrade.setGradeSeq(gradeSeq);
                        productFilterGrade.setGradeName(gradeName);
                        productFilterGrade.setFilterPrice(filterPrice);
                        productFilterGrade.setExpectRplDays(expectRplDays);
                        list.add(productFilterGrade);
                    }
                }
            }
        } catch (Exception e) {
            throw new ThirdException("根据产品获取保养项信息失败", "系统繁忙，请稍后再试",e);
        } finally {
            client.close();
        }
        return list;
    }

    /**
     * 用户历史受理信息查询接口
     */
    @Override
    @LoginRequired
    public List<ProductAppealVo> getCssHistoryAppInfo(String mobilePhone, int type) {
        CssWxRequest result = null;
        Client client = null;
        List<ProductAppealVo> list = Lists.newArrayList();
        try {
            LOG.info("============开始调用csm系统的用户历史受理信息查询接口================");
            CssWxService service = this.getCssWxService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            //传条件
            CssWxRequest req = new CssWxRequest();
            req.setTel(mobilePhone);
            if (type != 0) {
                req.setAppeal_Kind_Id(type);// 2:维修 3:安装 5:保养
            }
            // 查询请求
            result = service.CssEnduserAppWxSearch(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("用户历史受理信息查询接口返回信息status:[{}],message[{}]", status, message);
                // 判断是否成功
                if (status == 1) {
                    if (result.getOne_V_Msg().length() > 0) {
                        JSONArray myJsonArray = JSONArray.fromObject(result.getOne_V_Msg());
                        if (myJsonArray.size() > 0) {
                            for (int i = 0; i < myJsonArray.size(); i++) {
                                JSONObject dataObj = myJsonArray.getJSONObject(i);
                                String appealNo = dataObj.getString("appeal_no");        // 受理单号
                                String appealNumber = dataObj.has("appeal_number") ? dataObj.get("appeal_number") + "" : "";  // 工程单号(安装单号/保养单号/维修单号)
                                String serviceTime = dataObj.getString("service_time");    // 预约时间
                                String createTime = dataObj.getString("create_time");    // 受理单生成时间
                                Integer orderStatus = dataObj.getInt("stat");            // 订单状态  1:已受理  3:已派工  5:已接单  7:已拒绝  9:已完工  11:待回访  13:已结案  2:已确认  6:已退单  14:已关闭
                                String saleMarket = dataObj.getString("sale_market");    // 卖场名称
                                int appealKindId = dataObj.has("appeal_kind_id") ? dataObj.getInt("appeal_kind_id") : -1;// 受理类型  2:维修 3:安装 5:保养
                                String appealContent = dataObj.has("appeal_content") ? dataObj.getString("appeal_content") : "";// 受理内容
                                String enduserName = dataObj.has("enduser_name") ? dataObj.getString("enduser_name") : "";// 用户姓名
                                String mobile = dataObj.getString("mobile"); // 电话
                                String enduserAddress = dataObj.getString("enduser_address"); // 地址
                                String provinceName = dataObj.getString("province_name");// 省
                                String cityName = dataObj.getString("city_name");// 市
                                String countyName = dataObj.getString("county_name");// 区县
                                Integer provinceId = dataObj.has("province_id") ? dataObj.getInt("province_id") : 0; // 省Id
                                Integer cityId = dataObj.has("city_id") ? dataObj.getInt("city_id") : 0;// 市Id
                                Integer countyId = dataObj.has("city_id") ? dataObj.getInt("county_id") : 0;// 区Id
                                String qyhUserId = dataObj.getString("fix_man_id");//服务工程师id
                                // 封装成vo对象
                                ProductAppealVo productAppealVo = new ProductAppealVo();
                                productAppealVo.setAppealContent(appealContent);
                                productAppealVo.setAppealKindId(appealKindId);
                                productAppealVo.setAppealNo(appealNo);
                                productAppealVo.setAppealNumber(appealNumber);
                                productAppealVo.setServiceTime(serviceTime);
                                productAppealVo.setCreateTime(createTime);
                                productAppealVo.setProvinceId(provinceId);
                                productAppealVo.setProvinceName(provinceName);
                                productAppealVo.setCityId(cityId);
                                productAppealVo.setCityName(cityName);
                                productAppealVo.setCountyId(countyId);
                                productAppealVo.setCountyName(countyName);
                                productAppealVo.setMobile(mobile);
                                productAppealVo.setSaleMarket(saleMarket);
                                productAppealVo.setEnduserAddress(enduserAddress);
                                productAppealVo.setEnduserName(enduserName);
                                productAppealVo.setOrderStatus(orderStatus);
                                productAppealVo.setQyhUserId(qyhUserId);
                                // 产品相关
                                List<AppealProduct> appealProductList = Lists.newArrayList();
                                JSONArray jsonArry = (dataObj.getJSONArray("items"));
                                if (jsonArry.size() > 0) {
                                    for (int m = 0; m < jsonArry.size(); m++) {
                                        JSONObject jsonObj = jsonArry.getJSONObject(m);
                                        String netSaleNo = jsonObj.getString("net_sale_no");    // 购买单号
                                        String spec = jsonObj.getString("spec");                // 产品型号
                                        String item_code = jsonObj.getString("item_code");      // 产品编码
                                        String purchDate = jsonObj.getString("purch_date");    // 购买日期
                                        AppealProduct appealProduct = new AppealProduct();
                                        appealProduct.setNetSaleNo(netSaleNo);
                                        appealProduct.setSpec(spec);
                                        appealProduct.setItem_code(item_code);
                                        appealProduct.setPurchDate(purchDate);
                                        appealProductList.add(appealProduct);
                                    }
                                }
                                productAppealVo.setProducts(appealProductList);
                                list.add(productAppealVo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ThirdException("用户历史受理信息查询接口失败", "受理信息查询失败，请稍后再试",e);
        } finally {
            client.close();
        }
        return list;
    }

    @Override
    @LoginRequired
    public Result getStep(String typeName, String smallType) {
        Result rtnResult = new BaseResult();
        CssWxRequest result = null;
        Client client = null;
        List<RepairItem> list = new ArrayList<RepairItem>();
        try {
            LOG.info("============开始调用维修维修措施查询接口================");
            CssWxService service = this.getCssWxService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            //传条件
            CssWxRequest req = new CssWxRequest();
            if (StringUtils.isEmpty(typeName)) {
                req.setSmallC_Name(smallType);
            } else {
                req.setBigC_Name(typeName);
            }
            // 查询请求
            result = service.getStep(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("维修维修措施查询接口status:[{}],message[{}]", status, message);
                // 判断是否成功
                if (status == 1) {
                    if (result.getOne_V_Msg().length() > 0) {
                        JSONArray myJsonArray = JSONArray.fromObject(result.getOne_V_Msg());
                        if (myJsonArray.size() > 0) {
                            for (int i = 0; i < myJsonArray.size(); i++) {
                                JSONObject dataObj = myJsonArray.getJSONObject(i);
                                RepairItem repairItem = new RepairItem();
                                repairItem.setId(dataObj.getLong("fix_step_id"));
                                repairItem.setName(dataObj.getString("fix_step_name"));
                                repairItem.setItemCode(dataObj.getString("fix_step_code"));
                                repairItem.setRepairCode(dataObj.getString("step_type_no"));
                                repairItem.setTypeName(dataObj.getString("item_type_name"));
                                repairItem.setSmallType(dataObj.getString("smallc_name"));
                                list.add(repairItem);
                            }
                        }
                    }
                    rtnResult.setReturnCode(Constant.SUCCESS);
                    rtnResult.setData(list);
                } else {
                    rtnResult.setReturnCode(Constant.FAIL);
                    rtnResult.setReturnMsg(message);
                }
            }
        } catch (Exception e) {
            throw new ThirdException("维修措施查询接口失败", "维修措施查询失败，请稍后再试",e);
        } finally {
            client.close();
        }

        return rtnResult;
    }

    @LoginRequired
    public Result getInstallPart(String modelName) {
        Result rtnResult = new BaseResult();
        CssWxRequest result = null;
        Client client = null;
        List<InstallPart> list = new ArrayList<InstallPart>();
        try {
            LOG.info("============开始调用安装配件查询接口================");
            CssWxService service = this.getCssWxService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            //传条件
            CssWxRequest req = new CssWxRequest();
            req.setSpec(modelName);
            // 查询请求
            result = service.getInstallAccessories(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("安装配件查询接口status:[{}],message[{}]", status, message);
                // 判断是否成功
                if (status == 1) {
                    if (result.getOne_V_Msg().length() > 0) {
                        JSONArray myJsonArray = JSONArray.fromObject(result.getOne_V_Msg());
                        DecimalFormat df = new DecimalFormat("#.00");
                        if (myJsonArray.size() > 0) {
                            for (int i = 0; i < myJsonArray.size(); i++) {
                                JSONObject dataObj = myJsonArray.getJSONObject(i);
                                InstallPart installPart = new InstallPart();
                                installPart.setId(dataObj.getLong("css_item_id"));
                                installPart.setName(dataObj.getString("css_item_name"));
                                installPart.setPartCode(dataObj.getString("css_item_code"));
                                String sale_price = df.format(dataObj.getDouble("sale_price"));
                                String settle_price = df.format(dataObj.getDouble("settle_price"));
                                installPart.setSalePrice(new BigDecimal(sale_price));
                                installPart.setSettlePrice(new BigDecimal(settle_price));
                                installPart.setModelName(modelName);

                                list.add(installPart);
                            }
                        }
                    }
                    rtnResult.setReturnCode(Constant.SUCCESS);
                    rtnResult.setData(list);
                } else {
                    rtnResult.setReturnCode(Constant.FAIL);
                    rtnResult.setReturnMsg(message);
                }
            }
        } catch (Exception e) {
            throw new ThirdException("安装配件查询接口失败", "安装配件查询失败，请稍后再试",e);
        } finally {
            client.close();
        }
        return rtnResult;
    }

    @Override
    @LoginRequired
    public Result getRepairPart(String modelName) {
        Result rtnResult = new BaseResult();
        CssWxRequest result = null;
        Client client = null;
        List<RepairPart> list = new ArrayList<RepairPart>();
        try {
            LOG.info("============开始调用维修配件查询接口================");
            CssWxService service = this.getCssWxService();
            XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
            client = getClient(proxy);
            //传条件
            CssWxRequest req = new CssWxRequest();
            req.setSpec(modelName);
            // 查询请求
            result = service.getFixHeaderAccessories(req);
            if (null != result) {
                // 返回信息 getStatus 0-失败 1-成功
                Long status = result.getStatus();
                String message = result.getMessage();
                LOG.info("维修配件查询接口status:[{}],message[{}]", status, message);
                // 判断是否成功
                if (status == 1) {
                    if (result.getOne_V_Msg().length() > 0) {
                        JSONArray myJsonArray = JSONArray.fromObject(result.getOne_V_Msg());
                        DecimalFormat df = new DecimalFormat("#.00");
                        if (myJsonArray.size() > 0) {
                            for (int i = 0; i < myJsonArray.size(); i++) {
                                JSONObject dataObj = myJsonArray.getJSONObject(i);
                                RepairPart repairPart = new RepairPart();
                                repairPart.setId(dataObj.getLong("css_item_id"));
                                repairPart.setName(dataObj.getString("css_item_name"));
                                repairPart.setPartCode(dataObj.getString("css_item_code"));
                                String sale_price = df.format(dataObj.getDouble("sale_price"));
                                String settle_price = df.format(dataObj.getDouble("settle_price"));
                                repairPart.setSalePrice(new BigDecimal(sale_price));
                                repairPart.setSettlePrice(new BigDecimal(settle_price));
                                repairPart.setModelName(modelName);
                                list.add(repairPart);
                            }
                        }
                    }
                    rtnResult.setReturnCode(Constant.SUCCESS);
                    rtnResult.setData(list);
                } else {
                    rtnResult.setReturnCode(Constant.FAIL);
                    rtnResult.setReturnMsg(message);
                }
            }
        } catch (Exception e) {
            throw new ThirdException("调用维修配件查询接口失败", "维修配件查询失败，请稍后再试",e);
        } finally {
            client.close();
        }
        return rtnResult;
    }


    @Override
    @LoginRequired
    public Result securityQuery(String barcode, String userMsg, String area, String ciphertext) {
        Result result = new BaseResult();
        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
        try {
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(url);
            call.setOperationName(new QName(soapaction, "getProItem")); //设置要调用哪个方法
            call.addParameter(new QName(soapaction, "barcode"), //设置要传递的参数
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
            call.addParameter(new QName(soapaction, "userMsg"), //设置要传递的参数
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
            call.addParameter(new QName(soapaction, "area"), //设置要传递的参数
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
            call.addParameter(new QName(soapaction, "ciphertext"), //设置要传递的参数
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);

            call.setReturnType(new QName(soapaction, "getProItem"), String.class); //要返回的数据类型（自定义类型）

//             call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//（标准的类型）

            call.setUseSOAPAction(true);
            call.setSOAPActionURI(soapaction + "getProItem");

            LOG.info("调用防伪码查询接口:barcode[{}]", barcode);
            String str = (String) call.invoke(new Object[]{barcode, userMsg, area, ciphertext});//调用方法并传递参数
            LOG.info("防伪码查询结果:[{}]", str);
            JSONArray jsonArray = JSONArray.fromObject(str);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            //如果Flag为0，则查询成功
            if ("0".equals(jsonObject.get("Flag").toString())) {
                String prodDate = jsonObject.get("ProdDate").toString();
                String prodID = jsonObject.get("ProdID").toString();
                String prodName = jsonObject.get("ProdName").toString();
                String spec = jsonObject.get("Spec").toString();
                String deviceName = jsonObject.get("DeviceName").toString();
                String factoryName = jsonObject.get("FactoryName").toString();
                String teamName = jsonObject.get("TeamName").toString();
                String lotNo = jsonObject.get("LotNo").toString();
                String queryCount = jsonObject.get("QueryCount").toString();
                String barCode = jsonObject.get("BarCode").toString();
                String glCode = jsonObject.get("GLCode").toString();
                SecurityVo securityVo = new SecurityVo();
                securityVo.setProdDate(prodDate);
                securityVo.setProdID(prodID);
                securityVo.setProdName(prodName);
                securityVo.setSpec(spec);
                securityVo.setDeviceName(deviceName);
                securityVo.setFactoryName(factoryName);
                securityVo.setTeamName(teamName);
                securityVo.setLotNo(lotNo);
                securityVo.setQueryCount(queryCount);
                securityVo.setBarCode(barCode);
                securityVo.setGlCode(glCode);
                result.setReturnCode(Constant.SUCCESS);
                result.setData(securityVo);
            } else {
                String message = jsonObject.get("MSG").toString();
                result.setReturnMsg(message);
                result.setReturnCode(Constant.FAIL);
            }

        } catch (Exception ex) {
            throw new ThirdException("调用防伪码查询接口失败", "系统繁忙，请稍后再试",ex);
        }
        return result;
    }

    @Override
    public Result getCemAssetsInfo(String phone) {
        Result result = new BaseResult();
        result.setReturnCode(Constant.FAIL);
        String baseUrl = cemAssetsUrl;
        String aid = cemAid;
        String secretKey = cemSecretKey;

        long ts = new Date().getTime() / 1000;

        String sign = MD5.toMD5("aid" + aid +"mobile"+phone+ "ts" + ts +secretKey);

        Map<String,String> params=new HashMap<String, String>();
        params.put("aid",aid);
        params.put("mobile",phone);
        params.put("ts",String.valueOf(ts));
        params.put("sign",sign);
        String content = null;
        try {
            content = cemPost(baseUrl, params);
        } catch (IOException e) {
            throw new ThirdException("com.ziwow.scrmapp.common.service.impl.ThirdPartyServiceImpl.getCemAssetsInfo 方法调用失败", "系统繁忙，请稍后再试",e);
        }
        CemResp<CemAssertInfo> cemResp = JSON.parseObject(content, new TypeReference<CemResp<CemAssertInfo>>(){});
        if (cemResp!=null){
            if (CemResp.STATUS_SUCCESS.equals(cemResp.getStatus().getCode())){
                result.setData(cemResp.getData().getBasicInfo().getBuy_devices());
                result.setReturnCode(Constant.SUCCESS);
            }else {
                result.setReturnMsg(cemResp.getStatus().getMessage());
                result.setReturnCode(Constant.FAIL);
            }
        }
        return result;
    }

    @Override
    public Result getCemProductInfo(String productCode) {
        Result result = new BaseResult();
        result.setReturnCode(Constant.FAIL);

        String baseUrl = cemProductInfoUrl;
        String aid = cemAid;
        String secretKey = cemSecretKey;

        long ts = new Date().getTime() / 1000;

        String sign = MD5.toMD5("aid" + aid +"procode"+productCode+ "ts" + ts +secretKey);

        String url=baseUrl+"?aid="+aid+"&procode="+productCode+"&ts="+ts+"&sign="+sign;
        String content = null;
        try {
            content = cemGet(url);
        } catch (IOException e) {
            throw new ThirdException("com.ziwow.scrmapp.common.service.impl.ThirdPartyServiceImpl.getCemProductInfo 方法调用失败", "系统繁忙，请稍后再试",e);
        }
        CemResp<CemProductInfo> cemResp = JSON.parseObject(content, new TypeReference<CemResp<CemProductInfo>>(){});
        if (cemResp!=null){
            if (CemResp.STATUS_SUCCESS.equals(cemResp.getStatus().getCode())){
                result.setData(cemResp.getData());
                result.setReturnCode(Constant.SUCCESS);
            }else {
                result.setReturnMsg(cemResp.getStatus().getMessage());
                result.setReturnCode(Constant.FAIL);
            }
        }
        return result;
    }

    private String cemGet(String url) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);

        httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
        LOG.info("调用cem接口：http get:[{}]", url);
        final HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        LOG.info("调用cem接口返回值：[{}]", result);
        return result;
    }

    private String cemPost(String url, Map<String, String> params) throws IOException {
        JSONObject jsonBody =JSONObject.fromObject(params);

        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        HttpEntity reqEntity = new StringEntity(jsonBody.toString(),"UTF-8");
        httpPost.setEntity(reqEntity);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        LOG.info("调用cem接口：http post:[{}]", url);
        final HttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        LOG.info("调用cem接口返回值：[{}]", result);
        return result;
    }

    private static String url = "http://122.227.252.12:802/QYFWService0617/QYWebService.asmx";//提供接口的地址
    private static String soapaction = "http://tempuri.org/";   //域名，这是在server定义的
}