package com.ziwow.scrmapp.qyh.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class QyhAuthCorp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 企业号ID
     */
    private String corpid;

    /**
     * 套件ID
     */
    private String suiteid;

    /**
     * 企业号名称
     */
    private String corpname;

    /**
     * 永久授权码
     */
    private String suitepermanentcode;

    /**
     * 授权企业详细信息
     */
    private String authcorpinfo;

    /**
     * 授权应用详细信息
     */
    private String authappinfo;

    /**
     * 授权管理员的信息
     */
    private String authuserinfo;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 企业号ID
     */
    public String getCorpid() {
        return corpid;
    }

    /**
     * @param corpid 
	 *            企业号ID
     */
    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    /**
     * @return 套件ID
     */
    public String getSuiteid() {
        return suiteid;
    }

    /**
     * @param suiteid 
	 *            套件ID
     */
    public void setSuiteid(String suiteid) {
        this.suiteid = suiteid;
    }

    /**
     * @return 企业号名称
     */
    public String getCorpname() {
        return corpname;
    }

    /**
     * @param corpname 
	 *            企业号名称
     */
    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    /**
     * @return 永久授权码
     */
    public String getSuitepermanentcode() {
        return suitepermanentcode;
    }

    /**
     * @param suitepermanentcode 
	 *            永久授权码
     */
    public void setSuitepermanentcode(String suitepermanentcode) {
        this.suitepermanentcode = suitepermanentcode;
    }

    /**
     * @return 授权企业详细信息
     */
    public String getAuthcorpinfo() {
        return authcorpinfo;
    }

    /**
     * @param authcorpinfo 
	 *            授权企业详细信息
     */
    public void setAuthcorpinfo(String authcorpinfo) {
        this.authcorpinfo = authcorpinfo;
    }

    /**
     * @return 授权应用详细信息
     */
    public String getAuthappinfo() {
        return authappinfo;
    }

    /**
     * @param authappinfo 
	 *            授权应用详细信息
     */
    public void setAuthappinfo(String authappinfo) {
        this.authappinfo = authappinfo;
    }

    /**
     * @return 授权管理员的信息
     */
    public String getAuthuserinfo() {
        return authuserinfo;
    }

    /**
     * @param authuserinfo 
	 *            授权管理员的信息
     */
    public void setAuthuserinfo(String authuserinfo) {
        this.authuserinfo = authuserinfo;
    }

    /**
     * @return 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime 
	 *            创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}