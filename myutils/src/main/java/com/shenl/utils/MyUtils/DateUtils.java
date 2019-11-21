package com.shenl.utils.MyUtils;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.shenl.utils.MyCallback.TimerListener;
import com.shenl.utils.bean.TimeBean;
import com.shenl.utils.view.TimeDataView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * TODO 功能：限时倒计时
     * <p>
     * 参数说明:需要配合TimeDataView来使用
     * 作    者:   沈  亮
     * 创建时间:   2019/11/18
     */
    public static void LimitedTime(final String Millis, final TimeDataView timeDataView) {
        new CountDownTimer((Long.parseLong(Millis) - System.currentTimeMillis()) * 1000 - 1, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeBean timeBean = TimingData(System.currentTimeMillis() + "", Millis);
                timeDataView.setDay(timeBean.getDay());
                timeDataView.setHour(timeBean.getHour());
                timeDataView.setMinute(timeBean.getMinute());
                timeDataView.setSecond(timeBean.getSecond());
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    /**
     * TODO 功能：时间差倒计算器
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/14
     */
    public static TimeBean TimingData(String startTime, String endTime) {
        try {
            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;
            long ns = 1000;
            // 获得两个时间的毫秒时间差异
            long diff = Long.parseLong(endTime) - Long.parseLong(startTime);
            // 计算差多少天
            long day = diff / nd;
            // 计算差多少小时
            long hour = diff % nd / nh;
            // 计算差多少分钟
            long minute = diff % nd % nh / nm;
            // 计算差多少秒//输出结果
            long second = diff % nd % nh % nm / ns;
            TimeBean timeBean = new TimeBean();
            timeBean.setDay(day + "");
            timeBean.setHour(hour < 10 ? "0" + hour : "" + hour);
            timeBean.setMinute(minute < 10 ? "0" + minute : "" + minute);
            timeBean.setSecond(second < 10 ? "0" + second : "" + second);
            System.out.println(day + "天" + hour + "小时" + minute + "分" + second + "秒");
            return timeBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * TODO 功能：日期转毫秒
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/7
     */
    public static String DateToSecond(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
            //	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");//12小时制
            long Second = simpleDateFormat.parse(date).getTime();
            return Second+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * TODO 功能：毫秒转日期
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/7
     */
    public static String secondToDate(String Millis, String formatType) {
        long second = Long.parseLong(Millis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(second);//转换为毫秒
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        String dateString = format.format(date);
        return dateString;
    }

    /**
     * TODO 功能：毫秒转日期(默认只显示年－月－日)
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/7
     */
    public static String secondToDate(String Millis) {
        return secondToDate(Millis, "yyyy-MM-dd");
    }

    /**
     * TODO 功能：倒计时
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/17
     */
    public static CountDownTimer RunTime(final TextView tvCode, int second, final TimerListener timerListener) {
        CountDownTimer timer = new CountDownTimer(second * 1000 - 1, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCode.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                tvCode.setText("重新获取");
                timerListener.Finish();
            }
        }.start();
        return timer;
    }

    /**
     * TODO 功能：销毁倒计时器
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/17
     */
    public static void cancle(CountDownTimer timer) {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }
}
