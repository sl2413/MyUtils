package com.shenl.utils.MyUtils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * TODO 功能：操作字符串工具类
 *
 * 参数说明:
 * 作    者:   沈 亮
 * 创建时间:   2019/7/2
 */
public class StringUtil {

    /**
     * TODO 功能：中文转码uniCode
     *
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2017/11/22
     */
    public static String cnToUnicode(String cn){
        char[] chars = cn.toCharArray();
        String returnStr = "";
        for (int i = 0; i < chars.length; i++) {
            returnStr += "\\u" + Integer.toString(chars[i], 16);
        }
        return returnStr;
    }

    /**
     * TODO 功能：unicode编码转换中文
     *
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2017/11/22
     */
    public static String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }

    /**
     * TODO 功能：获取指定位数随机码
     *
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String getNonceStr(int bit) {
        String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random RANDOM = new SecureRandom();
        char[] nonceChars = new char[bit];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

}
