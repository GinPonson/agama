package cnblog;

import com.github.gin.agama.core.CrawlConfiger;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.proxy.ProxyPool;
import com.github.gin.agama.site.Page;

import java.net.Proxy;
import java.util.List;

/**
 * Created by FSTMP on 2016/8/30.
 */
public class CNBlogProcess implements PageProcess {

    @Override
    public void process(Page page) {
        //CNBlog cnBlog = page.getHtml().toEntity(CNBlog.class);
        List<BlogItem> cnBlog = page.getRender().renderToHtml().toEntityList(BlogItem.class);
        page.addRequests(page.getRender().renderToHtml().xpath("//div[@class='pager']/a").attrs("href"));

        for(BlogItem blogItems : cnBlog){
            //System.out.println(blogItems);
        }

        page.getResultItems().add(cnBlog);
    }

    public static void main(String[] args) {
        HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
        ProxyPool.addProxy(proxy);

        CrawlConfiger config = new CrawlConfiger("http://www.cnblogs.com/");
        //config.setProxy(proxy);
        config.setDepth(1);
        config.setThreadNum(2);
        JCrawler.create(new CNBlogProcess()).setConfig(config).run();

    }
}
