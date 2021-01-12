package Buffer;

import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;
import org.jcsp.lang.ChannelOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node implements CSProcess {

    private final ChannelInput<String> input;
    private final ChannelOutput<String> output;
    private final List<ChannelOutput<String>> availableOutputs;
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

    public void setAvailableOutputs(List<Node> nodes){
        for(Node node : nodes){
            availableOutputs.add(node.getOutput());
        }
    }

    public void addOutput(ChannelOutput<String> output){
        availableOutputs.add(output);
    }

    public ChannelOutput<String> getOutput(){
        return output;
    }

}