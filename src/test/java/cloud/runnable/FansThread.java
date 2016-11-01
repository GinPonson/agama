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
import java.util.Date;
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
        config.setThreadNum(1);
        config.setSleepTime(15000);

        List<YunUser> yunUserList = Singleton.getYunUserService().findFansUnCrawled();
        if(yunUserList.isEmpty()){
            //当缺少没有爬出粉丝的用户数据时，使用默认的uk
            Request request = RequestUtil.createRequest();
            request.setUrl(String.format(Constant.FANS_URL,Constant.DEFAULT_UK,Constant.LIMIT,0));
            config.getStartRequests().add(request);

            //保存默认uk，以免获取资源的时候找不到该用户
            //Singleton.getYunUserService().save(new YunUser("http://himg.bdimg.com/sys/portrait/item/83ac0a37.jpg","51xuejava_com",2889076181L,2,60,new Date(),152));
        } else {
            //穷尽获取每个用户的所有粉丝链接
            for(YunUser user : yunUserList){
                Request request = RequestUtil.createRequest();
                request.setUrl(String.format(Constant.FANS_URL, user.getUk(), Constant.LIMIT, 0));
                config.getStartRequests().add(request);
            }
        }

        JCrawler.create(new FansProcess()).persistBy(new YunUserDataStorer()).setConfig(config).run();
    }

    public static void main(String[] args) {
        Thread fansThread = new Thread(new FansThread());
        fansThread.start();
    }
}
