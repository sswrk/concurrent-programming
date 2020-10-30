package com.tw;

import com.tw.Semaphores.BinSemaphore;
import com.tw.Semaphores.Semaphore;

public class Philosopher implements Runnable {
    private BinSemaphore leftFork;
    private BinSemaphore rightFork;

    private Semaphore waiter;

    public Philosopher(BinSemaphore leftFork, BinSemaphore rightFork){
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public Philosopher(BinSemaphore leftFork, BinSemaphore rightFork, Semaphore waiter){
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.waiter = waiter;
    }

    private void think() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + ": zaczynam myslec");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + ": koncze myslec");
    }

    private void eat() throws InterruptedException {
        if(waiter != null) {
            waiter.acquire();
            System.out.println("Waiter: wpuszczam " + Thread.currentThread().getName());
        }
        leftFork.acquire();
        rightFork.acquire();
        System.out.println(Thread.currentThread().getName() + ": zaczynam jesc");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + ": koncze jesc");
        leftFork.release();
        rightFork.release();
        if(waiter != null) {
            System.out.println("Waiter: zegnam " + Thread.currentThread().getName());
            waiter.release();
        }
    }

    @Override
    public void run(){
        try {
            while(true) {
                think();
                eat();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
