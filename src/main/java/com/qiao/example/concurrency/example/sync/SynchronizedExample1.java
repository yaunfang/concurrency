package com.qiao.example.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* Synchronized锁，依赖于JVM
* */
@Slf4j
public class SynchronizedExample1 {

    /*Synchronized修饰代码块，该代码块被称为同步代码块*/
    public void test1(int j){
        synchronized ( this){
            for (int i=0;i<10;i++){
                log.info("test {} - {}",j,i);
            }
        }
    }

    /*Synchronized修饰方法，该方法被称为同步方法*/
    public synchronized void test2(int j){
        for (int i=0;i<10;i++){
            log.info("test {} - {}",j,i);
        }
    }

    /*交替执行*/
    public static void main(String[] args) {

        // 实例
        SynchronizedExample1 synchronizedExample1 = new SynchronizedExample1();
        SynchronizedExample1 synchronizedExample2 = new SynchronizedExample1();

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
