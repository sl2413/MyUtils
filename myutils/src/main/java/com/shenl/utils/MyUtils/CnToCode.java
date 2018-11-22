package com.shenl.utils.MyUtils;

/**
 * Created by shenl on 2017/11/22.
 */

public class CnToCode {
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

}
