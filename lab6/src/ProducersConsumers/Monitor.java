package ProducersConsumers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private ReentrantLock lock = new ReentrantLock();
    private Condition producerLock = lock.newCondition();
    private Condition freeConsumer = lock.newCondition();
    List<Condition> producerWithAssignment = new ArrayList<>();
    List<Condition> consumerWithAssignment = new ArrayList<>();

    private List<Integer> empty = new ArrayList<>();
    private List<Integer> busy = new ArrayList<>();
    private int maxNumber;

    public Monitor(int maxNumber) {
        this.maxNumber = maxNumber;
        for(int i=0; i<maxNumber; i++){
            empty.add(i);
            consumerWithAssignment.add(lock.newCondition());
        }
    }

    public int startProduction() throws InterruptedException {
        lock.lock();
        int current = -1;
        while(empty.isEmpty()) {
            producerLock.await();
        }
        current = empty.get(0);
        empty.remove(0);
        lock.unlock();
        return current;
    }

    public void endProduction(int current){
        lock.lock();
        busy.add(current);
        freeConsumer.signal();
        lock.unlock();
    }

    public int startConsumption() throws InterruptedException {
        lock.lock();
        int current = -1;
        while(busy.isEmpty()){
            freeConsumer.await();
        }
        current = busy.get(0);
        busy.remove(0);
        lock.unlock();
        return current;
    }

    public void endConsumption(int current){
        lock.lock();
        empty.add(current);
        producerLock.signal();
        lock.unlock();
    }
}