package org.pyj.vertical.JCrawler.downloader;

import java.net.Proxy;

import org.pyj.vertical.JCrawler.site.Page;
import org.pyj.vertical.JCrawler.site.Request;

public interface Downloader {
	public abstract Page download(Request req);
	
	public abstract void setHttpProxy(Proxy p);
}
