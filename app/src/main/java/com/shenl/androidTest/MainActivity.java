package com.shenl.androidTest;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;

import com.shenl.utils.Image.ImageUtils;
import com.shenl.utils.MyCallback.PermissionListener;
import com.shenl.utils.MyUtils.PageUtils;
import com.shenl.utils.activity.BaseActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] PERMISSIONS_STORAGE = {
                           Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE };
        getPermissions(MainActivity.this, PERMISSIONS_STORAGE, new PermissionListener(MainActivity.this) {
            @Override
            public void onGranted() {
                initView();
                initData();
                initEvent();
            }
        });
    }

    @Override
    public void initView() {
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
