package com.github.gin.agama.scheduler;

import com.github.gin.agama.site.Request;
import com.github.gin.agama.util.AgamaUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class DuplicateURLScheduler implements Scheduler{

	private Set<String> urls = setFromMap(new ConcurrentHashMap<String, Boolean>());

	private static <E>Set<E> setFromMap(Map<E,Boolean> map){
		return Collections.newSetFromMap(map);
	}

	private BlockingQueue<Request> requestQueue = new PriorityBlockingQueue(11,((o1, o2) -> {
		Request r1 = (Request) o1;
		Request r2 = (Request) o1;
		if(r1.getPriority() < r2.getPriority())	return 1;
		else if (r1.getPriority() > r2.getPriority()) return -1;
		else return 0;
	}));

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
		return AgamaUtils.isBlank(url) || !urls.add(url);
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
