package com.ziwow.scrmapp.common.bean.pojo;

import java.io.Serializable;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2017-12-21 14:11
 */
public class DispatchMasterParam implements Serializable {
    private static final long serialVersionUID = -6820127548454707462L;
    private String timeStamp;
    private String signture;
    private String acceptNumber;
    private String engineerId;
    private String engineerName;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSignture() {
        return signture;
    }

    public void setSignture(String signture) {
        this.signture = signture;
    }

    public String getAcceptNumber() {
        return acceptNumber;
    }

    public void setAcceptNumber(String acceptNumber) {
        this.acceptNumber = acceptNumber;
    }

    public String getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(String engineerId) {
        this.engineerId = engineerId;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }
}
