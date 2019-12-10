package com.qiao.example.concurrency.example.atomic;


/*
*
*   1、利用AtomicLong方法
*       底层是一个do...while()循环，通过判断底层的值是否与预期的值一致，否则继续循环。
* */

import com.qiao.example.concurrency.annoations.ThreadSafe;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@ThreadSafe
@Slf4j
public class AtomicExample2 {

    // 定义线程数
    public static int clientTotal = 10000;

    // 定义并发数
    public static int threadTotal = 200;

    //初始化(计数的值)
    public static AtomicLong count = new AtomicLong(0);

    @SneakyThrows
    public static void main(String[] args) {

        //定义一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //定义一个信号量（允许的并发数）
        final Semaphore semaphore = new Semaphore(threadTotal);

        //定义CountDownLatch
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i=0; i<clientTotal; i++){
            executorService.execute(() ->{
                try{
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    log.error("exception", 2);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        //关闭线程池
        executorService.shutdown();

        log.info("count:{}", count.get());
    }

    /*
    * 定义一个累加的方法
    * */
    private static void add(){
        // 先做增加操作，在获取当前值。
        count.incrementAndGet();

        // 先获取值，在增加。
        // count.getAndIncrement();
    }
}
