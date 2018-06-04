package com.ziwow.scrmapp.qyh.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class QyhAuthAgent implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 企业号ID
     */
    private String appid;

    /**
     * 套件ID
     */
    private String agentid;

    /**
     * 授权企业号主键
     */
    private String authcorpid;

    private Date createtime;

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id 
	 *            ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 企业号ID
     */
    public String getAppid() {
        return appid;
    }

    /**
     * @param appid 
	 *            企业号ID
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * @return 套件ID
     */
    public String getAgentid() {
        return agentid;
    }

    /**
     * @param agentid 
	 *            套件ID
     */
    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    /**
     * @return 授权企业号主键
     */
    public String getAuthcorpid() {
        return authcorpid;
    }

    /**
     * @param authcorpid 
	 *            授权企业号主键
     */
    public void setAuthcorpid(String authcorpid) {
        this.authcorpid = authcorpid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}