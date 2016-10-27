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

    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String get(){
        return threadLocal.get();
    }

    public static void set(String str){
        threadLocal.set(str);
    }

    @Override
    public void process(Page page) {
        //CNBlog cnBlog = page.getHtml().toEntity(CNBlog.class);
        List<BlogItem> cnBlog = page.getRender().renderToHtml().toEntityList(BlogItem.class);
        page.getRequests().addAll(page.getRender().renderToHtml().xpath("//div[@class='pager']/a").attrs("href"));

        for(BlogItem blogItems : cnBlog){
            if(blogItems.getPoster().equals("webNick"))
                threadLocal.set("webNick");
            System.out.println(blogItems);
            System.out.println(threadLocal.get() + "," + blogItems.getTitle()+","+Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
        //ProxyPool.addProxy(proxy);

        CNBlogProcess.set("aa");

        CrawlConfiger config = new CrawlConfiger("http://www.cnblogs.com/");
        //config.setProxy(proxy);
        config.setDepth(1);
        config.setThreadNum(2);
        JCrawler.create(new CNBlogProcess()).setConfig(config).run();

        System.out.println(Thread.currentThread().getName() + CNBlogProcess.get());
    }
}
