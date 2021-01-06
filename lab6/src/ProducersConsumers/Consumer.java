package ProducersConsumers;

import java.util.Map;

public class Consumer implements Runnable
{
    private Monitor monitor;
    private Map<Integer, Integer> hashMap;

    public Consumer(Monitor monitor, Map hashMap) {
        this.monitor = monitor;
        this.hashMap = hashMap;
    }

    public void run() {
        while(true) {
            try {
                int current= monitor.startConsumption();
                monitor.endConsumption(current);
                System.out.println("Konsument " + Thread.currentThread().getId() + " skonsumowal " + current);
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
