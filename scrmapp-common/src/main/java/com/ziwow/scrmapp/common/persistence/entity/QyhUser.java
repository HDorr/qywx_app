package com.ziwow.scrmapp.common.persistence.entity;

import java.io.Serializable;

public class QyhUser extends UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 成员UserID。对应管理端的帐号
     */
    private String userid;

    /**
     * 成员名称
     */
    private String name;

    /**
     * 成员所属部门id列表
     */
    private String departments;

    /**
     * 职位信息
     */
    private String position;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 性别。0表示未定义，1表示男性，2表示女性
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 微信号
     */
    private String weixinid;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 关注状态: 1=已关注，2=已冻结，4=未关注
     */
    private Integer status;

    /**
     * 扩展属性
     */
    private String extattr;
    
    /**
     * 企业号ID
     */
    private String corpid;
    
    

    /**
	 * @return the corpid
	 */
	public String getCorpid() {
		return corpid;
	}

	/**
	 * @param corpid the corpid to set
	 */
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 成员UserID。对应管理端的帐号
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid 
	 *            成员UserID。对应管理端的帐号
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return 成员名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            成员名称
     */
    public void setName(String name) {
        this.name = name;
    }

   

    /**
	 * @return the departments
	 */
	public String getDepartments() {
		int []arrDepartment = this.getDepartment();
		StringBuffer sb = new StringBuffer();
		if(arrDepartment != null && arrDepartment.length != 0){
			for(int i:arrDepartment){
				sb.append(i+",");
			}
			if(sb !=null){
				sb.deleteCharAt(sb.length()-1);
				departments = sb.toString();
			}
		}
		return departments;
	}

	/**
	 * @param departments the departments to set
	 */
	public void setDepartments(String departments) {
		this.departments = departments;
	}

	/**
     * @return 职位信息
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position 
	 *            职位信息
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile 
	 *            手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return 性别。0表示未定义，1表示男性，2表示女性
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * @param gender 
	 *            性别。0表示未定义，1表示男性，2表示女性
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email 
	 *            邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return 微信号
     */
    public String getWeixinid() {
        return weixinid;
    }

    /**
     * @param weixinid 
	 *            微信号
     */
    public void setWeixinid(String weixinid) {
        this.weixinid = weixinid;
    }

    /**
     * @return 头像url
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar 
	 *            头像url
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return 关注状态: 1=已关注，2=已冻结，4=未关注
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            关注状态: 1=已关注，2=已冻结，4=未关注
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return 扩展属性
     */
    public String getExtattr() {
        return extattr;
    }

    /**
     * @param extattr 
	 *            扩展属性
     */
    public void setExtattr(String extattr) {
        this.extattr = extattr;
    }
}