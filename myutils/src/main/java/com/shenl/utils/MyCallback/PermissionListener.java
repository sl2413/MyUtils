package com.shenl.utils.MyCallback;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.shenl.utils.MyUtils.PageUtils;
import java.util.List;

public abstract class PermissionListener {

    private Context context;

    public PermissionListener(Context context){
        this.context = context;
    }

    /**
     * TODO 功能：权限全部通过的回调方法
     *
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/11/21
     */
    public abstract void onGranted();

    /**
     * TODO 功能：申请权限中有未通过的回调方法
     *
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/11/21
     */
    public void onDenied(List<String> deniedPermission){
        if (!deniedPermission.isEmpty()) {
            /*for (int i = 0; i < deniedPermission.size(); i++) {
                PageUtils.showLog(deniedPermission.get(i)+"...未授权");
            }*/
            PageUtils.showAlertDialog(context, "系统警告", "授权未经允许,会影响正常功能使用,是否现在设置?", new DialogCallBack() {
                @Override
                public void onPositiveButton() {
                    //引导用户到设置中去进行设置
                    Intent intent = new Intent();
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                    context.startActivity(intent);
                }
            });
        }
    }
}
