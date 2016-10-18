package com.github.gin.agama.processer;

import com.github.gin.agama.core.CrawlConfiger;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.entity.BiliBili;
import com.github.gin.agama.entity.BiliBiliVedio;
import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.sorter.FileDataStorer;

import java.net.Proxy;
import java.util.List;

public class OSCPageProcess implements PageProcess{

	public void process(Page page) {
		//System.out.println(page.getHtml().toString());
		//System.out.println(page.getHtml().select(".vd-list-cnt").text());
		/*for(String link : page.getHtml().absLinks(".vd-list-cnt")){
			System.out.println(link);
		}*/
		System.out.println("-----------------------------------");
		BiliBili biliBili = page.getHtml().toEntity(BiliBili.class);
		for(BiliBiliVedio b : biliBili.getVedios()){
			System.out.println(b.toString());
		}

		//page.getRequests().addAll(page.getHtml().xpath("//div[@class='pagelistbox']/a/@href").texts());

	}

	public static void main(String[] args) {
		//HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
		//Request request = new Request("http://weibo.cn/gztq");
		CrawlConfiger config = new CrawlConfiger("http://www.bilibili.com/video/bangumi-two-1.html");
		//config.setProxy(proxy);
		config.setDepth(1);
		config.setThreadNum(2);
		config.setAjaxModel(true);
		JCrawler.create(new OSCPageProcess()).setConfig(config).run();
	}
}
