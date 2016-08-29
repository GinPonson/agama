package net.osc.gin.agama.processer;

import java.net.Proxy;
import java.util.List;

import net.osc.gin.agama.core.CrawlConfiger;
import net.osc.gin.agama.core.JCrawler;
import net.osc.gin.agama.entity.BiliBiliEntity;
import net.osc.gin.agama.proxy.HttpProxy;
import net.osc.gin.agama.site.Page;
import net.osc.gin.agama.sorter.FileDataStorer;
import net.osc.gin.agama.util.BeanUtils;

public class OSCPageProcess implements PageProcess{

	public void process(Page page) {
		//System.out.println(page.getHtml().toString());
		//System.out.println(page.getHtml().select(".vd-list-cnt").text());
		/*for(String link : page.getHtml().absLinks(".vd-list-cnt")){
			System.out.println(link);
		}*/
		System.out.println("-----------------------------------");
		List<BiliBiliEntity> lists = page.getHtml().toEntityList(BiliBiliEntity.class);
		for(BiliBiliEntity b : lists){
			System.out.println(b.toString());
		}

		page.getRequests().addAll(page.getHtml().xpath("//div[@class='pagelistbox']/a/@href").texts());

        page.setFields(BeanUtils.toCSVRecord(page));
	}

	public static void main(String[] args) {
		HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
		//Request request = new Request("http://weibo.cn/gztq");
		CrawlConfiger config = new CrawlConfiger("http://www.bilibili.com/video/bangumi-two-1.html");
		config.setProxy(proxy);
		config.setDepth(1);
		config.setThreadNum(2);
		JCrawler.create(new OSCPageProcess()).persistBy(new FileDataStorer("D:\\test.csv")).setConfig(config).run();
	}
}
