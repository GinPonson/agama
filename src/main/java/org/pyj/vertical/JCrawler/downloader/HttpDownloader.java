package org.pyj.vertical.JCrawler.downloader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pyj.vertical.JCrawler.client.HttpClient;

public class HttpDownloader implements Downloader{

	@Override
	public Page download(Request req) {
		HttpClient client = null;
		Page page = null;
		try {
			client = new HttpClient();
			Response res = client.execute(req);
			if(res.getResponseCode() == 200)
				page = handleResponse(res);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return page;
	}
	private Page handleResponse(Response res) throws IOException {
		Page page = new Page();
		page.setText(getContent(res));
		
		return page;
	}	
	
	private String getContent(Response res) throws IOException {
		String content ;
		String charset = getCharset(res.getContentType(),res.getContent());
		if(charset != null)
			content = new String(res.getContent(),charset);
		else
			content = new String(res.getContent());
		return content;
	}
	
	//1、contentType 2、meta
	public String getCharset(String contentType, byte[] contentByte) throws IOException {		
		if(contentType != null && !"".equals(contentType)){
			//考虑a = 1之间的空格，可以用正则text/html;charset=utf-8
			if(contentType.indexOf("charset") != -1)
				return matchCharset(contentType);		
		}
		Charset charset = Charset.defaultCharset();
		String content = new String(contentByte , charset.name());
		Document doc = Jsoup.parse(content);
		Elements elements = doc.getElementsByTag("meta");
		for(Element element : elements){
			//<meta http-equiv="content-type" content="text/html;charset=utf-8">
			String metaContent = element.attr("content");
			if(metaContent != null && !"".equals(metaContent))
				return matchCharset(metaContent);
			//<meta charset="utf-8">
			String metaCharset = element.attr("charset");
			if(metaCharset != null && !"".equals(metaCharset))
				return metaCharset;
		}
		return null;
	}

	public static final Pattern patternForCharset = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)");
	public static String matchCharset(String contentType) {
        Matcher matcher = patternForCharset.matcher(contentType);
        if (matcher.find()) {
            String charset = matcher.group(1);
            if (Charset.isSupported(charset)) {
                return charset;
            }
        }
        return null;
    }
}
