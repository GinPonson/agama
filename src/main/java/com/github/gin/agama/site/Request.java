package com.github.gin.agama.site;

import java.util.HashMap;
import java.util.Map;

public class Request {

	/**
	 * 请求头
	 */
	private Map<String,String> headers = new HashMap<String,String>();
	
	/**
	 * 请求url
	 */
	private String url;
	
	/**
	 * 请求方法
	 */
	private String method;

	/**
	 * 请求优先级
	 */
	private int priority = 0;

    /**
     * 是否为重试请求
     */
	private boolean isRetryRequest;

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
