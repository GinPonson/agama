package com.github.gin.agama.core;

import com.github.gin.agama.downloader.Downloader;
import com.github.gin.agama.downloader.HttpDownloader;
import com.github.gin.agama.downloader.PhantomDownloader;
import com.github.gin.agama.exception.AgamaException;
import com.github.gin.agama.pipeline.ConsolePipeline;
import com.github.gin.agama.pipeline.Pipeline;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.scheduler.DuplicateURLScheduler;
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

public class JCrawler{
	
	private static Logger log = LoggerFactory.getLogger(JCrawler.class);

	private Status THREAD_STATUS = Status.STOPPED;

	private Scheduler scheduler = new DuplicateURLScheduler();
	
	private CrawlConfiger configer = new CrawlConfiger();
	
	private Lock urlLock = new ReentrantLock();
	
	private Condition waitCondition = urlLock.newCondition();

    private Map<String,AtomicInteger> retryMap = new ConcurrentHashMap<>();

	private ThreadPool threadPool;
	
	private Downloader downloader;
	
	private PageProcess pageProcess;
	
	private Pipeline pipeline;

    private JCrawler(PageProcess pageProcess){
        this.pageProcess = pageProcess;
    }
	
	public static JCrawler create(PageProcess pageProcess){
		return new JCrawler(pageProcess);
	}

    public JCrawler crawl(String url){
        scheduler.push(new Request(url));
        return this;
    }
	
	public JCrawler persistBy(Pipeline pipeline){
		this.pipeline = pipeline;
		return this;
	}
	
	public JCrawler configBy(CrawlConfiger config) {
		this.configer = config;
		return this;
	}

	public JCrawler downloadBy(Downloader downloader){
		this.downloader = downloader;
		return this;
	}

    public void setDownloader(Downloader downloader){
        this.downloader = downloader;
    }

	public void setPipeline(Pipeline pipeline){
		this.pipeline = pipeline;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setPageProcess(PageProcess pageProcess) {
		this.pageProcess = pageProcess;
	}

	public void setConfiger(CrawlConfiger configer) {
		this.configer = configer;
	}

	public CrawlConfiger getConfiger() {
		return configer;
	}

	public int getThreadStatus(){
		return THREAD_STATUS.getValue();
	}

	public void run() {
		checkIfStarted();

		initComponent();

		THREAD_STATUS = Status.STARTED;

		while(Status.SHUTDOWN != THREAD_STATUS && !threadPool.isShutdown() ){
			Request request = scheduler.poll();
			if(request == null){
				if(threadPool.getThreadAlive() == 0){
					break;
				}
				watiURL();
			} else {
				final Request finRequest = request;
				threadPool.execute( () -> {
					process(finRequest);
					signalCondition();
				}) ;

			}
		}

		shutdown();
	}

    private void checkIfStarted() {
        if(THREAD_STATUS == Status.STOPPED){
            THREAD_STATUS = Status.PREPARED;
            if(log.isDebugEnabled())
                log.debug("thread prepared .....");
        }
        if(THREAD_STATUS == Status.STARTED){
            log.error("thread started !!!");
            throw new AgamaException("thread started !!!");
        }
    }

    private void initComponent() {
        if(downloader == null){
			downloader = configer.isUseAjax() ? new PhantomDownloader() : new HttpDownloader();
        }

        if(pipeline == null){
            pipeline = new ConsolePipeline();
        }

        if(threadPool == null){
			threadPool = new ThreadPool(configer.getThreadNum());
        }

        if(!configer.getStartRequests().isEmpty()){
			configer.getStartRequests().forEach(request -> scheduler.push(request));
        }

        THREAD_STATUS = Status.PREPARED;

    }
	
	private void watiURL() {
		urlLock.lock();
		try {
			if(threadPool.getThreadAlive() != 0){
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
	
	private void  process(Request request) {
        try{
            Page page = downloader.download(request);

            if(AgamaUtils.isNotBlank(page.getRawText())){
                pageProcess.process(page);

                addScheuleRequest(page.getRequests());

                pipeline.process(page.getResultItems().getItems());
            }

			sleep(configer.getInterval());
        }catch (Exception e){
			e.printStackTrace();
            AtomicInteger retriedTime = retryMap.get(request.getUrl());

            if(retriedTime == null){
                retriedTime = new AtomicInteger();
                retryMap.put(request.getUrl(),retriedTime);
            }

            int time = retriedTime.incrementAndGet();
            if(time <= configer.getRetryTime()){
                log.info("爬取网页出错,正在尝试第" + time + "次重连...");

				request.setPriority(999);
				addRetryRequest(request);
				sleep(1000 * 60);
            } else {
				log.error("错误原因:" + e.getMessage());
            }
        }
	}

	private void sleep(int time){
		try {
			if(log.isDebugEnabled()){
				log.debug("Thread:" + Thread.currentThread().getName() + " is sleeping");
			}

			Thread.sleep(time);

			if(log.isDebugEnabled()){
				log.debug("Thread:" + Thread.currentThread().getName() + " finished sleepping");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void addScheuleRequest(List<Request> requests) {
		if(AgamaUtils.isNotEmpty(requests)){
			requests.forEach(request -> scheduler.push(request));
		}
	}

	private void addRetryRequest(Request request){
		scheduler.add(request);
	}
	
	private void shutdown() {
		threadPool.shutdown();
		THREAD_STATUS = Status.STOPPED;
	}
	
    private enum Status{
		STOPPED(0),PREPARED(1),STARTED(2),SHUTDOWN(3);
		
		int statusNum;

		Status(int statusNum){
			this.statusNum = statusNum;
		}
		
		public int getValue(){
			return statusNum;
		}
		
	}

		
}
