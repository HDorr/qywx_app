package com.ziwow.scrmapp.wechat.weixin;

import java.io.Serializable;

public class TemplateData implements Serializable {

    /**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 5521335583589781087L;
	private String touser;
    private String template_id;
    private String url;
    private String topcolor;
    private TemplateDataItem data = new TemplateDataItem();
    private Miniprogram miniprogram = new Miniprogram();

    private TemplateData(String touser, String template_id, String url, String topcolor) {
        this.touser = touser;
        this.template_id = template_id;
        this.url = url;
        this.topcolor = topcolor;
    }

    public TemplateData(String touser, String template_id) {
        this.touser = touser;
        this.template_id = template_id;
    }

    public TemplateData(String touser, String template_id, String url) {
        this.touser = touser;
        this.template_id = template_id;
        this.url = url;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public TemplateDataItem getData() {
        return data;
    }

    public void setData(TemplateDataItem data) {
        this.data = data;
    }

    public TemplateDataItem getTemplateDataItemInstance() {
        return getData();
    }

    public TemplateData push(String key, String value, String color) {
        this.data.addItem(key, value, color);
        return this;
    }

    public TemplateData push(String key, String value) {
        this.data.addItem(key, value);
        return this;
    }

    public Miniprogram getMiniprogram(){
        return miniprogram;
    }


}
