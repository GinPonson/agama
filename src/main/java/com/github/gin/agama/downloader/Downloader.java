package com.github.gin.agama.downloader;

import java.net.Proxy;

import com.github.gin.agama.site.Request;
import com.github.gin.agama.site.Page;

public interface Downloader {
	Page download(Request req);
	
	void setHttpProxy(Proxy p);
}
