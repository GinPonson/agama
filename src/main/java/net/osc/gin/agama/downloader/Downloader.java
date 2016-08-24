package net.osc.gin.agama.downloader;

import java.net.Proxy;

import net.osc.gin.agama.site.Page;
import net.osc.gin.agama.site.Request;

public interface Downloader {
	Page download(Request req);
	
	void setHttpProxy(Proxy p);
}
