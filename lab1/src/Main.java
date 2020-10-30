public class Main {
    private static class CounterThread implements Runnable {
        private MyInteger c;
        private int id;

        CounterThread(MyInteger c, int id)
        {

            this.c = c;
            this.id = id;

        }

        public void run()
        {
            if(id%2 == 0)
                c.decrement();
            else
                c.increment();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        MyInteger c = new MyInteger(0);
        for (int  i=0; i<50; i++) {
            Thread t = new Thread(new CounterThread(c, i));
            t.start();
        }
        c.printInteger();
    }
}