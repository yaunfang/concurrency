package com.qiao.example.concurrency.example.atomic;


/*
*
*
* */

import com.qiao.example.concurrency.annoations.ThreadSafe;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
@Slf4j
public class AtomicExample4 {

    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        count.compareAndSet(0,11);
        count.compareAndSet(0,100);
        count.compareAndSet(100,500);
        count.compareAndSet(50,120);
        count.compareAndSet(100,200);
        log.info("coun:{}",count.get());
    }
}
