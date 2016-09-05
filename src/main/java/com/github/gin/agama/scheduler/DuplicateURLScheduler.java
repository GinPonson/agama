package com.github.gin.agama.scheduler;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.gin.agama.site.Request;

public class DuplicateURLScheduler implements Scheduler{

	private Set<String> urls = setFromMap(new ConcurrentHashMap<String, Boolean>());
	
	private BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<Request>();
			
	public static <E>Set<E> setFromMap(Map<E,Boolean> map){
		return Collections.newSetFromMap(map);
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
	
	public static void main(String[] args) {
		DuplicateURLScheduler a  = new DuplicateURLScheduler();
		a.push(new Request("aa"));
		System.out.println(a.poll());
	}
}
