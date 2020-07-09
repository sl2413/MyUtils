package com.qrandroid.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.qrandroid.project.R;
import com.shenl.utils.MyCallback.PermissionListener;
import com.shenl.utils.MyUtils.PageUtils;
import com.shenl.utils.application.AppManager;
import com.shenl.utils.autolayout.AutoLayoutActivity;
import com.shenl.utils.zxing.android.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AutoLayoutActivity {

    // 记录用户首次点击返回键的时间
    private long firstTime = 0;

    //授权标识, 主要用于操作返回状态
    private int REQUEST_PERMISSION_CODE = 2;

    private PermissionListener listener;

    public LinearLayout base_root;
    public RelativeLayout base_titleBar;
    public RelativeLayout base_noNetOrData;
    public LinearLayout base_noNet;
    public TextView tv_net_retry;
    public LinearLayout base_noData;
    public TextView tv_pageTitle;
    public ImageView iv_back;
    public ImageView iv_pageTitle;
    public TextView tv_desc;
    public ImageView iv_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        AppManager.getAppManager().addActivity(this);
        // 默认关闭系统键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //String name1 = getClass().getName();//获取全类名
        //String name2 = getClass().getSimpleName();//获取类名
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        View decor = getWindow().getDecorView();
        int ui = decor.getSystemUiVisibility();
        ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //设置状态栏中字体的颜色为黑色
        /*if (lightStatusBar) {
            ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //设置状态栏中字体的颜色为黑色
        } else {
            ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //设置状态栏中字体颜色为白色
        }*/
        decor.setSystemUiVisibility(ui);

        initBaseView();
    }

    private void initBaseView() {
        base_root = findViewById(R.id.base_root);
        base_titleBar = findViewById(R.id.base_titleBar);
        iv_back = findViewById(R.id.iv_back);
        tv_pageTitle = findViewById(R.id.tv_pageTitle);
        iv_pageTitle = findViewById(R.id.iv_pageTitle);
        tv_desc = findViewById(R.id.tv_desc);
        iv_desc = findViewById(R.id.iv_desc);
        base_noNetOrData = findViewById(R.id.base_noNetOrData);
        base_noNet = findViewById(R.id.base_noNet);
        tv_net_retry = findViewById(R.id.tv_net_retry);
        base_noData = findViewById(R.id.base_noData);
    }

    /**
     * TODO 功能：标题返回按钮点击事件
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/2/5
     */
    public void Back(View v) {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String ActivityName = getClass().getSimpleName();//获取类名
        if ("MainActivity".equals(ActivityName)) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (System.currentTimeMillis() - firstTime > 2000) {
                    PageUtils.showToast(BaseActivity.this, "再按一次退出程序");
                    firstTime = System.currentTimeMillis();
                } else {
                    finish();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * TODO 功能：用于初始化界面布局
     * <p>
     * 参数说明:
     * 作 者:  沈 亮
     * 创建时间:  下午10:46:05
     */
    public abstract void initView();

    /**
     * TODO 功能：用于初始化界面上的数据
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2017/11/9
     */
    public abstract void initData();

    /**
     * TODO 功能：用于初始化界面上的事件
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2017/11/9
     */
    public abstract void initEvent();

    /**
     * TODO 功能：需要数据传递的跳转activity
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/27
     */
    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
            //接收
            //Bundle extras = intent.getExtras();
            //String key = (String) extras.get("key");
        }
        startActivity(intent);
    }

    /**
     * TODO 功能：不需要数据传递的跳转activity
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/27
     */
    public void openActivity(Class<?> targetActivityClass) {
        openActivity(targetActivityClass, null);
    }

    /**
     * TODO 功能：设置状态栏颜色
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/27
     */
    public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View StatusView = createStatusView(activity, color);
            // 添加statusView到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(StatusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * TODO 功能：生成与状态栏大小相同的柜形框
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/27
     */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏的高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        // 绘制一个和状态栏一样高度的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

    /**
     * TODO 功能：使状态栏透明,适用于图片作为背景的界面，此时需要图片填充到状态栏
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/27
     */
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * TODO 功能：fragment选择器
     * <p>
     * 参数说明:
     * res: 资源文件，布局中fragement的容器
     * fragment: 要放置的fragment
     * 作    者:   沈 亮
     * 创建时间:   2018/12/13
     */
    public void Fragment_Secter(int res, Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction bt = fm.beginTransaction();
        bt.replace(res, fragment);
        bt.commit();
    }

    //动态需要请求的权限 ,例如下代码
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * TODO 功能：动态请求权限android 5.0以上系统有效
     * <p>
     * 参数说明: **注-> 需要请求的权限,也必须要在清单文件中配置
     * 作    者:   沈 亮
     * 创建时间:   2018/11/21
     */
    public void getPermissions(Activity activity, String[] PERMISSIONS_STORAGE, PermissionListener listener) {
        this.listener = listener;
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
                ActivityCompat.requestPermissions(activity, PERMISSIONS, REQUEST_PERMISSION_CODE);
            } else {
                listener.onGranted();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //返回的权限列表
        List<String> list = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == -1) {
                list.add(permissions[i]);
            }
        }
        //扫一扫功能权限列表
        if (requestCode == 1) {
            if (list.isEmpty()) {
                Intent intent = new Intent(BaseActivity.this, CaptureActivity.class);
                startActivityForResult(intent,1);
            }
        }
        if (requestCode == 2) {
            if (!list.isEmpty()) {
                listener.onDenied(list);
            } else {
                listener.onGranted();
            }
        }
    }



    //添加状态
    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (null != base_root) {
            base_root.addView(view, lp);
        }
    }


    /**
     * 切换页面的布局
     * @param pageState 页面状态 NORMAL  EMPTY  ERROR
     */
    public void changePageState(PageState pageState) {
        switch (pageState) {
            case NORMAL:
                if (base_noNetOrData.getVisibility() == View.VISIBLE) {
                    base_noNetOrData.setVisibility(View.GONE);
                }
                break;
            case ERROR:
                if (base_noNetOrData.getVisibility() == View.GONE) {
                    base_noNetOrData.setVisibility(View.VISIBLE);
                    base_noNet.setVisibility(View.VISIBLE);
                    base_noData.setVisibility(View.GONE);
                }
                break;
            case EMPTY:
                if (base_noNetOrData.getVisibility() == View.GONE) {
                    base_noNetOrData.setVisibility(View.VISIBLE);
                    base_noNet.setVisibility(View.GONE);
                    base_noData.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    public enum PageState {
        /**
         * 数据内容显示正常
         */
        NORMAL,
        /**
         * 数据为空
         */
        EMPTY,
        /**
         * 数据加载失败
         */
        ERROR
    }
}

