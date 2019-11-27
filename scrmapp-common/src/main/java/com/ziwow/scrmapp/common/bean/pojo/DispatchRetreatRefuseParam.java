package com.ziwow.scrmapp.common.bean.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 *  csm退单同步参数
 * @author songkaiqi
 * @since 2019/10/09/下午2:17
 */
public class DispatchRetreatRefuseParam extends DispatchOrderParam {

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date manualDate;


    public Date getManualDate() {
        return manualDate;
    }

    public void setManualDate(Date manualDate) {
        this.manualDate = manualDate;
    }

}
