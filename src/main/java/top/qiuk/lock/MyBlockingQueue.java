package top.qiuk.lock;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueue<T> implements Queue<T> {

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

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
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
            return false;
        } finally {
            lock.unlock();
        }
        signalCondition(pollCondition);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean offer(T t) {
        return false;
    }

    @Override
    public T remove() {
        return null;
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

    @Override
    public T element() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    private void signalCondition(Condition condition) {

        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }




}
