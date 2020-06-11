package com.ziwow.scrmapp.common.bean.pojo;


import java.io.Serializable;

/**
 * @author xiaohei
 */
public abstract class AbstractBaseParam implements Serializable {

    private static final long serialVersionUID = 1L;
    private String timeStamp;
    private String signature;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}