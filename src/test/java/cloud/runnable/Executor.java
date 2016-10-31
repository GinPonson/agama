package cloud.runnable;

/**
 * Created by FSTMP on 2016/10/31.
 */
public class Executor {

    public static void main(String[] args){
        Thread fansThread = new Thread(new FansThread());
        fansThread.start();

        Thread followThread = new Thread(new FollowThread());
        followThread.start();

        Thread yunThread = new Thread(new BaiduYunThread());
        yunThread.start();
    }
}
