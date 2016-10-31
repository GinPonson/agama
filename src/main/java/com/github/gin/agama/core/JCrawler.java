package com.github.gin.agama.core;

import com.github.gin.agama.downloader.Downloader;
import com.github.gin.agama.downloader.HttpDownloader;
import com.github.gin.agama.downloader.PhantomDownloader;
import com.github.gin.agama.exception.AgamaException;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.scheduler.DuplicateURLScheduler;
import com.github.gin.agama.scheduler.Scheduler;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.sorter.ConsoleDataStorer;
import com.github.gin.agama.sorter.DataStorer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
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

	private ThreadPool threadPool;
	
	private Downloader downloader;
	
	private PageProcess pageProcess;
	
	private DataStorer dataStorer;

	private int retryTime;

    public JCrawler(PageProcess pageProcess){
        this.pageProcess = pageProcess;
    }
	
	public static JCrawler create(PageProcess pageProcess){
		return new JCrawler(pageProcess);
	}

    public JCrawler crawl(String url){
        scheduler.push(new Request(url));
        return this;
    }
	
	public JCrawler persistBy(DataStorer dataStorer){
		this.dataStorer = dataStorer;
		return this;
	}
	
	public JCrawler setConfig(CrawlConfiger config) {
		this.configer = config;
		this.retryTime = config.getRetryTime();
		return this;
	}

    public JCrawler setDownloader(Downloader downloader){
        this.downloader = downloader;
        return this;
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
            if(configer.isAjaxModel()){
                downloader = new PhantomDownloader();
            } else {
                downloader = new HttpDownloader();
            }
        }

        if(dataStorer == null){
            dataStorer = new ConsoleDataStorer();
        }

        if(threadPool == null){
			threadPool = new ThreadPool(configer.getThreadNum());
        }

        if(!configer.getStartRequests().isEmpty()){
            for(Request request : configer.getStartRequests())
                scheduler.push(request);
        }

        THREAD_STATUS = Status.PREPARED;

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
				threadPool.execute(new Runnable() {					
					@Override
					public void run() {
						if(!isOutOfDepth(finRequest)){
							process(finRequest);
						}
						signalCondition();
						
					}
					
				});
			}
		}
		
		close();
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

		Page page = downloader.download(request);
		
		if(StringUtils.isNotBlank(page.getRawText())){
			pageProcess.process(page);
			
			addScheuleRequest(page.getRequests(),autoIncrement(request.getCurDepth()));
			
			dataStorer.store(page.getResultItems().getItems());
		}
			
		try {
			if(log.isDebugEnabled()){
				log.debug("Thread:"+Thread.currentThread().getName()+" is sleeping");
			}
			
			Thread.sleep(configer.getSleepTime());
			
			if(log.isDebugEnabled()){
				log.debug("Thread:"+Thread.currentThread().getName()+" finished sleepping");
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

    private int autoIncrement(int curDepth){
        AtomicInteger atomicInteger = new AtomicInteger(curDepth);
        return atomicInteger.incrementAndGet();
    }

	private void addScheuleRequest(List<Request> requests,int depth) {
		if(!requests.isEmpty()){
			for(Request request : requests){
                request.setCurDepth(depth);
				scheduler.push(request);
			}
		}
	}

    private boolean isOutOfDepth(Request request){
        if(configer.getDepth() == -1){
            return false;
        } else if(configer.getDepth() >= request.getCurDepth()){
            return false;
        } else {
            return true;
        }
    }
	
	private void close() {
		threadPool.shutdown();
	}
	
	private void resetRetryTime(){
		this.retryTime = configer.getRetryTime();
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setPageProcess(PageProcess pageProcess) {
		this.pageProcess = pageProcess;
	}

    private enum Status{
		STOPPED(0),PREPARED(1),STARTED(2),SHUTDOWN(3);
		
		int statusNum;
		
		private Status(){}
		
		private Status(int statusNum){
			this.statusNum = statusNum;
		}
		
		public int getValue(){
			return statusNum;
		}
		
	}

		
}
