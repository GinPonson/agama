package org.pyj.vertical.JCrawler.downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.pyj.vertical.JCrawler.processer.PageProcess;
import org.pyj.vertical.JCrawler.scheduler.DuplicateURLScheduler;
import org.pyj.vertical.JCrawler.scheduler.Scheduler;
import org.pyj.vertical.JCrawler.sorter.ConsoleDataStorer;
import org.pyj.vertical.JCrawler.sorter.DataStorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JCrawler{
	
	//private static Logger log = LoggerFactory.getLogger(JCrawler.class);

	private static Status THREAD_STATUS = Status.STOP;

	private int threadNum = 1;
	
	private Scheduler scheduler = new DuplicateURLScheduler();
	
	private List<Request> startRequests = new ArrayList<Request>();
	
	private Lock urlLock = new ReentrantLock();
	
	private Condition waitCondition = urlLock.newCondition();
	
	private ExecutorService executor;
	
	private Downloader downloader;
	
	private PageProcess pageProcess;
	
	private DataStorer dataStorer;
	
	private long waitTime = 3000;
	
	
	public static JCrawler create(PageProcess pageProcess){
		return new JCrawler(pageProcess);
	}
	
	public JCrawler(PageProcess pageProcess){
		this.pageProcess = pageProcess;
	}
	
	public JCrawler addRequest(String url){
		startRequests.add(new Request(url));
		return this;
	}
	
	public void thread(int num){
		this.threadNum = num;
	}
	
	public void use(DataStorer dataStorer){
		this.dataStorer = dataStorer;
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
			//log.info("thread prepared .....");
		}
		if(THREAD_STATUS == Status.STARTED){
			//log.info("thread started !!!");
			throw new RuntimeException("thread started !!!");
		}
	}	

	private void initComponent() {
		if(downloader == null)
			downloader = new HttpDownloader();
		
		if(dataStorer == null)
			dataStorer = new ConsoleDataStorer();
		
		if(executor == null)
			executor = Executors.newFixedThreadPool(threadNum);
		
		if(!startRequests.isEmpty()){
			for(Request request : startRequests)
				scheduler.push(request);
		} else {
			//log.warn("request is empty!!");
		}
		
		THREAD_STATUS = Status.PREPARED;
			
	}

	private void watiURL() {
		urlLock.lock();
		try {
			waitCondition.await(waitTime, TimeUnit.MILLISECONDS);
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

	public void setStartRequests(List<Request> startRequests) {
		this.startRequests = startRequests;
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
