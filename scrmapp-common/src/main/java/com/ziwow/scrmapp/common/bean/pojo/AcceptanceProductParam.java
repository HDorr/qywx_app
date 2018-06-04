package com.ziwow.scrmapp.common.bean.pojo;

import java.io.Serializable;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2017-12-22 11:21
 */
public class AcceptanceProductParam implements Serializable {
    private static final long serialVersionUID = 5684916459604953577L;
    private String item_kind;   		//产品组织   	1.净水机/2.饮水机/3.净水宝  不能为空
    private String item_code;           //产品编码
    private String from_channel;		//销售类型 	1:家用,2:商用,3:电商,4:战略部
    private String sale_market;			//卖场名称
    private String net_sale_no;			//购买单号
    private String bigc_name; 			//产品类别
    private String spec;				//产品型号
    private String barcode;				//产品条码
    private String purch_date;			//购买时间

    public String getItem_kind() {
        return item_kind;
    }

    public void setItem_kind(String item_kind) {
        this.item_kind = item_kind;
    }

    public String getFrom_channel() {
        return from_channel;
    }

    public void setFrom_channel(String from_channel) {
        this.from_channel = from_channel;
    }

    public String getSale_market() {
        return sale_market;
    }

    public void setSale_market(String sale_market) {
        this.sale_market = sale_market;
    }

    public String getNet_sale_no() {
        return net_sale_no;
    }

    public void setNet_sale_no(String net_sale_no) {
        this.net_sale_no = net_sale_no;
    }

    public String getBigc_name() {
        return bigc_name;
    }

    public void setBigc_name(String bigc_name) {
        this.bigc_name = bigc_name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPurch_date() {
        return purch_date;
    }

    public void setPurch_date(String purch_date) {
        this.purch_date = purch_date;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }
}
