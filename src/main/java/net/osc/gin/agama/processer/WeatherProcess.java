package net.osc.gin.agama.processer;

import java.net.Proxy;
import java.util.List;

import net.osc.gin.agama.core.CrawlConfiger;
import net.osc.gin.agama.core.JCrawler;
import net.osc.gin.agama.entity.BiliBiliEntity;
import net.osc.gin.agama.proxy.HttpProxy;
import net.osc.gin.agama.site.Page;

public class WeatherProcess implements PageProcess{

	public void process(Page page) {
		System.out.println(page.getRawText());

        System.out.println(page.getHtml().select("#pro").find("option[value=10101]").texts());

        System.out.println(page.getHtml().select("#kind").parent().attr("name"));

        System.out.println(page.getHtml().select(".list").find("a").attrs("href"));

        System.out.println(page.getHtml().imgs().text());
    }

	public static void main(String[] args) {
		HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
		CrawlConfiger config = new CrawlConfiger("http://www.weather.com.cn/alarm/newalarmlist.shtml");
		config.setProxy(proxy);
		config.setDepth(4);
		config.setThreadNum(2);
		config.setAjaxModel(true);
        config.setDriverPath("D:/download/phantomjs-2.1.1-windows/bin/phantomjs.exe");
		JCrawler.create(new WeatherProcess()).setConfig(config).run();
	}
}
