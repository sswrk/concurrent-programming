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
            if(methodRequestPut!=null && methodRequestPut.guard()){
                activationQueue.dequeuePutRequest();
                methodRequestPut.execute();
            }
            lock.unlock();

            lock.lock();
            MethodRequestTake methodRequestTake = activationQueue.getTakeRequest();
            if(methodRequestTake!=null && methodRequestTake.guard()){
                activationQueue.dequeueTakeRequest();
                methodRequestTake.execute();
            }
            lock.unlock();
        }
    }
}
