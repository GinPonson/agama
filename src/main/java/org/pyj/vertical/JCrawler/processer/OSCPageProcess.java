package org.pyj.vertical.JCrawler.processer;

import java.net.Proxy.Type;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pyj.vertical.JCrawler.downloader.CrawlConfiger;
import org.pyj.vertical.JCrawler.downloader.JCrawler;
import org.pyj.vertical.JCrawler.downloader.Page;
import org.pyj.vertical.JCrawler.downloader.Request;
import org.pyj.vertical.JCrawler.proxy.HttpProxy;

public class OSCPageProcess extends BasePageProcess{

	@Override
	public void processPage(Page page) {
		
		Elements elements = $(".ctt");
		for(Element e : elements){
			System.out.println(e.html());
		}
		System.out.println(page.getText());
		/*Elements elements1 = $("a");
		for(Element e : elements1){
			page.getRequests().add(e.attr("href"));
		}*/
		
	}
	
	public static void main(String[] args) {
		//HttpProxy proxy = new HttpProxy(Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
		//Request request = new Request("http://weibo.cn/gztq");
		//request.getHeaders().put("Cookie", "SUB=_2A257zrmqDeRxGeRG6lcT8yvPwzmIHXVZMMfirDV6PUJbstAKLWjskW1LHesxCZWdmP4tP6NxAvueEBgm5qAd3A..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5xrXkd.l1DGBGxFmjTHzJa5JpX5o2p; SUHB=0U152jriwP8Eiz; SSOLoginState=1456130555; gsid_CTandWM=4u3JCpOz5phuwcoiyIkv7bOn16d; _T_WM=219a624c89e759d7954169a12b3410c4");
		CrawlConfiger config = new CrawlConfiger("http://www.bilibili.com/video/bangumi-two-1.html");
		//config.setProxy(proxy);
		JCrawler.create(new OSCPageProcess()).setConfig(config).run();
	}
}
