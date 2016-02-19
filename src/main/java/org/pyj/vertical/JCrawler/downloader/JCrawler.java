package org.pyj.vertical.JCrawler.downloader;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.DebugGraphics;

import org.apache.commons.lang3.StringUtils;
import org.pyj.vertical.JCrawler.processer.PageProcess;
import org.pyj.vertical.JCrawler.scheduler.DuplicateURLScheduler;
import org.pyj.vertical.JCrawler.scheduler.Scheduler;
import org.pyj.vertical.JCrawler.sorter.ConsoleDataStorer;
import org.pyj.vertical.JCrawler.sorter.DataStorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JCrawler{
	
	private static Logger log = LoggerFactory.getLogger(JCrawler.class);

	private static Status THREAD_STATUS = Status.STOP;

	private Scheduler scheduler = new DuplicateURLScheduler();
	
	private CrawlConfiger configer = new CrawlConfiger();
	
	private Lock urlLock = new ReentrantLock();
	
	private Condition waitCondition = urlLock.newCondition();
	
	private ExecutorService executor;
	
	private Downloader downloader;
	
	private PageProcess pageProcess;
	
	private DataStorer dataStorer;
	
	
	public static JCrawler create(PageProcess pageProcess){
		return new JCrawler(pageProcess);
	}
	
	public JCrawler(PageProcess pageProcess){
		this.pageProcess = pageProcess;
	}
	
	public JCrawler persistBy(DataStorer dataStorer){
		this.dataStorer = dataStorer;
		return this;
	}
	
	public JCrawler setConfig(CrawlConfiger config) {
		this.configer = config;
		return this;
	}
	
	public void run() {
		checkIfStarted();
		
		initComponent();	
		
		THREAD_STATUS = Status.STARTED;
		
		while(Status.SHUTDOWN != THREAD_STATUS || !executor.isShutdown()){
			Request request = scheduler.poll();
			if(request == null){
				watiURL();
			} else {
				final Request finRequest = request;
				executor.execute(new Runnable() {					
					@Override
					public void run() {
						process(finRequest);			
						signalCondition();
						
					}
					
				});
			}
		}
		
	}
	
	private void checkIfStarted() {
		if(THREAD_STATUS == Status.STOP){
			THREAD_STATUS = Status.PREPARED;
			if(log.isDebugEnabled())
				log.debug("thread prepared .....");
		}
		if(THREAD_STATUS == Status.STARTED){
			log.error("thread started !!!");
			throw new RuntimeException("thread started !!!");
		}
	}	

	private void initComponent() {
		if(downloader == null){
			downloader = new HttpDownloader();
		}
		
		if(configer.getProxy() != null){
			downloader.setHttpProxy(configer.getProxy());
		}
		
		if(dataStorer == null){
			dataStorer = new ConsoleDataStorer();
		}
		
		if(executor == null){
			executor = Executors.newFixedThreadPool(configer.getThreadNum());
		}
		
		if(!configer.getStartRequests().isEmpty()){
			for(Request request : configer.getStartRequests())
				scheduler.push(request);
		} else {
			log.warn("there is no request!!");
		}
		
		THREAD_STATUS = Status.PREPARED;
			
	}

	private void watiURL() {
		urlLock.lock();
		try {
			waitCondition.await(configer.getSleepTime(), TimeUnit.MILLISECONDS);
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
		
		Page page = downloader.download(request);
		
		if(StringUtils.isNotBlank(page.getText()))
			pageProcess.process(page);
		
		addScheuleRequest(page.getRequests());
		
		dataStorer.process(page.getFields());
		
	}

	private void addScheuleRequest(List<String> requests) {
		if(!requests.isEmpty())
			for(String request : requests){
				scheduler.push(new Request(request));
			}
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public void setPageProcess(PageProcess pageProcess) {
		this.pageProcess = pageProcess;
	}

	public void setDownloader(Downloader downloader){
		this.downloader = downloader;
	}


	private enum Status{
		STOP(0),PREPARED(1),STARTED(2),SHUTDOWN(3);
		
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
