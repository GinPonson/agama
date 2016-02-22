package org.pyj.vertical.JCrawler.processer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pyj.vertical.JCrawler.downloader.Page;

public abstract class BasePageProcess implements PageProcess{
	
	private Document document;
	
	public abstract void processPage(Page page);
	
	@Override
	public void process(Page page) {
		document = Jsoup.parse(page.getText());
		
		processPage(page);
		
	}
	
	public Elements $(String tag){
		Elements elements = document.select(tag);
		
		return elements;
	}
	
	
	
	
}
