package ProducersConsumers;

import java.util.Random;

public class Producer implements Runnable
{
    private Buffer buffer;
    Random generator = new Random();

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        while(true)
        {
            try {
                buffer.put(generator.nextInt(10) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
