package top.qiuk.util;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2015/12/19. String的帮助类
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str
     *            目标字符串
     * @return 如果字符串是空或者长度是0，强返回true否则返回false
     */
    public static boolean isNull(String str) {
        return getLength(str) == 0 ? true : false;
    }

    /**
     * 返回String的长度，
     *
     * 目标字符串
     *
     * @return 如果str是空将返回为0，否则返回长度
     */
    public static int getLength(String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 生成随机的UUID（该uuid可以作为id使用）
     *
     * @return String类型
     */
    public static synchronized String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 将对象的属性以逗号的形式进行拼接生成字符串
     * @param clazz 对象类型
     * @return 拼装好的字符串
     */
    public static String objectToString(Class<?> clazz) {
        return StringUtil.objectToString(clazz, ",");
    }

    /**
     * 将对象的属性以指定的分隔符进行拼接生成字符串
     * @param clazz 对象类型
     * @param sign 分隔符
     * @return 拼装好的字符串
     */
    public static String objectToString(Class<?> clazz,String sign) {
        Field[] fields = clazz.getDeclaredFields();
        String[] strings = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            strings[i] = fields[i].getName();
        }
        return connectionString(strings, sign);
    }

    /**
     * 将字符串数组拼接成一个字符串
     * @param strs 需要拼接的数组
     * @param sign 分隔符
     * @return 拼装好的字符串
     */
    public static String connectionString(String[] strs,String sign) {
        StringBuilder sb = new StringBuilder();
        return connectionString(strs,sign,sb);
    }

    /**
     * 将字符串数组拼接成一个字符串
     * @param strs 需要拼接的数组
     * @param sign 分隔符
     * @param sb 字符串拼装容器
     * @return 拼装好的字符串
     */
    public static String connectionString(String[] strs,String sign,StringBuilder sb) {
        if (null == strs || 0 == strs.length) {
            return "";
        }
        for (String str : strs) {
            sb.append(str).append(sign);
        }
        String str = sb.toString();
        if (!StringUtil.isNull(str)) {
            return str.substring(0,str.length()-1);
        }
        return "";
    }

}