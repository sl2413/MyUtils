package com.shenl.utils.MyUtils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;


public class ClipBoardUtil {

    /**
     * TODO 功能：复制文字到剪切板
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2020/5/15
     */
    public static void copyText(Context context,String text){
        ClipboardManager cm = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("text",text));
    }

    /**
     * TODO 功能：读取剪切板中内容
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2020/5/15
     */
    public static String paste(Context context){
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
                CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();
                String addedTextString = String.valueOf(addedText);
                if (!TextUtils.isEmpty(addedTextString)) {
                    return addedTextString;
                }
            }
        }
        return "";
    }

    /**
     * 清空剪切板
     */
    public void clear(Context context){
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            try {
                manager.setPrimaryClip(manager.getPrimaryClip());
                manager.setPrimaryClip(ClipData.newPlainText("",""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
