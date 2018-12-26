package com.shenl.androidTest;

import android.os.Bundle;

import com.shenl.utils.activity.BaseActivity;
import com.shenl.utils.activity.SplashActivity;

public class MainActivity extends BaseActivity {


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
        Bundle bundle = new Bundle();
        bundle.putInt("view", R.layout.activity_main);
        openActivity(SplashActivity.class,bundle);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

}
