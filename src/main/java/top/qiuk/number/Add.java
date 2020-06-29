package top.qiuk.number;

import top.qiuk.constant.ExceptionTypeEnum;
import top.qiuk.constant.StringType;
import top.qiuk.exception.UtilRuntimeException;

import java.util.*;

/**
 * 数组自增
 * Created by 邱凯 on 2017/1/18.
 */
public class Add {

    private volatile static Map<String, char[]> stringCharMap = new HashMap<String, char[]>();

    static char[] nChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    static char[] sChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    static char[] nsChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private String key;
    private int length;

    private static Set<String> set = new HashSet<String>();

    private static Add add = new Add();

    /**
     * 对外公开的唯一的获取字符串的方法
     * @param stringType 字符串类型
     * @param length 字符串长度
     * @return 字符串
     */
    public static String getNextString(StringType stringType, int length) {
        return add.getId(stringType, length);
    }

    private String getId(StringType stringType, int length) {
        synchronized ("id") {
            this.key = this.getKey(stringType, length);
            this.length = length;

            char[] chars = null;
            chars = stringCharMap.get(key);

            if (stringType.equals(StringType.NUMBER)) {
                if (null == chars) {
                    chars = this.init(nChars);
                }
                return this.addJ(chars, length - 1, nChars);
            }
            if (stringType.equals(StringType.STRING)) {
                if (null == chars) {
                    chars = this.init(sChars);
                }
                return this.addJ(chars, length - 1, sChars);
            }
            if (stringType.equals(StringType.STRING_NUMBER)) {
                if (null == chars) {
                    chars = this.init(nsChars);
                }
                return this.addJ(chars, length - 1, nsChars);
            }
        }
        throw new UtilRuntimeException(ExceptionTypeEnum.EXCEPTION_TYPE_ENUM);
    }

    private String addC(char[] chars, int i, char[] _chars) {
        for (int j = 0, k; j < _chars.length; j++) {
            k = j + 1;
            if (chars[i] == _chars[j]) {
                if (k == _chars.length) {
                    chars[i] = _chars[0];
                    return null;
                } else {
                    chars[i] = _chars[k];
                }
                return createId(chars);
            }
        }
        return null;
    }

    private String addJ(char[] chars, int i, char[] _chars) {
        String id = this.addC(chars, i, _chars);
        if (null != id) {
            return id;
        }
        return addJ(chars, i - 1, _chars);
    }

    /**
     * 初始化 char
     *
     * @param _chars
     * @return
     */
    private char[] init(char[] _chars) {
        char[] c = new char[length];
        for (int i = 0; i < length; i++) {
            c[i] = _chars[0];
        }
        return c;
    }

    private String createId(char[] chars) {
        stringCharMap.put(key, chars);
        return new String(chars);
    }

    private String getKey(StringType stringType, int length) {
        if (StringType.NUMBER.equals(stringType))
            return "n" + length;
        if (StringType.STRING.equals(stringType))
            return "s" + length;
        if (StringType.STRING_NUMBER.equals(stringType))
            return "sn" + length;
        throw new UtilRuntimeException(ExceptionTypeEnum.EXCEPTION_TYPE_ENUM);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            String nextString = Add.getNextString(StringType.STRING_NUMBER, 3);
            System.out.println(nextString);
        }

    }
}
