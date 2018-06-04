package com.ziwow.scrmapp.qyh.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class QyhSuite implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 应用套件ID
     */
    private String suiteid;

    /**
     * 套件名称
     */
    private String suitename;

    /**
     * 应用套件Secret
     */
    private String suitesecret;

    /**
     * suite_ticket协议
     */
    private String suiteticket;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * @return 应用套件ID
     */
    public String getSuiteid() {
        return suiteid;
    }

    /**
     * @param suiteid 
	 *            应用套件ID
     */
    public void setSuiteid(String suiteid) {
        this.suiteid = suiteid;
    }

    /**
     * @return 套件名称
     */
    public String getSuitename() {
        return suitename;
    }

    /**
     * @param suitename 
	 *            套件名称
     */
    public void setSuitename(String suitename) {
        this.suitename = suitename;
    }

    /**
     * @return 应用套件Secret
     */
    public String getSuitesecret() {
        return suitesecret;
    }

    /**
     * @param suitesecret 
	 *            应用套件Secret
     */
    public void setSuitesecret(String suitesecret) {
        this.suitesecret = suitesecret;
    }

    /**
     * @return suite_ticket协议
     */
    public String getSuiteticket() {
        return suiteticket;
    }

    /**
     * @param suiteticket 
	 *            suite_ticket协议
     */
    public void setSuiteticket(String suiteticket) {
        this.suiteticket = suiteticket;
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