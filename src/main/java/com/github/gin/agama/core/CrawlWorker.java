package com.github.gin.agama.core;

import com.github.gin.agama.downloader.Downloader;
import com.github.gin.agama.entity.AgamaEntity;
import com.github.gin.agama.pipeline.Pipeline;
import com.github.gin.agama.scheduler.Scheduler;
import com.github.gin.agama.site.AbstractRender;
import com.github.gin.agama.site.JsonRender;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.util.AgamaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by FSTMP on 2017/3/7.
 */
public class CrawlWorker implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlWorker.class);

    private static final long _1_MINUTE = 1000 * 60;

    private Map<String, AtomicInteger> retryMap = new ConcurrentHashMap<>();

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private JCrawler jCrawler;

    public CrawlWorker(JCrawler jCrawler) {
        this.jCrawler = jCrawler;
    }

    @Override
    public void run() {
        Scheduler scheduler = jCrawler.getScheduler();
        Downloader downloader = jCrawler.getDownloader();
        Pipeline pipeline = jCrawler.getPipeline();

        while (true) {
            Request request = scheduler.poll();

            if (request == null) {
                waitRequest();

                request = scheduler.poll();
                if(request == null) {
                    jCrawler.singleComplete();
                    break;
                }
            }
            try {
                Page page = downloader.download(request);

                if (AgamaUtils.isNotBlank(page.getRawText())) {
                    //pageProcess.process(page);

                    //addScheduleRequest(page.getRequests());

                    AbstractRender render = new JsonRender();
                    AgamaEntity entity = render.inject(page, jCrawler.getPrey());

                    pipeline.process(entity);
                }

                interval();
            } catch (Exception e) {
                e.printStackTrace();
                AtomicInteger retriedTime = retryMap.get(request.getUrl());

                if (retriedTime == null) {
                    retriedTime = new AtomicInteger();
                    retryMap.put(request.getUrl(), retriedTime);
                }

                int time = retriedTime.incrementAndGet();
                if (time <= jCrawler.getConfigure().getRetryTime()) {
                    LOGGER.info("crawling the page error ,now trying reconnect [{}] times,", time);

                    request.setPriority(999);
                    request.setIsRetryRequest(true);
                    jCrawler.addRetryRequest(request);

                    lineUp(_1_MINUTE);
                } else {
                    LOGGER.error("error reason : {} ", e.getMessage());
                }
            }

            signalCondition();

        }
    }

    private void waitRequest() {
        lock.lock();
        try {
            long waitTime = jCrawler.getConfigure().getWaitTime();
            condition.await(waitTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void interval() {
        try {
            long interval = jCrawler.getConfigure().getInterval();
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void lineUp(long time){
        try {
            wait(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void signalCondition() {
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
