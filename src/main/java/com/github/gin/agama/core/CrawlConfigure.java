package com.github.gin.agama.core;

import com.github.gin.agama.site.Request;

import java.util.Set;

public class CrawlConfigure {

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
