package ActiveObject.MethodRequest;

import ActiveObject.Buffer;
import ActiveObject.Future;

public class MethodRequestTake extends MethodRequest{

    public MethodRequestTake(Buffer buffer, int numberOfElements, Future future, long consumerID) {
        super(buffer, numberOfElements, future, consumerID);
    }

    public boolean guard(){
        return buffer.hasElements(numberOfElements);
    }

    public void execute(){
        buffer.take(numberOfElements, threadID);
        future.setDone();
    }
}
