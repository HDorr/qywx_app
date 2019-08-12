package com.ziwow.scrmapp.common.utils;

import com.ziwow.scrmapp.common.enums.Guarantee;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
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
    @Deprecated
    public static Date getNormalRepairTerm(Date purchDate,int validTime){
        instance.setTime(getYear(purchDate,0));
        instance.add(Calendar.DATE,validTime);
        return instance.getTime();
    }


    /**
     * 根据购买时间算正常的延保到期时间
     * @param purchDate
     * @return
     */
    public static Date getEndNormalRepairTerm(Date purchDate){
        return getYear(purchDate,0);
    }

    /**
     * 根据购买时间算正常的延保起始时间
     * @param purchDate
     * @return
     */
    public static Date getNormalStartDate(Date purchDate){
        instance.setTime(purchDate);
        instance.add(Calendar.YEAR,1);
        return instance.getTime();
    }


    /**
     * 根据上次延保结束时间计算下次的延保起始时间
     * @param LastRepairTerm 上次延保结束时间
     */
    public static Date getExtStartDate(Date LastRepairTerm){
        instance.setTime(LastRepairTerm);
        instance.add(Calendar.DATE,1);
        return instance.getTime();
    }

    /**
     * 根据延保到期计算延保起始时间
     * @param repairTerm
     * @return
     */
    public static Date getMStartDate(Date repairTerm,int validTime){
        instance.setTime(repairTerm);
        instance.add(Calendar.YEAR,- (validTime / Dates.YEAR.getDay()));
        instance.add(Calendar.DATE,1);
        return instance.getTime();
    }

    /**
     * 根据 购买时间和保修天数计算保修日期（没用过延保卡的情况）
     * @param validTime 保修天数
     * @return
     */
    public static Date getEndRepairTerm(Date purchDate,int validTime){
        return getYear(purchDate,validTime / Dates.YEAR.getDay());
    }


    /**
     * 根据 上次延保截止时间计算下次延保截止时间
     * @param date
     * @param validTime
     * @return
     */
    public static Date getEwEndRepairTerm(Date date,int validTime){
        instance.setTime(date);
        instance.add(Calendar.DATE,1);
        return getYear(instance.getTime(),validTime / Dates.YEAR.getDay()-1);
    }


    /**
     * 已使用延保状态下延长延保期限= 根据 购买时间和保修天数计算保修日期
     * @param validTime 保修天数
     * @param guaranteeDate 最新质保的时间
     * @return
     */
    @Deprecated
    public static Date getExtendRepairTerm(Date guaranteeDate,int validTime){
        instance.setTime(guaranteeDate);
        if (validTime % Dates.YEAR.getDay() == 0){
            //年卡
            instance.add(Calendar.YEAR,validTime / Dates.YEAR.getDay());
        }else {
            //月卡
            instance.add(Calendar.MONTH,validTime / Dates.MONTH.getDay());
        }
        instance.add(Calendar.DATE,-1);
        return instance.getTime();
    }


    /**
     *  根据购买时间和保修期限获取保修状态
     * @param purchDate 购买时间
     * @param repairTerm 保修期限
     * @return
     */
    public static Guarantee getGuarantee(Date purchDate,Date repairTerm){
        final Date normal = getYear(purchDate,0);
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
        if (getYear(purchDate,0).getTime() > System.currentTimeMillis() || DateUtils.isSameDay(new Date(System.currentTimeMillis()),purchDate)){
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
     * 判断购买时间是否大约7天
     * @return
     */
    public static boolean gtSevenDay(Date date){
        instance.setTime(date);
        instance.add(Calendar.DATE,7);
        if (System.currentTimeMillis() < instance.getTime().getTime()){
            return false;
        }else {
            return true;
        }
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
            return "一年卡";
        }else {
            return nums[validTime/Dates.YEAR.getDay()]+"年卡";
        }
    }


    /**
     * 根据日期获取几年后的日期的前一天
     * @param date
     * @param year
     * @return
     */
    public static Date getYear(Date date,int year){
        instance.setTime(date);
        instance.add(Calendar.YEAR,1+year);
        instance.add(Calendar.DATE,-1);
        return instance.getTime();
    }


    /**
     * 判断是否可以继续使用延保卡
     * @param limitNum  限制使用次数
     * @param limitYear  使用年限
     * @param size     实际次数
     * @param ewCardYear  实际年限
     * @return
     */
    public static boolean isCanUseCard(Integer limitNum, Integer limitYear, int size, int ewCardYear) {
        if (limitNum != 0 && limitYear != 0){
            if (limitNum > size && limitYear > ewCardYear){
                return true;
            }else {
                return false;
            }
        }else if (limitNum == 0 && limitYear == 0){
            return true;
        }else {
            if (limitNum == 0){
                return limitYear > ewCardYear ? true : false;
            }else {
                return limitNum > size ? true : false;
            }
        }
    }

    /**
     * 使用过延保卡是否可以满足条件
     * @param limitNum  限制使用次数
     * @param limitYear  使用年限
     * @param size     实际次数
     * @param ewCardYear  实际年限
     * @return
     */
    public static String contentUseCard(Integer limitNum, Integer limitYear, int size, int ewCardYear) {
        if (limitNum != 0 && limitYear != 0){
            if (limitNum >= size && limitYear >= ewCardYear){
                return null;
            }else {
                return "对不起，延保卡的使用次数为".concat(limitNum.toString()).concat("次，")
                        .concat("且延保期限不能超过").concat(limitYear.toString()).concat("年");
            }
        }else if (limitNum == 0 && limitYear == 0){
            return null;
        }else {
            if (limitNum == 0){
                return limitYear >= ewCardYear ? null : "对不起，延保期限最多延长".concat(limitYear.toString()).concat("年");
            }else {
                return limitNum >= size ? null : "对不起，延保卡的使用次数最多为".concat(limitNum.toString()).concat("次");
            }
        }
    }





    public enum Dates{
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


    /**
     * 获取掩码
     * @return
     */
    public static String getMask(){
        return new StringBuffer()
                .append("16")
                .append(System.currentTimeMillis()%100001)
                .append(RandomStringUtils.randomNumeric(4))
                .toString();
    }

}

