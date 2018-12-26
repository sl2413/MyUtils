package com.shenl.utils.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.shenl.utils.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        int view = (int) bundle.get("view");

        setContentView(R.layout.activity_splash);
    }

}
