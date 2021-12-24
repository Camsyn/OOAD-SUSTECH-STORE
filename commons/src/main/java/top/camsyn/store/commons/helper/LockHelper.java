package top.camsyn.store.commons.helper;

import lombok.SneakyThrows;
import org.springframework.integration.support.locks.LockRegistry;
import top.camsyn.store.commons.exception.LockException;
import top.camsyn.store.commons.func.Supplier;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class LockHelper {
    @SneakyThrows
    public static void tryLock(Lock lock, int time, TimeUnit timeUnit){
        try {
            if (!lock.tryLock(time, timeUnit)) {
                lock.unlock();
                throw new LockException("竞争锁失败");
            }
        } catch (InterruptedException e) {
            lock.unlock();
            throw new LockException(e);
        }
    }

    public static void tryLock(Lock lock){
        tryLock(lock, 10, TimeUnit.SECONDS);
    }

    public static void unlock(Lock lock){
        lock.unlock();
    }

    @SneakyThrows
    public static <T> T lockTask(LockRegistry lockRegistry, Object lockKey, Supplier<T> supplier){
        Lock lock = lockRegistry.obtain(lockKey.toString());
        try {
            tryLock(lock);
            return supplier.get();
        }finally {
            unlock(lock);
        }
    }
}
