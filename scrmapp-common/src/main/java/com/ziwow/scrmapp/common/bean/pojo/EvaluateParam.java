package com.ziwow.scrmapp.common.bean.pojo;

public class EvaluateParam implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int number_type;		// 单号类型  1：安装单  2：保养单  3：维修单
	private String evaluate_note;	//评价内容
	private String number;			// 单号
	public int getNumber_type() {
		return number_type;
	}
	public void setNumber_type(int number_type) {
		this.number_type = number_type;
	}
	public String getEvaluate_note() {
		return evaluate_note;
	}
	public void setEvaluate_note(String evaluate_note) {
		this.evaluate_note = evaluate_note;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}