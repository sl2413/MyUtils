package com.shenl.utils.MyUtils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {

	/**
	 * TODO 功能：把一个map变成json字符串
	 * 
	 * @param：
	 * @author：沈 亮
	 * @Data：上午10:15:54
	 */
	public static String parseMapToJson(Map<?, ?> map) {
		try {
			Gson gson = new Gson();
			return gson.toJson(map);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * TODO 功能：把一个json字符串变成对象
	 * 
	 * @param：
	 * @author：沈 亮
	 * @Data：上午10:16:07
	 */
	public static <T> T parseJsonToBean(String json, Class<T> cls) {
		Gson gson = new Gson();
		T t = null;
		try {
			t = gson.fromJson(json, cls);
		} catch (Exception e) {
		}
		return t;
	}

	/**
	 * TODO 功能：把json字符串变成map
	 * 
	 * @param：
	 * @author：沈 亮
	 * @Data：上午10:16:17
	 */
	public static HashMap<String, Object> parseJsonToMap(String json) {
		Gson gson = new Gson();
		Type type = new TypeToken<HashMap<String, Object>>() {
		}.getType();
		HashMap<String, Object> map = null;
		try {
			map = gson.fromJson(json, type);
		} catch (Exception e) {
		}
		return map;
	}

	/**
	 * TODO 功能：把json字符串变成集合
	 * 
	 * @param：
	 * @author：沈 亮
	 * @Data：上午10:16:33
	 */
	public static List<?> parseJsonToList(String json, Type type) {
		Gson gson = new Gson();
		List<?> list = gson.fromJson(json, type);
		return list;
	}

	/**
	 * TODO 功能：获取json串中某个字段的值，注意，只能获取同一层级的value
	 * 
	 * @param：
	 * @author：沈 亮
	 * @Data：上午10:16:47
	 */
	public static String getFieldValue(String json, String key) {
		if (TextUtils.isEmpty(json))
			return null;
		if (!json.contains(key))
			return "";
		JSONObject jsonObject = null;
		String value = null;
		try {
			jsonObject = new JSONObject(json);
			value = jsonObject.getString(key);
			
		} catch (JSONException e) {
			System.out.print("报错的key:"+key);
			e.printStackTrace();
		}
		return value;
	}

}
