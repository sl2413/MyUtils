package com.shenl.utils.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.shenl.utils.MyCallback.PermissionListener;
import com.shenl.utils.zxing.android.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFragment extends Fragment {

    //授权标识, 主要用于操作返回状态
    private int REQUEST_PERMISSION_CODE = 2;
    private PermissionListener listener;

    /**
     * TODO 功能：用于初始化界面布局
     * <p>
     * 参数说明:
     * 作 者:  沈 亮
     * 创建时间:  下午10:46:05
     */
    public abstract void initView(View v);

    /**
     * TODO 功能：用于初始化界面上的数据
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2017/11/9
     */
    public abstract void initData();

    /**
     * TODO 功能：用于初始化界面上的事件
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2017/11/9
     */
    public abstract void initEvent();

    /**
     * TODO 功能：fragment选择器
     * <p>
     * 参数说明:
     * res: 资源文件，布局中fragement的容器
     * fragment: 要放置的fragment
     * 作    者:   沈 亮
     * 创建时间:   2018/12/13
     */
    public void Fragment_Secter(int res, Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction bt = fm.beginTransaction();
        bt.replace(res, fragment);
        bt.commit();
    }

    /**
     * TODO 功能：动态请求权限android 5.0以上系统有效
     * <p>
     * 参数说明: **注-> 需要请求的权限,也必须要在清单文件中配置
     * 作    者:   沈 亮
     * 创建时间:   2018/11/21
     */
    public void getPermissions(Activity activity, String[] PERMISSIONS_STORAGE, PermissionListener listener) {
        this.listener = listener;
        ArrayList<String> list = new ArrayList<>();
        //判断android版本
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            //遍历权限, 找出未开启的权限
            for (int i = 0; i < PERMISSIONS_STORAGE.length; i++) {
                if (ActivityCompat.checkSelfPermission(activity, PERMISSIONS_STORAGE[i]) != PackageManager.PERMISSION_GRANTED) {
                    list.add(PERMISSIONS_STORAGE[i]);
                }
            }
            if (!list.isEmpty()) {
                //添加请求权限
                String[] PERMISSIONS = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    PERMISSIONS[i] = list.get(i);
                }
                //发送权限请求
                ActivityCompat.requestPermissions(activity, PERMISSIONS, REQUEST_PERMISSION_CODE);
            } else {
                listener.onGranted();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //返回的权限列表
        List<String> list = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == -1) {
                list.add(permissions[i]);
            }
        }
        //扫一扫功能权限列表
        if (requestCode == 1) {
            if (list.isEmpty()) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, 1);
            }
        }
        if (requestCode == 2) {
            if (!list.isEmpty()) {
                listener.onDenied(list);
            } else {
                listener.onGranted();
            }
        }
    }
}
