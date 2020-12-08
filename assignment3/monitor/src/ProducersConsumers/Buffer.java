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
    private boolean firstProducerIsEmpty = true;
    private boolean firstConsumerIsEmpty = true;
    private int numberOfOperations;
    private int operationsCount;

    private long startTime = 0;

    int counter = 0;
    private List<Integer> buf = new ArrayList<>();
    private int maxNumber;

    public Buffer(int maxNumber, int numberOfOperations) {
        this.maxNumber = maxNumber;
        this.numberOfOperations = numberOfOperations;
        startTime = System.currentTimeMillis();
    }

    public void put(int numberOfElements) throws InterruptedException {
        lock.lock();
        while(!firstProducerIsEmpty) {
            producerLock.await();
        }
        while(buf.size() + numberOfElements > maxNumber) {
            //System.out.println("Producent " + Thread.currentThread().getId() + "czeka na dodanie " + numberOfElements +", stan bufora: " + buf.size());
            firstProducerIsEmpty = false;
            firstProducerLock.await();
        }
        for(int i=0; i<numberOfElements; i++) {
            counter++;
            buf.add(counter);
            //System.out.println("Producent " + Thread.currentThread().getId() + " wyprodukowal " + counter +", stan bufora: " + buf.size());
        }
        operationsCount++;
        if(operationsCount == numberOfOperations){
            printTimeResult();
        }
        //System.out.println("Producent " + Thread.currentThread().getId() + " wyprodukowal " + numberOfElements +" elementow, stan bufora: " + buf.size());
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
            //System.out.println("Konsument " + Thread.currentThread().getId() + " czeka na pobranie " + numberOfElements +", stan bufora: " + buf.size());
            firstConsumerIsEmpty = false;
            firstConsumerLock.await();
        }

        for(int i=0; i<numberOfElements; i++) {
            int consumedValue = buf.get(0);
            buf.remove(0);
            //System.out.println("Konsument " + Thread.currentThread().getId() + " konsumuje " + consumedValue +", stan bufora: " + buf.size());
        }
        operationsCount++;
        if(operationsCount == numberOfOperations){
            printTimeResult();
        }
        //System.out.println("Konsument " + Thread.currentThread().getId() + " skonsumowal " + numberOfElements +" elementow, stan bufora: " + buf.size());
        firstConsumerIsEmpty = true;
        consumerLock.signal();
        firstProducerLock.signal();
        lock.unlock();
    }

    private void printTimeResult(){
        long stopTime = System.currentTimeMillis();
        System.out.println(stopTime - startTime);
    }
}