package cloud.runnable;

import cloud.Constant;
import cloud.RequestUtil;
import cloud.Singleton;
import cloud.entity.YunUser;
import cloud.process.FansProcess;
import cloud.process.FollowProcess;
import cloud.storer.YunUserDataStorer;
import com.github.gin.agama.core.CrawlConfiger;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.proxy.ProxyPool;
import com.github.gin.agama.site.Request;

import java.net.Proxy;
import java.util.List;

/**
 * Created by GinPonson on 10/30/2016.
 */
public class FansThread implements Runnable {

    @Override
    public void run() {
        HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
        ProxyPool.addProxy(proxy);

        CrawlConfiger config = new CrawlConfiger();
        config.setThreadNum(2);

        List<YunUser> yunUserList = Singleton.getYunUserService().findFansUnCrawled();
        if(yunUserList.isEmpty()){
            Request request = RequestUtil.createRequest();
            request.setUrl(String.format(Constant.FANS_URL,Constant.DEFAULT_UK,Constant.LIMIT,0));
            config.getStartRequests().add(request);
        } else {
            for(YunUser user : yunUserList){
                for(int i =0 ; i < user.getFollowCount();i = i+ Constant.LIMIT){
                    Request request = RequestUtil.createRequest();
                    request.setUrl(String.format(Constant.FANS_URL, user.getUk(), Constant.LIMIT, i));
                    config.getStartRequests().add(request);
                }
            }
        }

        JCrawler.create(new FansProcess()).persistBy(new YunUserDataStorer()).setConfig(config).run();
    }

    public static void main(String[] args) {
        Thread fansThread = new Thread(new FansThread());
        fansThread.start();
    }
}
