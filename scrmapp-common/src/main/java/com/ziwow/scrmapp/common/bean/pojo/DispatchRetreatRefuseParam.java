package com.ziwow.scrmapp.common.bean.pojo;

/**
 *  csm退单同步参数
 * @author songkaiqi
 * @since 2019/10/09/下午2:17
 */
public class DispatchRetreatRefuseParam {
    private String timeStamp;
    private String signture;

    /**
     * 受理单号
     */
    private String acceptNumber;

    /**
     * 备注
     */
    private String remarks;



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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "DispatchRetreatParam{" +
                "timeStamp='" + timeStamp + '\'' +
                ", signture='" + signture + '\'' +
                ", acceptNumber='" + acceptNumber + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
