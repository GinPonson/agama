package cloud.runnable;

import cloud.Constant;
import cloud.RequestUtil;
import cloud.Singleton;
import cloud.entity.YunUser;
import cloud.process.BaiduYunProcess;
import cloud.storer.YunDataDataStorer;
import com.github.gin.agama.core.CrawlConfiger;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.proxy.ProxyPool;
import com.github.gin.agama.site.Request;

import java.net.Proxy;
import java.util.List;

/**
 * Created by FSTMP on 2016/10/31.
 */
public class BaiduYunThread implements Runnable {

    @Override
    public void run() {
        HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
        ProxyPool.addProxy(proxy);

        CrawlConfiger config = new CrawlConfiger();
        config.setThreadNum(1);

        List<YunUser> yunUserList = Singleton.getYunUserService().findUnfinish();
        if(yunUserList.isEmpty()){
            Request request = RequestUtil.createRequest();
            request.setUrl(String.format(Constant.YUN_URL,Constant.DEFAULT_UK,0));
            config.getStartRequests().add(request);
        } else {
            for(YunUser user : yunUserList){
                for(int i =0 ; i < user.getFollowCount();i = i+ Constant.LIMIT){
                    Request request = RequestUtil.createRequest();
                    request.setUrl(String.format(Constant.YUN_URL, user.getUk(), i));
                    config.getStartRequests().add(request);
                }
            }
        }

        JCrawler.create(new BaiduYunProcess()).persistBy(new YunDataDataStorer()).setConfig(config).run();
    }
}
