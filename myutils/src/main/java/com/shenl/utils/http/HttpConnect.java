package com.shenl.utils.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class HttpConnect {
	/**
	 * TODO 功能：get请求
	 * 
	 * @param：
	 * 		params:  网络请求地址
	 * 		callBack:  回调函数   new RequestCallBack<T> 
	 * @author：沈 亮
	 * @Data：下午3:09:23
	 */
	public static void getConnect(RequestParams params,Callback.CommonCallback<?> callBack){
		params.setCharset("utf-8");
		params.setConnectTimeout(1000*5);
		x.http().get(params, callBack);
	}
	/**
	 * TODO 功能：post请求
	 * 
	 * @param：
	 * 		params:  网络请求地址
	 * 		callBack:  回调函数   new RequestCallBack<T> 
	 * @author：沈 亮
	 * @Data：下午3:09:23
	 */
	public static void postConnect(RequestParams params,Callback.CommonCallback<?> callBack){
		params.setCharset("utf-8");
        params.setConnectTimeout(1000*5);
		x.http().post(params,callBack);
	}

	/**
	 * TODO 功能：判断当前网络是否可用
	 * 
	 * @param：
	 * @author：沈 亮
	 * @Data：下午3:19:30
	 */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = mgr.getAllNetworkInfo();  
        if (info != null) {  
            for (int i = 0; i < info.length; i++) {  
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {  
                    return true;  
                }  
            }  
        }  
        return false;  
    }
}
