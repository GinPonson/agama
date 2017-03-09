package com.github.gin.agama.core;

public class CrawlerConfig {

    /**
     *  crawl interval
     */
    private int interval = 3000;

    /**
     *  time wait for request
     */
    private int waitTime = 3000;

    /**
     *  retry times
     */
    private int retryTime = 5;

    /**
     *  amount of crawler
     */
    private int threadNum = 1;

    /**
     *  if use selenium load the page
     */
    private boolean useAjax = false;

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

    public int getRetryTime() {
        return retryTime;
    }

    public CrawlerConfig setRetryTime(int retryTime) {
        this.retryTime = retryTime;
        return this;
    }

    public boolean isUseAjax() {
        return useAjax;
    }

    public CrawlerConfig setUseAjax(boolean useAjax) {
        this.useAjax = useAjax;
        return this;
    }
}
