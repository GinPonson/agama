package org.pyj.vertical.JCrawler.downloader;

import java.io.IOException;
import java.net.Proxy;
import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pyj.vertical.JCrawler.client.HttpClient;
import org.pyj.vertical.JCrawler.site.Page;
import org.pyj.vertical.JCrawler.site.Request;
import org.pyj.vertical.JCrawler.site.Response;
import org.pyj.vertical.JCrawler.util.UrlUtils;

public class HttpDownloader implements Downloader{

	private Proxy proxy = Proxy.NO_PROXY;
	
	@Override
	public Page download(Request req) {
		HttpClient client = null;
		Page page = null;
		try {
			client = new HttpClient(proxy);
			Response res = client.execute(req);
			if(res.getResponseCode() == 200){
				page = handleResponse(res);
				page.setDomain(req.getDomain());
				page.setUrl(req.getUrl());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return page;
	}
	
	@Override
	public void setHttpProxy(Proxy p) {
		this.proxy = p;		
	}
	
	private Page handleResponse(Response res) throws IOException {
		Page page = new Page();
		page.setRawText(getContent(res));
		
		return page;
	}	
	
	private String getContent(Response res) throws IOException {
		String content ;
		String charset = getCharset(res.getContentType(),res.getContentByte());
		if(charset != null)
			content = new String(res.getContentByte(),charset);
		else
			content = new String(res.getContentByte());
		return content;
	}
	
	//1、contentType 2、meta
	public String getCharset(String contentType, byte[] contentByte) throws IOException {		
		if(contentType != null && !"".equals(contentType)){
			//考虑a = 1之间的空格，可以用正则text/html;charset=utf-8
			if(contentType.indexOf("charset") != -1)
				return UrlUtils.matchCharset(contentType);		
		}
		Charset charset = Charset.defaultCharset();
		String content = new String(contentByte , charset.name());
		Document doc = Jsoup.parse(content);
		Elements elements = doc.getElementsByTag("meta");
		for(Element element : elements){
			//<meta http-equiv="content-type" content="text/html;charset=utf-8">
			String metaContent = element.attr("content");
			if(metaContent != null && !"".equals(metaContent))
				return UrlUtils.matchCharset(metaContent);
			//<meta charset="utf-8">
			String metaCharset = element.attr("charset");
			if(metaCharset != null && !"".equals(metaCharset))
				return metaCharset;
		}
		return null;
	}

	

}
