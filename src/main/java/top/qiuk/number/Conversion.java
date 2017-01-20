package top.qiuk.number;

/**
 * 转化
 * Created by 邱凯 on 2017/1/20.
 */
public class Conversion {


    public static void main(String[] str) {


        String conversion = new Conversion().conversion(Long.MAX_VALUE, Add.nsChars, 0);
        System.out.println(conversion);
    }


    public String conversion(long value, char[] chars, int location) {
        int length = chars.length;
        int i = (int) (value % length);
        if (location != length)
            return conversion(value / length, chars, ++location) + String.valueOf(chars[i]);
        else
            return "";
    }

}
