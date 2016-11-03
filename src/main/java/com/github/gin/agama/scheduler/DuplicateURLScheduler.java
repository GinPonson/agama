package com.github.gin.agama.scheduler;

import com.github.gin.agama.site.Request;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class DuplicateURLScheduler implements Scheduler{

	private Set<String> urls = setFromMap(new ConcurrentHashMap<String, Boolean>());
	
	public static <E>Set<E> setFromMap(Map<E,Boolean> map){
		return Collections.newSetFromMap(map);
	}

    private BlockingQueue<Request> requestQueue = new PriorityBlockingQueue(11, new Comparator<Request>() {

        @Override
        public int compare(Request o1, Request o2) {
			if(o1.getPriority() < o2.getPriority())	return 1;
			else if (o1.getPriority() > o2.getPriority()) return -1;
			else return 0;
        }
    });

	@Override
	public void add(Request request) {
		requestQueue.add(request);
	}

    @Override
	public void push(Request request) {
		if(!isDuplicate(request.getUrl()))
			requestQueue.add(request);
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
