package com.shenl.utils.MyCallback;

import android.app.ProgressDialog;
import android.content.Context;
import com.shenl.utils.MyUtils.JsonUtil;
import com.shenl.utils.MyUtils.PageUtils;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;

public abstract class HttpCallBack implements Callback.CommonCallback<String>{
    private Context context;
    private ProgressDialog dialog;
    public HttpCallBack(Context context){
        this.context = context;
    }
    public HttpCallBack(Context context, ProgressDialog dialog){
        this.context = context;
        this.dialog = dialog;
    }
    @Override
    public abstract void onSuccess(String s);

    @Override
    public void onError(Throwable throwable, boolean b) {
        if (throwable instanceof HttpException) { // 网络错误
            //dialog.dismiss();
            HttpException httpEx = (HttpException) throwable;
            int responseCode = httpEx.getCode();
            String responseMsg = httpEx.getMessage();
            String errorResult = httpEx.getResult();
            //错误码:401
            //错误信息:Unauthorized
            //{appcode:"401",msg:"用户未登录！"}
            PageUtils.showLog("错误码:"+responseCode);
            PageUtils.showLog("错误信息:"+responseMsg);
            PageUtils.showLog("错误结果:"+errorResult);
            String appcode = JsonUtil.getFieldValue(errorResult, "appcode");
            if ("401".equals(appcode)){
                String msg = JsonUtil.getFieldValue(errorResult, "msg");
                PageUtils.showToast(context,msg);
            }
        }
    }

    @Override
    public void onCancelled(CancelledException e) {
        e.printStackTrace();
    }

    @Override
    public void onFinished() {
        if (dialog != null){
            dialog.dismiss();
        }
        PageUtils.showLog("网络请求完毕");
    }
}
