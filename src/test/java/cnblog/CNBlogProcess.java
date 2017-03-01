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
        /*page.addRequests(
                page.getRender()
                        .renderToHtml()
                        .xpath("//div[@class='pager']/a")
                        .attrs("href")
        );*/

        page.getResultItems().add(
                page.getRender()
                        .renderToHtml()
                        .toEntityList(CNBlog.class)
        );
    }

    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        int gg = 321;
        Integer e = 321;
        Integer f = 321;
        Long g = 3l;
        System.out.println(c == d);//true
        System.out.println(e == f);//false
        System.out.println(e == gg);
        System.out.println(c == (a+b));//true
        System.out.println(c.equals(a+b));//true
        System.out.println(g ==(a+b));//false
        System.out.println(g.equals(a+b));//false
        /*JCrawler.create()
                .redis("192.168.153.131:6379")
                .crawl("http://www.cnblogs.com/")
                .processBy(new CNBlogProcess())
                .run();*/
    }
}
