package ProducersConsumers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private ReentrantLock lock = new ReentrantLock();
    private Condition producerLock = lock.newCondition();
    private Condition consumerLock = lock.newCondition();
    private Condition firstProducerLock = lock.newCondition();
    private Condition firstConsumerLock = lock.newCondition();

    int counter = 0;
    private List<Integer> buf = new ArrayList<>();
    private int maxNumber;

    public Buffer(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void put(int numberOfElements) throws InterruptedException {
        lock.lock();
        while(lock.hasWaiters(firstProducerLock)){
            producerLock.await();
        }
        while(buf.size() + numberOfElements > maxNumber) {
            System.out.println("Producent " + Thread.currentThread().getId() + "czeka na dodanie " + numberOfElements +", stan bufora: " + buf.size());
            firstProducerLock.await();
        }
        for(int i=0; i<numberOfElements; i++) {
            counter++;
            buf.add(counter);
            //System.out.println("Producent " + Thread.currentThread().getId() + " wyprodukowal " + counter +", stan bufora: " + buf.size());
        }
        System.out.println("Producent " + Thread.currentThread().getId() + " wyprodukowal " + numberOfElements +" elementow, stan bufora: " + buf.size());
        producerLock.signal();
        firstConsumerLock.signal();

        lock.unlock();
    }

    public void take(int numberOfElements) throws InterruptedException {
        lock.lock();
        while(lock.hasWaiters(firstConsumerLock)){
            consumerLock.await();
        }
        while(buf.size() < numberOfElements) {
            System.out.println("Konsument " + Thread.currentThread().getId() + " czeka na pobranie " + numberOfElements +", stan bufora: " + buf.size());
            firstConsumerLock.await();
        }

        for(int i=0; i<numberOfElements; i++) {
            int consumedValue = buf.get(0);
            buf.remove(0);
            //System.out.println("Konsument " + Thread.currentThread().getId() + " konsumuje " + consumedValue +", stan bufora: " + buf.size());
        }
        System.out.println("Konsument " + Thread.currentThread().getId() + " skonsumowal " + numberOfElements +" elementow, stan bufora: " + buf.size());
        consumerLock.signal();
        firstProducerLock.signal();
        lock.unlock();
    }
}