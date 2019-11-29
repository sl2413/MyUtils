package com.shenl.utils.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.shenl.utils.R;

public class TabView {

    /**
     * TODO 功能：仅有文字tabView
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/28
     */
    public static void addTabItem(Context context, TabLayout tabs, String text) {
        addTabItem(context, tabs, text, 12, 0, 50, R.color.tab);
    }

    /**
     * TODO 功能：仅有图标tabView
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/28
     */
    public static void addTabItem(Context context, TabLayout tabs, int icon) {
        addTabItem(context, tabs, "", 12, icon, 50, R.color.tab);
    }

    /**
     * TODO 功能：仅有图标tabView
     * <p>
     * 参数说明:  可设计图标的大小
     * 作    者:   沈  亮
     * 创建时间:   2019/11/28
     */
    public static void addTabItem(Context context, TabLayout tabs, int icon, int Size) {
        addTabItem(context, tabs, "", 12, icon, Size, R.color.tab);
    }

    /**
     * TODO 功能：仅有图标tabView
     * <p>
     * 参数说明:  可设计图标的大小以及选中的状态
     * 作    者:   沈  亮
     * 创建时间:   2019/11/28
     */
    public static void addTabItem(Context context, TabLayout tabs, int icon, int Size, int selector) {
        addTabItem(context, tabs, "", 12, icon, Size, selector);
    }

    /**
     * TODO 功能：文字图片都有的tabView
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/28
     */
    public static void addTabItem(Context context, TabLayout tabs, String text, int icon, int selector) {
        addTabItem(context, tabs, text, 12, icon, 50, selector);
    }

    /**
     * TODO 功能：添加tabview的条目
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/28
     */
    public static void addTabItem(Context context, TabLayout tabs, String text, int fontSize, int icon, int iconSize, int selector) {
        BadgeButton item = (BadgeButton) View.inflate(context, R.layout.item_tab, null);
        //BadgeButton item = (BadgeButton) context.getLayoutInflater().inflate(R.layout.item_tab, tabs, false);
        if (icon == 0) {
            item.setIcon(null);
        } else {
            Drawable wrap = wrap(context, icon, iconSize, selector);
            item.setIcon(wrap);
        }
        if (!TextUtils.isEmpty(text)) {
            item.setText(text);
        }
        item.setTextSize(fontSize);
        item.setBadgeText("");
        item.setBadgeVisible(false);
        item.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tabs.addTab(tabs.newTab().setCustomView(item));
    }

    /**
     * TODO 功能：本地资源转drawable,并带选择器效果
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/12/28
     */
    public static Drawable wrap(Context context, int icon) {
        return wrap(context, icon, 50, R.color.tab);
    }

    /**
     * TODO 功能：本地资源转drawable,并带选择器效果
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/12/28
     */
    public static Drawable wrap(Context context, int icon, int Size) {
        return wrap(context, icon, Size, R.color.tab);
    }

    /**
     * TODO 功能：本地资源转drawable,并带选择器效果
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/12/28
     */
    public static Drawable wrap(Context context, int icon, int Size, int selector) {
        ColorStateList mTint = null;
        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), icon, context.getTheme());
        if (mTint == null) {
            mTint = ResourcesCompat.getColorStateList(context.getResources(), selector, context.getTheme());
        }
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable.mutate());
            drawable.setBounds(0, 0, Size, Size);
            DrawableCompat.setTintList(drawable, mTint);
        }
        return drawable;
    }

    /**
     * TODO 功能：设置气泡上的数字
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/28
     */
    public static void setNumberBadge(TabLayout tabs, int position, String Number) {
        int num = Integer.parseInt(Number);
        BadgeButton button = (BadgeButton) tabs.getTabAt(position).getCustomView();
        button.setBadgeText(num >= 100 ? "99+" : num + "");
        button.setBadgeVisible(true);
    }

    /**
     * TODO 功能：设置气泡上的文字
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/28
     */
    public static void setStringBadge(TabLayout tabs, int position, String badge) {
        BadgeButton button = (BadgeButton) tabs.getTabAt(position).getCustomView();
        button.setBadgeText(badge);
        button.setBadgeVisible(true);
    }

}
