package org.pyj.vertical.JCrawler.client;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.pyj.vertical.JCrawler.downloader.Request;
import org.pyj.vertical.JCrawler.downloader.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient {
	private static final Logger log  = LoggerFactory.getLogger(HttpClient.class);
	
	private HttpURLConnection conn = null;
	protected Proxy proxy = Proxy.NO_PROXY;
	
	public HttpClient(){}
	
	public HttpClient(Proxy proxy){
		this.proxy = proxy;
	}
	
	public Response execute(Request req) throws IOException{
		log.info("正在抓取页面:"+req.getUrl());	
		
		URL url = new URL(req.getUrl());
		conn = (HttpURLConnection) url.openConnection(proxy);
		
		conn.setRequestProperty("user-agent", "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 5.1;SV1)");
		
		setHeaders(req.getHeaders());
		
		conn.connect();
		
		byte[] bytes = getResponseByByte();
				
		Response response = new Response();
		response.setContentByte	(bytes);
		response.setResponseCode(conn.getResponseCode());
		response.setResponseMsg(conn.getResponseMessage());
		response.setContentType(conn.getContentType());
		
		return response;
	}
	
	private byte[] getResponseByByte(){
		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = null;
		byte[] bytes = null;
		try{
			bis = new BufferedInputStream(conn.getInputStream());
			baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] bufs = new byte[1024*4];
			while((len = bis.read(bufs)) != -1){
				baos.write(bufs, 0, len);
			}
			bytes = baos.toByteArray();
			
			if(log.isDebugEnabled()){
				log.debug("返回报文长度:"+baos.toByteArray().length);
				log.debug("返回内容:"+new String(bytes));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(bis != null)
					bis.close();
				if(baos != null)
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}		
		return bytes;
	}
	
	private void setHeaders(Map<String, String> headers) {
		if(log.isDebugEnabled()){
			log.debug("Request Header:");
		}
		if(headers != null && !headers.isEmpty()){
			for(Entry<String, String> header : headers.entrySet()){
				conn.setRequestProperty(header.getKey(), header.getValue());
				
				if(log.isDebugEnabled()){
					log.debug(header.getKey()+":"+header.getValue() );
				}
			}
		}
		
	}
}
