package ActiveObject;

public class Future {
    private volatile int result;
    private volatile boolean done = false;

    public boolean isDone(){
        return done;
    }

    public void setDone(){
        done = true;
    }

    public int getResult(){
        return result;
    }

    public void setResult(int result){
        this.result = result;
    }

}
