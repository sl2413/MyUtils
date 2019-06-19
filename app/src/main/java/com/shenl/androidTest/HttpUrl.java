package com.shenl.androidTest;

import org.xutils.http.RequestParams;
import org.xutils.http.app.ParamsBuilder;

import java.util.HashMap;
import java.util.Map;

public class HttpUrl {
    public static final String BASE = "http://10.19.92.71:88/";
    public static final String IPaddress = BASE + "auth_oa";

    public static Map<String,String> getMsgCode(){
        Map<String,String> msgCode = new HashMap<>();
        msgCode.put("0000","操作成功");
        msgCode.put("0001","操作失败");
        return msgCode;
    }

    public static RequestParams getParams(){
        RequestParams params = new RequestParams();
        params.addHeader("","");
        return params;
    }
}
