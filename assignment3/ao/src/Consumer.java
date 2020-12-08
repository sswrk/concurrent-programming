import ActiveObject.BufferProxy;
import ActiveObject.Future;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable
{
    private BufferProxy bufferProxy;

    private final Random generator = new Random();
    private final int minQuantity;
    private final int maxQuantity;
    private final long additionalTaskTime;

    public Consumer(BufferProxy bufferProxy, long additionalTaskTime, int minQuantity, int maxQuantity) {
        this.additionalTaskTime = additionalTaskTime;
        this.bufferProxy = bufferProxy;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
    }

    public void run() {
        while(true) {
            int quantity = randomQuantity();
            Future resultContainer = bufferProxy.take(quantity, Thread.currentThread().getId());
            this.performOperations();
            //System.out.println(resultContainer.getResult());
        }
    }

    private int randomQuantity(){
        return generator.nextInt(maxQuantity-minQuantity+1) + minQuantity;
    }

    private void performOperations() {
        //System.out.println("    Konsument " + Thread.currentThread().getId() + ": cos robie...");
        try {
            TimeUnit.MILLISECONDS.sleep(additionalTaskTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
