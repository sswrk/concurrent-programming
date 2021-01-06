import ProducersConsumers.*;

import java.util.ArrayList;

public class Main {
    private static void producersConsumers() throws InterruptedException
    {
        Buffer buffer = new Buffer(5);
        int consumerNumber = 15;
        int producerNumber = 5;

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0 ; i < producerNumber; i++) {
            Thread newProducer = new Thread(new Producer(buffer));
            newProducer.start();
            threads.add(newProducer);
        }

        for (int i = 0; i < consumerNumber; i++) {
            Thread newConsumer = new Thread(new Consumer(buffer));
            newConsumer.start();
            threads.add(newConsumer);
        }

        for (Thread thread: threads) {
            thread.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        producersConsumers();
    }
}
