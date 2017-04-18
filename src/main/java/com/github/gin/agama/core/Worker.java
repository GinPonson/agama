package com.github.gin.agama.core;

import com.github.gin.agama.downloader.Downloader;
import com.github.gin.agama.pipeline.Pipeline;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.scheduler.Scheduler;
import com.github.gin.agama.site.entity.AgamaEntity;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.site.render.Render;
import com.github.gin.agama.util.AgamaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author GinPonson
 */
public class Worker implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    private static final long _1_MINUTE = 1000 * 60;

    private Map<String, AtomicInteger> retryMap = new ConcurrentHashMap<>();

    private Lock lock = new ReentrantLock();

    private Condition waitCondition = lock.newCondition();

    private JCrawler jCrawler;

    private CrawlerContext context;

    Worker(JCrawler jCrawler, CrawlerContext context) {
        this.jCrawler = jCrawler;
        this.context = context;
    }

    @Override
    public void run() {
        ContextHolder.setContext(this.context);
        Scheduler scheduler = context.getScheduler();
        Pipeline pipeline = context.getPipeline();
        PageProcess pageProcess = context.getPageProcess();

        while (true) {
            Request request = scheduler.poll();
            if (request == null) {
                //等待请求
                waitRequest();

                request = scheduler.poll();
                if (request == null) {
                    LOGGER.info(" No request in scheduler,this thread will quit! ");
                    jCrawler.singleComplete();
                    break;
                }
            }
            try {
                Page page = download(request);
                if (AgamaUtils.isNotBlank(page)) {
                    Class<? extends AgamaEntity> prey = jCrawler.getPrey(request.getUrl());
                    Render render = context.getRender(prey);
                    List<AgamaEntity> entityList = render.renderToList(page, prey);

                    pageProcess.process(page, entityList);
                    pipeline.process(entityList);
                    Thread.sleep(context.getConfigure().getInterval());
                }
            } catch (Exception e) {
                LOGGER.error(" Exception stack :", e);
            }

            signalCondition();
        }
    }

    private Page download(Request request) {
        Downloader downloader = context.getDownloader();
        Page page = null;
        try {
            page = downloader.download(request);
        } catch (Exception e) {
            try {
                LOGGER.error(" Download the page of [{}] error ," +
                        " exception stack :", request.getUrl(), e);
                AtomicInteger retriedTime = retryMap.computeIfAbsent(
                        request.getUrl(),
                        k -> new AtomicInteger()
                );

                int times = retriedTime.incrementAndGet();
                if (times <= context.getConfigure().getRetryTimes()) {
                    request.setPriority(999);
                    request.setIsRetryRequest(true);

                    //1分钟后重试请求
                    LOGGER.info(" Retry download the page of [{}] after 1 minute...", request.getUrl());
                    Thread.sleep(_1_MINUTE);

                    LOGGER.info(" Now trying download the page of [{}] - [{}] times.",
                            request.getUrl(), times);
                    jCrawler.addRequest(request);
                } else {
                    LOGGER.error(" Fail to download the page of [{}]!", request.getUrl());
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        }

        return page;
    }

    /**
     * 等待请求
     */
    private void waitRequest() {
        lock.lock();
        try {
            long waitTime = context.getConfigure().getWaitTime();
            waitCondition.await(waitTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 唤醒等待请求的线程
     */
    private void signalCondition() {
        lock.lock();
        try {
            waitCondition.signal();
        } finally {
            lock.unlock();
        }
    }

}
