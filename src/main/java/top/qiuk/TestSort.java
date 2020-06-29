package top.qiuk;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class TestSort {
    public static int MAXSIZE = 61;
    public static void sortInt(int[] arr) { // 采用选择法对一维数组进行排序
        for (int i = 0; i < arr.length - 1; i++) {
            int k = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j] < arr[k])
                    k = j; // 用k记录最小值的下标
            if (k > i) { // 在外循环中实施交换
                arr[i] = arr[i] + arr[k];
                arr[k] = arr[i] - arr[k];
                arr[i] = arr[i] - arr[k];
            }
        }
    }

    public static void main(String args[]) {
        int score[] = new int[MAXSIZE];
        try {
            for (int i = 0; i < MAXSIZE; i++)
                score[i] = (int) (Math.random() * 100 + 0.5);
            sortInt(score);
            BufferedWriter dout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("score.txt")));
            for (int i = 60; i >= 0; i--) {
                dout.write(score[i]+"\n");
                System.out.println(score[i]);
            }
            dout.close();
            // 结果保存到文件
        } catch (Exception e) {
            System.err.println("发生异常：" + e);
                    e.printStackTrace();
        }
        // try-catch结构处理异常
    }
}

