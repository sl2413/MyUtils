package com.shenl.utils.MyUtils;

public class IsPhoneUtils {
	public static boolean isPhone(String number) {
		String reg="[1][356789]\\d{9}";
		boolean matches = number.matches(reg);
		return matches;
		
	}

}
