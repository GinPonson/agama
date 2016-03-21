package org.pyj.vertical.JCrawler.downloader;

import java.util.HashMap;
import java.util.Map;

public class Request {
	
	/**
	 * request url
	 */
	private String url;
	
	/**
	 * request method
	 */
	private String method;
	
	/**
	 * domain
	 */
	private String domain;
	
	/**
	 * request header
	 */
	private Map<String,String> headers = new HashMap<String,String>();
	
	/**
	 * current crawle depth
	 */
	private int curDepth = 1;
	
	private int priority;
	
	public Request(){}

	public Request(String url) {
		super();
		this.url = url;
	}
	
	public Request(String url,int curDepth) {
		super();
		this.url = url;
		this.curDepth = curDepth;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getCurDepth() {
		return curDepth;
	}

	public void setCurDepth(int curDepth) {
		this.curDepth = curDepth;
	}

	

}
