package org.pyj.vertical.JCrawler.processer;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pyj.vertical.JCrawler.downloader.CrawlConfiger;
import org.pyj.vertical.JCrawler.downloader.JCrawler;

public class OSCPageProcess extends BasePageProcess{

	@Override
	public void process() {
		
		Elements elements = $(".ctt");
		for(Element e : elements){
			System.out.println(e.html());
		}
	}
	
	public static void main(String[] args) {
		//HttpProxy proxy = new HttpProxy(Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
		CrawlConfiger config = new CrawlConfiger("http://weibo.cn/gztq");
		JCrawler.create(new OSCPageProcess()).setConfig(config).run();
	}
}
