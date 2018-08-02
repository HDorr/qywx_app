package com.ziwow.scrmapp.wechat.controller;

import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrders;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.wechat.service.WechatQyhOrderService;
import com.ziwow.scrmapp.wechat.vo.WechatQyhOrderVo;
import com.ziwow.scrmapp.wechat.vo.WechatQyhProductVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: yiyongchang
 * @Date: 18-7-30 上午9:30
 * @Description: 企业号预约单接口
 */
@Controller
@RequestMapping(value = "/scrmapp/consumer")
public class WechatQyhOrderController {
    private final Logger logger = LoggerFactory.getLogger(WechatQyhOrderController.class);

    @Autowired
    private WechatQyhOrderService wechatQyhOrderService;

    @RequestMapping(value = "/qyh/order/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addWechatQyhOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody WechatQyhOrderVo wechatQyhOrderVo) {
        if(wechatQyhOrderVo == null){
            logger.error("企业号门店预约单为空");
            throw new RuntimeException("企业号门店预约单为空");
        }

        if(wechatQyhOrderVo.getProductList() == null || wechatQyhOrderVo.getProductList().size() == 0){
            logger.error("企业号门店预约单产品为空");
            throw new RuntimeException("企业号门店预约单产品为空");
        }
        String userId = wechatQyhOrderService.getUserIdByPhone(wechatQyhOrderVo.getUserPhone());
        if(StringUtils.isEmpty(userId)){
            logger.error("该用户手机在服务号对应的userId不存在");
            throw new RuntimeException("该用户手机在服务号对应的userId不存在");
        }

        Date currentDate = new Date();

        WechatOrders wechatOrders = new WechatOrders();
        wechatOrders.setOrdersCode(wechatQyhOrderVo.getOrderCode());
        wechatOrders.setOrderType(wechatQyhOrderVo.getOrderType());
        wechatOrders.setOrderTime(dateTimeFormat(wechatQyhOrderVo.getOrderTime()));
        wechatOrders.setStatus(wechatQyhOrderVo.getOrderStatus());
        wechatOrders.setContacts(wechatQyhOrderVo.getUserName());
        wechatOrders.setContactsMobile(wechatQyhOrderVo.getUserPhone());
        wechatOrders.setProvince(wechatQyhOrderVo.getProvince());
        wechatOrders.setCity(wechatQyhOrderVo.getCity());
        wechatOrders.setArea(wechatQyhOrderVo.getDistrict());
        wechatOrders.setAddress(wechatQyhOrderVo.getAddress());
        wechatOrders.setUserId(userId);
        wechatOrders.setCreateTime(currentDate);
        wechatOrders.setUpdateTime(currentDate);
        wechatOrders.setDescription(wechatQyhOrderVo.getDescription());

        List<Product> productList = new ArrayList<Product>();
        for(WechatQyhProductVo vo : wechatQyhOrderVo.getProductList()){
            Product product = new Product();
            product.setProductName(vo.getProductName());
            product.setProductCode(vo.getProductCode());
            product.setModelName(vo.getModelName());
            product.setTypeName(vo.getTypeName());
            product.setItemKind(vo.getItemKind());
            product.setProductImage(vo.getProductImage());
            product.setCreateTime(currentDate);
            productList.add(product);
        }

        //将订单和产品同步到数据库
        wechatQyhOrderService.addWechatQyhOrder(wechatOrders,productList);
        logger.info("同步预约单数据成功");
        return "200";
    }

    private Date dateTimeFormat(String dateStr){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (ParseException e) {
            logger.error("日期转换异常");
            e.printStackTrace();
        }
        return null;
    }
}
