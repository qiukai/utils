package top.qiuk.algorithm;

public class 一个数组中连续最大的最大值数组 {

    static int[] ints = new int[]{-1, 3, -4, 5, -1, 1,0, 9, 10, -4};


    public static void main(String[] args) {
        int[] ints_max = null;
        int max = 0;
        for (int i = 0; i < ints.length; i++) {
            int tempInt = 0;
            for (int j = i; j < ints.length; j++) {
                tempInt += ints[j];
                int max1 = Math.max(max, tempInt);
                if (max1 != max) {
                    ints_max = new int[j-i+1];
                    for (int k = 0 ;k<=j-i;k++) {
                        ints_max[k] = ints[i+k];
                    }
                    max = max1;
                }
            }
        }

        System.out.println("max:" + max);
        for (int i1 = 0; i1 < ints_max.length; i1++) {
            System.out.println(ints_max[i1]);
        }

    }

}
