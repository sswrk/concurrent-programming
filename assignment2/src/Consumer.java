import ActiveObject.BufferProxy;
import ActiveObject.Future;

import java.util.Random;

public class Consumer implements Runnable
{
    private BufferProxy bufferProxy;

    private final Random generator = new Random();
    private final int minQuantity = 1;
    private final int maxQuantity = 10;

    public Consumer(BufferProxy bufferProxy) {
        this.bufferProxy = bufferProxy;
    }

    public void run() {
        while(true) {
            int quantity = randomQuantity();
            Future resultContainer = bufferProxy.take(quantity, Thread.currentThread().getId());

            while(!resultContainer.isDone()){
                this.performOperations();
            }
        }
    }

    private int randomQuantity(){
        return generator.nextInt(maxQuantity-minQuantity+1) + minQuantity;
    }

    private void performOperations() {
        System.out.println("    Konsument " + Thread.currentThread().getId() + ": cos robie...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
