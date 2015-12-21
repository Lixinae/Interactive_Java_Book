package fr.umlv.javanotebook.validation;

/**
 * Project :Interactive_Java_Book
 * Created by Narex on 21/12/2015.
 */

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockedBlockingBuffer<E> {
    private final ArrayDeque<E> buffer = new ArrayDeque<>();
    private final int capacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition isEmpty = lock.newCondition();
    private final Condition isFull = lock.newCondition();


    public LockedBlockingBuffer(int capacity) {
        this.capacity = capacity;
    }

    public void put(E e) throws InterruptedException {
        lock.lock();
        try {
            while (buffer.size() == capacity) {
                isFull.await();
            }
            buffer.addLast(e);
            isEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (buffer.size() == 0) {
                isEmpty.await();
            }
            isFull.signalAll();
            return buffer.removeFirst();
        } finally {
            lock.unlock();
        }
    }
}
