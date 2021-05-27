package com.mattmalec.pterodactyl4j.utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// i love you jda
public class LockUtils {

    public static void locked(ReentrantLock lock, Runnable task) {
        try {
            tryLock(lock);
            task.run();
        } finally {
            if (lock.isHeldByCurrentThread())
                lock.unlock();
        }
    }

    public static void tryLock(Lock lock) {
        try {
            if (!lock.tryLock() && !lock.tryLock(10, TimeUnit.SECONDS))
                throw new IllegalStateException("Could not acquire lock in a reasonable timeframe! (10 seconds)");
        } catch (InterruptedException e) {
            throw new IllegalStateException("Unable to acquire lock while thread is interrupted!");
        }
    }

}
