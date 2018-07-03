package com.ziwow.scrmapp.wechat.service;

import java.sql.SQLDataException;
import java.util.Date;
import java.util.List;

import com.ziwow.scrmapp.common.bean.pojo.DispatchDotParam;
import com.ziwow.scrmapp.common.bean.pojo.DispatchMasterParam;
import com.ziwow.scrmapp.common.bean.pojo.DispatchOrderParam;
import com.ziwow.scrmapp.common.bean.pojo.WechatOrdersParam;
import com.ziwow.scrmapp.common.bean.vo.*;
import com.ziwow.scrmapp.common.exception.ParamException;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrders;
import com.ziwow.scrmapp.common.result.Result;

/**
 * Created by xiaohei on 2017/4/10.
 */
public interface WechatOrdersService {
    WechatOrders getWechatOrdersByCode(String ordersCode);

    WechatOrderVo getWechatOrdersVoByCode(String ordersCode);


    /**
     * 沁园取消接口
     *
     * @param ordersCode
     * @return
     */
    Result cancelOrders(String ordersCode);
    int updateOrdersStatus(String ordersCode, String userId, Date updateTime, int status);
    Result geneatorCode(WechatOrdersParam wechatOrdersParam);
    WechatOrders saveOrders(WechatOrders wechatOrders);

    //根据productId获取产品信息(批量)
    List<ProductVo> getProductInfoById(String productIds);

    //新增一单多产品接口
    WechatOrders saveOrdersMultiProduct(WechatOrders wechatOrders, String productIds);

    List<WechatOrdersVo> findByUserId(String userId);
    WechatOrdersVo getVoByOrdersCode(String ordersCode);
    void syncHistoryAppInfo(String mobilePhone, String userId);
    WechatOrdersParam getParamByOrdersCode(String ordersCode);
    Result updateOrdersTime(String ordersCode, Date date, String ordersTime);
    QyhUserVo getQyhUserInfo(String ordersCode);
    List<WechatOrderMsgVo> getWechatOrderMsgVo(String mobilePhone);
    void sendWechatOrderTemplateMsg(WechatOrderMsgVo wechatOrderMsgVo);
    void sendAppointmentTemplateMsg(String ordersCode, String serverType);
    void sendOrderCancelTemplateMsg(String userId, String serverType);
    void sendOrderFinishTemplateMsg(String ordersCode, String userId, String orderTime);
    void sendDispatchMasterTemplateMsg(WechatOrders wechatOrders, String engineerName, String mobilePhone);
    //void sendReOrderTimeTemplateMsg(WechatOrders wechatOrders);
    void sendEngineerMsgText(String engineerId, String engineerPhone, WechatOrders wechatOrders);
    int dispatch(String ordersCode, String qyhUserId, Date updateTime, int status);
    void changeAppointmentTemplate(String userId, String orderType, String ordersCode, String orderTime, String qyhUserName, String qyhUserPhone);
    void dispatchDot(DispatchDotParam dispatchDotParam) throws SQLDataException, ParamException;
    void dispatchMaster(DispatchMasterParam dispatchMasterParam) throws Exception;
    void dispatchCompleteOrder(DispatchOrderParam dispatchOrderParam) throws Exception;
    int getDispatchOrderNumByDate(String orderDate);

    void testSendSms(Integer ordersType, String mobile);

  void syncMakeAppointment(String scOrderItemId, String ordersCode, String serviceFeeIds);

    void updateMakeAppointment(String oldOrderCode, String newOrdersCode);

    void cancelMakeAppointment(String ordersCode);
}
