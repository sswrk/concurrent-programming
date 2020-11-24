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
                buffer.take(generator.nextInt(11) + 490);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
