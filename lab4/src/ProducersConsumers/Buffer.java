package ProducersConsumers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private Lock lock = new ReentrantLock();
    private Condition producerLock = lock.newCondition();
    private Condition consumerLock = lock.newCondition();

    int counter = 0;
    private List<Integer> buf = new ArrayList<>();
    private int maxNumber;

    public Buffer(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void put(int numberOfElements) throws InterruptedException {
        lock.lock();
        while(buf.size() + numberOfElements > maxNumber) {
            System.out.println("Producent " + Thread.currentThread().getId() + "czeka");
            producerLock.await();
        }
        for(int i=0; i<numberOfElements; i++) {
            counter++;
            buf.add(counter);
            System.out.println("Producent " + Thread.currentThread().getId() + " wyprodukowal " + counter);
        }
        consumerLock.signal();
        lock.unlock();
    }

    public void take(int numberOfElements) throws InterruptedException {
        lock.lock();
        while(buf.size() < numberOfElements) {
            System.out.println("Konsument " + Thread.currentThread().getId() + " czeka");
            consumerLock.await();
        }

        for(int i=0; i<numberOfElements; i++) {
            int consumedValue = buf.get(0);
            buf.remove(0);
            System.out.println("Konsument " + Thread.currentThread().getId() + " konsumuje " + consumedValue);
        }
        producerLock.signal();
        lock.unlock();
    }
}