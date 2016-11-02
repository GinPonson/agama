package com.github.gin.agama.scheduler;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.github.gin.agama.site.Request;

public class DuplicateURLScheduler implements Scheduler{

	private Set<String> urls = setFromMap(new ConcurrentHashMap<String, Boolean>());
	
	private BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<Request>();

    private Lock lock = new ReentrantLock();

    private Condition waitCondition = lock.newCondition();
			
	public static <E>Set<E> setFromMap(Map<E,Boolean> map){
		return Collections.newSetFromMap(map);
	}


    private BlockingQueue<Request> blockingQueue = new PriorityBlockingQueue(11, new Comparator<Request>() {

        @Override
        public int compare(Request o1, Request o2) {
            return 0;
        }
    });

    @Override
	public void push(Request request) {
        lock.lock();
        try {
            if(!isDuplicate(request.getUrl()))
                requestQueue.add(request);
        } finally {
            lock.unlock();
        }
	}

	private boolean isDuplicate(String url) {
		if(url == null || "".equals(url))
			return true;
		return !urls.add(url);
	}

	@Override
	public Request poll() {
		return requestQueue.poll();
	}
	
	public int getLeftRequestSize(){
		return requestQueue.size();
	}

	public int getTolalRequestSize(){
		return urls.size();
	}



}
