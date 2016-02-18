package org.pyj.vertical.JCrawler.downloader;

public interface Scheduler {
	
	public void push(Request request);
	
	public Request poll();
}
