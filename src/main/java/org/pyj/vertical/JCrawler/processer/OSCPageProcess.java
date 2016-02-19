package org.pyj.vertical.JCrawler.processer;

import java.net.Proxy.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pyj.vertical.JCrawler.downloader.CrawlConfiger;
import org.pyj.vertical.JCrawler.downloader.JCrawler;
import org.pyj.vertical.JCrawler.downloader.Page;
import org.pyj.vertical.JCrawler.proxy.HttpProxy;

public class OSCPageProcess implements PageProcess{

	@Override
	public void process(Page page) {
		Document document = Jsoup.parse(page.getText());
		
		System.out.println(page.getText());
		
		Elements elements = document.select(".ctt");
		for(Element e : elements){
			System.out.println(e.text());
		}
	}
	//http://blog.csdn.net/xiaochunyong/article/details/7193744
	public static void main(String[] args) {
		HttpProxy proxy = new HttpProxy(Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
		CrawlConfiger config = new CrawlConfiger(proxy, "http://weibo.cn/gztq");
		JCrawler.create(new OSCPageProcess()).setConfig(config).run();
	}
}
