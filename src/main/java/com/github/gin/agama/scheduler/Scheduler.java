package com.github.gin.agama.scheduler;

import com.github.gin.agama.site.Request;

public interface Scheduler {
	
	public void push(Request request);
	
	public Request poll();
}
