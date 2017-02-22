package weather;

import com.github.gin.agama.core.CrawlConfigure;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.proxy.ProxyPool;
import com.github.gin.agama.site.Page;

import java.net.Proxy;

/**
 * Created by FSTMP on 2017/2/22.
 */
public class GitHubPageProcess implements PageProcess {
    @Override
    public void process(Page page) {
        page.getResultItems().add(page.getRender().renderToHtml().toEntityList(GitHub.class));
    }

    public static void main(String[] args){
        HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
        ProxyPool.addProxy(proxy);
        CrawlConfigure config = new CrawlConfigure("https://github.com/GinPonson?tab=repositories");
        config.setThreadNum(2);
        config.setUseAjax(true);
        JCrawler.create(new WeatherProcess())
                //.downloadBy(new WeatherDownloader())
                .useConfig(config)
                .run();
    }
}
