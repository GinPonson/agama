package org.pyj.vertical.JCrawler.downloader;

import java.util.HashSet;
import java.util.Set;

import org.pyj.vertical.JCrawler.proxy.HttpProxy;

public class CrawlConfiger {
	
	//代理池
	//private Set<HttpProxy> proxyPool = new HashSet<HttpProxy>();
	
	private HttpProxy proxy;
	
	//请求集
	private Set<Request> startRequests = new HashSet<Request>();
	
	//默认睡眠时间
	private int sleepTime = 3000;
	
	private int threadNum = 1;
	
	public CrawlConfiger(){}
	
	public CrawlConfiger(String url){
		startRequests.add(new Request(url));
	}
	
	public CrawlConfiger(HttpProxy proxy,String url){
		this.proxy = proxy;
		startRequests.add(new Request(url));
	}
	
	public CrawlConfiger(HttpProxy proxy,Request request){
		this.proxy = proxy;
		startRequests.add(request);
	}

	public HttpProxy getProxy() {
		return proxy;
	}

	public void setProxy(HttpProxy proxy) {
		this.proxy = proxy;
	}

	public Set<Request> getStartRequests() {
		return startRequests;
	}

	public void setStartRequests(Set<Request> startRequests) {
		this.startRequests = startRequests;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}
	
	
}
