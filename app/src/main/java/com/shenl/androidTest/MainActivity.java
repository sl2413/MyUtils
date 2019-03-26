package com.shenl.androidTest;

import android.os.Bundle;

import com.shenl.utils.MyCallback.HttpCallBack;
import com.shenl.utils.activity.BaseActivity;
import com.shenl.utils.http.HttpConnect;

import org.xutils.http.RequestParams;

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
        RequestParams params = HttpUrl.getParams();
        params.setUri("");
        HttpConnect.getConnect(params, new HttpCallBack(MainActivity.this) {
            @Override
            public void onSuccess(String s) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
