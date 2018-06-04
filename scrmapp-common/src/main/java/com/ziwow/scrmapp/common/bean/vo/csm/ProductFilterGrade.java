package com.ziwow.scrmapp.common.bean.vo.csm;

public class ProductFilterGrade implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int filterGradeId;	   // 滤芯级别ID
	private String filterGrade;    // 滤芯级别名称
	private String gradeSeq;       // 滤芯序号
	private String gradeName;      // 滤芯名称
	private String filterPrice;    // 滤芯指导价
	private Integer expectRplDays; // 标准更换周期(天)
	public int getFilterGradeId() {
		return filterGradeId;
	}
	public void setFilterGradeId(int filterGradeId) {
		this.filterGradeId = filterGradeId;
	}
	public String getFilterGrade() {
		return filterGrade;
	}
	public void setFilterGrade(String filterGrade) {
		this.filterGrade = filterGrade;
	}
	public String getGradeSeq() {
		return gradeSeq;
	}
	public void setGradeSeq(String gradeSeq) {
		this.gradeSeq = gradeSeq;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public Integer getExpectRplDays() {
		return expectRplDays;
	}
	public void setExpectRplDays(Integer expectRplDays) {
		this.expectRplDays = expectRplDays;
	}
	public String getFilterPrice() {
		return filterPrice;
	}
	public void setFilterPrice(String filterPrice) {
		this.filterPrice = filterPrice;
	}
}