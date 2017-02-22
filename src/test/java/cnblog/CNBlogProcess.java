package cnblog;

import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.site.Page;

/**
 * Created by FSTMP on 2016/8/30.
 */
public class CNBlogProcess implements PageProcess {

    @Override
    public void process(Page page) {
        page.addRequests(
                page.getRender()
                        .renderToHtml()
                        .xpath("//div[@class='pager']/a")
                        .attrs("href")
        );

        page.getResultItems().add(
                page.getRender()
                        .renderToHtml()
                        .toEntityList(CNBlog.class)
        );
    }

    public static void main(String[] args) {
        JCrawler.create()
                .crawl("http://www.cnblogs.com/")
                .processBy(new CNBlogProcess())
                .run();
    }
}
