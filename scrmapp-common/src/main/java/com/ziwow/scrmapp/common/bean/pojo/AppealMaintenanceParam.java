package com.ziwow.scrmapp.common.bean.pojo;

public class AppealMaintenanceParam implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String web_appeal_no;        // 受理单号
    private int fix_man_id;            // 服务工程师ID
    private String fix_man_name;        // 服务工程师名称
    private String enduser_name;        // 用户姓名
    private String enduser_address;        // 用户地址
    private String mobile;                // 电话
    private String problem_name;        // 故障描述
    private String fix_date;            // 维修时间
    private int is_replace;             //是否更换配件
    private int fix_type_id;            //措施类型
    private String prod_info;           // 保养单对应产品信息
    private int fix_step_id;            //措施ID
    private String fix_step_code;       //措施编码
    private String fix_step_name;       //措施名称
    private String repairPartStr;       //维修配件字符串
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

    public String getProblem_name() {
        return problem_name;
    }

    public void setProblem_name(String problem_name) {
        this.problem_name = problem_name;
    }

    public String getFix_date() {
        return fix_date;
    }

    public void setFix_date(String fix_date) {
        this.fix_date = fix_date;
    }

    public int getIs_replace() {
        return is_replace;
    }

    public void setIs_replace(int is_replace) {
        this.is_replace = is_replace;
    }

    public int getFix_type_id() {
        return fix_type_id;
    }

    public void setFix_type_id(int fix_type_id) {
        this.fix_type_id = fix_type_id;
    }

    public String getProd_info() {
        return prod_info;
    }

    public void setProd_info(String prod_info) {
        this.prod_info = prod_info;
    }

    public int getFix_step_id() {
        return fix_step_id;
    }

    public void setFix_step_id(int fix_step_id) {
        this.fix_step_id = fix_step_id;
    }

    public String getFix_step_code() {
        return fix_step_code;
    }

    public void setFix_step_code(String fix_step_code) {
        this.fix_step_code = fix_step_code;
    }

    public String getFix_step_name() {
        return fix_step_name;
    }

    public void setFix_step_name(String fix_step_name) {
        this.fix_step_name = fix_step_name;
    }

    public String getRepairPartStr() {
        return repairPartStr;
    }

    public void setRepairPartStr(String repairPartStr) {
        this.repairPartStr = repairPartStr;
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
