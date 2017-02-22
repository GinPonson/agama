package com.github.gin.agama.core;

import java.util.HashSet;
import java.util.Set;

import com.github.gin.agama.site.Request;

public class CrawlConfigure {

	/**
	 * 请求集
	 */
	private Set<Request> startRequests = new HashSet<Request>();
	
	/**
	 * 每次抓取默认间隔时间
	 */
	private int interval = 3000;
	
	/**
	 * 空队列等待时间
	 */
	private int waitTime = 3000;
	
	/**
	 * 重试次数
	 */
	private int retryTime = 5;
	
	/**
	 * 爬虫线程数量
	 */
	private int threadNum = 2;

    /**
     * 是否加载ajax内容
     */
	private boolean useAjax = false;

	public CrawlConfigure(){}
	
	public CrawlConfigure(String url){
		startRequests.add(new Request(url));
	}

	public CrawlConfigure(Request request){
		startRequests.add(request);
	}

    public Set<Request> getStartRequests() {
		return startRequests;
	}

	public void setStartRequests(Set<Request> startRequests) {
		this.startRequests = startRequests;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public int getRetryTime() {
		return retryTime;
	}

	public void setRetryTime(int retryTime) {
		this.retryTime = retryTime;
	}

	public boolean isUseAjax() {
		return useAjax;
	}

	public void setUseAjax(boolean useAjax) {
		this.useAjax = useAjax;
	}
}
