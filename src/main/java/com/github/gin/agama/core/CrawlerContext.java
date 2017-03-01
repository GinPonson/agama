package com.github.gin.agama.core;

import com.github.gin.agama.downloader.Downloader;
import com.github.gin.agama.pipeline.Pipeline;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.scheduler.Scheduler;

/**
 * Created by GinPonson on 3/1/2017.
 */
public class CrawlerContext {

    private ThreadPool threadPool;

    private Downloader downloader;

    private PageProcess pageProcess;

    private Pipeline pipeline;

    private Scheduler scheduler;
}
