package ProducersConsumers;

import java.util.Map;

public class Producer implements Runnable
{
    private Monitor monitor;
    private Map<Integer, Integer> hashMap;

    public Producer(Monitor monitor, Map hashMap) {
        this.hashMap = hashMap;
        this.monitor = monitor;
    }

    public void run() {
        while(true)
        {
            try {
                int current = monitor.startProduction();
                hashMap.put(current, 1);
                monitor.endProduction(current);
                System.out.println("Producent " + Thread.currentThread().getId() + " wyprodukowal " + current);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
