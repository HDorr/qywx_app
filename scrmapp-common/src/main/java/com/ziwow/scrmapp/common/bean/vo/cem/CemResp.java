package com.ziwow.scrmapp.common.bean.vo.cem;

import java.util.List;

public class CemResp<T> {

  public final static String STATUS_SUCCESS="E0";

  /**
   * data : {"basicInfo":{"birthday":"","buy_service_cards":[],"user_name":"尹民毫（管理员测试号）","sex":"男","buy_filters":[],"buy_devices":[[{"name":"保修到期时间","value":"-"},{"name":"设备管理中心","value":"-"},{"name":"购买日期","value":"2016-11-18"},{"name":"产品大类","value":"纯水机"},{"name":"产品条码","value":"112"},{"name":"设备服务网点","value":"-"},{"name":"购买渠道","value":"-"},{"name":"产品编码","value":"104043-0001"},{"name":"安装日期","value":"-"},{"name":"产品型号","value":"QR-RO-05D-纯水机电商；"}]]}}
   * status : {"code":"E0","message":"ok"}
   */

  private T data;
  private StatusBean status;

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public StatusBean getStatus() {
    return status;
  }

  public void setStatus(StatusBean status) {
    this.status = status;
  }


  public static class StatusBean {

    /**
     * code : E0
     * message : ok
     */

    private String code;
    private String message;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }
}
