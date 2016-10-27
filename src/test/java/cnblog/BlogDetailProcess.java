package cnblog;

import com.github.gin.agama.core.CrawlConfiger;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.proxy.ProxyPool;
import com.github.gin.agama.site.Page;

import java.net.Proxy;

/**
 * Created by FSTMP on 2016/10/21.
 */
public class BlogDetailProcess implements PageProcess {

    @Override
    public void process(Page page) {
        BlogDetail blogDetail = page.getRender().renderToHtml().toEntity(BlogDetail.class);

        System.out.println(blogDetail);
    }

    public static void main(String[] args) {
        HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
        //ProxyPool.addProxy(proxy);
        CrawlConfiger config = new CrawlConfiger("http://www.cnblogs.com/ginponson/p/5947200.html");
        //config.setProxy(proxy);
        config.setDepth(1);
        config.setThreadNum(2);
        config.setAjaxModel(true);
        JCrawler.create(new BlogDetailProcess()).setConfig(config).run();
    }
}
