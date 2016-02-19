package org.pyj.vertical.JCrawler.downloader;

import java.net.Proxy;

public interface Downloader {
	public Page download(Request req);
	
	public void setHttpProxy(Proxy p);
}
