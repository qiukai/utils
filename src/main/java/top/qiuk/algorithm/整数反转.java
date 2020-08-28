package top.qiuk.algorithm;

public class 整数反转 {


    public static void main(String[] args) {
        new 整数反转().reverse(1);
    }

    public int reverse(int x) {

        int maxValue = Integer.MAX_VALUE;
        int minValue = Integer.MIN_VALUE;


        String xStr = String.valueOf(x);
        char[] chars = xStr.toCharArray();

        int length = chars.length;
        char[] chars1 = new char[length];

        boolean b = true;

        int i = 0;
        if (chars[0] == '-') {
            chars1[0] = '-';
            i = 1;
            b = false;
        }

        for (; i < chars1.length; i++) {

            if(b){

                chars1[i] = chars[length-i-1];
            } else {
                chars1[i] = chars[length-i];
            }
        }

        return 0;
    }


}
