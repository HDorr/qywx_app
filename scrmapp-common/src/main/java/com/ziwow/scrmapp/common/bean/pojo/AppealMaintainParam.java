package com.ziwow.scrmapp.common.bean.pojo;

public class AppealMaintainParam implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String web_appeal_no;        // 受理单号编码
    private int fix_man_id;                // 服务工程师ID
    private String fix_man_name;        // 服务工程师名称
    private String enduser_name;        // 用户姓名
    private String enduser_address;        // 用户地址
    private String mobile;                // 电话
    private int is_change_filter;        // 是否更换滤芯 1是 2否
    private String service_time;        // 保养时间
    private String prod_info;           // 保养单对应产品信息
    private String filterStr;           //滤芯更换字符串
    private String maintainStr;         //保养字符串
    private int is_finish;

    public String getWeb_appeal_no() {
        return web_appeal_no;
    }

    public void setWeb_appeal_no(String web_appeal_no) {
        this.web_appeal_no = web_appeal_no;
    }

    public int getFix_man_id() {
        return fix_man_id;
    }

    public void setFix_man_id(int fix_man_id) {
        this.fix_man_id = fix_man_id;
    }

    public String getFix_man_name() {
        return fix_man_name;
    }

    public void setFix_man_name(String fix_man_name) {
        this.fix_man_name = fix_man_name;
    }

    public String getEnduser_name() {
        return enduser_name;
    }

    public void setEnduser_name(String enduser_name) {
        this.enduser_name = enduser_name;
    }

    public String getEnduser_address() {
        return enduser_address;
    }

    public void setEnduser_address(String enduser_address) {
        this.enduser_address = enduser_address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIs_change_filter() {
        return is_change_filter;
    }

    public void setIs_change_filter(int is_change_filter) {
        this.is_change_filter = is_change_filter;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getProd_info() {
        return prod_info;
    }

    public void setProd_info(String prod_info) {
        this.prod_info = prod_info;
    }

    public String getFilterStr() {
        return filterStr;
    }

    public void setFilterStr(String filterStr) {
        this.filterStr = filterStr;
    }

    public String getMaintainStr() {
        return maintainStr;
    }

    public void setMaintainStr(String maintainStr) {
        this.maintainStr = maintainStr;
    }

    public int getIs_finish() {
        return is_finish;
    }

    public void setIs_finish(int is_finish) {
        this.is_finish = is_finish;
    }

    public class Product {
        private String item_code;            // 产品编码
        private String barcode;                //产品条码
        private String barcode_image;                //条码图片

        public String getItem_code() {
            return item_code;
        }

        public void setItem_code(String item_code) {
            this.item_code = item_code;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getBarcode_image() {
            return barcode_image;
        }

        public void setBarcode_image(String barcode_image) {
            this.barcode_image = barcode_image;
        }
    }
}
