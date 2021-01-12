import java.util.ArrayList;
import java.util.List;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Parallel;

import Buffer.Node;
import Buffer.DistributedBuffer;

public class Main {
    private static final int producerNumber = 10;
    private static final int consumerNumber = 20;
    private static final int[] bufferLayout = {15, 20, 25};
    private static final int maxNodeSize = 7;

    public static void main(String[] args){

        List<CSProcess> processes = setProcesses();

        Parallel parallel = new Parallel(processes.toArray(new CSProcess[0]));
        parallel.run();
    }

    private static List<CSProcess> setProcesses(){
        DistributedBuffer buffer = new DistributedBuffer(bufferLayout, maxNodeSize);

        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();

        for(int i=0; i<producerNumber; i++){
            producers.add(new Producer(Integer.toString(i), buffer));
        }

        for(int i=0; i<consumerNumber; i++){
            consumers.add(new Consumer(Integer.toString(i), buffer));
        }

        List<Node> nodes = buffer.getNodes();

        List<CSProcess> processes = new ArrayList<>();
        processes.addAll(producers);
        processes.addAll(consumers);
        processes.addAll(nodes);

        return processes;
    }
}