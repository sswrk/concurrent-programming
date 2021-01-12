package Buffer;

import org.jcsp.lang.Channel;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.util.Buffer;

import java.util.ArrayList;
import java.util.List;

public class DistributedBuffer {

    private final List<List<Node>> nodesLayout;

    public DistributedBuffer(int[] layout, int maxNodeSize){
        this.nodesLayout = new ArrayList<>();
        setNetwork(layout, maxNodeSize);
    }

    public List<Node> getHeadNodes(){
        return nodesLayout.get(0);
    }

    public void addEndOutput(ChannelOutput<String> output){
        for(Node node : nodesLayout.get(nodesLayout.size()-1)){
            node.addOutput(output);
        }
    }

    public List<Node> getNodes(){
        List<Node> nodes = new ArrayList<>();
        for(List<Node> layoutStep : nodesLayout){
            nodes.addAll(layoutStep);
        }
        return nodes;
    }

    private void setNetwork(int[] layout, int maxNodeSize){
        int nodeSize = maxNodeSize;
        for(int i=0; i<layout.length; i++){
            if(nodeSize>1){
                nodeSize--;
            }
            List<Node> layoutStep = new ArrayList<>();
            for(int j=0; j<layout[i]; j++){
                layoutStep.add(new Node(Channel.any2one(new Buffer<>(nodeSize)), "step: " + i + ", j: " + j));
            }
            this.nodesLayout.add(layoutStep);
        }
        for(int i = 0; i<this.nodesLayout.size()-1; i++){
            for(Node node : this.nodesLayout.get(i)){
                node.setAvailableOutputs(this.nodesLayout.get(i+1));
            }
        }
    }

}