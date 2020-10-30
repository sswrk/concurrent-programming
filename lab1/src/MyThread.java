public class MyThread implements Runnable{
    Thread thread;
    private String name;
    MyThread(String name){
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println(name);
    }
    public void start() {
        System.out.println("Thread started");
        if (thread == null) {
            thread = new Thread(this, name);
            thread.start();
        }
    }
}
