package com.shenl.utils.MyUtils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class B2SorS2B {
	/**
	 * TODO 功能：位图转换base64字符编码
	 * 
	 * @param：bitmap:传入的需要转码的位图
	 * @author：沈 亮
	 * @Data：上午10:15:00
	 */
	public static String Bitmap2String(Bitmap bitmap) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, bos);// 参数100表示不压缩
		byte[] bytes = bos.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}
	
	/**
	 * TODO 功能：Base64编码转位图
	 * 
	 * @param：
	 * @author：沈 亮
	 * @Data：下午12:49:21
	 */
	public static Bitmap String2Bitmap(String imgString) {
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(imgString, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}
}
