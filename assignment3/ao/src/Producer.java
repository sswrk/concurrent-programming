import ActiveObject.BufferProxy;
import ActiveObject.Future;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable
{
    private final BufferProxy bufferProxy;

    private final Random generator = new Random();
    private final int minQuantity;
    private final int maxQuantity;
    private final long additionalTaskTime;

    public Producer(BufferProxy bufferProxy, long additionalTaskTime, int minQuantity, int maxQuantity) {
        this.additionalTaskTime = additionalTaskTime;
        this.bufferProxy = bufferProxy;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
    }

    public void run() {
        while(true) {
            int quantity = randomQuantity();
            Future resultContainer = bufferProxy.put(quantity, Thread.currentThread().getId());
            this.performOperations();
            //System.out.println(resultContainer.getResult());
        }
    }

    private int randomQuantity(){
        return generator.nextInt(maxQuantity-minQuantity+1) + minQuantity;
    }

    private void performOperations() {
        //System.out.println("    Producent " + Thread.currentThread().getId() + ": cos robie...");
        try {
            TimeUnit.MILLISECONDS.sleep(additionalTaskTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
