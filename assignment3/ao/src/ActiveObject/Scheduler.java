package ActiveObject;

import ActiveObject.MethodRequest.MethodRequest;
import ActiveObject.MethodRequest.MethodRequestTake;
import ActiveObject.MethodRequest.MethodRequestPut;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler implements Runnable{

    private final ActivationQueue activationQueue = new ActivationQueue();
    private final Lock lock = new ReentrantLock();
    private final Condition queueEmpty = lock.newCondition();

    public void enqueue(MethodRequest methodRequest){
        lock.lock();
        activationQueue.enqueue(methodRequest);
        queueEmpty.signal();
        lock.unlock();
    }

    public void run(){
        while(true){
            boolean operationPerformed = false;
            lock.lock();
            MethodRequestPut methodRequestPut = activationQueue.getPutRequest();
            MethodRequestTake methodRequestTake = activationQueue.getTakeRequest();
            lock.unlock();

            if(methodRequestPut!=null && methodRequestPut.guard()){
                lock.lock();
                activationQueue.dequeuePutRequest();
                lock.unlock();
                methodRequestPut.execute();
                operationPerformed = true;
            }

            if(methodRequestTake!=null && methodRequestTake.guard()){
                lock.lock();
                activationQueue.dequeueTakeRequest();
                lock.unlock();
                methodRequestTake.execute();
                operationPerformed = true;
            }

            if(!operationPerformed){
                try {
                    lock.lock();
                    while(activationQueue.isEmpty()) {
                        queueEmpty.await();
                    }
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
