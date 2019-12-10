package com.qiao.example.concurrency.example.atomic;


/*
*  1、利用LongAdder类
*
*   在低并发的时候可以全部提交，而在高并发的情况下可以将一个数据分散。缺点：在高并发下数据可以有误差。
*
* */

import com.qiao.example.concurrency.annoations.ThreadSafe;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@ThreadSafe
@Slf4j
public class AtomicExample3 {

    // 定义线程数
    public static int clientTotal = 10000;

    // 定义并发数
    public static int threadTotal = 200;

    //初始化(计数的值)
    public static LongAdder count = new LongAdder(); //该方法默认值为0

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
    * 定义一个累加的方法
    * */
    private static void add(){
        count.increment(); //加一
    }
}
