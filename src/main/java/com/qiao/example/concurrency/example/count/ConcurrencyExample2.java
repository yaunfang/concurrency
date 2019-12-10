package com.qiao.example.concurrency.example.count;


/*
*   原子性
*   1、利用Atomic包
*       通过死循环不断的尝试修改直到修改成功，
*       这样在低并发的情况下修改成功的几率挺高的，但是在高并发的情况下几率就会大大降低。
* */

import com.qiao.example.concurrency.annoations.ThreadSafe;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Slf4j
public class ConcurrencyExample2 {

    // 定义线程数
    public static int clientTotal = 10000;

    // 定义并发数
    public static int threadTotal = 200;

    //初始化(计数的值)
    public static AtomicInteger count = new AtomicInteger(0);

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
