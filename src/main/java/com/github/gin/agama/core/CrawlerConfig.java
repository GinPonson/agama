package com.github.gin.agama.core;

/**
 * 爬虫的基本设置
 * @author  GinPonson
 */
public class CrawlerConfig {

    /**
     *  线程等待请求的时间
     *  默认等待15s，超时没有请求则认为完成
     */
    private int waitTime = 15000;

    /**
     *  单个爬虫爬取的间隔
     */
    private int interval = 3000;

    /**
     *  请求失败的重试次数
     */
    private int retryTimes = 5;

    /**
     *  爬虫线程的数量
     */
    private int threadNum = 2;

    /**
     *  否启用代理，代理设置在Proxy.json
     */
    private boolean enableProxy;

    public int getInterval() {
        return interval;
    }

    public CrawlerConfig setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public CrawlerConfig setThreadNum(int threadNum) {
        this.threadNum = threadNum;
        return this;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public CrawlerConfig setWaitTime(int waitTime) {
        this.waitTime = waitTime;
        return this;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public boolean isEnableProxy() {
        return enableProxy;
    }

    public CrawlerConfig setEnableProxy(boolean enableProxy) {
        this.enableProxy = enableProxy;
        return this;
    }
}
