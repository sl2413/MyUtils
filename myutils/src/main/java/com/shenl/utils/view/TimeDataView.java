package com.shenl.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenl.utils.R;


public class TimeDataView extends LinearLayout {

    private TextView tv_day;
    private TextView tv_hour;
    private TextView tv_minute;
    private TextView tv_second;
    private TextView tv_text_day;
    private TextView tv_text_hour;
    private TextView tv_text_minute;
    private TextView tv_text_second;

    public TimeDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * TODO 功能：初始化控件页面
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/15
     */
    private void init(Context context, AttributeSet attrs) {
        // 加载布局
        View view = LayoutInflater.from(context).inflate(R.layout.timedata_layout, this);
        tv_day = (TextView) view.findViewById(R.id.tv_day);
        tv_text_day = (TextView) view.findViewById(R.id.tv_text_day);
        tv_hour = (TextView) view.findViewById(R.id.tv_hour);
        tv_text_hour = (TextView) view.findViewById(R.id.tv_text_hour);
        tv_minute = (TextView) view.findViewById(R.id.tv_minute);
        tv_text_minute = (TextView) view.findViewById(R.id.tv_text_minute);
        tv_second = (TextView) view.findViewById(R.id.tv_second);
        tv_text_second = (TextView) view.findViewById(R.id.tv_text_second);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeDataStyle);
        int bgcolor = typedArray.getColor(R.styleable.TimeDataStyle_bgColor, Color.parseColor("#000000"));
        boolean unit = typedArray.getBoolean(R.styleable.TimeDataStyle_unit, false);
        float textSize = typedArray.getDimension(R.styleable.TimeDataStyle_textSize, 10);
        //设置数字背景颜色
        tv_day.setBackgroundColor(bgcolor);
        tv_hour.setBackgroundColor(bgcolor);
        tv_minute.setBackgroundColor(bgcolor);
        tv_second.setBackgroundColor(bgcolor);
        //是否显示单位
        if (unit){
            tv_text_day.setText("天");
            tv_text_hour.setText("时");
            tv_text_minute.setText("分");
            tv_text_second.setText("秒");
        }
        //设置字体大小
        tv_day.setTextSize(textSize);
        tv_text_day.setTextSize(textSize);
        tv_hour.setTextSize(textSize);
        tv_text_hour.setTextSize(textSize);
        tv_minute.setTextSize(textSize);
        tv_text_minute.setTextSize(textSize);
        tv_second.setTextSize(textSize);
        tv_text_second.setTextSize(textSize);
    }

    /**
     * TODO 功能：设置字体大小
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2020/4/29
     */
    public void setTextSize(float textSize){
        tv_day.setTextSize(textSize);
        tv_text_day.setTextSize(textSize);
        tv_hour.setTextSize(textSize);
        tv_text_hour.setTextSize(textSize);
        tv_minute.setTextSize(textSize);
        tv_text_minute.setTextSize(textSize);
        tv_second.setTextSize(textSize);
        tv_text_second.setTextSize(textSize);
    }

    /**
     * TODO 功能：设置天数
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/15
     */
    public void setDay(String day) {
        if ("0".equals(day)) {
            tv_day.setVisibility(View.GONE);
            tv_text_day.setVisibility(View.GONE);
        } else {
            tv_day.setText(day);
        }
    }

    /**
     * TODO 功能：设置小时
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/15
     */
    public void setHour(String hour) {
        tv_hour.setText(hour);
    }

    /**
     * TODO 功能：设置分钟
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/15
     */
    public void setMinute(String minute) {
        tv_minute.setText(minute);
    }

    /**
     * TODO 功能：设置秒数
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/9/15
     */
    public void setSecond(String second) {
        tv_second.setText(second);
    }
}
