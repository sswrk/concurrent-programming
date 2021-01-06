package ProducersConsumers;

import java.util.Random;

public class Consumer implements Runnable
{
    private Buffer buffer;
    Random generator = new Random();

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        while(true) {
            try {
                buffer.take(generator.nextInt(5) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
