package com.ziwow.scrmapp.common.bean.pojo;

public class AppealInstallParam implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String web_appeal_no;
    private int fix_man_id;
    private String fix_man_name;
    private String enduser_name;
    private String mobile;
    private String install_time;
    private int is_replace;             //是否更换配件
    private String installProdStr;      // 安装单产品信息
    private String note;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInstall_time() {
        return install_time;
    }

    public void setInstall_time(String install_time) {
        this.install_time = install_time;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getIs_replace() {
        return is_replace;
    }

    public void setIs_replace(int is_replace) {
        this.is_replace = is_replace;
    }

    public String getInstallProdStr() {
        return installProdStr;
    }

    public void setInstallProdStr(String installProdStr) {
        this.installProdStr = installProdStr;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public class Product {
        private String item_code;
        private String barcode;                //产品条码
        private String barcode_image;          //条码图片

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