package ProducersConsumers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable
{
    private Buffer buffer;
    private Random generator = new Random();
    private long additionalTaskTime;
    private int minElements;
    private int maxElements;

    public Consumer(Buffer buffer, long additionalTaskTime, int minElements, int maxElements) {
        this.additionalTaskTime = additionalTaskTime;
        this.buffer = buffer;
        this.maxElements = maxElements;
        this.minElements = minElements;
    }

    public void run() {
        while(true) {
            try {
                int quantity = randomQuantity();
                buffer.take(quantity);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            performOperations();
        }
    }

    private int randomQuantity(){
        return generator.nextInt(maxElements-minElements+1) + minElements;
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
