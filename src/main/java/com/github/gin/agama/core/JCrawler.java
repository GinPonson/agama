package com.github.gin.agama.core;

import com.github.gin.agama.Closeable;
import com.github.gin.agama.downloader.Downloader;
import com.github.gin.agama.downloader.HttpDownloader;
import com.github.gin.agama.downloader.DefaultPhantomDownloader;
import com.github.gin.agama.entity.AgamaEntity;
import com.github.gin.agama.exception.AgamaException;
import com.github.gin.agama.pipeline.ConsolePipeline;
import com.github.gin.agama.pipeline.Pipeline;
import com.github.gin.agama.processer.DefaultPageProcess;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.scheduler.DuplicateUrlScheduler;
import com.github.gin.agama.scheduler.FIFOUrlScheduler;
import com.github.gin.agama.scheduler.RedisUrlScheduler;
import com.github.gin.agama.scheduler.Scheduler;
import com.github.gin.agama.site.*;
import com.github.gin.agama.util.AgamaUtils;
import org.apache.http.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.support.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JCrawler.class);

    private Status THREAD_STATUS = Status.STOPPED;

    private CrawlConfigure configure = new CrawlConfigure();

    private Set<Request> startRequests = new HashSet<>();

    private Lock urlLock = new ReentrantLock();

    private Condition waitCondition = urlLock.newCondition();

    private CountDownLatch cdl;

    private Map<String, AtomicInteger> retryMap = new ConcurrentHashMap<>();

    private Class<? extends AgamaEntity> prey;

    private ThreadPool threadPool;

    private Downloader downloader;

    private PageProcess pageProcess;

    private Pipeline pipeline;

    private Scheduler scheduler;

    public static JCrawler create() {
        return new JCrawler();
    }

    public JCrawler prey(Class<? extends AgamaEntity> prey) {
        this.prey = prey;
        return this;
    }

    public JCrawler crawl(Request... requests) {
        for (Request request : requests) {
            startRequests.add(request);
        }
        return this;
    }

    public JCrawler crawl(String... urls) {
        for (String url : urls) {
            startRequests.add(new Request(url));
        }
        return this;
    }

    public JCrawler persistBy(Pipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public JCrawler useConfig(CrawlConfigure config) {
        this.configure = config;
        return this;
    }

    public JCrawler downloadBy(Downloader downloader) {
        this.downloader = downloader;
        return this;
    }

    public JCrawler processBy(PageProcess pageProcess) {
        this.pageProcess = pageProcess;
        return this;
    }

    public JCrawler scheduleBy(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public JCrawler redis(String address) {
        this.scheduler = new RedisUrlScheduler(address);
        return this;
    }

    public void run() {
        if (downloader == null) {
            downloader = configure.isUseAjax() ? new DefaultPhantomDownloader() : new HttpDownloader();
        }
        if (pipeline == null) {
            pipeline = new ConsolePipeline();
        }
        if (scheduler == null) {
            scheduler = new DuplicateUrlScheduler(new FIFOUrlScheduler());
        }
        if (!this.startRequests.isEmpty()) {
            this.startRequests.forEach(request -> scheduler.push(request));
        }
        if (pageProcess == null) {
            pageProcess = new DefaultPageProcess();
        }

        this.cdl = new CountDownLatch(configure.getThreadNum());

        for (int i = 0; i < configure.getThreadNum(); i++) {
            CrawlWorker worker = new CrawlWorker(this);
            Thread thread = new Thread(worker,"CrawlWorker"+i);
            thread.start();
        }

        shutdown();
    }

    private void shutdown() {
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (scheduler instanceof Closeable) {
            Closeable closeable = (Closeable) scheduler;
            closeable.close();
        }
    }

    private void checkIfStarted() {
        if (THREAD_STATUS == Status.STOPPED) {
            THREAD_STATUS = Status.PREPARED;
        }
        if (THREAD_STATUS == Status.STARTED) {
            throw new AgamaException("thread started !!!");
        }
    }

    public void singleComplete(){
        this.cdl.countDown();
    }


    public void addScheduleRequest(List<Request> requests) {
        if (AgamaUtils.isNotEmpty(requests)) {
            requests.forEach(request -> scheduler.push(request));
        }
    }

    public void addRetryRequest(Request request) {
        scheduler.push(request);
    }


    public CrawlConfigure getConfigure() {
        return configure;
    }

    public Downloader getDownloader() {
        return downloader;
    }

    public PageProcess getPageProcess() {
        return pageProcess;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public Class<? extends AgamaEntity> getPrey() {
        return prey;
    }

    public int getThreadStatus() {
        return THREAD_STATUS.getValue();
    }

    private enum Status {
        STOPPED(0), PREPARED(1), STARTED(2), SHUTDOWN(3);

        int statusNum;

        Status(int statusNum) {
            this.statusNum = statusNum;
        }

        public int getValue() {
            return statusNum;
        }

    }


}
