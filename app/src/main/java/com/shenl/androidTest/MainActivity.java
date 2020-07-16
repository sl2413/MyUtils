package com.shenl.androidTest;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenl.utils.MyCallback.TabSelectedListener;
import com.shenl.utils.MyUtils.BroadcastUtils;
import com.shenl.utils.MyUtils.DateUtils;
import com.shenl.utils.MyUtils.FileUtils;
import com.shenl.utils.MyUtils.PageUtils;
import com.shenl.utils.MyUtils.ShareUtils;
import com.shenl.utils.activity.BaseActivity;
import com.shenl.utils.activity.BaseFragmentActivity;
import com.shenl.utils.superlibrary.adapter.BaseViewHolder;
import com.shenl.utils.superlibrary.adapter.SuperBaseAdapter;
import com.shenl.utils.superlibrary.recycleview.ProgressStyle;
import com.shenl.utils.superlibrary.recycleview.SuperRecyclerView;
import com.shenl.utils.view.BadgeButton;
import com.shenl.utils.view.SpinnerView;
import com.shenl.utils.view.TabView;
import com.shenl.utils.view.TimeDataView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private SuperRecyclerView sup_list;
    private TimeDataView tdv_time;
    private BroadcastReceiver receiver;
    private TabLayout tabs;
    private SpinnerView sv_list;
    private List<String> list;
    private ImageView iv_image;


    @Override
    protected int initLayout() {
        PageUtils.createNotificationChannel(MainActivity.this, "1", "name", 1);
        receiver = BroadcastUtils.StartBroadcast(MainActivity.this, "shenl", new BroadcastUtils.ReceiverListener() {
            @Override
            public void Receive(Intent intent) {
                PageUtils.showLog("广播接收到");
            }
        });
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        sv_list = findViewById(R.id.sv_list);
        sup_list = findViewById(R.id.sup_list);
        iv_image = findViewById(R.id.iv_image);
        //倒计时
        tdv_time = findViewById(R.id.tdv_time);
        tabs = findViewById(R.id.tabs);
        TabView.addTabItem(MainActivity.this,tabs,"首页",R.mipmap.list_1,R.color.tab);
        TabView.addTabItem(MainActivity.this,tabs,"扩展",R.mipmap.list_2,R.color.tab);
        TabView.addTabItem(MainActivity.this,tabs,"我的",R.mipmap.list_3,R.color.tab);
        tabs.getTabAt(0).select();
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("条目" + i);
        }
        sv_list.setAdapter(new svAdapter(list));
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        sup_list.setLayoutManager(manager);
        sup_list.setRefreshEnabled(true);
        sup_list.setLoadMoreEnabled(true);
        sup_list.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        sup_list.setAdapter(new myAdapter(MainActivity.this, list));
        DateUtils.LimitedTime(DateUtils.DateToSecond("2019-11-23 00:00:00"), tdv_time);
    }

    @Override
    public void initEvent() {
        sv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sv_list.setText(list.get(position));
            }
        });
        tabs.addOnTabSelectedListener(new TabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                BadgeButton customView = (BadgeButton) tab.getCustomView();
                PageUtils.showToast(MainActivity.this,customView.getText()+"");
            }
        });
        sup_list.setLoadingListener(new SuperRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sup_list.setNoMore(true);
                    }
                },3000);
            }
        });
    }

    public void openPhoto(View v) {
        FileUtils.openPhoto(MainActivity.this);
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

    public void showProgress(View v){
        PageUtils.showDialog(MainActivity.this,5);
    }

    public void PlayVideo(View v){
        openActivity(Main2Activity.class);
    }

    public void Share(View v){
        final List<String> urls = new ArrayList<>();
        urls.add("http://t7.baidu.com/it/u=378254553,3884800361&fm=79&app=86&f=JPEG?w=1280&h=2030");
        urls.add("http://t8.baidu.com/it/u=3571592872,3353494284&fm=79&app=86&f=JPEG?w=1200&h=1290");
        urls.add("http://m.qianrong.vip:8383/menuPic/2020/06/15/49c6e521d34d65b7ad9e40bfcbebbdc6.jpg");

        final ArrayList<File> files = new ArrayList<>();
        for (int i=0;i<urls.size();i++){
            ShareUtils.CreateShareFile(MainActivity.this, urls.get(i), new ShareUtils.CallBack() {
                @Override
                public void Result(File file) {
                    PageUtils.showLog("返出路径："+file.getAbsolutePath());
                    files.add(file);
                    if (urls.size() == files.size()){
                        ShareUtils.ShareImage(MainActivity.this,files);
                    }
                }
            });
        }

//        ShareUtils.ShareText(MainActivity.this,"12312312");

    }

    public void Loading(View v){
        PageUtils.showDialog(MainActivity.this,1);
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

    class svAdapter extends BaseAdapter{

        private List<String> list;
        public svAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(MainActivity.this);
            tv.setPadding(10,10,10,10);
            tv.setTextSize(15);
            tv.setText(list.get(position));
            return tv;
        }
    }
}
