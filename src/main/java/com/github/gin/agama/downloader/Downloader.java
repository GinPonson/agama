package com.github.gin.agama.downloader;

import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;

public interface Downloader {
	Page download(Request req);
}
