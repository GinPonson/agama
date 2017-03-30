package com.github.gin.agama.core;

import com.github.gin.agama.AgamaException;
import com.github.gin.agama.Closeable;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.site.entity.AgamaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author  GinPonson
 */
public class JCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JCrawler.class);

    private Set<Request> startRequests = new HashSet<>();

    private Class<? extends AgamaEntity> prey;

    private CountDownLatch cdl;

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
        if(prey != null) {
            throw new AgamaException("Prey could not be null !");
        }
        if (context == null) {
            context = CrawlerContext.create().build();
        }

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
            LOGGER.error("error msg:",e);
        }
        context.getCloseableList().forEach(Closeable::close);
    }

    void singleComplete() {
        this.cdl.countDown();
    }

    public void addRequest(Request request) {
        context.getScheduler().push(request);
    }

    public Class<? extends AgamaEntity> getPrey() {
        return prey;
    }

}
