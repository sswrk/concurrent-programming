package Semaphores;

public class Semaphore
{
    private int value;

    public Semaphore(int value)
    {
        if (value > 0)
            this.value = value;
        else
            throw new IllegalArgumentException("value should be > 0");
    }

    synchronized public void acquire()
    {
        while(value <= 0)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.value--;
    }

    synchronized public void release()
    {
        this.value++;
        notify();
    }

    public int getValue(){
        return this.value;
    }
}
