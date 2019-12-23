package com.ziwow.scrmapp.common.persistence.mapper;

import com.ziwow.scrmapp.common.bean.pojo.WechatOrdersParam;
import com.ziwow.scrmapp.common.bean.vo.*;
import com.ziwow.scrmapp.common.pagehelper.Page;
import com.ziwow.scrmapp.common.persistence.entity.WechatOrders;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WechatOrdersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WechatOrders record);

    int insertSelective(WechatOrders record);

    WechatOrders selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WechatOrders record);

    int updateByPrimaryKey(WechatOrders record);

    WechatOrderVo getWechatOrdersVoByCode(@Param("ordersCode") String ordersCode);

    List<WechatOrdersVo> getOrdersByUserId(String userId);

    WechatOrdersVo getByOrdersCode(@Param("ordersCode") String ordersCode);

    int updateStatus(@Param("ordersCode") String ordersCode, @Param("updateTime") Date updateTime, @Param("status") int status);

    WechatOrdersParam getParamByOrdersCode(String ordersCode);

    int updateOrdersTime(@Param("ordersCode") String ordersCode, @Param("updateTime") Date updateTime, @Param("ordersTime") String ordersTime);

    int updateOrdersTimeByEngineer(@Param("ordersCode") String ordersCode, @Param("qyhUserId") String qyhUserId, @Param("updateTime") Date updateTime, @Param("ordersTime") String ordersTime, @Param("reason") String reason);

    QyhUserVo getQyhUserVoByOrdersCode(String ordersCode);

    List<WechatOrderMsgVo> getWechatOrderMsgVo(@Param("mobilePhone") String mobilePhone);

    QyhUserOrdersVo getOrdersCountByUserId(String qyhUserId);

    WechatOrdersVo getFinishedOrdersDetail(String ordersCode);

    List<WechatOrdersVo> getQyhUserOrdersVo(@Param("qyhUserId") String qyhUserId, @Param("condition") String condition);

    List<WechatOrdersVo> getFinishedByQyhUserId(String qyhUserId);

    int updateOrdersStatus(@Param("ordersId") Long ordersId, @Param("qyhUserId") String qyhUserId, @Param("updateTime") Date updateTime, @Param("reason") String reason);

    WechatOrdersVo getUserOrdersInfo(String ordersCode);

    int updateQyhUserIdAndStatus(@Param("ordersCode") String ordersCode, @Param("qyhUserId") String qyhUserId, @Param("updateTime") Date updateTime, @Param("status") int status);

    int updateOrdersNoAndStatus(@Param("ordersCode") String ordersCode, @Param("ordersNo") String ordersNo, @Param("updateTime") Date updateTime, @Param("status") int status);

    CompleteParam getCompleteParamByOrdersCode(String ordersCode);

    List<QyhUserTomorrowOrderVo> getTomorrowHandleOrder();

    WechatOrdersUserInfo getWechatOrdersUserInfo(@Param("ordersCode") String ordersCode);

    AppointmentMsgVo getAppointmentMsgVo(@Param("ordersCode") String ordersCode);

    List<WechatOrderVo> findBeforeSevenDaysOrders();

    public int getDispatchOrderNumByDate(@Param("orderDate")String orderDate);

    int updateOrderStatus(@Param("ordersId") Long ordersId, @Param("status") int status, @Param("date") Date date);

    List<WechatOrdersVo> getWechatOrdersByProductId(@Param("productId") Long productId);

    /**
     * 判断是否是原单原回的订单
     * @param orderCode
     * @return
     */
    Integer isYDYHOrder(@Param("orderCode") String orderCode);

    List<WechatOrdersVo> pageOrdersByUserId(@Param("userId") String userId, @Param("page") Page page);

    long selectCountByUserId(@Param("userId") String userId);

    /**
     * 根据code查询id
     * @param orderCode
     * @return
     */
    long getIdByCode(@Param("orderCode") String orderCode);

    Map<String,String> selectByUserIdMaintainOrders(@Param("userId") String userId);

}