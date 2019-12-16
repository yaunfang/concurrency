package com.qiao.example.concurrency.example.atomic;


/*
*
* AtomicReferenceFiledUpdate类说明：
*   原子性的去更新一个类的实例
* */

import com.qiao.example.concurrency.annoations.ThreadSafe;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;

@ThreadSafe
@Slf4j
public class AtomicExample5 {

    private static AtomicIntegerFieldUpdater<AtomicExample5> update = AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class,"count");

    // 这个变量必须是非静态且volatile的
    private volatile int count  = 597;

    private static AtomicExample5 atomic5 = new AtomicExample5();

    public static void main(String[] args) {
        if (update.compareAndSet(atomic5,597,120)){
            log.info("update 1 ",atomic5);
        }

        if (update.compareAndSet(atomic5,597,110)){
            log.info("update 2",atomic5);
        }else{
            log.info("update 3",atomic5);
        }

    }
}
