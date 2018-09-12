package com.ziwow.scrmapp.common.bean.vo.cem;

import java.util.List;

public class CemAssertInfo {
  /**
   * basicInfo : {"birthday":"","buy_service_cards":[],"user_name":"尹民毫（管理员测试号）","sex":"男","buy_filters":[],"buy_devices":[[{"name":"保修到期时间","value":"-"},{"name":"设备管理中心","value":"-"},{"name":"购买日期","value":"2016-11-18"},{"name":"产品大类","value":"纯水机"},{"name":"产品条码","value":"112"},{"name":"设备服务网点","value":"-"},{"name":"购买渠道","value":"-"},{"name":"产品编码","value":"104043-0001"},{"name":"安装日期","value":"-"},{"name":"产品型号","value":"QR-RO-05D-纯水机电商；"}]]}
   */

  private BasicInfoBean basicInfo;

  public BasicInfoBean getBasicInfo() {
    return basicInfo;
  }

  public void setBasicInfo(BasicInfoBean basicInfo) {
    this.basicInfo = basicInfo;
  }

  public static class BasicInfoBean {

    /**
     * birthday :
     * buy_service_cards : []
     * user_name : 尹民毫（管理员测试号）
     * sex : 男
     * buy_filters : []
     * buy_devices : [[{"name":"保修到期时间","value":"-"},
     *                  {"name":"设备管理中心","value":"-"},
     *                  {"name":"购买日期","value":"2016-11-18"},
     *                  {"name":"产品大类","value":"纯水机"},
     *                  {"name":"产品条码","value":"112"},
     *                  {"name":"设备服务网点","value":"-"},
     *                  {"name":"购买渠道","value":"-"},
     *                  {"name":"产品编码","value":"104043-0001"},
     *                  {"name":"安装日期","value":"-"},
     *                  {"name":"产品型号","value":"QR-RO-05D-纯水机电商；"}]]
     */

    private String birthday;
    private String user_name;
    private String sex;
    private List<?> buy_service_cards;
    private List<?> buy_filters;
    private List<List<BuyDevicesBean>> buy_devices;

    public String getBirthday() {
      return birthday;
    }

    public void setBirthday(String birthday) {
      this.birthday = birthday;
    }

    public String getUser_name() {
      return user_name;
    }

    public void setUser_name(String user_name) {
      this.user_name = user_name;
    }

    public String getSex() {
      return sex;
    }

    public void setSex(String sex) {
      this.sex = sex;
    }

    public List<?> getBuy_service_cards() {
      return buy_service_cards;
    }

    public void setBuy_service_cards(List<?> buy_service_cards) {
      this.buy_service_cards = buy_service_cards;
    }

    public List<?> getBuy_filters() {
      return buy_filters;
    }

    public void setBuy_filters(List<?> buy_filters) {
      this.buy_filters = buy_filters;
    }

    public List<List<BuyDevicesBean>> getBuy_devices() {
      return buy_devices;
    }

    public void setBuy_devices(List<List<BuyDevicesBean>> buy_devices) {
      this.buy_devices = buy_devices;
    }

    public static class BuyDevicesBean {

      /**
       * name : 保修到期时间
       * value : -
       */

      private String name;
      private String value;

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public String getValue() {
        return value;
      }

      public void setValue(String value) {
        this.value = value;
      }
    }
  }
}
