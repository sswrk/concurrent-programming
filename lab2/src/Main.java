import Semaphores.*;

public class Main
{
    private static void doubleThreadCounter() {
        Counter counter = new Counter();

        new Thread(() ->
        {
            for (int i = 0 ; i < 100000000; i++)
            {
                try {
                    counter.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() ->
        {
            for (int i = 0 ; i < 100000000; i++) {
                try {
                    counter.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println(counter.value());
    }

    private static void fivePhilosophers(){
        final Philosopher[] philosophers = new Philosopher[5];
        BinSemaphore[] forks = new BinSemaphore[philosophers.length];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new BinSemaphore();
        }

        for (int i = 0; i < philosophers.length; i++) {
            BinSemaphore leftFork = forks[i];
            BinSemaphore rightFork = forks[(i + 1) % forks.length];

            philosophers[i] = new Philosopher(leftFork, rightFork);

            Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
            t.start();
        }

    }

    private static void fivePhilosophersWaiter(){
        Philosopher[] philosophers = new Philosopher[5];
        Semaphore waiter = new Semaphore(4);
        BinSemaphore[] forks = new BinSemaphore[philosophers.length];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new BinSemaphore();
        }

        for (int i = 0; i < philosophers.length; i++) {
            BinSemaphore leftFork = forks[i];
            BinSemaphore rightFork = forks[(i + 1) % forks.length];

            philosophers[i] = new Philosopher(leftFork, rightFork, waiter);

            Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
            t.start();
        }

    }

    public static void main(String[] args) throws InterruptedException
    {
        //doubleThreadCounter();
        //fivePhilosophers();
        fivePhilosophersWaiter();
    }
}
