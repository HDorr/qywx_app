package com.ziwow.scrmapp.common.bean.vo.csm;

public class ProductItem implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String itemKind;  // 净水机/饮水机/净水宝
	private String itemCode;  // 产品编码
	private String itemName;  // 产品名称
	private String bigcName;  // 产品大类
	private String spec;  	  // 产品型号
	private String smallcName;  // 产品小类
	private int filterGradeId; // 滤芯级别Id
	private String filterGrade; // 滤芯级别名称
	private String barcode;  // 产品条码
	private Integer fromChannel; // 销售类型(1:家用,2:商用,3:电商,4:战略部)
	private String purchDate;// 购买时间
	private String orderId;  // 购买单号
	private int isXs;   	 // 1:线上   2:线下
	public String getItemKind() {
		return itemKind;
	}
	public void setItemKind(String itemKind) {
		this.itemKind = itemKind;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getBigcName() {
		return bigcName;
	}
	public void setBigcName(String bigcName) {
		this.bigcName = bigcName;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getSmallcName() {
		return smallcName;
	}
	public void setSmallcName(String smallcName) {
		this.smallcName = smallcName;
	}
	public String getFilterGrade() {
		return filterGrade;
	}
	public void setFilterGrade(String filterGrade) {
		this.filterGrade = filterGrade;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public Integer getFromChannel() {
		return fromChannel;
	}
	public void setFromChannel(Integer fromChannel) {
		this.fromChannel = fromChannel;
	}
	public int getFilterGradeId() {
		return filterGradeId;
	}
	public void setFilterGradeId(int filterGradeId) {
		this.filterGradeId = filterGradeId;
	}
	public String getPurchDate() {
		return purchDate;
	}
	public void setPurchDate(String purchDate) {
		this.purchDate = purchDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getIsXs() {
		return isXs;
	}
	public void setIsXs(int isXs) {
		this.isXs = isXs;
	}
}