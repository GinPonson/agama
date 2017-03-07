package cnblog;

import com.github.gin.agama.core.CrawlConfigure;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.site.Page;

/**
 * Created by FSTMP on 2016/8/30.
 */
public class CNBlogProcess implements PageProcess {

    @Override
    public void process(Page page) {
        /*page.addRequests(
                page.getRender()
                        .renderToHtml()
                        .xpath("//div[@class='pager']/a")
                        .attrs("href")
        );*/
        System.out.println(page.getRawText());
        /*page.getResultItems().add(
                page.getRender()
                        .renderToHtml()
                        .toEntityList(CNBlog.class)
        );*/
    }

    public static void main(String[] args) {
        JCrawler.create()
                //.redis("192.168.153.131:6379")
                .crawl("http://www.cnblogs.com/")
                .processBy(new CNBlogProcess())
                .run();
    }
}
