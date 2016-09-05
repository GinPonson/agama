package com.github.gin.agama.processer;

import java.net.Proxy;
import java.util.Map;
import java.util.TreeMap;

import com.github.gin.agama.core.CrawlConfiger;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.sorter.FileDataStorer;

public class WeatherProcess implements PageProcess{

	public void process(Page page) {
		System.out.println(page.getRawText());

        System.out.println(page.getHtml().select("#pro").find("option[value=10101]").texts());

        System.out.println(page.getHtml().select("#kind").parent().attr("name"));

        System.out.println(page.getHtml().select(".list").find("a").attrs("href"));

        System.out.println(page.getHtml().imgs().text());

        System.out.println(page.getHtml().xpath("//div[@class='friendLink']/p").first().find("/a").first().attr("href"));

        System.out.println(page.getHtml().xpath("//div[@class='friendLink']//a").attrs("href"));

        Map<String,String> map = new TreeMap<>();
        map.put("文本", page.getHtml().xpath("//div[@class='friendLink']/p").first().find("/a").first().text());
        map.put("链接", page.getHtml().xpath("//div[@class='friendLink']/p").first().find("/a").first().attr("href"));
        map.put("打开方式", page.getHtml().xpath("//div[@class='friendLink']/p").first().find("/a").first().attr("target"));
        page.getRecords().add(map);


    }

    public static void main(String[] args) {
		HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
		CrawlConfiger config = new CrawlConfiger("http://www.weather.com.cn/alarm/newalarmlist.shtml");
		config.setProxy(proxy);
		config.setDepth(4);
		config.setThreadNum(2);
		config.setAjaxModel(true);
        config.setDriverPath("D:/download/phantomjs-2.1.1-windows/bin/phantomjs.exe");
		JCrawler.create(new WeatherProcess()).persistBy(new FileDataStorer("D:\\test.csv")).setConfig(config).run();
	}
}
