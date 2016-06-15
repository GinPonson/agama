package net.osc.gin.agama.client;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.osc.gin.agama.site.Request;
import net.osc.gin.agama.site.Response;

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
			bis = new BufferedInputStream(getInputStream());
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
				log.error("流关闭错误！");
				e.printStackTrace();
			}			
		}		
		return bytes;
	}
	
	private InputStream getInputStream() {
		InputStream input = null;
		String contentEncoding = conn.getContentEncoding();
		try {
			if(StringUtils.isNotBlank(contentEncoding)){
				if("gzip".equalsIgnoreCase(contentEncoding)){
					input = new GZIPInputStream(conn.getInputStream());
				} else if("deflate".equalsIgnoreCase(contentEncoding)){
					input =  new InflaterInputStream(conn.getInputStream());
				} else {
					input = conn.getInputStream();
				}
			} else {
				input = conn.getInputStream();
			}
		}catch(IOException e){
			log.error("获取输入流错误！");
			e.printStackTrace();
		}
		return input;
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
