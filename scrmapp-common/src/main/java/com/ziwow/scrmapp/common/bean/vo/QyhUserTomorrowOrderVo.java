package com.ziwow.scrmapp.common.bean.vo;

public class QyhUserTomorrowOrderVo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String qyhUserId;   // 服务工程师id
	private int installNum;		// 安装数
	private int repairNum;		// 维修数
	private int maintainNum;    // 保养数
	private int totalNum;		// 总工单数
	public String getQyhUserId() {
		return qyhUserId;
	}
	public void setQyhUserId(String qyhUserId) {
		this.qyhUserId = qyhUserId;
	}
	public int getInstallNum() {
		return installNum;
	}
	public void setInstallNum(int installNum) {
		this.installNum = installNum;
	}
	public int getRepairNum() {
		return repairNum;
	}
	public void setRepairNum(int repairNum) {
		this.repairNum = repairNum;
	}
	public int getMaintainNum() {
		return maintainNum;
	}
	public void setMaintainNum(int maintainNum) {
		this.maintainNum = maintainNum;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
}
