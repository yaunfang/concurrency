package com.qiao.example.concurrency.example.atomic;


/*
*
*
* */

import com.qiao.example.concurrency.annoations.NotThreadSafe;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@NotThreadSafe
@Slf4j
public class AtomicExample1 {

    // 定义线程数
    public static int clientTotal = 1000;

    // 定义并发数
    public static int threadTotal = 200;

    //初始化(计数的值)
    public static int count = 0;

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

        log.info("count:{}", count);
    }

    /*
    * 定义一个累加的方法(多次执行)
    * */
    private static void add(){
        count++;
        log.info("****************我执行了一次*********");
    }
}
