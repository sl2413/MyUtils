package com.shenl.androidTest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.shenl.utils.activity.BaseFragmentActivity;
import com.shenl.utils.view.VerticalViewPager;
import java.util.ArrayList;
import java.util.List;

public class PlayViewActivity extends BaseFragmentActivity {

    private VerticalViewPager vvp_paer;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_view);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        vvp_paer = findViewById(R.id.vvp_paer);
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        list.add("https://cloud.video.taobao.com/play/u/3075039900/p/2/e/6/t/1/266889755296.mp4?appKey\\u003d38829");
        list.add("https://cloud.video.taobao.com/play/u/897609396/p/2/e/6/t/1/214007186037.mp4?appKey\\u003d38829");
        list.add("https://cloud.video.taobao.com/play/u/2207771318806/p/2/e/6/t/1/265088638724.mp4?appKey=38829");
        //设置viewpager 缓存数，可以根据需要调整
        vvp_paer.setOffscreenPageLimit(3);
        vvp_paer.setAdapter(new MyFragment(getSupportFragmentManager()));
    }

    @Override
    public void initEvent() {

    }


    class MyFragment extends FragmentPagerAdapter{

        public MyFragment(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            VideoFragment fragment = new VideoFragment(list.get(i));
            return fragment;
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }



    class MyAdapter extends PagerAdapter{

        private List<String> list;

        public MyAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = View.inflate(PlayViewActivity.this, R.layout.item_video, null);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction bt = fm.beginTransaction();
            bt.replace(R.id.fl_pager, new VideoFragment(list.get(position)));
            bt.commit();
            /*MyWebView wv = view.findViewById(R.id.wv);
            String html = "<!DOCTYPE html><html lang='en'>";
            html += "<head>";
            html += "<meta charset='UTF-8'>";
            html += "<meta name='viewport' content='width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0'>";
            html += "</head>";
            html += "<body>";
            html += "<video src='"+list.get(position)+"' style='width: 100vw;height: 100vh;'></video>";
            html += "</body></html>";
            wv.loadData(html,"text/html","utf-8");*/
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
