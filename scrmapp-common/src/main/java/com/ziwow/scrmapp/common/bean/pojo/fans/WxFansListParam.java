package com.ziwow.scrmapp.common.bean.pojo.fans;


import com.ziwow.scrmapp.common.bean.pojo.AbstractBaseParam;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * {
 * "signature":"b33c05e61171e9920b2ed223a6ea0370",
 * "timeStamp":"1590641628",
 * "page":0,
 * "size":100,
 * "dataType":2,
 * "followAt":"2020-05-31"
 * }
 *
 * @author xiaohei
 */
public class WxFansListParam extends AbstractBaseParam {

    private Integer page = 0; // 当前页，从0开始
    private Integer size = 8; // 分页大小
    private String followAt; // 获取指定日期的用户增量
    private Integer dataType = 1; // 1、查询列表， 2、查询总数， 默认查询列表

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        // 防止size过大查询阻塞
        if (size > 100) {
            size = 100;
        }
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getOffset() {
        return page * size;
    }

    public String getFollowAt() {
        if (StringUtils.isBlank(followAt)) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            followAt = DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd");
        }
        return followAt;
    }

    public void setFollowAt(String followAt) {
        this.followAt = followAt;
    }

    public String getFollowStartAt() {
        return getFollowAt() + " 00:00:00";
    }

    public String getFollowEndAt() {
        return getFollowAt() + " 23:59:59";
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    //    public static void main(String[] args) {
//        DispatchWxFansParam dispatchWxFansParam = new DispatchWxFansParam();
//        System.out.println(dispatchWxFansParam.getFollowStartAt());
//        System.out.println(dispatchWxFansParam.getFollowEndAt());
//    }


    @Override
    public String toString() {
        return "DispatchWxFansParam{" +
                "timeStamp='" + getTimeStamp() + '\'' +
                ", signature='" + getSignature() + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", followAt='" + getFollowAt() + '\'' +
                '}';
    }
}