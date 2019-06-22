package com.shenl.androidTest;

import android.content.Context;

import com.shenl.utils.MyUtils.JsonUtil;
import com.shenl.utils.MyUtils.PageUtils;

import org.xutils.http.RequestParams;
import org.xutils.http.app.ParamsBuilder;

import java.util.HashMap;
import java.util.Map;

public class HttpUrl {
    public static final String BASE = "http://10.19.92.71:88/";
    public static final String IPaddress = BASE + "auth_oa";

    public static void getMsgCode(Context context, String json) {
        String code = JsonUtil.getFieldValue(json, "code");
        if (!"0".equals(code)) {
            PageUtils.showToast(context, JsonUtil.getFieldValue(json, "msg"));
            return;
        }
    }

    public static RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.addHeader("", "");
        return params;
    }
}
