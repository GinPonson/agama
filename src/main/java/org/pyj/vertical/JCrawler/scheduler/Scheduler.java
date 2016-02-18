package org.pyj.vertical.JCrawler.scheduler;

import org.pyj.vertical.JCrawler.downloader.Request;

public interface Scheduler {
	
	public void push(Request request);
	
	public Request poll();
}
