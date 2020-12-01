package ActiveObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    private final List<Integer> buffer = new ArrayList<>();
    private final int maxNumber;

    private int currentElementID = 1;

    public Buffer(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void put(int numberOfElements, long producerID){
        for(int i=0; i<numberOfElements; i++){
            buffer.add(currentElementID);
            currentElementID++;
        }
        System.out.println("Producent " + producerID + " wyprodukowal " + numberOfElements + " elementow, stan bufora: " + getCurrentSize());
    }

    public void take(int numberOfElements, long consumerID){
        for(int i=0; i<numberOfElements; i++){
            buffer.remove(0);
        }
        System.out.println("Konsument " + consumerID + " skonsumowal " + numberOfElements + " elementow, stan bufora: " + getCurrentSize());
    }

    public boolean hasSpace(int numberOfElements){
        return buffer.size() + numberOfElements <= maxNumber;
    }

    public boolean hasElements(int numberOfElements){
        return buffer.size() - numberOfElements >= 0;
    }

    public int getCurrentSize(){
        return buffer.size();
    }
}