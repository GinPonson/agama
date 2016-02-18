package org.pyj.vertical.JCrawler.processer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pyj.vertical.JCrawler.downloader.JCrawler;
import org.pyj.vertical.JCrawler.downloader.Page;

public class OSCPageProcess implements PageProcess{

	@Override
	public void process(Page page) {
		Document document = Jsoup.parse(page.getText());
System.out.println(page.getText());
		Elements as = document.select("a");
		//相对url补全
		Pattern p = Pattern.compile("http://my.oschina.net/flashsword/blog/\\d+");
		for(Element e : as){
			String href = e.attr("href");
			Matcher m = p.matcher(href);
			if(m.matches())
				page.getRequests().add(href);
System.out.println("--------"+href);
		}
		for(String req : page.getRequests()){
			System.out.println(req);
		}
		
		//通过判断title的有无跳过
		Elements titles = document.select(".BlogEntity .BlogTitle h1");
		String title = titles.text();
		page.getFields().put("title", title);
		
		//根据需要过滤
		Elements contents = document.select(".BlogContent");
		String content = contents.html();
		page.getFields().put("content", content);
		
		String tagStr = "";
		Elements tags = document.select(".BlogTags a");
		for(Element e : tags){
			String tag = e.text();
			tagStr += tag + ",";
		}
		page.getFields().put("tag", tagStr);
	}

	public static void main(String[] args) {
		JCrawler.create(new OSCPageProcess()).addRequest("http://www.cnblogs.com/").run();
	}
}
