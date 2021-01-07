package Buffer;

import org.jcsp.lang.Channel;
import org.jcsp.lang.SharedChannelOutput;
import org.jcsp.util.Buffer;

import java.util.ArrayList;

public class DistributedBuffer {

    private final ArrayList<ArrayList<Node>> nodesLayout;

    public DistributedBuffer(int[] layout, int nodeSize){
        this.nodesLayout = new ArrayList<>();
        setNetwork(layout, nodeSize);
    }

    public ArrayList<Node> getHeadNodes(){
        return nodesLayout.get(0);
    }

    public void addEndOutput(SharedChannelOutput<String> output){
        for(Node node : nodesLayout.get(nodesLayout.size()-1)){
            node.addOutput(output);
        }
    }

    public ArrayList<Node> getNodes(){
        ArrayList<Node> nodes = new ArrayList<>();
        for(ArrayList<Node> layoutStep : nodesLayout){
            nodes.addAll(layoutStep);
        }
        return nodes;
    }

    private void setNetwork(int[] layout, int nodeSize){
        for(int i=0; i<layout.length; i++){
            ArrayList<Node> layoutStep = new ArrayList<>();
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