import Semaphores.BinSemaphore;

class Counter
{
    private int c = 0;
    private BinSemaphore semaphore;

    Counter()
    {
        this.c = 0;
        this.semaphore = new BinSemaphore();
        try
        { Thread.sleep(10); }
        catch (InterruptedException ignored)
        {}
    }

    void increment() throws InterruptedException {
        this.semaphore.acquire();
        c++;
        this.semaphore.release();
    }

    void decrement() throws InterruptedException {
        this.semaphore.acquire();
        c--;
        this.semaphore.release();
    }

    int value()
    {
        return this.c;
    }
}
