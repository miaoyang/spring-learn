package com.ym.learn.test.lock;

import lombok.extern.slf4j.Slf4j;

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

    public static void main(String[] args) {
       ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
       for (int i = 0; i < 100; i++){
           reentrantLockTest.test();
       }
    }

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
}
