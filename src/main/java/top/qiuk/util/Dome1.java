package top.qiuk.util;

import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Dome1 {


    public static void main(String[] args) {
//        testHashMap();


        ExecutorService executorService = Executors.newFixedThreadPool(10);


        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {


                int access = access("123");
                System.out.println(access);

            });
        }


    }

//
//    ThreadPoolExecutor threadPoolExecutor =  new ThreadPoolExecutor(5,5,10, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());
//
//    public void work(){
//        boolean b = true;
//        while (b) {
//            //  select * from  你的数据
//            Object data = new Object();  // 你db 里面查出来的数据
//            // 如果db 中没有数据了
//            if (data = null) {
//                b = false;
//            }
//            threadPoolExecutor.execute(()->{
//                upload(data);  // 上传数据
//            });
//        }
//    }


    private static ConcurrentHashMap<String, AtomicInteger> concurrentHashMap = new ConcurrentHashMap<>();

    public static int access(String ip) {
        return concurrentHashMap.computeIfAbsent(ip, (key) -> {
            return new AtomicInteger();
        }).incrementAndGet();
    }


    public static void testHashMap() {


        ExecutorService executorService = Executors.newFixedThreadPool(1);


        HashSet<Object> hashSet = new HashSet<>();
        User user = new User();
        user.setAge(20);
        hashSet.add(user);
        user.setAge(21);
        boolean contains = hashSet.contains(user);
        System.out.println(contains);
    }
}

class User {
    private Integer age;
    private Long id;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(age, user.age) &&
                Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, id);
    }
}
