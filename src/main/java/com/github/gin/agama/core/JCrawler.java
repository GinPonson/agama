package com.github.gin.agama.core;

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

    private CrawlConfiger configer = new CrawlConfiger();

    private Lock urlLock = new ReentrantLock();

    private Condition waitCondition = urlLock.newCondition();

    private Map<String, AtomicInteger> retryMap = new ConcurrentHashMap<>();

    private ThreadPool threadPool;

    private Downloader downloader;

    private PageProcess pageProcess;

    private Pipeline pipeline;

    private JCrawler(PageProcess pageProcess) {
        this.pageProcess = pageProcess;
    }

    public static JCrawler create(PageProcess pageProcess) {
        return new JCrawler(pageProcess);
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

    public JCrawler useConfig(CrawlConfiger config) {
        this.configer = config;
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
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("thread prepared .....");
        }
        if (THREAD_STATUS == Status.STARTED) {
            LOGGER.error("thread started !!!");
            throw new AgamaException("thread started !!!");
        }
    }

    private void initComponent() {
        if (downloader == null) {
            downloader = configer.isUseAjax() ? new DefaultPhantomDownloader() : new HttpDownloader();
        }
        if (pipeline == null) {
            pipeline = new ConsolePipeline();
        }
        if (threadPool == null) {
            threadPool = new ThreadPool(configer.getThreadNum());
        }
        if (!configer.getStartRequests().isEmpty()) {
            configer.getStartRequests().forEach(request -> scheduler.push(request));
        }

        THREAD_STATUS = Status.PREPARED;

    }

    private void watiURL() {
        urlLock.lock();
        try {
            if (threadPool.getThreadAlive() != 0) {
                waitCondition.await(configer.getWaitTime(), TimeUnit.MILLISECONDS);
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

            sleep(configer.getInterval());
        } catch (Exception e) {
            e.printStackTrace();
            AtomicInteger retriedTime = retryMap.get(request.getUrl());

            if (retriedTime == null) {
                retriedTime = new AtomicInteger();
                retryMap.put(request.getUrl(), retriedTime);
            }

            int time = retriedTime.incrementAndGet();
            if (time <= configer.getRetryTime()) {
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
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Thread:" + Thread.currentThread().getName() + " is sleeping");
            }

            Thread.sleep(time);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Thread:" + Thread.currentThread().getName() + " finished sleepping");
            }
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
        THREAD_STATUS = Status.STOPPED;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public CrawlConfiger getConfiger() {
        return configer;
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
