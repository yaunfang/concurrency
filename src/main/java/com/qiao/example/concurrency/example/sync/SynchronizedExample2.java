package com.qiao.example.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* Synchronized锁，依赖于JVM
* */
@Slf4j
public class SynchronizedExample2 {

    /*Synchronized修饰类*/
    public void test1(int j){
        synchronized (SynchronizedExample2.class){
            for (int i=0;i<10;i++){
                log.info("test {} - {}",j,i);
            }
        }
    }

    /*Synchronized修饰静态方法*/
    public static synchronized void test2(int j){
        for (int i=0;i<10;i++){
            log.info("test {} - {}",j,i);
        }
    }

    /*一个线程执行完成后在执行另一个*/
    public static void main(String[] args) {

        // 实例
        SynchronizedExample2 synchronizedExample1 = new SynchronizedExample2();
        SynchronizedExample2 synchronizedExample2 = new SynchronizedExample2();

        // 定义一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 定义线程
        executorService.execute(() ->{
            synchronizedExample1.test2(1);
        });
        executorService.execute(()->{
            synchronizedExample2.test2(2);
        });
    }
}
