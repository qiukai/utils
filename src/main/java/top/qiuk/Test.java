package top.qiuk;

public class Test {
    public static void main(String[] args) throws InterruptedException {


        int[] ints = new int[]{1,2,3,4,5};


        int i = new Solution().minSubArrayLen(10, ints);
        System.out.println(i);


    }
}
