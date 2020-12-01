package ActiveObject.MethodRequest;

import ActiveObject.Buffer;
import ActiveObject.Future;

public class MethodRequestPut extends MethodRequest{
    public MethodRequestPut(Buffer buffer, int numberOfElements, Future future, long producerID) {
        super(buffer, numberOfElements, future, producerID);
    }

    public boolean guard(){
        return buffer.hasSpace(numberOfElements);
    }

    public void execute(){
        buffer.put(numberOfElements, threadID);
        future.setDone();
    }
}
