package com.ziwow.scrmapp.common.service;

import java.util.List;

import com.ziwow.scrmapp.common.bean.pojo.*;
import com.ziwow.scrmapp.common.bean.vo.csm.*;
import com.ziwow.scrmapp.common.bean.vo.mall.MallOrderVo;
import com.ziwow.scrmapp.common.result.Result;

public interface ThirdPartyService {
	// 商城系统
	public boolean registerCheck(String userName);
	public void registerMember(String mobile, String password, String openId);
	public List<MallOrderVo> queryOrders(String mobilePhone);
	public String getProductImg(String spec);

	// csm系统
	/**
	 * 根据卡号查询延保卡
	 * @param cardNo
	 * @return
	 */
	EwCardVo getEwCardListByNo(String cardNo);


	/**
	 * 注册延保卡
	 * @param CSMEwCardParam
	 */
	BaseCardVo registerEwCard(CSMEwCardParam CSMEwCardParam);

	/**
	 * 根据手机号判断是否存在安装工单
	 * @param phone
	 * @return
	 */
	boolean existInstallList(String phone);

	/**
	 * 根据产品条码查询安装单的购买时间
	 * @param productBarCode
	 * @return
	 */
	String getPurchDate(String productBarCode);

	public ProductItem getProductItem(ProductParam productParam);
	public List<ProductItem> getProductList(String itemCodes);
	public List<ProductFilterGrade> getItemFilterGrade(ProductFilterGradeParam productFilterGradeParam);
	public Result addCssAppeal(AcceptanceFormParam acceptanceFormParam);
	public Result updateCssAppeal(String webAppealNo, String serviceTime, int isUser);
	public Result cancelCssAppeal(String webAppealNo);
    public Result wxSingleBackation(String webAppealNo, String reject_season);
    public Result cssAppealInstallation(AppealInstallParam appealInstallParam);
	public Result cssAppealMaintain(AppealMaintainParam appealMaintainParam);
	public Result cssAppealMaintenance(AppealMaintenanceParam appealMaintenanceParam);
	public Result cssEvaluate(EvaluateParam evaluateParam);
	public List<ProductFilterGrade> cssMaintenanceItem(ProductFilterGradeParam productFilterGradeParam);
	public List<ProductAppealVo> getCssHistoryAppInfo(String mobilePhone, int type);
	Result getStep(String typeName,String modelName);
	Result getInstallPart(String modelName);
	Result getRepairPart(String modelName);
	Result securityQuery(String barcode, String userMsg, String area, String ciphertext);

	//cem
	Result getCemAssetsInfo(String phone);

	Result getCemProductInfo(String productCode);



}