package com.ym.learn.test.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/9 09:54
 * @Desc: 重入锁
 */
@Slf4j
public class ReentrantLockTest {
    private static final ReentrantLock lock = new ReentrantLock();
    private ExecutorService executorService = Executors.newFixedThreadPool(5);


    @Test
    public void test(){
        lock.lock();
        try {
            executorService.execute(()->{
                log.info("当前线程：{}",Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception e) {
            log.error("加锁失败",e);
        }finally {
            lock.unlock();
        }
    }

    public void test2() throws InterruptedException {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        try {
            reentrantLockTest.wait(1000);
        }catch (InterruptedException e) {

        }


    }
}
