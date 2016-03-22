package org.pyj.vertical.JCrawler.downloader;

import java.net.Proxy;

import org.pyj.vertical.JCrawler.site.Page;
import org.pyj.vertical.JCrawler.site.Request;

public interface Downloader {
	public Page download(Request req);
	
	public void setHttpProxy(Proxy p);
}
