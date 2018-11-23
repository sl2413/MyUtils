package com.shenl.utils.MyUtils;

public class ValidUtils {
	/**
	 * TODO 功能：验证是否为正确的手机号
	 *
	 * 参数说明:
	 * 作    者:   沈 亮
	 * 创建时间:   2018/11/23
	 */
	public static boolean isPhone(String number) {
		String reg="(^13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
		boolean matches = number.matches(reg);
		return matches;
	}

	/**
	 * TODO 功能：验证是否为正确的身份证号
	 *
	 * 参数说明:
	 * 作    者:   沈 亮
	 * 创建时间:   2018/11/23
	 */
	public static boolean isIdCard(String idCard){
		String reg="^\\d{6}(18|19|20)?\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$";
		boolean matches = idCard.matches(reg);
		return matches;
	}

	/**
	 * TODO 功能：验证是否为正确的email地址
	 *
	 * 参数说明:
	 * 作    者:   沈 亮
	 * 创建时间:   2018/11/23
	 */
	public static boolean isEmail(String email){
		String reg="^[-_A-Za-z0-9]+@([_A-Za-z0-9]+.)+[A-Za-z0-9]{2,3}$";
		boolean matches = email.matches(reg);
		return matches;
	}

}
