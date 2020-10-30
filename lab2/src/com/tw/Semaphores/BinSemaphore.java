package com.tw.Semaphores;

public class BinSemaphore
{
    private boolean available = true;

    synchronized public void acquire() throws InterruptedException {
        while (!this.available)
        {
            wait();
        }
        this.available = false;
    }

    synchronized public void release()
    {
        this.available = true;
        notify();
    }
}
