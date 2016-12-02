package top.qiuk.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {

	/**
	 * 对象执行get方法
	 * @param obj 目标对象
	 * @param att 对象中的属性值
	 * @return 执行结果
	 * @throws Exception
	 */
	public static Object getter(Object obj, String att) throws Exception {
		if (null == obj) {
			return null;
		}
		Method met = obj.getClass().getMethod("get" + iniStr(att));
		return met.invoke(obj);
	}

	/**
	 * 将值赋值给对象
	 * @param obj 目标对象
	 * @param field 属性
	 * @param obj1 值
	 * @throws Exception
	 */
	public static void setter(Object obj, Field field, Object obj1) throws Exception {
		if (obj1 == null) {
			return;
		}
		Method met = obj.getClass().getMethod("set" + iniStr(field.getName()), field.getType());
		met.invoke(obj, field.getType().cast(obj1));
	}

	/**
	 * 将字符串首字母变成大写
	 * @param old 目标字符串
	 * @return
	 */
	public static String iniStr(String old) {
		String str = null;
		if (old.charAt(0) >= 'A' && old.charAt(0) <= 'Z') {
			return old;
		} else {
			str = old.substring(0, 1).toUpperCase() + old.substring(1);
		}
		return str;
	}
}
