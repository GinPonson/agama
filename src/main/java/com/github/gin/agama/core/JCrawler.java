package com.github.gin.agama.core;

import com.github.gin.agama.Closeable;
import com.github.gin.agama.entity.AgamaEntity;
import com.github.gin.agama.exception.AgamaException;
import com.github.gin.agama.site.Request;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class JCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JCrawler.class);

    private Status THREAD_STATUS = Status.STOPPED;

    private Set<Request> startRequests = new HashSet<>();

    private CountDownLatch cdl;

    private Class<? extends AgamaEntity> prey;

    private List<Worker> workers;

    private CrawlerContext context;

    public static JCrawler create() {
        return new JCrawler();
    }

    public JCrawler prey(Class<? extends AgamaEntity> prey) {
        this.prey = prey;
        return this;
    }

    public JCrawler crawl(Request... requests) {
        Collections.addAll(startRequests, requests);
        return this;
    }

    public JCrawler crawl(String... urls) {
        for (String url : urls) {
            startRequests.add(new Request(url));
        }
        return this;
    }

    public JCrawler context(CrawlerContext context) {
        this.context = context;
        return this;
    }

    public void run() {
        Assert.assertNotNull("Prey could not be null !",prey);
        if (!this.startRequests.isEmpty()) {
            this.startRequests.forEach(request -> context.getScheduler().push(request));
        }

        this.cdl = new CountDownLatch(context.getConfigure().getThreadNum());

        workers = new ArrayList<>(context.getConfigure().getThreadNum());
        for (int i = 0; i < context.getConfigure().getThreadNum(); i++) {
            Worker worker = new Worker(this, context);
            workers.add(worker);
            Thread thread = new Thread(worker, "CrawlWorker" + i);
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
        context.getCloseables().forEach(Closeable::close);
    }

    private void checkIfStarted() {
        if (THREAD_STATUS == Status.STOPPED) {
            THREAD_STATUS = Status.PREPARED;
        }
        if (THREAD_STATUS == Status.STARTED) {
            throw new AgamaException("thread started !!!");
        }
    }

    public void singleComplete() {
        this.cdl.countDown();
    }

    public void addRetryRequest(Request request) {
        context.getScheduler().push(request);
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
