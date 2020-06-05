package com.shenl.utils.MyUtils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shenl.utils.MyCallback.DialogCallBack;
import com.shenl.utils.R;
import com.shenl.utils.view.GifView;

import static android.content.Context.NOTIFICATION_SERVICE;


public class PageUtils {

    //是否打印log
    public static final boolean PrintFalg = true;
    private static final String SUCCESS = "0000";
    //自定义吐司
    private static Toast toast = null;

    /**
     * TODO 功能：显示一个吐司
     *
     * @param：
     * @author：沈 亮
     * @Data：下午2:42:33
     */
    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        if (TextUtils.isEmpty(text)) {
            toast.setText("请求超时");
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    /**
     * TODO 功能：网络错误显示吐司
     *
     * @param：
     * @author：沈 亮
     * @Data：下午2:42:51
     */
    public static void showToast(Context context) {
        showToast(context, null);
    }

    /**
     * TODO 功能：在控制台打印log信息
     *
     * @param：
     * @author：沈 亮
     * @Data：上午9:33:14
     */
    public static void showLog(String text) {
        if (PrintFalg) {
            Log.e("shenl", text);
        }
    }

    /**
     * TODO 功能：显示一个不确定进度对话框并且可以自定义提交消息
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/16
     */
    public static ProgressDialog showDialog(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        //使得点击对话框外部不消失对话框
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(msg);
        progressDialog.show();
        return progressDialog;
    }

    /**
     * TODO 功能：显示一个带样式的不确定进度对话框并且可以自定义提交消息
     *
     * 参数说明: ProgressStyle为样式编号
     * 作    者:   沈  亮
     * 创建时间:   2020/6/3
     */
    public static Dialog showDialog(Context context,int ProgressStyle){
        final Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_Dialog);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);// 一句话搞定
        View view = View.inflate(context, R.layout.progress_style, null);
        GifView iv_style = view.findViewById(R.id.iv_style);
        switch (ProgressStyle){
            case 1:
                iv_style.setMovieResource(R.drawable.style1);
                break;
            case 2:
                iv_style.setMovieResource(R.drawable.style2);
                break;
            case 3:
                iv_style.setMovieResource(R.drawable.style3);
                break;
            case 4:
                iv_style.setMovieResource(R.drawable.style4);
                break;
            case 5:
                iv_style.setMovieResource(R.drawable.style5);
                break;
        }
        dialog.setContentView(view,new LinearLayout.LayoutParams(PhoneUtils.getPhoneWidth(context)/4, ViewGroup.LayoutParams.WRAP_CONTENT));
        //使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    /**
     * TODO 功能：显示一个不确定进度对话框
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/2/8
     */
    public static ProgressDialog showDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        //使得点击对话框外部不消失对话框
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载请稍后...");
        progressDialog.show();
        return progressDialog;
    }

    /**
     * TODO 功能：显示一个询问式对话框
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/16
     */
    public static AlertDialog showAlertDialog(Context context, String title, String msg, final DialogCallBack callback) {
        AlertDialog AlertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton("取消", callback.onNegativeButton())
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.onPositiveButton();
                    }
                }).show();
        return AlertDialog;
    }

    /**
     * TODO 功能：创建一个根据内容自动宽高的对话框
     * <p>
     * 参数说明: 默认大小，宽高=75%
     * 作    者:   沈  亮
     * 创建时间:   2019/8/3
     */
    public static Dialog showCustomDialog(Context context, View view) {
        final Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_Dialog);
        dialog.setContentView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    /**
     * TODO 功能：创建一个自定义大小的对话框
     * <p>
     * 参数说明: 可以自定义大小 例如：0.75f
     * 作    者:   沈  亮
     * 创建时间:   2019/8/3
     */
    public static Dialog showCustomDialog(Context context, View view, float Wpercent, float Hpercent) {
        final Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_Dialog);
        dialog.setContentView(view);
        //使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(true);
        //设置对话框的大小
        view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(context).getScreenHeight() * 0.23f));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenSizeUtils.getInstance(context).getScreenWidth() * Wpercent);
        lp.height = (int) (ScreenSizeUtils.getInstance(context).getScreenHeight() * Hpercent);
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }




    /**
     * TODO 功能：手机状态栏显示通知信息
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/8/3
     */
    public static void showNotification(Context context, int icon, int NotificationId, String title, String content) {
        showNotification(context,icon,NotificationId,title,content,new Intent());
    }

    /**
     * TODO 功能：手机状态栏显示通知信息
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/8/3
     */
    public static void showNotification(Context context, int icon, int NotificationId, String title, String content, Intent intent) {
        //在手机状态栏提示新消息
        //调用手机提示音
        Uri uri1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone rt = RingtoneManager.getRingtone(context, uri1);
        rt.play();

        //状态栏显示消息
        NotificationManager manger = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(icon)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(PendingIntent.getActivity(context.getApplicationContext(), 6, intent, 0))
                .setAutoCancel(true)
                .build();
        manger.notify(NotificationId, notification);


       /* NotificationManager manger = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pIntent = PendingIntent.getActivity(context.getApplicationContext(), 6, intent, 0);
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("收到一条订阅消息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(icon)
                //.setLargeIcon(BitmapFactory.decodeResource(icon))
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        //manger.notify(NotificationId, notification);
        //通过渠道显示消息
        //notification.flags |= Notification.FLAG_AUTO_CANCEL;
        if (manger != null) {
            manger.notify(NotificationId, notification);
        }*/
    }

    /**
     * TODO 功能：android 8.0创建渠道通知
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/22
     */
    public static void createNotificationChannel(Context context, String channelId, String channelName, int importance) {
        NotificationManager manger = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = null;
            if (importance == 0) {
                channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            } else {
                channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            }
            //是否绕过请勿打扰模式
            channel.canBypassDnd();
            //闪光灯
            channel.enableLights(true);
            //锁屏显示通知
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            //闪关灯的灯光颜色
            channel.setLightColor(Color.RED);
            //桌面launcher的消息角标
            channel.canShowBadge();
            //是否允许震动
            channel.enableVibration(true);
            //获取系统通知响铃声音的配置
            channel.getAudioAttributes();
            //获取通知取到组
            channel.getGroup();
            //设置可绕过  请勿打扰模式
            channel.setBypassDnd(true);
            //设置震动模式
            channel.setVibrationPattern(new long[]{100, 100, 200});
            //是否会有灯光
            channel.shouldShowLights();
            manger.createNotificationChannel(channel);
        }
    }

}
