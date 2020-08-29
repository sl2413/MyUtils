package com.shenl.androidTest;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.shenl.utils.activity.BaseActivity;
import com.shenl.utils.superlibrary.adapter.BaseViewHolder;
import com.shenl.utils.superlibrary.adapter.SuperBaseAdapter;
import com.shenl.utils.superlibrary.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends BaseActivity {

    private SuperRecyclerView sup_list;
    private List<String> list;

    @Override
    protected int initLayout() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initView() {
        sup_list = findViewById(R.id.sup_list);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        /*for (int i = 0; i < 10; i++) {
            list.add("条目" + i);
        }*/
        LinearLayoutManager manager = new LinearLayoutManager(Main2Activity.this);
//        GridLayoutManager manager = new GridLayoutManager(Main2Activity.this, 2);
        sup_list.setLayoutManager(manager);
        sup_list.setRefreshEnabled(false);
        sup_list.setLoadMoreEnabled(false);
        View view1 = View.inflate(Main2Activity.this, R.layout.empty_data, null);


        myAdapter myAdapter = new myAdapter(Main2Activity.this, list);
        View view = View.inflate(Main2Activity.this, R.layout.header, null);
        myAdapter.addHeaderView(view);

        sup_list.setAdapter(myAdapter);
        sup_list.setEmptyView(view1);

        //StopNewWorkReceiver();
    }

    @Override
    protected void initEvent() {

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
