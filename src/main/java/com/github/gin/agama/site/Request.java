package com.github.gin.agama.site;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  GinPonson
 */
public class Request {

	private Map<String,String> headers = new HashMap<>();

	private Map<String,String> cookies = new HashMap<>();

	private String method = "GET";

	private boolean isRetryRequest;
	
	private String url;
	
	private int priority;

	public Request(){}

	public Request(String url) {
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

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

    public boolean isRetryRequest() {
        return isRetryRequest;
    }

    public void setIsRetryRequest(boolean isRetryRequest) {
        this.isRetryRequest = isRetryRequest;
    }
}
