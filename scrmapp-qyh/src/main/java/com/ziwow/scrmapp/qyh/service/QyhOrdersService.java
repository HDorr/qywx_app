package com.ziwow.scrmapp.qyh.service;

import java.util.List;

import com.ziwow.scrmapp.common.bean.pojo.CancelProductParam;
import com.ziwow.scrmapp.common.bean.vo.CompleteParam;
import com.ziwow.scrmapp.common.bean.vo.QyhUserOrdersVo;
import com.ziwow.scrmapp.common.bean.vo.WechatOrdersVo;
import com.ziwow.scrmapp.common.result.Result;

/**
 * Created by xiaohei on 2017/4/21.
 */
public interface QyhOrdersService {

    /**
     * 统计企业号用户工单条数
     *
     * @param qyhUserId
     * @return
     */
    QyhUserOrdersVo getOrdersCountByQyhUserId(String qyhUserId, String condition);

    /**
     * 根据当日，明日，总共条件查找工单列表
     *
     * @param qyhUserId
     * @param condition
     * @return
     */
    List<WechatOrdersVo> getOrdersListByQyhUserId(String qyhUserId, String condition);


    /**
     * 已完工工单列表
     *
     * @param qyhUserId
     * @return
     */
    List<WechatOrdersVo> getFinishedByQyhUserId(String qyhUserId);


    /**
     * 修改订单状态
     *
     * @param ordersId
     * @param ordersCode
     * @param qyhUserId
     * @param updateTime
     * @return
     */
    Result changeOrdersTime(Long ordersId, String ordersCode, String qyhUserId, String updateTime, String reason);


    /**
     * 师傅拒绝工单
     *
     * @param ordersId
     * @param qyhUserId
     * @return
     */
    Result refuseOrders(Long ordersId, String ordersCode, String qyhUserId, String reason);

    /**
     * 已完工订单详情
     *
     * @param ordersCode
     * @return
     */
    WechatOrdersVo getFinishedOrdersDetail(String ordersCode);


    /**
     * 师傅点击完工后跳转的页面数据
     *
     * @param ordersCode
     * @return
     */
    WechatOrdersVo getFinishDetail(String ordersCode);

    /**
     * 同步安装单
     *
     * @param completeParam
     * @param products
     * @return
     */
    Result syncInstallWechatOrders(CompleteParam completeParam, String products, String cancelRemark);

    /**
     * 同步维修单
     *
     * @param completeParam
     * @param is_replace
     * @param products
     * @return
     */
    Result syncRepairWechatOrders(CompleteParam completeParam, int is_replace, String products);

    /**
     * 同步保养单
     *
     * @param completeParam
     * @param is_change_filter
     * @param products
     * @return
     */
    Result syncMaintainWechatOrders(CompleteParam completeParam, int is_change_filter, String products);

    void saveInstallRecord(Long ordersId, String installParts);

    /**
     * 保存保养记录
     *
     * @param ordersId       工单id
     * @param productId      产品id
     * @param filters
     * @param maintainPrices
     */
    void saveMatainRecord(Long ordersId, Long productId, String filters, String maintainPrices);

    CompleteParam getCompleteParamByOrdersCode(String ordersCode);

    /**
     * 待处理工单详情
     *
     * @param ordersCode
     * @return
     */
    WechatOrdersVo getVoByOrdersCode(String ordersCode);

    /**
     * 修改产品条码
     *
     * @param productBarCode
     * @return
     */
    void updateBarCode(String productBarCode, String barCodeImage, Long productId);


    void updateBarCodeImage(String barCodeImage, Long productId);

    Result findRepairItem(String typeName, String smallcName, String name);

    Result findInstallPart(String modelName, String name);

    Result findRepairPart(String modelName, String name);

    Result findMaintainInfo(Long levelId, String productCode);

    public Result cancelProduct(CancelProductParam cancelProductParam) throws Exception;

    public int getProductStatus(Long orderId);

    void finishMakeAppointment(String ordersCode);

    int doCancel(Long ordersId, Long productId,String ordersCode);

    boolean isFinish(List<Integer> productStatus);

    /**
     * 判断工单是否正常完工，即状态为已完成，不包括取消
     * @param productStatus
     * @return
     */
    boolean isNormaFinish(List<Integer> productStatus);
}
