package com.shenl.utils.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenl.utils.R;


public class Counter extends LinearLayout {

    private TextView count;
    private boolean b = true;

    public Counter(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context, attrs);
    }

    /**
     * TODO 功能：初始化控件布局
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/2/21
     */
    private void Init(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.cunter_view, Counter.this);
        final TextView Readuce = (TextView) view.findViewById(R.id.Reduce);
        count = (TextView) view.findViewById(R.id.Count);
        TextView Add = (TextView) view.findViewById(R.id.Add);

        //减号方法
        Readuce.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b) {
                    int s = (Integer.parseInt(getCount()) - 1);
                    setCount(s + "");
                    if (s <= 0) {
                        Readuce.setTextColor(Color.GRAY);
                        count.setTextColor(Color.GRAY);
                        b = false;
                    }
                }
            }
        });
        //加号方法
        Add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                b = true;
                Readuce.setTextColor(Color.BLACK);
                count.setTextColor(Color.BLACK);
                setCount((Integer.parseInt(getCount()) + 1) + "");
            }
        });

    }

    /**
     * TODO 功能：获取数量
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/2/21
     */
    public String getCount() {
        return count.getText().toString().trim();
    }

    /**
     * TODO 功能：设置数量
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/2/21
     */
    public void setCount(String c) {
        count.setText(c);
    }
}
