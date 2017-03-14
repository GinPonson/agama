package cnblog;

import com.github.gin.agama.core.CrawlerConfig;
import com.github.gin.agama.core.CrawlerContext;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.scheduler.FIFOUrlScheduler;
import com.github.gin.agama.site.bean.XpathEntity;

/**
 * Created by GinPonson on 3/14/2017.
 */
public class Attack extends XpathEntity{

    public static void main(String[] args) {
        JCrawler.create()
                //.redis("192.168.153.131:6379")
                .crawl("http://www.yibaogao.com/")
                .context(
                        CrawlerContext.create()
                                .scheduleBy(new FIFOUrlScheduler())
                                .persistBy(new AttackPiline())
                                .useConfig(new CrawlerConfig().setInterval(100).setThreadNum(4))
                                .build()
                ).prey(Attack.class)
                //.processBy(new CNBlogProcess())
                .run();
    }
}
