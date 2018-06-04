package com.ziwow.scrmapp.common.log;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

/**
 * 描述: 自定义Appender类
 *
 * @Author: John
 * @Create: 2017-12-15 14:34
 */
public class MyDailyRollingFileAppender extends DailyRollingFileAppender {

    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        return this.getThreshold().equals(priority);
    }
}
