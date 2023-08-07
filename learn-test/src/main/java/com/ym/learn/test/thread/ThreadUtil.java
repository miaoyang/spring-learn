package com.ym.learn.test.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/24 20:11
 * @Desc:
 */
public class ThreadUtil {
    private static final AtomicInteger nextId = new AtomicInteger(0);
    private final static ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return String.valueOf(nextId.getAndIncrement());
        }
    };

    public static int getNextId() {
        return nextId.get();
    }

    @Test
    public void testGetNextId(){
        for (int i = 0; i < 10; i++) {
            System.out.println("ThreadId: "+Thread.currentThread()+" nextId: " + i);
        }
    }

}
