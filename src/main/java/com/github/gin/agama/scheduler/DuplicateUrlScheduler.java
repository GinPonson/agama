package com.github.gin.agama.scheduler;

import com.github.gin.agama.site.Request;
import com.github.gin.agama.util.AgamaUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author  GinPonson
 */
public class DuplicateUrlScheduler implements Scheduler{

	private Set<String> urls = setFromMap(new ConcurrentHashMap<String, Boolean>());

	private static <E>Set<E> setFromMap(Map<E,Boolean> map){
		return Collections.newSetFromMap(map);
	}

    private Scheduler scheduler;

    public DuplicateUrlScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    private boolean isDuplicate(String url) {
        return !urls.add(url);
    }

    @Override
	public void push(Request request) {
		if(!isDuplicate(request.getUrl()) || request.isRetryRequest()){
            scheduler.push(request);
        }
	}

	@Override
	public Request poll() {
		return scheduler.poll();
	}

	@Override
	public int left() {
		return scheduler.left();
	}
	/*
	public int getLeftRequestSize(){
		return requestQueue.size();
	}

	public int getTolalRequestSize(){
		return urls.size();
	}*/

}
