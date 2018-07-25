package com.cetiti.tlgm.service.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期日历工具（中国法定节假日，周末加班情况）
 * <p>
 * 注：中国每年会发布本年的节假日计划，然后需要根据此定期更新
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-07-25 15:31:41
 */
public class ChineseCalendarUtil {

    /**
     * 2018年法定节假日
     */
    private static final List<String> lawHolidays2018 = Arrays.asList("2017-12-30", "2017-12-31",
            "2018-01-01", "2018-02-15", "2018-02-16", "2018-02-17", "2018-02-18",
            "2018-02-19", "2018-02-20", "2018-02-21", "2018-04-05", "2018-04-06",
            "2018-04-07", "2018-04-29", "2018-04-30", "2018-05-01", "2018-06-16",
            "2018-06-17", "2018-06-18", "2018-09-22", "2018-09-23", "2018-09-24",
            "2018-10-01", "2018-10-02", "2018-10-03", "2018-10-04", "2018-10-05",
            "2018-10-06", "2018-10-07");

    /**
     * 2018年额外补节假日周末
     */
    private static final List<String> extraWorkdays2018 = Arrays.asList("2018-02-11", "2018-02-24",
            "2018-04-08", "2018-04-28", "2018-09-29", "2018-09-30");

    private static final List<String> lawHolidays = new ArrayList<>(lawHolidays2018);
    private static final List<String> extraWorkdays = new ArrayList<>(extraWorkdays2018);

    /**
     * 判断是否为法定节假日
     *
     * @param calendar
     * @return
     * @throws Exception
     */
    public static boolean isLawHoliday(String calendar) throws Exception {
        isMatchDateFormat(calendar);
        if (lawHolidays.contains(calendar)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为周末
     *
     * @param calendar
     * @return
     * @throws Exception
     */
    public static boolean isWeekends(String calendar) throws Exception {
        isMatchDateFormat(calendar);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(calendar);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为额外补班的工作日
     *
     * @param calendar
     * @return
     * @throws Exception
     */
    public static boolean isExtraWorkday(String calendar) throws Exception {
        isMatchDateFormat(calendar);
        if (extraWorkdays.contains(calendar)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是工作日
     *
     * @param calendar
     * @return
     * @throws Exception
     */
    public static boolean isWorkday(String calendar) throws Exception {
        isMatchDateFormat(calendar);

        if (isLawHoliday(calendar)) {
            return false;
        }
        if (isExtraWorkday(calendar)) {
            return true;
        }
        if (isWeekends(calendar)) {
            return false;
        }

        return true;
    }

    private final static Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    /**
     * 正则表达式判断是否是合理的日期格式，若不是则抛出异常
     *
     * @param calendar 日期yyyy-MM-dd
     * @throws Exception
     */
    private static void isMatchDateFormat(String calendar) throws Exception {
        Matcher matcher = pattern.matcher(calendar);

        boolean flag = matcher.matches();
        if (!flag) {
            throw new Exception("日期格式不正确，请重试(yyyy-MM-dd)");
        }
    }
}
