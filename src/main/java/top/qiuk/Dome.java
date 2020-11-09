package top.qiuk;


public class Dome {

    public static void main(String[] args) throws Exception {







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