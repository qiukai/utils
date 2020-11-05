package top.qiuk.lock;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueue<T> {

    private Lock lock = new ReentrantLock();

    private Condition pushCondition = lock.newCondition();

    private Condition pollCondition = lock.newCondition();

    private List<T> list = new LinkedList<>();

    private int maxSize;

    private int currSize = 0;

    public MyBlockingQueue() {
        this(Integer.MAX_VALUE);
    }

    public MyBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void add(T t) {

        try {
            lock.lock();
            while (currSize >= maxSize) {
                pushCondition.await();
            }
            list.add(t);
            if (++currSize < maxSize) {
                pushCondition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        signalCondition(pollCondition);
    }

    public T poll() {
        T t = null;
        try {
            lock.lock();
            while (currSize <= 0) {
                pollCondition.await();
            }
            t = list.get(0);
            list.remove(0);
            if (--currSize > 0) {
                pollCondition.signal();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        signalCondition(pushCondition);
        return t;
    }

    private void signalCondition(Condition condition) {

        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        return currSize;
    }


}
