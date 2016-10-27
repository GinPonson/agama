package other;

/**
 * Created by FSTMP on 2016/10/25.
 */
public class TestRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args){
        TestRunnable r = new TestRunnable();
        Thread t = new Thread(r);
        t.start();
        TestRunnable r1 = new TestRunnable();
        Thread t1 = new Thread(r1);
        t1.run();
    }
}
