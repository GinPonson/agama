package org.pyj.vertical.JCrawler.processer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pyj.vertical.JCrawler.downloader.CrawlConfiger;
import org.pyj.vertical.JCrawler.downloader.JCrawler;
import org.pyj.vertical.JCrawler.downloader.Page;

public class OSCPageProcess extends BasePageProcess{

	@Override
	public void processPage(Page page) {
		//System.out.println(page.getHtml().toString());
		Elements elements = $("a");
		for(Element e : elements){
			//e.setBaseUri("http://www.bilibili.com/");
			//System.out.println(e.attr("href"));
			//System.out.println(e.absUrl("href"));
		}
		//System.out.println(Jsoup.parse(page.getRawText()).);
		//System.out.println(page.getText());
		/*Elements elements1 = $("a");
		for(Element e : elements1){
			page.getRequests().add(e.attr("href"));
		}*/
		//page.getFields().put("links", page.getHtml().absLinks().get(0));
		for(String link :page.getHtml().links() ){
			System.out.println(link);
		}
		
	}
	
	public static void main(String[] args) {
		//HttpProxy proxy = new HttpProxy(Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
		//Request request = new Request("http://weibo.cn/gztq");
		CrawlConfiger config = new CrawlConfiger("http://www.bilibili.com/video/bangumi-two-1.html");
		//config.setProxy(proxy);
		JCrawler.create(new OSCPageProcess()).setConfig(config).run();
	}
}
