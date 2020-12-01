package ActiveObject;

import ActiveObject.MethodRequest.MethodRequest;
import ActiveObject.MethodRequest.MethodRequestTake;
import ActiveObject.MethodRequest.MethodRequestPut;

import java.util.ArrayList;
import java.util.List;

public class ActivationQueue {

    private final List<MethodRequestTake> takeRequests = new ArrayList<>();
    private final List<MethodRequestPut> putRequests = new ArrayList<>();

    public void enqueue(MethodRequest request){
        if(request instanceof MethodRequestPut)
            putRequests.add((MethodRequestPut) request);
        else if(request instanceof MethodRequestTake)
            takeRequests.add((MethodRequestTake) request);
    }

    public MethodRequestPut getPutRequest(){
        if(putRequests.isEmpty())
            return null;
        return putRequests.get(0);
    }

    public MethodRequestTake getTakeRequest(){
        if(takeRequests.isEmpty())
            return null;
        return takeRequests.get(0);
    }

    public void dequeuePutRequest(){
        putRequests.remove(0);
    }

    public void dequeueTakeRequest(){
        takeRequests.remove(0);
    }

}
