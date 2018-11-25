package com.shenl.androidTest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shenl.utils.MyUtils.PhoneUtils;
import com.shenl.utils.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        btn = findViewById(R.id.btn);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneUtils.getChoosedPhoneNumber(MainActivity.this,1,new Intent());
            }
        });
    }

}
