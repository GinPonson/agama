package other;

/**
 * Created by FSTMP on 2016/10/25.
 */
public class TestThread implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args){
        TestThread t = new TestThread();
        t.run();
        TestThread t1 = new TestThread();
        t1.run();
    }
}
