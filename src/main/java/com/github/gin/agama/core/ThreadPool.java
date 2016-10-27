package com.github.gin.agama.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class ThreadPool {
	
	private int threadNum;
	
	private AtomicInteger threadAlive = new AtomicInteger();

	private ExecutorService executor;
	
	public ThreadPool(int threadNum){
		this.threadNum = threadNum;
		executor = Executors.newFixedThreadPool(threadNum);
	}
	
	public abstract void execute(final Runnable runnable);
	
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
