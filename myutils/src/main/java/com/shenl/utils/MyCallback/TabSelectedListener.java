package com.shenl.utils.MyCallback;

import android.support.design.widget.TabLayout;

public abstract class TabSelectedListener implements TabLayout.OnTabSelectedListener {
    @Override
    public abstract void onTabSelected(TabLayout.Tab tab);

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
