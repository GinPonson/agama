package other;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by FSTMP on 2016/10/25.
 */
public class TestExcutor {

    public ExecutorService executorService = Executors.newFixedThreadPool(1);

    String aa ;

    public void run(final String t1){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(aa);
                for (int i = 0; i < 10; i++)
                    System.out.println(Thread.currentThread().getName() + ":" + t1);
                System.out.println(aa);
            }
        });
        //executorService.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        TestExcutor t1 = new TestExcutor();
        t1.run("t1");
        Thread.sleep(1);
        t1.aa = "aa";
        //TestExcutor t2 = new TestExcutor();
        //t2.run("t2");
        //TestExcutor t3 = new TestExcutor();
        //t3.run("t3");
    }
}
