package cn.bs.zjzc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mgc on 2016/7/7.
 */
public class DateTimeUtils {

    /**
     * 得到(今天，明天，后天)的日期
     *
     * @param day (现在、今天0，明天1，后天2)
     * @return
     */
    public static String getDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yestoday = sdf.format(calendar.getTime());
        return yestoday;
    }

    /**
     * 将时间戳转为日期
     *
     * @param time
     * @return
     */
    public static String getTimeToDate(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(Long.parseLong(time + "000"));
    }

    /**
     * 将日期转为时间戳
     *
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public static String getDateToTime(String dateTime) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(dateTime);
        return String.valueOf(date.getTime() / 1000);
    }
}
