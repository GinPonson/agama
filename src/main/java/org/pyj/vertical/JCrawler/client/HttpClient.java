package org.pyj.vertical.JCrawler.client;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Map.Entry;

import org.pyj.vertical.JCrawler.downloader.Request;
import org.pyj.vertical.JCrawler.downloader.Response;
import org.pyj.vertical.JCrawler.proxy.HttpProxy;

public class HttpClient {
	//private static final Logger log  = LoggerFactory.getLogger(HttpClient.class);
	
	protected HttpURLConnection conn = null;
	protected Proxy proxy = Proxy.NO_PROXY;
	
	private void init(String url) throws IOException{
		initConnection(url);
		setDefaultHeader();
	}
	
	private void initConnection(String _url) throws IOException{
		URL url = new URL(_url);
		conn = (HttpURLConnection) url.openConnection(proxy);
	}
	
	private void setDefaultHeader() {
		if(conn == null){
			throw new NullPointerException("init HttpURLConnection first!");
		}
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("user-agent", "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 5.1;SV1)");
	}	
	
	private void setHeaders(Map<String, String> headers) {
		if(headers != null && !headers.isEmpty()){
			for(Entry<String, String> header : headers.entrySet()){
				conn.setRequestProperty(header.getKey(), header.getValue());
			}
		}
	}
	
	private void connect() throws IOException{
		if(conn == null){
			throw new NullPointerException("init HttpURLConnection first!");
		}		
		conn.connect();
	}
	
	public Response execute(Request req) throws IOException{
System.out.println("download page:"+req.getUrl());		
		init(req.getUrl());
		
		setHeaders(req.getHeaders());
		
		connect();
		
		byte[] bytes = getResponseByByte();
				
		Response response = new Response();
		response.setContent(bytes);
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
//System.out.println(baos.toByteArray().length);
//System.out.println(new String(bytes));
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
	
	public void setHttpProxy(HttpProxy p){
		this.proxy = p;
	}
	
	public URLConnection getUrlConnection(){
		return conn;
	}
	
	public void setRequestProperty(String key,String value){
		conn.setRequestProperty(key, value);
	}
}
