package org.pyj.vertical.JCrawler.downloader;

import java.util.HashMap;
import java.util.Map;

public class Request {
	private String url;
	
	private String method;
	
	private String domain;
	
	private Map<String,String> headers = new HashMap<String,String>();
	
	private int priority;
	
	public Request(){}

	public Request(String url) {
		super();
		this.url = url;
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
	
	
}
