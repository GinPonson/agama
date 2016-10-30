package cloud.runnable;

import cloud.process.FollowProcess;
import com.github.gin.agama.core.CrawlConfiger;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.site.Request;

import java.net.Proxy;

/**
 * Created by GinPonson on 10/30/2016.
 */
public class FollowThread implements Runnable {
    @Override
    public void run() {
        HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
        //ProxyPool.addProxy(proxy);

        Request request = new Request();
        request.getHeaders().put("X-Requested-With","XMLHttpRequest");
        request.getHeaders().put("Accept", "application/json, text/javascript, */*; q=0.01");
        request.getHeaders().put("Referer", "https://yun.baidu.com/share/home?uk=325913312#category/type=0");
        request.getHeaders().put("Accept-Language", "zh-CN");
        request.setUrl("http://yun.baidu.com/pcloud/friend/getfollowlist?query_uk=2889076181&limit=20&start=0&bdstoken=e6f1efec456b92778e70c55ba5d81c3d&channel=chunlei&clienttype=0&web=1&logid=MTQ3NDA3NDg5NzU4NDAuMzQxNDQyMDY2MjA5NDA4NjU=");

        CrawlConfiger config = new CrawlConfiger(request);
        config.setDepth(1);
        config.setThreadNum(2);
        JCrawler.create(new FollowProcess()).setConfig(config).run();
    }
}
