package weather;

import com.github.gin.agama.core.CrawlerConfig;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.proxy.Proxys;
import com.github.gin.agama.site.Page;

import java.net.Proxy;

public class WeatherProcess implements PageProcess {

	public void process(Page page) {
		System.out.println(page.getRawText());

        /*System.out.println(page.getHtml().select("#pro").find("option[value=10101]").texts());

        System.out.println(page.getHtml().select("#kind").parent().attr("name"));

        System.out.println(page.getHtml().select(".list").find("a").attrs("href"));

        System.out.println(page.getHtml().imgs().text());

        System.out.println(page.getHtml().xpath("//div[@class='friendLink']/p").first().find("/a").first().attr("href"));

        System.out.println(page.getHtml().xpath("//div[@class='friendLink']//a").attrs("href"));

        Map<String,String> map = new TreeMap<>();
        map.put("文本", page.getHtml().xpath("//div[@class='friendLink']/p").first().find("/a").first().text());
        map.put("链接", page.getHtml().xpath("//div[@class='friendLink']/p").first().find("/a").first().attr("href"));
        map.put("打开方式", page.getHtml().xpath("//div[@class='friendLink']/p").first().find("/a").first().attr("target"));
*/
    }

    public static void main(String[] args) {
		HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
        Proxys.addProxy(proxy);
		CrawlerConfig config = new CrawlerConfig();
		config.setThreadNum(2);
		config.setUseAjax(true);
        //config.setDriverPath("D:/download/phantomjs-2.1.1-windows/bin/phantomjs.exe");
		JCrawler.create()
                //.processBy(new WeatherProcess())
                .crawl("http://www.weather.com.cn/alarm/newalarmlist.shtml")
                //.downloadBy(new WeatherDownloader())
                //.redis("127.0.0.1:6379")
                //.scheduleBy(new RedisUrlScheduler("127.0.0.1:6379"))
                //.useConfig(config)
                .run();
	}
}
