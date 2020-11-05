package top.qiuk.algorithm;

public class 有序数组交集 {


    public static void main(String[] args) {
        int[] array1 = {1,2,3,4,5};
        int[] array2 = {3,4,5,6};

        int[] ints = retainAll(array1, array2);

        for (int anInt : ints) {
            System.out.println(anInt);
        }


        char[] chars1 = {'a','b','c','d'};
        char[] chars2 = {'a','c','d'};

        char c = find(chars1, chars2);
        System.out.println(c);

        lock();
    }


    public static int[] retainAll(int[] array1, int[] array2) {


        int num1 = 0;
        int num2 = 0;
        int resultNum = 0;
        int min = Math.min(array1.length, array2.length);
        int[] temp = new int[min];
        while (num1 < array1.length && num2 < array2.length) {
            if (array1[num1] == array2[num2]) {
                temp[resultNum] = array1[num1];
                num1++;
                num2++;
                resultNum++;
                continue;
            }
            if (array1[num1] < array2[num2]) {
                num1++;
                continue;
            }
            if (array1[num1] > array2[num2]) {
                num2++;
            }
        }
        int[] result = new int[resultNum];
        for (int i = 0; i < result.length; i++) {
            result[i] = temp[i];
        }
        return result;

    }


    /*
    select distinct id,name
from (
select user.id as id,user.username as name
from blog left join user on blog.user_id = user.id where blog.add_time > '2018-05-30' and blog.add_time < '2018-05-31') temp

select user_id
from (
select count(*) count,user_id
from blog where add_time>'2018-05-01' and add_time<'2018-06-01' group by user_id) temp order by count desc limit 10
     */

    public static char find(char[] chars1,char[] chars2) {

        for (int i = 0; i < chars1.length; i++) {
            if (chars1[i] != chars2[i]) {
                return chars1[i];
            }
        }

        throw new RuntimeException("没有不同的字符");
    }


    public static void lock(){
        new Thread(()->{

            while (true) {
                synchronized ("AA") {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized ("BB"){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("lock1");
                    }
                }
            }



        }).start();

        new Thread(()->{

            while (true) {
                synchronized ("BB") {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized ("AA"){
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("lock2");
                    }

                }
            }


        }).start();
    }


}
