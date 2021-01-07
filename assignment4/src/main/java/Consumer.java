import org.jcsp.lang.Channel;
import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.CSProcess;
import org.jcsp.util.InfiniteBuffer;

import Buffer.DistributedBuffer;

public class Consumer implements CSProcess{

    private final AltingChannelInput<String> input;
    private final String id;

    public Consumer(String id, DistributedBuffer buffer){
        this.id = id;
        Any2OneChannel<String> channel = Channel.any2one(new InfiniteBuffer<>());
        this.input = channel.in();
        buffer.addEndOutput(channel.out());
    }

    @Override
    public void run(){
        while(true){
            String message = input.read();
            System.out.println("Consumer " + id + " received a message: " + message);
        }
    }

}