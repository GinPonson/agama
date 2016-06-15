package net.osc.gin.agama.downloader;

import java.net.Proxy;

import net.osc.gin.agama.site.Page;
import net.osc.gin.agama.site.Request;

public interface Downloader {
	public abstract Page download(Request req);
	
	public abstract void setHttpProxy(Proxy p);
}
