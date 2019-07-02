package com.shenl.utils.MyUtils;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.shenl.utils.MyCallback.DialogCallBack;
import com.shenl.utils.R;


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


    public static void showNotification(Context context, Bitmap icon,int NotificationId, String title, String content) {
        //在手机状态栏提示新消息
        //调用手机提示音
        Uri uri1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone rt = RingtoneManager.getRingtone(context, uri1);
        rt.play();
        //状态栏显示消息
        NotificationManager manger = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        //android8.0需要创建渠道, 通过渠道显示消息
        /*if (Build.VERSION.SDK_INT>=26){
            NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            if (manger != null) {
                manger.createNotificationChannel(channel);
            }
        }*/

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(title);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
        /*builder.setChannelId(PUSH_CHANNEL_ID);
        Uri uri = Uri.parse("rong://" + context.getApplicationInfo().packageName).buildUpon().appendPath("conversation").appendPath(Conversation.ConversationType.PRIVATE.getName().toLowerCase(Locale.US)).appendQueryParameter("targetId", senderUserId).appendQueryParameter("title", "私人会话").build();
        Intent intent = new Intent("android.intent.action.VIEW", uri);
        PendingIntent pIntent = PendingIntent.getActivity(context.getApplicationContext(), 6, intent, 0);
        builder.setContentIntent(pIntent);*/

        Notification notification = builder.build();
        manger.notify(NotificationId, notification);
        //通过渠道显示消息
        /*notification.flags |= Notification.FLAG_AUTO_CANCEL;
        if (manger != null) {
            manger.notify(PUSH_NOTIFICATION_ID, notification);
        }*/
    }

}
