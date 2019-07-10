package com.ziwow.scrmapp.common.utils;

import com.ziwow.scrmapp.common.enums.Guarantee;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 关于延保卡的工具类
 * @author songkaiqi
 * @since 2019/06/13/上午11:19
 */
public class EwCardUtil {
    final static Calendar instance = Calendar.getInstance();

    private static String[] nums = new String[]{"零","一","二","三","四","五","六","七","八","九","十","十一"};

    /**
     * 正常延保状态= 根据 购买时间和保修天数计算保修日期
     * @param purchDate 购买时间
     * @param validTime 保修天数
     * @return
     */
    public static Date getNormalRepairTerm(Date purchDate,int validTime){
        instance.setTime(purchDate);
        instance.add(Calendar.YEAR,1);
        instance.add(Calendar.DATE,validTime);
        return instance.getTime();
    }


    /**
     * 根据购买时间算正常的延保到期时间
     * @param purchDate
     * @return
     */
    public static Date getEndNormalRepairTerm(Date purchDate){
        instance.setTime(purchDate);
        instance.add(Calendar.YEAR,1);
        return instance.getTime();
    }

    /**
     * 已过正常延保状态= 根据 购买时间和保修天数计算保修日期
     * @param validTime 保修天数
     * @return
     */
    public static Date getEndRepairTerm(Date purchDate,int validTime){
        instance.setTime(getEndNormalRepairTerm(purchDate));
        //判断时间的大小(为多久)
        if (validTime % Dates.YEAR.getDay() == 0){
            //年卡
            instance.add(Calendar.YEAR,validTime / Dates.YEAR.getDay());
        }else {
            //月卡
            instance.add(Calendar.MONTH,validTime / Dates.MONTH.getDay());
        }
        return instance.getTime();
    }

    /**
     * 已使用延保状态下延长延保期限= 根据 购买时间和保修天数计算保修日期
     * @param validTime 保修天数
     * @param guaranteeDate 最新质保的时间
     * @return
     */
    public static Date getExtendRepairTerm(Date guaranteeDate,int validTime){
        instance.setTime(guaranteeDate);
        if (validTime % Dates.YEAR.getDay() == 0){
            //年卡
            instance.add(Calendar.YEAR,validTime / Dates.YEAR.getDay());
        }else {
            //月卡
            instance.add(Calendar.MONTH,validTime / Dates.MONTH.getDay());
        }
        return instance.getTime();
    }


    /**
     *  根据购买时间和保修期限获取保修状态
     * @param purchDate 购买时间
     * @param repairTerm 保修期限
     * @return
     */
    public static Guarantee getGuarantee(Date purchDate,Date repairTerm){
        instance.setTime(purchDate);
        instance.add(Calendar.YEAR,1);
        final Date normal = instance.getTime();
        long now = System.currentTimeMillis();
        if(normal.getTime() > now || DateUtils.isSameDay(new Date(now),normal)){
            //正常质保
            return Guarantee.NORMAL_GUARANTEE;
        }else{
            if (repairTerm != null && (repairTerm.getTime() > now || DateUtils.isSameDay(new Date(now),repairTerm))){
                //延保质保中
                return Guarantee.EXTEND_GUARANTEE;
            }else {
                //延保到期
                return Guarantee.GUARANTEE_END;
            }
        }
    }


    /**
     *  根据购买时间判断该机器是否可以使用延保卡
     * @param purchDate
     * @return
     */
    public static boolean isGuantee(Date purchDate){
        instance.setTime(purchDate);
        instance.add(Calendar.YEAR,2);
        if (instance.getTime().getTime() > System.currentTimeMillis() || DateUtils.isSameDay(new Date(System.currentTimeMillis()),purchDate)){
            return true;
        }
        return false;
    }

    /**
     *  根据购买时间判断是否在正常质保的阶段
     * @param purchDate
     * @return
     */
    public static boolean isNormal(Date purchDate){
        instance.setTime(purchDate);
        instance.add(Calendar.YEAR,1);
        if (instance.getTime().getTime() > System.currentTimeMillis() || DateUtils.isSameDay(new Date(System.currentTimeMillis()),purchDate)){
            return true;
        }
        return false;
    }


    /**
     * 已过正常延保时判断是否出了延保期限
     * @param repairTerm 延保时间
     * @return
     */
    public static boolean isExtend(Date repairTerm){
        if (repairTerm.getTime() > System.currentTimeMillis() || DateUtils.isSameDay(new Date(System.currentTimeMillis()),repairTerm)){
            return true;
        }
        return false;
    }


    /**
     * 根据时间判断卡片
     * @param validTime
     * @return
     */
    public static String getEwDate(int validTime){
        if (validTime == Dates.MONTH.getDay()){
            return "月卡";
        }else if (Dates.MONTH.getDay() < validTime && validTime < Dates.YEAR.getDay()){
            if (Dates.QUARTERLY.getDay() == validTime){
                return "季度卡";
            }else {
                return nums[validTime/Dates.MONTH.getDay()]+"月卡";
            }
        }else if (validTime == Dates.YEAR.getDay()){
            return "年卡";
        }else {
            return nums[validTime/Dates.YEAR.getDay()]+"年卡";
        }
    }

    public static Date getStartTime(Date repairTerm, int validTime) {
        instance.setTime(repairTerm);
        instance.add(Calendar.YEAR,-(validTime/Dates.YEAR.getDay()));
        return instance.getTime();
    }

    enum Dates{
        /** 年卡 */
        YEAR(365),
        /** 月卡 */
        MONTH(30),
        /** 季度卡 */
        QUARTERLY(120)
        ;
        private int day;

        Dates(int day) {
            this.day = day;
        }

        public int getDay() {
            return day;
        }
    }


}

