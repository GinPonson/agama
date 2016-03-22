package org.pyj.vertical.JCrawler.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPool {
	
	private int threadNum;
	
	private AtomicInteger threadAlive = new AtomicInteger();
	
	private Lock lock = new ReentrantLock();
	
	private Condition condition = lock.newCondition();
	
	private ExecutorService executor;
	
	public ThreadPool(int threadNum){
		this.threadNum = threadNum;
		executor = Executors.newFixedThreadPool(threadNum);
	}
	
	public void execute(final Runnable runnable){
		if(getThreadAlive() >= threadNum){
			lock.lock();
			try {
				while(getThreadAlive() >= threadNum){
					condition.await();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		threadAlive.incrementAndGet();
		
		executor.execute(new Runnable(){
			@Override
			public void run() {
				try{
					runnable.run();
				} finally{
					lock.lock();
					condition.signal();
					threadAlive.decrementAndGet();
					lock.unlock();
				}
				
			}
			
		});
	}
	
	
	public int getThreadAlive(){
		return threadAlive.get();
	}
	
	public boolean isShutdown(){
		return executor.isShutdown();
	}
	
	public void shutdown(){
		executor.shutdown();
	}
}
