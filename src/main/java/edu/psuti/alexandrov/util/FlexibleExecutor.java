package edu.psuti.alexandrov.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FlexibleExecutor extends ThreadPoolExecutor {

    private volatile boolean isFree = true;
    private volatile boolean isPaused = false;
    private final Lock pauseLock = new ReentrantLock();
    private final Condition unpaused = pauseLock.newCondition();

    public FlexibleExecutor() {
        super(4, 4, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    }

    public boolean isFree() {
        return isFree;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signal();
        } finally {
            pauseLock.unlock();
        }
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        isFree = false;
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused) {
                unpaused.await();
            }
        } catch (InterruptedException e) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        isFree = true;
    }
}
