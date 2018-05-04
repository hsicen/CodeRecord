package com.alamkanak.weekview;

import java.util.Calendar;

/**
 * <p>作者：黄思程  2018/5/3 15:25
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：日期格式化接口
 */
public interface DateTimeInterpreter {

    /*** 日期格式化接口
     ***  @param date 日期
     ***  @return 格式化的日期 */
    String interpretDate(Calendar date);

    /*** 时间格式化接口
     ***  @param hour 时间
     ***  @return 格式化的时间 */
    String interpretTime(int hour);
}
