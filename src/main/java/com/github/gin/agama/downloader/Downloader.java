package com.github.gin.agama.downloader;

import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;

import java.io.IOException;

/**
 * @author  GinPonson
 */
public interface Downloader {
	Page download(Request req) throws IOException;
}
