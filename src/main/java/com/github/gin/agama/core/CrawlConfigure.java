package com.github.gin.agama.core;

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

    public CrawlConfigure setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public CrawlConfigure setThreadNum(int threadNum) {
        this.threadNum = threadNum;
        return this;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public CrawlConfigure setWaitTime(int waitTime) {
        this.waitTime = waitTime;
        return this;
    }

    public int getRetryTime() {
        return retryTime;
    }

    public CrawlConfigure setRetryTime(int retryTime) {
        this.retryTime = retryTime;
        return this;
    }

    public boolean isUseAjax() {
        return useAjax;
    }

    public CrawlConfigure setUseAjax(boolean useAjax) {
        this.useAjax = useAjax;
        return this;
    }
}
