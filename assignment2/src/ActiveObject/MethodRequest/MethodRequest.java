package ActiveObject.MethodRequest;

import ActiveObject.Buffer;
import ActiveObject.Future;

public class MethodRequest {
    protected Buffer buffer;
    protected Future future;
    protected int numberOfElements;
    protected long threadID;

    public MethodRequest(Buffer buffer, int numberOfElements, Future future, long threadID){
        this.buffer = buffer;
        this.future = future;
        this.numberOfElements = numberOfElements;
        this.threadID = threadID;
    }
}
