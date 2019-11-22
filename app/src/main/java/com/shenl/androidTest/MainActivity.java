package com.shenl.androidTest;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.shenl.utils.MyUtils.BroadcastUtils;
import com.shenl.utils.MyUtils.ImageUtils;
import com.shenl.utils.MyUtils.DateUtils;
import com.shenl.utils.MyUtils.PageUtils;
import com.shenl.utils.activity.BaseActivity;
import com.shenl.utils.superlibrary.adapter.BaseViewHolder;
import com.shenl.utils.superlibrary.adapter.SuperBaseAdapter;
import com.shenl.utils.superlibrary.recycleview.SuperRecyclerView;
import com.shenl.utils.view.TimeDataView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private SuperRecyclerView sup_list;
    private TimeDataView tdv_time;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PageUtils.createNotificationChannel(MainActivity.this, "1", "name", 1);

        receiver = BroadcastUtils.StartBroadcast(MainActivity.this, "shenl", new BroadcastUtils.ReceiverListener() {
            @Override
            public void Receive(Intent intent) {
                PageUtils.showLog("广播接收到");
            }
        });

        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        sup_list = findViewById(R.id.sup_list);

        //倒计时
        tdv_time = findViewById(R.id.tdv_time);
    }

    @Override
    public void initData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("条目" + i);
        }
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        sup_list.setLayoutManager(manager);
        sup_list.setRefreshEnabled(true);
        sup_list.setAdapter(new myAdapter(MainActivity.this, list));

        DateUtils.LimitedTime(DateUtils.DateToSecond("2019-11-23 00:00:00"), tdv_time);
    }

    @Override
    public void initEvent() {

    }

    public void openPhoto(View v) {
        ImageUtils.openPhoto(MainActivity.this);
    }

    public void sendMsg(View v) {
        //PageUtils.showNotification(MainActivity.this,R.mipmap.ic_launcher,1,"测试","测试内容......");
        NotificationManager manger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(MainActivity.this, "1")
                .setContentTitle("收到一条订阅消息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setSmallIcon(R.drawable.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .build();
        manger.notify(1, notification);

    }

    public void Send(View v){
        BroadcastUtils.SendBroadcas(MainActivity.this,"shenl");
    }

    public void Stop(View v){
        BroadcastUtils.StopBroadcast(MainActivity.this,receiver);
    }


    class myAdapter extends SuperBaseAdapter<String> {

        public myAdapter(Context context, List<String> data) {
            super(context, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, String item, int position) {
            holder.setText(R.id.tv_text, item);
        }

        @Override
        protected int getItemViewLayoutId(int position, String item) {
            return R.layout.item;
        }
    }
}
