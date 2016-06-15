package net.osc.gin.agama.scheduler;

import net.osc.gin.agama.site.Request;

public interface Scheduler {
	
	public void push(Request request);
	
	public Request poll();
}
