package com.qiao.example.concurrency.example.atomic;


/*
*
* */

import com.qiao.example.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@ThreadSafe
@Slf4j
public class AtomicExample6 {

    // 定义一个AtomicBoolean类
    private static AtomicBoolean is_update = new AtomicBoolean(false);

    // 定义线程数
    public static int clientTotal = 1000;

    // 定义并发数
    public static int threadTotal = 200;

    public static void main(String[] args) throws Exception {

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
                    test();
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
        // 日志打印结果
        log.info("is_update:{}"+is_update.get());
    }

    /*定义Test方法（只执行一次）*/

    public static void test() {
        if (is_update.compareAndSet(false,true)){
            log.info("**********我执行了一次**********");
        }
    }
}
