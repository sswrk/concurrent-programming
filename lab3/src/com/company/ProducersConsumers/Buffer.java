package com.company.ProducersConsumers;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
    int counter = 0;
    private List<Integer> buf = new ArrayList<>();
    private int maxNumber;

    public Buffer(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public synchronized void put() throws InterruptedException {
        while(buf.size() >= maxNumber) {
            System.out.println("Producent " + Thread.currentThread().getId() + "czeka");
            wait();
        }
        counter++;
        buf.add(counter);
        System.out.println("Producent " + Thread.currentThread().getId() + " wyprodukowal " + counter);
        notify();
    }

    public synchronized void take() throws InterruptedException {
        while(buf.size() <= 0) {
            System.out.println("Konsument " + Thread.currentThread().getId() + " czeka");
            wait();
        }
        int consumedValue = buf.get(0);
        buf.remove(0);
        System.out.println("Konsument " + Thread.currentThread().getId() + " konsumuje " + consumedValue);
        notify();
    }
}