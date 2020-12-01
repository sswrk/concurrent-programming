import ActiveObject.BufferProxy;
import ActiveObject.Future;

import java.util.Random;

public class Producer implements Runnable
{
    private final BufferProxy bufferProxy;

    private final Random generator = new Random();
    private final int minQuantity = 490;
    private final int maxQuantity = 500;

    public Producer(BufferProxy bufferProxy) {
        this.bufferProxy = bufferProxy;
    }

    public void run() {
        while(true) {
            int quantity = randomQuantity();
            Future resultContainer = bufferProxy.put(quantity, Thread.currentThread().getId());

            while(!resultContainer.isDone()){
                this.performOperations();
            }
        }
    }

    private int randomQuantity(){
        return generator.nextInt(maxQuantity-minQuantity+1) + minQuantity;
    }

    private void performOperations() {
        System.out.println("    Producent " + Thread.currentThread().getId() + ": cos robie...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
