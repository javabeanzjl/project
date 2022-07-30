package com.bjpowernode.crm.settings.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理日期的工具
 */
public class DateUtils {
    /**
     * 对指定类型的日期进行初始化
     */
    public static String formateDateTime(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm ss");
        return sf.format(date);
    }
}
