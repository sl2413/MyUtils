package com.shenl.utils.QRCode;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.shenl.utils.zxing.android.CaptureActivity;
import com.shenl.utils.zxing.bean.ZxingConfig;
import com.shenl.utils.zxing.common.Constant;
import com.shenl.utils.zxing.encode.CodeCreator;

import java.util.ArrayList;

/**
 * TODO 功能：操作二维码工具类
 * 　此工具类需配合　core-3.3.3.jar　使用
 * 参数说明:
 * 作    者:   沈 亮
 * 创建时间:   2018/12/14
 */
public class QRCodeUtils {

    /**
     * TODO 功能：扫描二维码
     * <p>
     * 参数说明:   text => 要生成二维码的字符串
     * 作    者:   沈 亮
     * 创建时间:   2018/12/14
     */
    public static void ReadQRCode(Activity activity) {
        ReadQRCode(activity, null);
    }

    /**
     * TODO 功能：扫描二维码,可对扫描页面进行设置
     * <p>
     * 参数说明:
     * ZxingConfig config = new ZxingConfig();<p>
     * config.setPlayBeep(true);//是否播放扫描声音 默认为true<p>
     * config.setShake(true);//是否震动  默认为true<p>
     * config.setDecodeBarCode(true);//是否扫描条形码 默认为true<p>
     * config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色<p>
     * config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色<p>
     * config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色<p>
     * config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描<p>
     * 作    者:   沈 亮
     * 创建时间:   2018/12/14
     */
    public static void ReadQRCode(Activity activity, ZxingConfig config) {
        String[] PERMISSIONS_STORAGE = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
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
                ActivityCompat.requestPermissions(activity, PERMISSIONS, 1);
            } else {
                Intent intent = new Intent(activity, CaptureActivity.class);
                if (config != null) {
                    intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                }
                activity.startActivityForResult(intent, 1);
            }
        }
    }


    /**
     * TODO 功能：生成二维码，默认大小为500*500
     * <p>
     * 参数说明:
     * text => 要生成二维码的字符串
     * 作    者:   沈 亮
     * 创建时间:   2018/12/14
     */
    public static Bitmap createQRCode(String text) {
        return createQRCode(text, null);
    }

    /**
     * TODO 功能：生成带logo的二维码
     * <p>
     * 参数说明:
     * text => 要生成二维码的字符串
     * logo => 二维码的logo
     * 作    者:   沈 亮
     * 创建时间:   2018/12/14
     */
    public static Bitmap createQRCode(String text, Bitmap logo) {
        return createQRCode(text, 500, logo);
    }

    /**
     * TODO 功能：自定义二维码的大小与logo
     * <p>
     * 参数说明:
     * text => 要生成二维码的字符串
     * size => 二维码的大小
     * logo => 二维码的logo
     * 作    者:   沈 亮
     * 创建时间:   2018/12/14
     */
    public static Bitmap createQRCode(String text, int size, Bitmap logo) {
        return CodeCreator.createQRCode(text, size, size, logo);
    }
}
