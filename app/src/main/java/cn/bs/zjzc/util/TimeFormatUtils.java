package cn.bs.zjzc.util;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/7.
 */
public class TimeFormatUtils {

    /**
     * 根据Date类型转化成字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String parseDate(Date date, String format) {
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        if (date == null) {
            date = new Date();
        }
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "无";
        }
    }

    /**
     * 格式化时间戳字符串
     *
     * @param mills
     * @param format
     * @return
     */
    public static String formatTimeStr(String mills, String format) {
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        try {
            long l = new Date().getTime() - Long.parseLong(mills);
            return new SimpleDateFormat(format).format(new Date(l));
        } catch (Exception e) {
            e.printStackTrace();
            return "无";
        }
    }

    public static Date string2date(String dataStr, String format) {
        Date date = null;
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }


        if (TextUtils.isEmpty(dataStr)) {
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(dataStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
