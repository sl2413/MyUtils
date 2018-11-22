package com.shenl.utils.MyUtils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.List;

import static com.shenl.utils.view.PullToRefreshLayout.TAG;

public class AppManager {
    /**
     * TODO 功能：获取所以安装app的信息
     *
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/27
     */
    public static List<PackageInfo> getAllAppNames(Context context){
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> list = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        /*for (PackageInfo packageInfo : list) {
            //得到手机上已经安装的应用的名字,即在AndriodMainfest.xml中的app_name。
            String appName=packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            //得到应用所在包的名字,即在AndriodMainfest.xml中的package的值。
            String packageName=packageInfo.packageName;
        }*/
        return list;
    }
    /**
     * TODO 功能：手动安装app
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/27
     */
    public static void installApk(Context context, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * TODO 功能：卸载app
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/27
     */
    public static void unInstallApp(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);   // 包名
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);
    }

    /**
     * TODO 功能：打开指定的app
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/27
     */
    public static void OpenApp(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        context.startActivity(intent);
    }
    /**
     * TODO 功能：检测是否有指定的应用
     *
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/27
     */
    public static boolean checkPackInfo(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }
}
