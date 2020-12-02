package ActiveObject;

import ActiveObject.MethodRequest.MethodRequest;
import ActiveObject.MethodRequest.MethodRequestTake;
import ActiveObject.MethodRequest.MethodRequestPut;

import java.util.concurrent.locks.ReentrantLock;

public class Scheduler implements Runnable{

    private final ActivationQueue activationQueue = new ActivationQueue();
    private final ReentrantLock lock = new ReentrantLock();

    public void enqueue(MethodRequest methodRequest){
        lock.lock();
        activationQueue.enqueue(methodRequest);
        lock.unlock();
    }

    public void run(){
        while(true){
            lock.lock();
            MethodRequestPut methodRequestPut = activationQueue.getPutRequest();
            MethodRequestTake methodRequestTake = activationQueue.getTakeRequest();
            lock.unlock();
            if(methodRequestPut!=null && methodRequestPut.guard()){
                lock.lock();
                activationQueue.dequeuePutRequest();
                lock.unlock();
                methodRequestPut.execute();
            }
            if(methodRequestTake!=null && methodRequestTake.guard()){
                lock.lock();
                activationQueue.dequeueTakeRequest();
                lock.unlock();
                methodRequestTake.execute();
            }
        }
    }
}
