package com.qf.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author zhangjun
 * @Date 2020/1/3
 */
public class DateUtil {

    /**
     * 获取当前实现的下n个小时的Date对象（整点）
     * @param n
     * @return
     */
    public static Date getNextNDate(int n){
        //获取当前的日历对象
        Calendar calendar=Calendar.getInstance();
        //操作当前时间，设置为当前时间下n个小时的整点时间
        calendar.add(Calendar.HOUR_OF_DAY,n);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //从日历对象中获取date数据
        Date time = calendar.getTime();
        return time;
    }
}
