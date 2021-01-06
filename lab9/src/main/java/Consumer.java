import org.jcsp.lang.*;

/**
 * Consumer class: reads ints from input channel, displays them,
 * then
 * terminates when a negative value is read.
 */
public class Consumer implements CSProcess {
    private One2OneChannelInt in;
    private One2OneChannelInt req;

    public Consumer(final One2OneChannelInt req, final
    One2OneChannelInt in) {
        this.req = req;
        this.in = in;
    } // constructor

    public void run() {
        int item;
        while (true) {
            ChannelOutputInt channelOutputInt = req.out();
            channelOutputInt.write(0);
//            req.write(0); // Request data - blocks until data is available
            ChannelInputInt channelInputInt = in.in();
            item = channelInputInt.read();
//            item = in.read();
            if (item < 0)
                break;
            System.out.println(item);
        } // for
        System.out.println("Consumer ended.");
    } // run
} // class Consumer2

//czy beda od razu zadania z czer