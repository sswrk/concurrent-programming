import ActiveObject.BufferProxy;
import ActiveObject.Scheduler;

import java.util.ArrayList;

public class Main {
    private static void producersConsumers() throws InterruptedException
    {
        int bufferSize = 1000;
        int consumerNumber = 10;
        int producerNumber = 10;

        Scheduler scheduler = new Scheduler();
        BufferProxy bufferProxy = new BufferProxy(bufferSize, scheduler);

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0 ; i < producerNumber; i++) {
            Thread newProducer = new Thread(new Producer(bufferProxy));
            newProducer.start();
            threads.add(newProducer);
        }

        for (int i = 0; i < consumerNumber; i++) {
            Thread newConsumer = new Thread(new Consumer(bufferProxy));
            newConsumer.start();
            threads.add(newConsumer);
        }

        Thread schedulerThread = new Thread(scheduler);
        schedulerThread.start();
        threads.add(schedulerThread);

        for (Thread thread: threads) {
            thread.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        producersConsumers();
    }
}
