package com.ziwow.scrmapp.common.bean.pojo;

import com.ziwow.scrmapp.common.bean.vo.csm.DispatchDotProductVo;

import java.io.Serializable;
import java.util.List;

/**
 * 描述: 400派单给网点
 *
 * @Author: John
 * @Create: 2017-12-20 15:44
 */
public class DispatchOrderParam implements Serializable {
    private static final long serialVersionUID = -1825087525524580226L;
    private String timeStamp;
    private String signture;
    private String acceptNumber;
    private String finishNumber;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getFinishNumber() {
        return finishNumber;
    }

    public void setFinishNumber(String finishNumber) {
        this.finishNumber = finishNumber;
    }
}