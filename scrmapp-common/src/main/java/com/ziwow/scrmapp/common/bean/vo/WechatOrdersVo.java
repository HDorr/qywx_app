package com.ziwow.scrmapp.common.bean.vo;

import com.ziwow.scrmapp.common.persistence.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaohei on 2017/4/10.
 */
public class WechatOrdersVo {
    private Long id;
    private String ordersCode;
    private Integer orderType;
    private String orderTypeName;
    private int status;
    private String orderStatus;
    private String userName;
    private String userMobile;
    private String userAddress;
    private String imageUrl;
    private String orderTime;
    private String description;
    private String faultImage;
    private String endTime;
    private String maintainItem;
    private String changeFilterItem;
    private String repairItemRecord;
    private String repairPartRecord;
    private String installPartRecord;
    private String qyhUserId;
    private int btnHidden;

    private List<WechatOrdersRecordVo> wechatOrdersRecordList;
    private List<Filter> filterList;
    private List<MaintainPrice> maintainPriceList;
    private List<InstallPart> installParts;
    private List<RepairItem> repairItems;
    private List<RepairPart> repairParts;
    private List<ProductVo> products = new ArrayList<ProductVo>();

    private String maintType;
    private String buyFilter;
    private String contactsTelephone;
    private Integer confirmStatus;//按钮状态
    private String confirmContent;//按钮内容
    
    //查看完工详情时使用
    private List<ProductFinishVo> productFinish = new ArrayList<ProductFinishVo>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrdersCode() {
        return ordersCode;
    }

    public void setOrdersCode(String ordersCode) {
        this.ordersCode = ordersCode;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaultImage() {
        return faultImage;
    }

    public void setFaultImage(String faultImage) {
        this.faultImage = faultImage;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMaintainItem() {
        return maintainItem;
    }

    public void setMaintainItem(String maintainItem) {
        this.maintainItem = maintainItem;
    }

    public String getChangeFilterItem() {
        return changeFilterItem;
    }

    public void setChangeFilterItem(String changeFilterItem) {
        this.changeFilterItem = changeFilterItem;
    }

    public String getRepairItemRecord() {
        return repairItemRecord;
    }

    public void setRepairItemRecord(String repairItemRecord) {
        this.repairItemRecord = repairItemRecord;
    }

    public String getRepairPartRecord() {
        return repairPartRecord;
    }

    public void setRepairPartRecord(String repairPartRecord) {
        this.repairPartRecord = repairPartRecord;
    }

    public String getInstallPartRecord() {
        return installPartRecord;
    }

    public void setInstallPartRecord(String installPartRecord) {
        this.installPartRecord = installPartRecord;
    }

    public String getQyhUserId() {
        return qyhUserId;
    }

    public void setQyhUserId(String qyhUserId) {
        this.qyhUserId = qyhUserId;
    }

    public int getBtnHidden() {
        return btnHidden;
    }

    public void setBtnHidden(int btnHidden) {
        this.btnHidden = btnHidden;
    }

    public List<WechatOrdersRecordVo> getWechatOrdersRecordList() {
        return wechatOrdersRecordList;
    }

    public void setWechatOrdersRecordList(List<WechatOrdersRecordVo> wechatOrdersRecordList) {
        this.wechatOrdersRecordList = wechatOrdersRecordList;
    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Filter> filterList) {
        this.filterList = filterList;
    }

    public List<MaintainPrice> getMaintainPriceList() {
        return maintainPriceList;
    }

    public void setMaintainPriceList(List<MaintainPrice> maintainPriceList) {
        this.maintainPriceList = maintainPriceList;
    }

    public List<InstallPart> getInstallParts() {
        return installParts;
    }

    public void setInstallParts(List<InstallPart> installParts) {
        this.installParts = installParts;
    }

    public List<RepairItem> getRepairItems() {
        return repairItems;
    }

    public void setRepairItems(List<RepairItem> repairItems) {
        this.repairItems = repairItems;
    }

    public List<RepairPart> getRepairParts() {
        return repairParts;
    }

    public void setRepairParts(List<RepairPart> repairParts) {
        this.repairParts = repairParts;
    }

    public List<ProductVo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductVo> products) {
        this.products = products;
    }

    public List<ProductFinishVo> getProductFinish() {
        return productFinish;
    }

    public void setProductFinish(List<ProductFinishVo> productFinish) {
        this.productFinish = productFinish;
    }

	public String getMaintType() {
		return maintType;
	}

	public void setMaintType(String maintType) {
		this.maintType = maintType;
	}

	public String getBuyFilter() {
		return buyFilter;
	}

	public void setBuyFilter(String buyFilter) {
		this.buyFilter = buyFilter;
	}

	public String getContactsTelephone() {
		return contactsTelephone;
	}

	public void setContactsTelephone(String contactsTelephone) {
		this.contactsTelephone = contactsTelephone;
	}

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getConfirmContent() {
        if(this.getConfirmStatus() == null || this.getConfirmStatus().intValue() == 0 || this.getConfirmStatus().intValue() == 1){
            return "确认接单";
        }else if(this.getConfirmStatus().intValue() == 2){
            return "确认到达";
        }else if(this.getConfirmStatus().intValue() == 3){
            return "完工提交";
        }else{
            return "未知功能";
        }
    }

    public void setConfirmContent(String confirmContent) {
        this.confirmContent = confirmContent;
    }
}
