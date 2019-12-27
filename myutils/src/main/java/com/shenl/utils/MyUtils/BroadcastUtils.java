package com.shenl.utils.MyUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class BroadcastUtils {

    /**
     * TODO 功能：启动广播监听
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/22
     */
    public static BroadcastReceiver StartBroadcast(Context context,String action,ReceiverListener listener){
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        BroadcastReceiver receiver = getBroadcast(listener);
        context.registerReceiver(receiver,filter);
        return receiver;
    }

    /**
     * TODO 功能：发送广播
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/22
     */
    public static void SendBroadcas(Context context,String action){
        SendBroadcas(context,action,null);
    }

    /**
     * TODO 功能：发送广播
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/12/27
     */
    public static void SendBroadcas(Context context,String action,Bundle bundle){
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        context.sendBroadcast(intent);
    }

    /**
     * TODO 功能：停止广播接收
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/22
     */
    public static void StopBroadcast(Context context,BroadcastReceiver receiver){
        context.unregisterReceiver(receiver);
    }

    /**
     * TODO 功能：获取创建一个自定义广播
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/22
     */
    public static BroadcastReceiver  getBroadcast(final ReceiverListener listener){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                listener.Receive(intent);
            }
        };
    }


    public interface ReceiverListener{
        void Receive(Intent intent);
    }
}
