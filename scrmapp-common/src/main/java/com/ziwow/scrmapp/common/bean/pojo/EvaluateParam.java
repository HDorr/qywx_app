package com.ziwow.scrmapp.common.bean.pojo;

public class EvaluateParam implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int number_type;		// 单号类型  1：安装单  2：保养单  3：维修单
	private String evaluate_note;	//评价内容
	// 单号
	private String number;
	private Integer is_attitude;//服务态度
	private Integer is_specialty;//专业度
	private Integer is_integrity;//诚信情况
	private Integer is_recommend;//推荐意愿
	private Integer is_wxzs;//
	private Integer is_wxgz;
	private Integer is_nps_score;//nps评分

	public Integer getIs_nps_score() {
		return is_nps_score;
	}

	public void setIs_nps_score(Integer is_nps_score) {
		this.is_nps_score = is_nps_score;
	}

	// 单号
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

	public Integer getIs_attitude() {
		return is_attitude;
	}

	public void setIs_attitude(Integer is_attitude) {
		this.is_attitude = is_attitude;
	}

	public Integer getIs_specialty() {
		return is_specialty;
	}

	public void setIs_specialty(Integer is_specialty) {
		this.is_specialty = is_specialty;
	}

	public Integer getIs_integrity() {
		return is_integrity;
	}

	public void setIs_integrity(Integer is_integrity) {
		this.is_integrity = is_integrity;
	}

	public Integer getIs_recommend() {
		return is_recommend;
	}

	public void setIs_recommend(Integer is_recommend) {
		this.is_recommend = is_recommend;
	}

	public Integer getIs_wxzs() {
		return is_wxzs;
	}

	public void setIs_wxzs(Integer is_wxzs) {
		this.is_wxzs = is_wxzs;
	}

	public Integer getIs_wxgz() {
		return is_wxgz;
	}

	public void setIs_wxgz(Integer is_wxgz) {
		this.is_wxgz = is_wxgz;
	}
}