/*
 * {EasyGank}  Copyright (C) {2015}  {CaMnter}
 *
 * This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 * You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <http://www.gnu.org/licenses/>.
 *
 * The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <http://www.gnu.org/philosophy/why-not-lgpl.html>.
 */

package com.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

import com.common.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


@SuppressLint("SimpleDateFormat")
/**
 * Description：DateUtils
 * Created by：zjl
 * Time：2015-09-24 15:23
 */
public class DateUtils {

    // yyyy-MM-dd hh:mm:ss 12小时制
    // yyyy-MM-dd HH:mm:ss 24小时制
    public static final String TYPE_01 = "yyyy-MM-dd HH:mm:ss";
    public static final String TYPE_02 = "yyyy-MM-dd";
    public static final String TYPE_03 = "HH:mm:ss";
    public static final String TYPE_04 = "yyyy年MM月dd日";

    public static String formatDate(long time, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return new SimpleDateFormat(format).format(cal.getTime());
    }

    public static String formatDate(String longStr, String format) {
        try {
            return formatDate(Long.parseLong(longStr), format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long formatStr(String timeStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(timeStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 判断是否为闰年
     */
    public static boolean isLeapYear(int year) {
        if (year % 100 == 0 && year % 400 == 0) {
            return true;
        } else if (year % 100 != 0 && year % 4 == 0) {
            return true;
        }
        return false;
    }

    /**
     * 得到某月有多少天数
     */
    public static int getDaysOfMonth(int year, int month) {
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isLeapYear(year))
                    return 29;
                else
                    return 28;
            default:
                return 31;
        }
    }

    /**
     * 获取某年中的某月的第几天是星期几
     */
    public static int getWeekdayOfMonth(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取某年中的某月的第几天是星期几
     */
    public static String getWeekOfMonth(int year, int month, int day) {
        int week = getWeekdayOfMonth(year, month, day);
        return getWeekDayStr(week);
    }

    /**
     * 获取现在是上午还是下午
     *
     * @return 0是上午，1是下午
     */
    public static int getAmOrPm() {
        return Calendar.getInstance().get(Calendar.AM_PM);
    }

    public static int[] getCurrentYearAndMonth() {
        return convertDateLongToDateIntArray(Calendar.getInstance().getTimeInMillis() / 1000);
    }

    public static long getCurrentSeconds() {
        return Calendar.getInstance().getTimeInMillis() / 1000;
    }

    public static long getCurrentMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String formatZero(int a) {
        if (a == 0)
            return "00";
        if (a < 10)
            return "0" + a;
        return a + "";
    }

    public static String getWeekDayStr(int weekday) {
        if (Calendar.MONDAY == weekday) {
            return "周一";
        } else if (Calendar.TUESDAY == weekday) {
            return "周二";
        } else if (Calendar.WEDNESDAY == weekday) {
            return "周三";
        } else if (Calendar.THURSDAY == weekday) {
            return "周四";
        } else if (Calendar.FRIDAY == weekday) {
            return "周五";
        } else if (Calendar.SATURDAY == weekday) {
            return "周六";
        } else if (Calendar.SUNDAY == weekday) {
            return "周日";
        } else {
            return "Unknown";
        }
    }

    public static String getAmPmStr(int ampm) {
        if (ampm == 0) {
            return "上午";
        }
        return "下午";
    }

    public static String getTimeView(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time * 1000));
        int mouth = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(calendar.DAY_OF_MONTH);
        int weekday = calendar.get(calendar.DAY_OF_WEEK);
        int ampm = calendar.get(calendar.AM_PM);
        int hour = calendar.get(calendar.HOUR);
        int minute = calendar.get(calendar.MINUTE);
        return mouth + "月" + day + "日 " + getWeekDayStr(weekday) + " " + getAmPmStr(ampm) + " " + formatZero(hour) + ":" + formatZero(minute);
    }

    /**
     * 将long型的秒数转换为int[]的年、月、日、时、分、秒数组
     */
    public static int[] convertDateLongToDateIntArray(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String str = sdf.format(new Date(time * 1000));
        int[] result = new int[6];
        result[0] = Integer.parseInt(str.split("-")[0]);
        result[1] = Integer.parseInt(str.split("-")[1]);
        result[2] = Integer.parseInt(str.split("-")[2]);
        result[3] = Integer.parseInt(str.split("-")[3]);
        result[4] = Integer.parseInt(str.split("-")[4]);
        result[5] = Integer.parseInt(str.split("-")[5]);
        return result;
    }

    /**
     * 将String类型的日期转换为long型的秒数
     */
    public static long convertDateStringToDateLong(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        try {
            Date date = sdf.parse(dateString);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param time
     * @return 6月25日 周四  下午 16:00
     */
    public static String getSpecificDateFormat(long time) {
        Date currentDate = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        GregorianCalendar ca = new GregorianCalendar();
        ca.setTime(currentDate);
        StringBuffer stringBuffer = new StringBuffer("");
        SimpleDateFormat formatMonth = new SimpleDateFormat("M月dd日 ");
        SimpleDateFormat formatDay = new SimpleDateFormat("HH:mm");
        int weekday = Integer.valueOf(calendar.get(calendar.DAY_OF_WEEK));
        stringBuffer.append(formatMonth.format(currentDate));
        stringBuffer.append(getWeekDayStr(weekday));
        stringBuffer.append(ca.get(GregorianCalendar.AM_PM) == 0 ? " 上午 " : " 下午 ");
        stringBuffer.append(formatDay.format(currentDate));
        return stringBuffer.toString();
    }

    /**
     * @param context
     * @param time 毫秒
     * @return 获得时间字符串
     */
    public static String getDateStr(Context context, long time) {
        Resources res = context.getResources();
        StringBuffer buffer = new StringBuffer();
        Calendar createCal = Calendar.getInstance();

        createCal.setTimeInMillis(time);
        Calendar currentcal = Calendar.getInstance();
        currentcal.setTimeInMillis(System.currentTimeMillis());

        long diffTime = (currentcal.getTimeInMillis() - createCal.getTimeInMillis()) / 1000;

        // 同一月
        if (currentcal.get(Calendar.MONTH) == createCal.get(Calendar.MONTH)) {
            // 同一天
            if (currentcal.get(Calendar.DAY_OF_MONTH) == createCal.get(Calendar.DAY_OF_MONTH)) {
                if (diffTime < 3600 && diffTime >= 60) {
                    buffer.append((diffTime / 60) + res.getString(R.string.msg_few_minutes_ago));
                } else if (diffTime < 60) {
                    buffer.append(res.getString(R.string.msg_now));
                } else {
                    buffer.append(res.getString(R.string.msg_today)).append(" ").append(formatDate(createCal.getTimeInMillis(), "HH:mm"));
                }
            }
            // 前一天
            else if (currentcal.get(Calendar.DAY_OF_MONTH) - createCal.get(Calendar.DAY_OF_MONTH) == 1) {
                buffer.append(res.getString(R.string.msg_yesterday)).append(" ").append(formatDate(createCal.getTimeInMillis(), "HH:mm"));
            }
        }

        if (buffer.length() == 0) {
            buffer.append(formatDate(createCal.getTimeInMillis(), "MM-dd HH:mm"));
        }

        String timeStr = buffer.toString();
        if (currentcal.get(Calendar.YEAR) != createCal.get(Calendar.YEAR)) {
            timeStr = createCal.get(Calendar.YEAR) + " " + timeStr;
        }
        return timeStr;
    }


}