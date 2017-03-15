package com.github.gin.agama.downloader;

import java.io.IOException;
import java.nio.charset.Charset;

import com.github.gin.agama.site.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Response;
import com.github.gin.agama.util.UrlUtils;

public class HttpDownloader implements Downloader{

	@Override
	public Page download(Request request) throws IOException {
		Page page = null;
		HttpClient client = new HttpClient();
		Response response = client.execute(request);
		if(response.getResponseCode() == 200){
			page = handleResponse(request,response);
		} else if (response.getResponseCode() == 302 || response.getResponseCode() == 301) {
		    //Location
        }
		return page;
	}

	private Page handleResponse(Request request,Response response) throws IOException {
		Page page = new Page();
        page.setUrl(request.getUrl());
        page.setContentType(response.getContentType());

        String charset = getCharset(response.getContentType(), response.getContentByte());
        page.setCharset(charset);
        page.setRawText(getContent(response,charset));

		return page;
	}	
	
	private String getContent(Response response,String charset) throws IOException {
		String content ;
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
