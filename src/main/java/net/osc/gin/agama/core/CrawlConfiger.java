package net.osc.gin.agama.core;

import java.util.HashSet;
import java.util.Set;

import net.osc.gin.agama.proxy.HttpProxy;
import net.osc.gin.agama.site.Request;

public class CrawlConfiger {
	
	//代理池
	//private Set<HttpProxy> proxyPool = new HashSet<HttpProxy>();
	
	private HttpProxy proxy;
	
	/**
	 * 请求集
	 */
	private Set<Request> startRequests = new HashSet<Request>();
	
	/**
	 * 每次抓取默认间隔时间
	 */
	private int sleepTime = 3000;
	
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
	private int threadNum = 1;
	
	/**
	 * 抓取深度
	 */
	private int depth = -1;
	
	private boolean isAjaxModel = false;

    private String driverPath;
	
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

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public boolean isAjaxModel() {
		return isAjaxModel;
	}

	public void setAjaxModel(boolean isAjaxModel) {
		this.isAjaxModel = isAjaxModel;
	}

    public String getDriverPath() {
        return driverPath;
    }

    public void setDriverPath(String driverPath) {
        this.driverPath = driverPath;
    }
}
