package top.qiuk.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dome {

    public static void main(String[] args) throws InterruptedException {


        int maxSize = 10;
        MyBlockingQueue<Integer> myQueue = new MyBlockingQueue<>(maxSize);


        int k = 1000;

        int proThreadSize = 20;
        int couThreadSize = 5;


        ExecutorService executorService = Executors.newFixedThreadPool(proThreadSize);
        ExecutorService executorService1 = Executors.newFixedThreadPool(couThreadSize);

        for (int j = 0; j < proThreadSize; j++) {
            executorService.execute(() -> {

                for (int i = 0; i < k; i++) {
                    myQueue.add(i);
                }

            });
        }


        for (int j = 0; j < couThreadSize; j++) {
            executorService1.execute(() -> {
                for (int i = 0; i < k * proThreadSize / couThreadSize; i++) {
                    int size = myQueue.size();
                    if (size > maxSize)
                        System.out.println("----" + size + "---maxSize---" + maxSize);

                    try {

                        Integer poll = myQueue.poll();
                        System.out.println("+" + poll + "------" + i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }


//        countDownLatch.await();
        executorService1.shutdown();
        executorService.shutdown();
    }


}
