package com.github.gin.agama.core;

import com.github.gin.agama.site.entity.AgamaEntity;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.site.render.Render;
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

/**
 * @author  GinPonson
 */
public class Worker implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    private static final long _1_MINUTE = 1000 * 60;

    private Map<String, AtomicInteger> retryMap = new ConcurrentHashMap<>();

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private JCrawler jCrawler;

    private CrawlerContext context;

    Worker(JCrawler jCrawler, CrawlerContext context) {
        this.jCrawler = jCrawler;
        this.context = context;
    }

    @Override
    public void run() {
        ContextHolder.setContext(this.context);
        while (true) {
            Request request = context.getScheduler().poll();

            if (request == null) {
                waitRequest();

                request = context.getScheduler().poll();
                if (request == null) {
                    jCrawler.singleComplete();
                    break;
                }
            }
            try {
                Page page = context.getDownloader().download(request);

                if (AgamaUtils.isNotBlank(page.getRawText())) {
                    Render render = context.getRenderMap().get(jCrawler.getPrey().getSuperclass());
                    List<AgamaEntity> entityList = render.renderToList(page, jCrawler.getPrey());

                    for(AgamaEntity entity : entityList){
                        //pageProcess.process(page);
                        context.getPipeline().process(entity);
                    }
                }

                interval();
            } catch (Exception e) {
                e.printStackTrace();
                AtomicInteger retriedTime = retryMap.computeIfAbsent(
                        request.getUrl(),
                        k -> new AtomicInteger()
                );

                int time = retriedTime.incrementAndGet();
                if (time <= context.getConfigure().getRetryTime()) {
                    LOGGER.info("Crawling the page error,now trying reconnect [{}] times,", time);

                    request.setPriority(999);
                    request.setIsRetryRequest(true);
                    jCrawler.addRequest(request);

                    lineUp(_1_MINUTE);
                } else {
                    LOGGER.error("Error reason : {} ", e.getMessage());
                    e.printStackTrace();
                }
            }

            signalCondition();

        }
    }

    private void waitRequest() {
        lock.lock();
        try {
            long waitTime = context.getConfigure().getWaitTime();
            condition.await(waitTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void interval() {
        try {
            long interval = context.getConfigure().getInterval();
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void lineUp(long time) {
        try {
            Thread.sleep(time);
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
