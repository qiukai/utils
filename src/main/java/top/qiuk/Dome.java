package top.qiuk;


import java.util.Arrays;

public class Dome {

    public static void main(String[] args) throws Exception {


        int[] nums = new int[]{1, 3, 5, 2};
        new Dome().sort(nums);

        System.out.println(Arrays.toString(nums));

    }




    public static void sort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j <= nums.length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp;
                    temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }


}