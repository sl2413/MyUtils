package com.shenl.utils.MyUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPutils {
	
	private static final String CACHE_FILE_NAME = "NEUQSOFT";
	private static SharedPreferences mSharedPreferences;
	
	/**
	 * TODO 功能：向SharedPreferences中存储一个布尔值
	 * 
	 * @param：
	 * @author：沈 亮
	 * @Data：上午10:17:26
	 */
	public static void putBoolean(Context context, String key, boolean value) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putBoolean(key, value).commit();
	}

	/**
	 * TODO 功能：从SharedPreferences中取一个布尔值
	 * 
	 * @param：
	 * @author：沈 亮
	 * @Data：上午10:17:59
	 */
	public static boolean getBoolean(Context context, String key) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getBoolean(key, false);
	}
	
	/**
	 * TODO 功能：向SharedPreferences中存储一个字符串
	 * 
	 * @param：
	 * @author：沈 亮
	 * @Data：上午10:18:10
	 */
	public static void putString(Context context, String key, String value) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putString(key, value).commit();
	}

	/**
	 * TODO 功能：从SharedPreferences中取一个字符串
	 * 
	 * @param：
	 * @author：沈 亮
	 * @Data：上午10:18:23
	 */
	public static String getString(Context context, String key,String value) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getString(key, value);
	}

	/**
	 * TODO 功能：向SharedPreferences中存储一个数字
	 *
	 * @param：
	 * @author：沈 亮
	 * @Data：上午10:18:10
	 */
	public static void putInt(Context context, int key, int value) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putInt(key+"", value).commit();
	}

	/**
	 * TODO 功能：从SharedPreferences中取一个字符串
	 *
	 * @param：
	 * @author：沈 亮
	 * @Data：上午10:18:23
	 */
	public static int getInt(Context context, int key, int value) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getInt(key+"", value);
	}

}
