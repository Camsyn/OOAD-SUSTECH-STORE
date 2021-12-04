package com.example.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.redis.util.RedisLockRegistry;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@SpringBootTest
class TestApplicationTests {

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    int tmp1;
    int tmp2;
    private Lock lock;
    private Lock lock2;

    private void testRedisLock() throws InterruptedException {
        tmp1 = 0;
        tmp2 = 0;
        /**
         * 不使用分布式锁
         */
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                tmp1++;
                System.out.println("Thread:" + Thread.currentThread().getId() + "   tmp1=" + tmp1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                tmp1++;
                System.out.println("Thread:" + Thread.currentThread().getId() + "   tmp1=" + tmp1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        if (lock == null) lock = redisLockRegistry.obtain("test");
        if (lock2 == null) lock2 = redisLockRegistry.obtain("test1");
        Thread.sleep(11000);
        System.out.println();
        /**
         * 使用分布式锁示例
         */
        final Thread thread0 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    if (lock.tryLock(30, TimeUnit.SECONDS)) {
                        tmp2++;
                        System.out.println("Thread:" + Thread.currentThread().getId() + "tmp2=" + tmp2);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        thread0.start();

        final Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    if (lock2.tryLock(30, TimeUnit.SECONDS)) {
                        tmp2++;
                        System.out.println("Thread:" + Thread.currentThread().getId() + "tmp2=" + tmp2);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock2.unlock();
                }
            }
        });
        thread1.start();
        thread0.join();
        thread1.join();
    }

    @Test
    void contextLoads() throws InterruptedException {
        testRedisLock();
    }

}
