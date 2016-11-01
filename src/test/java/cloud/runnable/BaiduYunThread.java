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
        config.setThreadNum(2);
        //config.setSleepTime(8000);

        List<YunUser> yunUserList = Singleton.getYunUserService().findUnfinish();
        if(yunUserList.isEmpty()){
            //当缺少没有爬取资源完毕的用户数据时，使用默认uk获取
            Request request = RequestUtil.createRequest();
            request.setUrl(String.format(Constant.YUN_URL,Constant.DEFAULT_UK,0));
            config.getStartRequests().add(request);
        } else {
            //获取每个用户的所有资源链接
            for(YunUser user : yunUserList){
                Request request = RequestUtil.createRequest();
                request.setUrl(String.format(Constant.YUN_URL, user.getUk(), 0));
                config.getStartRequests().add(request);
            }
        }

        JCrawler.create(new BaiduYunProcess()).persistBy(new YunDataDataStorer()).setConfig(config).run();
    }
}
