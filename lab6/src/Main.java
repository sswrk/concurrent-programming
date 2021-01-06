import ProducersConsumers.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private static void producersConsumers() throws InterruptedException
    {
        Map<Integer, Integer> hashMap = new ConcurrentHashMap<>();
        Monitor monitor = new Monitor(15);
        int consumerNumber = 15;
        int producerNumber = 5;

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0 ; i < producerNumber; i++) {
            Thread newProducer = new Thread(new Producer(monitor, hashMap));
            newProducer.start();
            threads.add(newProducer);
        }

        for (int i = 0; i < consumerNumber; i++) {
            Thread newConsumer = new Thread(new Consumer(monitor, hashMap));
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
