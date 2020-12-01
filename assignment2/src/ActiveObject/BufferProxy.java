package ActiveObject;

import ActiveObject.MethodRequest.MethodRequestTake;
import ActiveObject.MethodRequest.MethodRequestPut;

public class BufferProxy {
    private final Buffer buffer;
    private final Scheduler scheduler;

    public BufferProxy(int bufferSize, Scheduler scheduler) {
        buffer = new Buffer(bufferSize);
        this.scheduler = scheduler;
    }

    public Future put(int numberOfElements, long producerID){
        Future resultContainer = new Future();
        MethodRequestPut requestPut = new MethodRequestPut(buffer, numberOfElements, resultContainer, producerID);
        scheduler.enqueue(requestPut);
        return resultContainer;
    }

    public Future take(int numberOfElements, long consumerID){
        Future resultContainer = new Future();
        MethodRequestTake requestGet = new MethodRequestTake(buffer, numberOfElements, resultContainer, consumerID);
        scheduler.enqueue(requestGet);
        return resultContainer;
    }
}
