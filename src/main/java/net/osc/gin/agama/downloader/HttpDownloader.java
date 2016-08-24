package net.osc.gin.agama.downloader;

import java.io.IOException;
import java.net.Proxy;
import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.osc.gin.agama.client.HttpClient;
import net.osc.gin.agama.site.Page;
import net.osc.gin.agama.site.Request;
import net.osc.gin.agama.site.Response;
import net.osc.gin.agama.util.UrlUtils;

public class HttpDownloader implements Downloader{

    private HttpClient client = new HttpClient();

	@Override
	public Page download(Request request) {
		Page page = null;
		try {
			Response response = client.execute(request);
			if(response.getResponseCode() == 200){
				page = handleResponse(request,response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return page;
	}
	
	@Override
	public void setHttpProxy(Proxy p) {
        client.setProxy(p);
	}
	
	private Page handleResponse(Request request,Response response) throws IOException {
		Page page = new Page();
		page.setRawText(getContent(response));
        page.setUrl(request.getUrl());

		return page;
	}	
	
	private String getContent(Response response) throws IOException {
		String content ;
		String charset = getCharset(response.getContentType(), response.getContentByte());
		if(charset != null)
			content = new String(response.getContentByte(),charset);
		else
			content = new String(response.getContentByte());
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
				return metaCharset.trim();
		}
		return null;
	}

	

}
