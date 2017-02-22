package com.github.gin.agama.core;

import com.github.gin.agama.Closeable;
import com.github.gin.agama.downloader.Downloader;
import com.github.gin.agama.downloader.HttpDownloader;
import com.github.gin.agama.downloader.DefaultPhantomDownloader;
import com.github.gin.agama.exception.AgamaException;
import com.github.gin.agama.pipeline.ConsolePipeline;
import com.github.gin.agama.pipeline.Pipeline;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.scheduler.DuplicateUrlScheduler;
import com.github.gin.agama.scheduler.FIFOUrlScheduler;
import com.github.gin.agama.scheduler.Scheduler;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.util.AgamaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JCrawler.class);

    private static final int _1_MINUTE = 1000 * 60;

    private Status THREAD_STATUS = Status.STOPPED;

    private Scheduler scheduler = new DuplicateUrlScheduler(new FIFOUrlScheduler());

    private CrawlConfigure configure = new CrawlConfigure();

    private Lock urlLock = new ReentrantLock();

    private Condition waitCondition = urlLock.newCondition();

    private Map<String, AtomicInteger> retryMap = new ConcurrentHashMap<>();

    private ThreadPool threadPool;

    private Downloader downloader;

    private PageProcess pageProcess;

    private Pipeline pipeline;

    private JCrawler() { }

    public static JCrawler create() {
        return new JCrawler();
    }

    public JCrawler crawl(Request... requests) {
        for (Request request : requests) {
            scheduler.push(request);
        }
        return this;
    }

    public JCrawler crawl(String... urls) {
        for (String url : urls) {
            scheduler.push(new Request(url));
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
        this.scheduler = scheduler;
        return this;
    }

    public void run() {
        checkIfStarted();

        initComponent();

        THREAD_STATUS = Status.STARTED;

        while (Status.SHUTDOWN != THREAD_STATUS && !threadPool.isShutdown()) {
            Request request = scheduler.poll();
            if (request == null) {
                if (threadPool.getThreadAlive() == 0) {
                    break;
                }
                watiURL();
            } else {
                final Request finRequest = request;
                threadPool.execute(() -> {
                    process(finRequest);
                    signalCondition();
                });

            }
        }

        shutdown();
    }

    private void checkIfStarted() {
        if (THREAD_STATUS == Status.STOPPED) {
            THREAD_STATUS = Status.PREPARED;
        }
        if (THREAD_STATUS == Status.STARTED) {
            throw new AgamaException("thread started !!!");
        }
    }

    private void initComponent() {
        if (downloader == null) {
            downloader = configure.isUseAjax() ? new DefaultPhantomDownloader() : new HttpDownloader();
        }
        if (pipeline == null) {
            pipeline = new ConsolePipeline();
        }
        if (threadPool == null) {
            threadPool = new ThreadPool(configure.getThreadNum());
        }
        if (!configure.getStartRequests().isEmpty()) {
            configure.getStartRequests().forEach(request -> scheduler.push(request));
        }

        THREAD_STATUS = Status.PREPARED;

    }

    private void watiURL() {
        urlLock.lock();
        try {
            if (threadPool.getThreadAlive() != 0) {
                waitCondition.await(configure.getWaitTime(), TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            urlLock.unlock();
        }
    }

    private void signalCondition() {
        urlLock.lock();
        try {
            waitCondition.signal();
        } finally {
            urlLock.unlock();
        }
    }

    private void process(Request request) {
        try {
            Page page = downloader.download(request);

            if (AgamaUtils.isNotBlank(page.getRawText())) {
                pageProcess.process(page);

                addScheuleRequest(page.getRequests());

                pipeline.process(page.getResultItems().getItems());
            }

            sleep(configure.getInterval());
        } catch (Exception e) {
            e.printStackTrace();
            AtomicInteger retriedTime = retryMap.get(request.getUrl());


            if (retriedTime == null) {
                retriedTime = new AtomicInteger();
                retryMap.put(request.getUrl(), retriedTime);
            }

            int time = retriedTime.incrementAndGet();
            if (time <= configure.getRetryTime()) {
                LOGGER.info("爬取网页出错,正在尝试第" + time + "次重连...");

                request.setPriority(999);
                request.setIsRetryRequest(true);
                addRetryRequest(request);
                sleep(_1_MINUTE);
            } else {
                LOGGER.error("错误原因:" + e.getMessage());
            }
        }
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addScheuleRequest(List<Request> requests) {
        if (AgamaUtils.isNotEmpty(requests)) {
            requests.forEach(request -> scheduler.push(request));
        }
    }

    private void addRetryRequest(Request request) {
        scheduler.push(request);
    }

    private void shutdown() {
        threadPool.shutdown();
        if(scheduler instanceof Closeable){
            Closeable closeable = (Closeable) scheduler;
            closeable.close();
        }
        THREAD_STATUS = Status.STOPPED;
    }

    public CrawlConfigure getConfigure() {
        return configure;
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
