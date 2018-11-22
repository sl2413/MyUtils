package com.shenl.utils.MyUtils;

import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangyu on 2016/2/24.
 */
public class StringUtil {
    //将Byte[]转成汉字
    public static String byte2Chinese(String strSW, byte[] pRecvRes) {
        String contentStr = strSW.substring(4, strSW.length() - 4);
        String tmpResult = contentStr.replace("0000", "");
        Log.i("contentStr",contentStr);
        Log.i("tmpResult",tmpResult);
        int tmpLength = tmpResult.length();
        byte[] tmp = subBytes(pRecvRes, 2, tmpLength / 2);
        String chineseStr = null;
        try {
            chineseStr = new String(tmp, "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return chineseStr;
    }

    //将Byte[]转换成String
    public static String byte2String(byte[] buffer, int bufferLength) throws UnsupportedEncodingException {
        String bufferString = "";
        String dbgString = "";
        for (int i = 0; i < bufferLength; i++) {
            String hexChar = Integer.toHexString(buffer[i] & 0xFF);
            if (hexChar.length() == 1) {
                hexChar = "0" + hexChar;
            }

            if (i % 16 == 0) {
                if (dbgString != "") {
                    bufferString += dbgString;
                    dbgString = "";
                }
            }
            dbgString += hexChar;
        }

        if (dbgString != "") {
            bufferString += dbgString;
        }
        return bufferString;
    }

    //将String转成byte[]
    public static byte[] toByteArray(String hexString) {
        int hexStringLength = hexString.length();
        byte[] byteArray = null;
        int count = 0;
        char c;
        int i;

        // Count number of hex characters
        for (i = 0; i < hexStringLength; i++) {
            c = hexString.charAt(i);
            if (c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a'
                    && c <= 'f') {
                count++;
            }
        }

        byteArray = new byte[(count + 1) / 2];
        boolean first = true;
        int len = 0;
        int value;
        for (i = 0; i < hexStringLength; i++) {
            c = hexString.charAt(i);
            if (c >= '0' && c <= '9') {
                value = c - '0';
            } else if (c >= 'A' && c <= 'F') {
                value = c - 'A' + 10;
            } else if (c >= 'a' && c <= 'f') {
                value = c - 'a' + 10;
            } else {
                value = -1;
            }

            if (value >= 0) {
                if (first) {
                    byteArray[len] = (byte) (value << 4);
                } else {
                    byteArray[len] |= value;
                    len++;
                }
                first = !first;
            }
        }
        return byteArray;
    }

    //截取byte数组
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++) bs[i - begin] = src[i];
        return bs;
    }

}
