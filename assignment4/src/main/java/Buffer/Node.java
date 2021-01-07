package Buffer;

import java.util.ArrayList;
import java.util.Random;

import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.SharedChannelOutput;

public class Node implements CSProcess{

    private final AltingChannelInput<String> input;
    private final SharedChannelOutput<String> output;
    private final ArrayList<SharedChannelOutput<String>> availableOutputs;
    private final Random random;
    private final String id;

    public Node(Any2OneChannel<String> channel, String id){
        this.id = id;
        this.input = channel.in();
        this.output = channel.out();
        this.random = new Random();
        this.availableOutputs = new ArrayList<>();
    }

    @Override
    public void run(){
        while(true){
            String message = input.read() + " (from buffer node " + id +")";
            availableOutputs.get(random.nextInt(availableOutputs.size())).write(message);
        }
    }

    public void setAvailableOutputs(ArrayList<Node> nodes){
        for(Node node : nodes){
            availableOutputs.add(node.getOutput());
        }
    }

    public void addOutput(SharedChannelOutput<String> output){
        availableOutputs.add(output);
    }

    public SharedChannelOutput<String> getOutput(){
        return output;
    }

}