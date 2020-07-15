package com.shenl.utils.MyUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresPermission;

public class NetUtils {
    /**
     * 没有网络
     */
    public static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    public static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    public static final int NETWORK_WIFI = 1;

    /**
     * TODO 功能：获取当前网络状态
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2020/7/15
     */
    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static int getNetWorkState(Context context) {
        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //如果网络连接，判断该网络类型
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;//wifi
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;//mobile
            }
        } else {
            //网络异常
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }
}
