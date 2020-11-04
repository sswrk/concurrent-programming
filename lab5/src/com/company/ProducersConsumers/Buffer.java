package com.company.ProducersConsumers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private Lock lock = new ReentrantLock();
    private Condition producerLock = lock.newCondition();
    private Condition consumerLock = lock.newCondition();
    private Condition firstProducerLock = lock.newCondition();
    private Condition firstConsumerLock = lock.newCondition();
    private boolean firstProducerIsEmpty = true;
    private boolean firstConsumerIsEmpty = true;

    int counter = 0;
    private List<Integer> buf = new ArrayList<>();
    private int maxNumber;

    public Buffer(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void put(int numberOfElements) throws InterruptedException {
        lock.lock();
        while(!firstProducerIsEmpty) {
            producerLock.await();
        }
        while(buf.size() + numberOfElements > maxNumber) {
            System.out.println("Producent " + Thread.currentThread().getId() + "czeka na dodanie " + numberOfElements +", stan bufora: " + buf.size());
            firstProducerIsEmpty = false;
            firstProducerLock.await();
        }
        for(int i=0; i<numberOfElements; i++) {
            counter++;
            buf.add(counter);
            System.out.println("Producent " + Thread.currentThread().getId() + " wyprodukowal " + counter);
        }
        firstProducerIsEmpty = true;
        producerLock.signal();
        firstConsumerLock.signal();

        lock.unlock();
    }

    public void take(int numberOfElements) throws InterruptedException {
        lock.lock();
        while(!firstConsumerIsEmpty) {
            consumerLock.await();
        }
        while(buf.size() < numberOfElements) {
            System.out.println("Konsument " + Thread.currentThread().getId() + " czeka na pobranie " + numberOfElements +", stan bufora: " + buf.size());
            firstConsumerIsEmpty = false;
            firstConsumerLock.await();
        }

        for(int i=0; i<numberOfElements; i++) {
            int consumedValue = buf.get(0);
            buf.remove(0);
            System.out.println("Konsument " + Thread.currentThread().getId() + " konsumuje " + consumedValue);
        }
        firstConsumerIsEmpty = true;
        consumerLock.signal();
        firstProducerLock.signal();
        lock.unlock();
    }
}