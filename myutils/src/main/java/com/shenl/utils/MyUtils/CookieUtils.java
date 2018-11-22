package com.shenl.utils.MyUtils;

import org.xutils.http.cookie.DbCookieStore;

import java.net.HttpCookie;
import java.util.List;

/**
 * TODO 功能：获取服务器返回来的cookie
 *
 * @param：
 * @author：沈 亮
 * @Data：2017/11/7
 */
public class CookieUtils {
    public static String getCookie(String cookieName) {
    /*
                   //服务器登陆时候需要加以下代码返出cookie
                    String cookie = paramHttpServletRequest.getSession().getId();
                    Cookie ccc = new Cookie("test",cookie);
                    paramHttpServletResponse.addCookie(ccc);
                    */
        DbCookieStore instance = DbCookieStore.INSTANCE;
        List<HttpCookie> cookies = instance.getCookies();
        for (HttpCookie cookie : cookies) {
            String name = cookie.getName();
            String value = cookie.getValue();
            if (cookieName.equals(name)) {
                return value;
            }
        }
        return "";
    }
}
