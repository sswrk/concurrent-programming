import java.util.ArrayList;
import java.util.Random;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.SharedChannelOutput;

import Buffer.Node;
import Buffer.DistributedBuffer;

public class Producer implements CSProcess{

    private final ArrayList<SharedChannelOutput<String>> availableOutputs;
    private final Random random;
    private final String id;
    private final DistributedBuffer buffer;

    public Producer(String id, DistributedBuffer buffer){
        this.id = id;
        this.buffer = buffer;
        this.random = new Random();
        this.availableOutputs = new ArrayList<>();
        setAvailableOutputs();
    }

    @Override
    public void run(){
        while(true){
            availableOutputs.get(random.nextInt(availableOutputs.size())).write("Producer " + id);
        }
    }

    public void setAvailableOutputs(){
        ArrayList<Node> nodes = buffer.getHeadNodes();
        for(Node node : nodes){
            availableOutputs.add(node.getOutput());
        }
    }
}
