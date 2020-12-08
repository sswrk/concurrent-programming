import ProducersConsumers.*;

import java.util.ArrayList;

public class Main {

    private static void producersConsumers() throws InterruptedException
    {
        int bufferSize = 500;

        int consumerNumber = 3;
        int consumerMinElements = 1;
        int consumerMaxElements = 10;

        int producerNumber = 3;
        int producerMinElements = 1;
        int producerMaxElements = 10;

        int numberOfOperations = 100;
        long additionalTaskTime = 3000;

        Buffer buffer = new Buffer(bufferSize, numberOfOperations);

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0 ; i < producerNumber; i++) {
            Thread newProducer = new Thread(new Producer(buffer, additionalTaskTime, producerMinElements, producerMaxElements));
            newProducer.start();
            threads.add(newProducer);
        }

        for (int i = 0; i < consumerNumber; i++) {
            Thread newConsumer = new Thread(new Consumer(buffer, additionalTaskTime, consumerMinElements, consumerMaxElements));
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
